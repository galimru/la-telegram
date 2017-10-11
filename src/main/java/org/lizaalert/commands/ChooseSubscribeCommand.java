package org.lizaalert.commands;

import com.github.galimru.telegram.methods.SendMessage;
import com.github.galimru.telegram.objects.Update;
import com.github.galimru.telegram.util.TelegramUtil;
import com.google.common.collect.ImmutableMap;
import org.lizaalert.entities.Category;
import org.lizaalert.entities.Forum;
import org.lizaalert.entities.Subscribe;
import org.lizaalert.managers.ContextProvider;
import org.lizaalert.managers.SessionManager;
import org.lizaalert.repositories.CategoryRepository;
import org.lizaalert.repositories.ForumRepository;
import org.lizaalert.repositories.SubscribeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ChooseSubscribeCommand extends AbstractCommand {

    public ChooseSubscribeCommand(String chatId, SessionManager sessionManager) {
        super(chatId, sessionManager);
    }

    @Override
    public void execute(Update update) {
        SubscribeRepository subscribeRepository = ContextProvider.getBean(SubscribeRepository.class);
        List<Subscribe> subscribes = subscribeRepository.findByUser(sessionManager.getSession().getUser());
        call(fromTemplate("message-with-home", SendMessage.class, ImmutableMap.of(
                "chat_id", chatId,
                "text", "Вы можете отменить офоромленную подписку, если вы больше не хотите получать уведомления"
        )));
        call(fromTemplate("subscribe-list", SendMessage.class, ImmutableMap.of(
                "chat_id", chatId,
                "subscribes", subscribes
        )));
    }

    @Override
    public boolean complete(Update update) {
        SubscribeRepository subscribeRepository = ContextProvider.getBean(SubscribeRepository.class);
        Optional<String> callbackData = TelegramUtil.getCallbackData(update);
        if (callbackData.isPresent()) {
            String forumId = callbackData.get();
            Subscribe subscribe = subscribeRepository.findOne(UUID.fromString(forumId));
            if (subscribe != null) {
                sessionManager.put("subscribeId", subscribe.getId().toString());
                return true;
            }
        }
        call(fromTemplate("message", SendMessage.class, ImmutableMap.of(
                "chat_id", chatId,
                "text", "Подписка с таким названием не найдена"
        )));
        return false;
    }
}
