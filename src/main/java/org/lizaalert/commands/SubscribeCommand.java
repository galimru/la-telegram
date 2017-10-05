package org.lizaalert.commands;

import com.github.galimru.telegram.model.Update;
import org.lizaalert.entities.Forum;
import org.lizaalert.entities.Session;
import org.lizaalert.entities.User;
import org.lizaalert.providers.ContextProvider;
import org.lizaalert.repositories.ForumRepository;
import org.lizaalert.repositories.UserRepository;

public class SubscribeCommand extends AbstractCommand {

    public SubscribeCommand(Session session, Update update) {
        super(session, update);
    }

    @Override
    public boolean execute() {
        String text = update.getMessage().getText();
        ForumRepository forumRepository = ContextProvider.getBean(ForumRepository.class);
        Forum forum = forumRepository.findByName(text);
        if (forum == null) {
            sendResponse("message", "text", String.format("Форум %s не найден", text));
            return false;
        }
        User user = getSession().getUser();
        UserRepository userRepository = ContextProvider.getBean(UserRepository.class);
        user = userRepository.findById(user.getId());
        user.getForums().add(forum);
        userRepository.save(user);
        sendResponse("message", "text", String.format("Отлично! Ты успешно подписан на форум %s", forum.getName()));
        sendResponse("message-with-home", "text", "Если кому-то потребуется помощь, я уведомлю тебя");
        return true;
    }
}
