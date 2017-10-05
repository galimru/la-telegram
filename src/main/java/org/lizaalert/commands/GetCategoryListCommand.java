package org.lizaalert.commands;

import com.github.galimru.telegram.model.Update;
import org.lizaalert.entities.*;
import org.lizaalert.providers.ContextProvider;
import org.lizaalert.providers.SessionManager;
import org.lizaalert.repositories.CategoryRepository;

import java.util.*;

public class GetCategoryListCommand extends AbstractCommand {

    public GetCategoryListCommand(SessionManager sessionManager, Update update) {
        super(sessionManager, update);
    }

    @Override
    public boolean execute() {
        CategoryRepository categoryRepository = ContextProvider.getBean(CategoryRepository.class);
        List<Category> categories = categoryRepository.findAll();
        sendResponse("message-with-home", "text", "Выбери регион который ты хочешь отслеживать");
        sendResponse("category-list", "categories", categories);
        return true;
    }

}
