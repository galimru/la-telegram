package org.lizaalert.commands;

import org.lizaalert.entities.Forum;
import org.lizaalert.entities.State;
import org.lizaalert.entities.User;
import org.lizaalert.providers.ContextProvider;
import org.lizaalert.repositories.ForumRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GetRegionsCommand extends AbstractCommand {

    public GetRegionsCommand(User user, State state, String text) {
        super(user, state, text);
    }

    @Override
    public void execute() {
        ForumRepository forumRepository = ContextProvider.getBean(ForumRepository.class);
        List<Forum> forums = forumRepository.findAllEnabled();
        Map<String, Object> params = new HashMap<>();
        params.put("forums", forums);
        sendTemplate(UUID.fromString("2c3b41e4-90cd-49ce-8221-60adec996376"), params);
    }

}
