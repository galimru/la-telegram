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
            subscribeRepository.delete(subscribe);
            sendResponse("message", "text", "Подписка успешно отменена");
            BotanService botanService = ContextProvider.getBean(BotanService.class);
            User user = sessionManager.getSession().getUser();
            botanService.track(user, "unsubscribe", ImmutableMap.of(
                    "forum", subscribe.getForum().getName(),
                    "message", TelegramUtil.getMessage(update))
            );
        } else {
            sendResponse("message", "text", "Подписка с таким названием не найдена");
        }
    }

}
