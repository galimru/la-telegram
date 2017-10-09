package org.lizaalert.commands;

import com.github.galimru.telegram.model.Update;
import org.lizaalert.entities.Category;
import org.lizaalert.entities.Forum;
import org.lizaalert.entities.Subscribe;
import org.lizaalert.managers.ContextProvider;
import org.lizaalert.managers.SessionManager;
import org.lizaalert.repositories.CategoryRepository;
import org.lizaalert.repositories.ForumRepository;
import org.lizaalert.repositories.SubscribeRepository;

import java.util.List;
import java.util.UUID;

public class ChooseSubscribeCommand extends AbstractCommand {

    public ChooseSubscribeCommand(SessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    public void execute(Update update) {
        SubscribeRepository subscribeRepository = ContextProvider.getBean(SubscribeRepository.class);
        List<Subscribe> subscribes = subscribeRepository.findByUser(sessionManager.getSession().getUser());
        sendResponse("message-with-home", "text", "Вы можете отменить офоромленную подписку, если вы больше не хотите получать уведомления");
        sendResponse("subscribe-list", "subscribes", subscribes);
    }

    @Override
    public boolean complete(Update update) {
        SubscribeRepository subscribeRepository = ContextProvider.getBean(SubscribeRepository.class);
        Subscribe subscribe = null;
        if (update.getCallbackQuery() != null) {
            String forumId = update.getCallbackQuery().getData();
            subscribe = subscribeRepository.findOne(UUID.fromString(forumId));
        }
        if (subscribe == null) {
            sendResponse("message", "text", "Подписка с таким названием не найдена");
            return false;
        }
        sessionManager.put("subscribeId", subscribe.getId().toString());
        return true;
    }
}
