package org.lizaalert.services;

import com.google.common.util.concurrent.RateLimiter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.lizaalert.entities.Forum;
import org.lizaalert.entities.Topic;
import org.lizaalert.repositories.ForumRepository;
import org.lizaalert.repositories.TopicRepository;
import org.lizaalert.util.ForumUtils;
import org.lizaalert.util.HTMLUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.util.List;

@Component
public class ForumService {

    private final Log log = LogFactory.getLog(ForumService.class);

    private static final long DELAY = 5 * 60 * 1000;
    private static final double REQUESTS_PER_SECOND = 2;

    @Value("${org.lizaalert.viewforum.url}")
    private String viewforumUrl;

    @Value("${org.lizaalert.viewtopic.url}")
    private String viewtopicUrl;

    @Autowired private ForumRepository forumRepository;
    @Autowired private TopicRepository topicRepository;
    @Autowired private QueueService queueService;

    private RateLimiter rateLimiter = RateLimiter.create(REQUESTS_PER_SECOND);

    @Scheduled(fixedDelay = DELAY, initialDelay = DELAY)
    public void run() {
        List<Forum> forums = forumRepository.findAll();

        for(Forum forum : forums) {
            rateLimiter.acquire();
            update(forum);
        }
    }

    private void update(Forum forum) {
        StopWatch sw = new StopWatch(getClass().getSimpleName() + "#update");
        if (log.isDebugEnabled()) {
            log.debug(String.format("Fetching forum with id %d", forum.getForumId()));
        }
        String url = String.format(viewforumUrl, forum.getForumId());
        sw.start();
        try {
            Document document = Jsoup.connect(url).get();
            Elements topicElements = document.select(".topiclist.topics > li:not(.global-announce)");
            for(Element topicElement : topicElements) {
                Element titleElement = topicElement.select("dl > dt > a.topictitle").first();
                String title = titleElement.text();

                Element authorElement = topicElement.select("dl > dt > span.for-desc > a ").first();
                String author = authorElement.text();

                Integer topicId = ForumUtils.getTopicId(titleElement.attr("href"));

                if (topicId != null) {
                    Topic topic = topicRepository.findByTopicId(topicId);
                    if (topic == null) {
                        topic = new Topic();
                        topic.setForum(forum);
                        topic.setTopicId(topicId);
                        topic.setAuthor(author);
                        topic.setTitle(title);
                        topic.setActive(ForumUtils.isActive(title));
                        reload(topic);
                        topicRepository.saveAndFlush(topic);
                        queueService.asyncNotify(topic);
                    } else if (!StringUtils.equals(title, topic.getTitle())) {
                        topic.setTitle(title);
                        topic.setActive(ForumUtils.isActive(title));
                        topicRepository.saveAndFlush(topic);
                        queueService.asyncNotify(topic);
                    }
                }
            }
        } catch (IOException e) {
            log.error(String.format("Error on connecting to url %s", url), e);
        }
        sw.stop();
        if (log.isDebugEnabled()) {
            log.debug(sw.shortSummary());
        }
    }

    private void reload(Topic topic) {
        rateLimiter.acquire();
        StopWatch sw = new StopWatch(getClass().getSimpleName() + "#reload");
        if (log.isDebugEnabled()) {
            log.debug(String.format("Fetching topic with id %d", topic.getTopicId()));
        }
        String url = String.format(viewtopicUrl, topic.getTopicId());
        sw.start();
        try {
            Document document = Jsoup.connect(url).get();
            Element contentElement = document.select(".post > .content").first();
            String message = HTMLUtils.transform(contentElement);
            if (message.length() > Topic.MESSAGE_LENGTH) {
                message = StringUtils.abbreviate(message, Topic.MESSAGE_LENGTH);
            }
            topic.setMessage(message);
            Element imgElement = contentElement.select("img").first();
            if (imgElement != null) {
                topic.setImageUrl(imgElement.attr("abs:src"));
            }
        } catch (IOException e) {
            log.error(String.format("Error on connecting to url %s", url), e);
        }
        sw.stop();
        if (log.isDebugEnabled()) {
            log.debug(sw.shortSummary());
        }
    }

}
