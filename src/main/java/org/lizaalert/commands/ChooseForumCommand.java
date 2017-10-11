package org.lizaalert.commands;

import com.github.galimru.telegram.methods.EditMessageText;
import com.github.galimru.telegram.methods.SendMessage;
import com.github.galimru.telegram.objects.CallbackQuery;
import com.github.galimru.telegram.objects.Message;
import com.github.galimru.telegram.objects.Update;
import com.github.galimru.telegram.util.TelegramUtil;
import com.google.common.collect.ImmutableMap;
import org.lizaalert.entities.Category;
import org.lizaalert.entities.Forum;
import org.lizaalert.managers.ContextProvider;
import org.lizaalert.managers.SessionManager;
import org.lizaalert.repositories.CategoryRepository;
import org.lizaalert.repositories.ForumRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ChooseForumCommand extends AbstractCommand {

    public ChooseForumCommand(String chatId, SessionManager sessionManager) {
        super(chatId, sessionManager);
    }

    @Override
    public void execute(Update update) {
        String categoryId = sessionManager.get("categoryId");
        CategoryRepository categoryRepository = ContextProvider.getBean(CategoryRepository.class);
        Category category = categoryRepository.findOne(UUID.fromString(categoryId));
        ForumRepository forumRepository = ContextProvider.getBean(ForumRepository.class);
        List<Forum> forums = forumRepository.findByCategory(category);
        call(fromTemplate("forum-list", SendMessage.class, ImmutableMap.of(
                "chat_id", chatId,
                "forums", forums
        )));
    }

    @Override
    public boolean complete(Update update) {
        ForumRepository forumRepository = ContextProvider.getBean(ForumRepository.class);
        Optional<String> callbackData = TelegramUtil.getCallbackData(update);
        if (callbackData.isPresent()) {
            String forumId = callbackData.get();
            Forum forum = forumRepository.findOne(UUID.fromString(forumId));
            if (forum != null) {
                sessionManager.put("forumId", forum.getId().toString());
                TelegramUtil.getCallbackQuery(update)
                        .map(CallbackQuery::getMessage)
                        .map(Message::getMessageId)
                        .ifPresent(id -> call(new EditMessageText()
                                .setChatId(chatId)
                                .setMessageId(id)
                                .setText("Выбран подраздел: " + forum.getName()))
                        );
                return true;
            }
        }
        call(fromTemplate("message", SendMessage.class, ImmutableMap.of(
                "chat_id", chatId,
                "text", "Подраздел с таким названием не найден"
        )));
        return false;
    }
}
