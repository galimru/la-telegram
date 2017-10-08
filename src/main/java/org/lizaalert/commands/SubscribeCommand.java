package org.lizaalert.commands;

import com.github.galimru.telegram.model.Message;
import com.github.galimru.telegram.model.Update;
import com.google.common.collect.ImmutableMap;
import org.lizaalert.entities.Forum;
import org.lizaalert.entities.Subscribe;
import org.lizaalert.entities.User;
import org.lizaalert.managers.ContextProvider;
import org.lizaalert.managers.SessionManager;
import org.lizaalert.repositories.ForumRepository;
import org.lizaalert.repositories.SubscribeRepository;
import org.lizaalert.services.BotanService;

import java.util.UUID;

public class SubscribeCommand extends AbstractCommand {

    public SubscribeCommand(SessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    public void execute(Update update) {
        String forumId = sessionManager.get("forumId");

        ForumRepository forumRepository = ContextProvider.getBean(ForumRepository.class);
        Forum forum = forumRepository.findOne(UUID.fromString(forumId));

        User user = sessionManager.getSession().getUser();
        SubscribeRepository subscribeRepository = ContextProvider.getBean(SubscribeRepository.class);
        Subscribe subscribe = subscribeRepository.findByUserAndForum(user, forum);

        if (subscribe == null) {
            subscribe = new Subscribe();
            subscribe.setUser(user);
            subscribe.setForum(forum);
            subscribeRepository.save(subscribe);
            sendResponse("message", "text", String.format("Вы успешно подписаны на %s", forum.getName()));
            BotanService botanService = ContextProvider.getBean(BotanService.class);
            Message message = update.getCallbackQuery() != null
                    ? update.getCallbackQuery().getMessage()
                    : update.getMessage();
            botanService.track(user, "subscribe", ImmutableMap.of(
                    "forum", forum.getName(),
                    "message", message)
            );
        } else {
            sendResponse("message", "text", String.format("Вы уже имеете подписку на %s", forum.getName()));
        }
    }
}
