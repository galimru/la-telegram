package org.lizaalert.commands;

import com.github.galimru.telegram.methods.SendMessage;
import com.github.galimru.telegram.objects.ParseMode;
import com.github.galimru.telegram.objects.Update;
import org.apache.commons.lang.StringUtils;
import org.lizaalert.entities.Forum;
import org.lizaalert.entities.Topic;
import org.lizaalert.managers.ContextProvider;
import org.lizaalert.managers.SessionManager;
import org.lizaalert.repositories.ForumRepository;
import org.lizaalert.repositories.TopicRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public class MoreResultsCommand extends AbstractCommand {

    private static final int PAGE_SIZE = 5;

    public MoreResultsCommand(String chatId, SessionManager sessionManager) {
        super(chatId, sessionManager);
    }

    @Override
    public void execute(Update update) {
        String regionId = attributeManager.get("regionId");
        if (StringUtils.isEmpty(regionId)) {
            return;
        }
        ForumRepository forumRepository = ContextProvider.getBean(ForumRepository.class);
        Forum forum = forumRepository.findOne(UUID.fromString(regionId));
        TopicRepository topicRepository = ContextProvider.getBean(TopicRepository.class);
        String pageIndex = sessionManager.get("pageIndex");
        int page = 0;
        if (!StringUtils.isEmpty(pageIndex)) {
            page = Integer.valueOf(pageIndex);
        }
        Page<Topic> topics = topicRepository.findByForumAndActiveIsTrueOrderByCreatedAt(forum, new PageRequest(page, PAGE_SIZE));
        page++;
        for(Topic topic : topics) {
            call(new SendMessage()
                    .setChatId(chatId)
                    .setDisableNotification(true)
                    .setText(topic.getMessage())
                    .setParseMode(ParseMode.HTML)
            );
        }
        if (topics.getNumberOfElements() < PAGE_SIZE) {
            page = 0;
        }
        sessionManager.put("pageIndex", page);
    }
}
