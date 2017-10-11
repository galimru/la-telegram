package org.lizaalert.commands;

import com.github.galimru.telegram.methods.AnswerCallbackQuery;
import com.github.galimru.telegram.methods.EditMessageText;
import com.github.galimru.telegram.methods.SendMessage;
import com.github.galimru.telegram.objects.CallbackQuery;
import com.github.galimru.telegram.objects.Message;
import com.github.galimru.telegram.objects.Update;
import com.github.galimru.telegram.util.TelegramUtil;
import com.google.common.collect.ImmutableMap;
import org.lizaalert.entities.*;
import org.lizaalert.managers.ContextProvider;
import org.lizaalert.managers.SessionManager;
import org.lizaalert.repositories.CategoryRepository;

import java.util.*;

public class ChooseCategoryCommand extends AbstractCommand {

    public ChooseCategoryCommand(String chatId, SessionManager sessionManager) {
        super(chatId, sessionManager);
    }

    @Override
    public void execute(Update update) {
        CategoryRepository categoryRepository = ContextProvider.getBean(CategoryRepository.class);
        List<Category> categories = categoryRepository.findAll();
        call(fromTemplate("message-with-home", SendMessage.class, ImmutableMap.of(
                "chat_id", chatId,
                "text", "После оформления подписки вы будете получать уведомления о новых темах поиска на форуме"
        )));
        call(fromTemplate("category-list", SendMessage.class, ImmutableMap.of(
                "chat_id", chatId,
                "categories", categories
        )));
    }

    @Override
    public boolean complete(Update update) {
        CategoryRepository categoryRepository = ContextProvider.getBean(CategoryRepository.class);
        Optional<String> callbackData = TelegramUtil.getCallbackData(update);
        if (callbackData.isPresent()) {
            String categoryId = callbackData.get();
            Category category = categoryRepository.findOne(UUID.fromString(categoryId));
            if (category != null) {
                sessionManager.put("categoryId", category.getId().toString());
                call(new AnswerCallbackQuery()
                        .setCallbackQueryId(update.getCallbackQuery().getId()));
                TelegramUtil.getCallbackQuery(update)
                        .map(CallbackQuery::getMessage)
                        .map(Message::getMessageId)
                        .ifPresent(id -> call(new EditMessageText()
                                .setMessageId(id)
                                .setText(category.getName()))
                        );
                return true;
            }
            call(new AnswerCallbackQuery()
                    .setCallbackQueryId(update.getCallbackQuery().getId())
                    .setShowAlert(true)
                    .setText("Раздел с таким названием не найден"));
        }
        return false;
    }
}
