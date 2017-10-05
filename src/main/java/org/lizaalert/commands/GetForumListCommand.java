package org.lizaalert.commands;

import com.github.galimru.telegram.model.Update;
import org.lizaalert.entities.Category;
import org.lizaalert.entities.Forum;
import org.lizaalert.entities.Session;
import org.lizaalert.providers.ContextProvider;
import org.lizaalert.repositories.CategoryRepository;
import org.lizaalert.repositories.ForumRepository;

import java.util.*;

public class GetForumListCommand extends AbstractCommand {

    public GetForumListCommand(Session session, Update update) {
        super(session, update);
    }

    @Override
    public boolean execute() {
        String text = update.getMessage().getText();
        CategoryRepository categoryRepository = ContextProvider.getBean(CategoryRepository.class);
        Category category = categoryRepository.findByName(text);
        if (category == null) {
            sendResponse("message", "text", String.format("Раздел %s не найден", text));
            return false;
        }
        ForumRepository forumRepository = ContextProvider.getBean(ForumRepository.class);
        List<Forum> forums = forumRepository.findByCategory(category);
        sendResponse("message-with-home", "text", "Выбери область которую ты хочешь отслеживать");
        sendResponse("forum-list", "forums", forums);
        return true;
    }

}
