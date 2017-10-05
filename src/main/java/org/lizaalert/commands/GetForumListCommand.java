package org.lizaalert.commands;

import com.github.galimru.telegram.model.Update;
import org.lizaalert.entities.Category;
import org.lizaalert.entities.Forum;
import org.lizaalert.providers.ContextProvider;
import org.lizaalert.providers.SessionManager;
import org.lizaalert.repositories.CategoryRepository;
import org.lizaalert.repositories.ForumRepository;

import java.util.List;
import java.util.UUID;

public class GetForumListCommand extends AbstractCommand {

    public GetForumListCommand(SessionManager sessionManager, Update update) {
        super(sessionManager, update);
    }

    @Override
    public boolean execute() {
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
            sendResponse("message", "text", "Регион не найден");
            return false;
        }
        sessionManager.put("categoryId", category.getId().toString());
        ForumRepository forumRepository = ContextProvider.getBean(ForumRepository.class);
        List<Forum> forums = forumRepository.findByCategory(category);
        sendResponse("message-with-home", "text", "Выбери область которую ты хочешь отслеживать");
        sendResponse("forum-list", "forums", forums);
        return true;
    }

}
