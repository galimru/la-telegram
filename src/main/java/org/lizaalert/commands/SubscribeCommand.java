package org.lizaalert.commands;

import com.github.galimru.telegram.model.Update;
import org.lizaalert.entities.Forum;
import org.lizaalert.entities.User;
import org.lizaalert.providers.ContextProvider;
import org.lizaalert.providers.SessionManager;
import org.lizaalert.repositories.ForumRepository;
import org.lizaalert.repositories.UserRepository;

import java.util.UUID;

public class SubscribeCommand extends AbstractCommand {

    public SubscribeCommand(SessionManager sessionManager, Update update) {
        super(sessionManager, update);
    }

    @Override
    public boolean execute() {
        ForumRepository forumRepository = ContextProvider.getBean(ForumRepository.class);
        Forum forum;
        if (update.getCallbackQuery() != null) {
            String forumId = update.getCallbackQuery().getData();
            forum = forumRepository.findOne(UUID.fromString(forumId));
        } else {
            String text = update.getMessage().getText();
            forum = forumRepository.findByName(text);
        }
        if (forum == null) {
            sendResponse("message", "text", "Область не найдена");
            return false;
        }
        sessionManager.put("forumId", forum.getId().toString());
        User user = sessionManager.getSession().getUser();
        UserRepository userRepository = ContextProvider.getBean(UserRepository.class);
        user = userRepository.findById(user.getId());
        user.getForums().add(forum);
        userRepository.save(user);
        sendResponse("message", "text", String.format("Отлично! Ты успешно подписан на форум %s", forum.getName()));
        sendResponse("message-with-home", "text", "Если кому-то потребуется помощь, я уведомлю тебя");
        return true;
    }
}
