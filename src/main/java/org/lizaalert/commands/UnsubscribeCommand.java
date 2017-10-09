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
import util.TelegramUtil;

import java.util.Optional;
import java.util.UUID;

public class UnsubscribeCommand extends AbstractCommand {

    public UnsubscribeCommand(SessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    public void execute(Update update) {
        String subscribeId = sessionManager.get("subscribeId");

        SubscribeRepository subscribeRepository = ContextProvider.getBean(SubscribeRepository.class);
        Subscribe subscribe = subscribeRepository.findOne(UUID.fromString(subscribeId));

        if (subscribe != null) {
            sendResponse("confirm-message", "text", String.format("Вы действительно хотите отменить подписку на %s", subscribe.getForum().getName()));
        } else {
            sendResponse("message", "text", "Подписка с таким названием не найдена");
        }
    }

    @Override
    public boolean complete(Update update) {
        String text = TelegramUtil.getText(update);
        if ("Да".equals(text)) {
            sendResponse("message-with-home", "text", "Подписка успешно отменена");
            BotanService botanService = ContextProvider.getBean(BotanService.class);
            User user = sessionManager.getSession().getUser();
            String subscribeId = sessionManager.get("subscribeId");
            SubscribeRepository subscribeRepository = ContextProvider.getBean(SubscribeRepository.class);
            Subscribe subscribe = subscribeRepository.findOne(UUID.fromString(subscribeId));
            botanService.track(user, "unsubscribe", ImmutableMap.of(
                    "forum", subscribe.getForum().getName(),
                    "message", TelegramUtil.getMessage(update))
            );
            return true;
        } else if ("Нет".equals(text)) {
            sendResponse("message-with-home", "text", "Отмена не подтверждена");
            return true;
        }
        return false;
    }
}
