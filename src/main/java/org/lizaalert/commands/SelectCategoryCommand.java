package org.lizaalert.commands;

import com.github.galimru.telegram.methods.AnswerCallbackQuery;
import com.github.galimru.telegram.methods.EditMessageText;
import com.github.galimru.telegram.methods.SendMessage;
import com.github.galimru.telegram.objects.*;
import com.github.galimru.telegram.util.TelegramUtil;
import com.google.common.collect.ImmutableMap;
import org.lizaalert.entities.Category;
import org.lizaalert.managers.ContextProvider;
import org.lizaalert.managers.SessionManager;
import org.lizaalert.repositories.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SelectCategoryCommand extends AbstractCommand {

    public SelectCategoryCommand(String chatId, SessionManager sessionManager) {
        super(chatId, sessionManager);
    }

    @Override
    public void execute(Update update) {
        CategoryRepository categoryRepository = ContextProvider.getBean(CategoryRepository.class);
        List<Category> categories = categoryRepository.findAll();
        call(new SendMessage().setChatId(chatId)
                .setText("Выбор региона")
                .setReplyMarkup(new ReplyKeyboardMarkup()
                        .setResizeKeyboard(true).setKeyboard(new KeyboardButton[][] {
                                new KeyboardButton[]{new KeyboardButton().setText("В начало")}
                        })));
        call(fromTemplate("category-list", SendMessage.class, ImmutableMap.of(
                "chat_id", chatId,
                "categories", categories
        )));
    }

    @Override
    public boolean onReceive(Update update) {
        CategoryRepository categoryRepository = ContextProvider.getBean(CategoryRepository.class);
        Optional<String> callbackData = TelegramUtil.getCallbackData(update);
        if (callbackData.isPresent()) {
            String categoryId = callbackData.get();
            Category category = categoryRepository.findOne(UUID.fromString(categoryId));
            if (category != null) {
                sessionManager.put("categoryId", category.getId().toString());
                TelegramUtil.getCallbackQuery(update)
                        .map(CallbackQuery::getMessage)
                        .map(Message::getMessageId)
                        .ifPresent(id -> call(new EditMessageText()
                                .setChatId(chatId)
                                .setMessageId(id)
                                .setParseMode(ParseMode.MARKDOWN)
                                .setText("*Выбран округ:* " + category.getName()))
                        );
                return true;
            }
            call(new AnswerCallbackQuery()
                    .setCallbackQueryId(update.getCallbackQuery().getId())
                    .setShowAlert(true)
                    .setText("Округ с таким названием не найден"));
        }
        return false;
    }
}
