package org.lizaalert.commands;

import com.github.galimru.telegram.methods.SendMessage;
import com.github.galimru.telegram.objects.KeyboardButton;
import com.github.galimru.telegram.objects.ReplyKeyboardMarkup;
import com.github.galimru.telegram.objects.Update;
import org.apache.commons.lang.StringUtils;
import org.lizaalert.entities.Forum;
import org.lizaalert.entities.User;
import org.lizaalert.managers.AttributeManager;
import org.lizaalert.managers.ContextProvider;
import org.lizaalert.managers.SessionManager;
import org.lizaalert.repositories.ForumRepository;

import java.util.UUID;

public class SearchNowCommand extends AbstractCommand {

    public SearchNowCommand(String chatId, SessionManager sessionManager) {
        super(chatId, sessionManager);
    }

    @Override
    public void execute(Update update) {
        User user = sessionManager.getSession().getUser();
        String regionId = attributeManager.get("regionId");
        if (StringUtils.isEmpty(regionId)) {
            call(new SendMessage()
                    .setChatId(chatId)
                    .setText("Для просмотра текущих поисков выберите регион")
                    .setReplyMarkup(new ReplyKeyboardMarkup()
                            .setResizeKeyboard(true)
                            .setKeyboard(new KeyboardButton[][]{
                                    new KeyboardButton[]{new KeyboardButton().setText("Выбрать регион")},
                                    new KeyboardButton[]{new KeyboardButton().setText("В начало")}
                            })));
        } else {
            ForumRepository forumRepository = ContextProvider.getBean(ForumRepository.class);
            Forum forum = forumRepository.findOne(UUID.fromString(regionId));
            call(new SendMessage()
                    .setChatId(chatId)
                    .setText(String.format("Текущие поиски для региона %s", forum.getName()))
                    .setReplyMarkup(new ReplyKeyboardMarkup()
                            .setResizeKeyboard(true)
                            .setKeyboard(new KeyboardButton[][]{
                                    new KeyboardButton[]{new KeyboardButton().setText("Еще")},
                                    new KeyboardButton[]{new KeyboardButton().setText("Сменить регион")},
                                    new KeyboardButton[]{new KeyboardButton().setText("В начало")}
                            })));
            MoreResultsCommand moreResultsCommand = new MoreResultsCommand(sessionManager.getSession().getChatId(), sessionManager);
            moreResultsCommand.setAttributeManager(attributeManager);
            moreResultsCommand.execute(update);
        }
    }
}
