package org.lizaalert.commands;

import com.github.galimru.telegram.methods.SendMessage;
import com.github.galimru.telegram.objects.Update;
import com.github.galimru.telegram.util.TelegramUtil;
import com.google.common.collect.ImmutableMap;
import org.lizaalert.entities.Subscribe;
import org.lizaalert.entities.User;
import org.lizaalert.managers.ContextProvider;
import org.lizaalert.managers.SessionManager;
import org.lizaalert.repositories.SubscribeRepository;
import org.lizaalert.services.BotanService;

import java.util.UUID;

public class UnsubscribeCommand extends AbstractCommand {

    public UnsubscribeCommand(String chatId, SessionManager sessionManager) {
        super(chatId, sessionManager);
    }

    @Override
    public void execute(Update update) {
        String subscribeId = sessionManager.get("subscribeId");

        SubscribeRepository subscribeRepository = ContextProvider.getBean(SubscribeRepository.class);
        Subscribe subscribe = subscribeRepository.findOne(UUID.fromString(subscribeId));

        if (subscribe != null) {
            subscribeRepository.delete(subscribe);
            call(fromTemplate("message", SendMessage.class, ImmutableMap.of(
                    "chat_id", chatId,
                    "text", "Подписка успешно отменена"
            )));
            BotanService botanService = ContextProvider.getBean(BotanService.class);
            User user = sessionManager.getSession().getUser();
            botanService.track(user, "unsubscribe", ImmutableMap.of(
                    "forum", subscribe.getForum().getName(),
                    "message", TelegramUtil.getMessage(update))
            );
        } else {
            call(fromTemplate("message", SendMessage.class, ImmutableMap.of(
                    "chat_id", chatId,
                    "text", "Подписка с таким названием не найдена"
            )));
        }
    }

}
