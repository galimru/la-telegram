package org.lizaalert.commands;

import com.github.galimru.telegram.model.Update;
import org.lizaalert.entities.*;
import org.lizaalert.managers.ContextProvider;
import org.lizaalert.managers.SessionManager;
import org.lizaalert.repositories.CategoryRepository;

import java.util.*;

public class ChooseCategoryCommand extends AbstractCommand {

    public ChooseCategoryCommand(SessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    public void execute(Update update) {
        CategoryRepository categoryRepository = ContextProvider.getBean(CategoryRepository.class);
        List<Category> categories = categoryRepository.findAll();
        sendResponse("message-with-home", "text", "После оформления подписки вы будете получать уведомления о новых темах поиска на форуме");
        sendResponse("category-list", "categories", categories);
    }

    @Override
    public boolean complete(Update update) {
        CategoryRepository categoryRepository = ContextProvider.getBean(CategoryRepository.class);
        Category category;
        if (update.getCallbackQuery() != null) {
            String categoryId = update.getCallbackQuery().getData();
            category = categoryRepository.findOne(UUID.fromString(categoryId));
        } else {
            String text = update.getMessage().getText();
            category = categoryRepository.findByName(text);
        }
        if (category == null) {
            sendResponse("message", "text", "Раздел с таким названием не найден");
            return false;
        }
        sessionManager.put("categoryId", category.getId().toString());
        return true;
    }
}
