package org.lizaalert.commands;

import com.github.galimru.telegram.model.Update;
import org.lizaalert.entities.Category;
import org.lizaalert.entities.Forum;
import org.lizaalert.managers.ContextProvider;
import org.lizaalert.managers.SessionManager;
import org.lizaalert.repositories.CategoryRepository;
import org.lizaalert.repositories.ForumRepository;

import java.util.List;
import java.util.UUID;

public class ChooseForumCommand extends AbstractCommand {

    public ChooseForumCommand(SessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    public void execute(Update update) {
        String categoryId = sessionManager.get("categoryId");
        CategoryRepository categoryRepository = ContextProvider.getBean(CategoryRepository.class);
        Category category = categoryRepository.findOne(UUID.fromString(categoryId));
        ForumRepository forumRepository = ContextProvider.getBean(ForumRepository.class);
        List<Forum> forums = forumRepository.findByCategory(category);
        sendResponse("forum-list", "forums", forums);
    }

    @Override
    public boolean complete(Update update) {
        ForumRepository forumRepository = ContextProvider.getBean(ForumRepository.class);
        Forum forum;
        if (update.getCallbackQuery() != null) {
            String forumId = update.getCallbackQuery().getData();
            forum = forumRepository.findOne(UUID.fromString(forumId));
        } else {
            String text = update.getMessage().getText();
            forum = forumRepository.findByName(text);
        }
        if (forum == null) {
            sendResponse("message", "text", "Подраздел с таким названием не найден");
            return false;
        }
        sessionManager.put("forumId", forum.getId().toString());
        return true;
    }
}
