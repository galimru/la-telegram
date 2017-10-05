package org.lizaalert.services;

import com.github.galimru.telegram.actions.SendMessage;
import org.lizaalert.entities.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MessageService {

    @Autowired private TemplateService templateService;
    @Autowired private QueueService queueService;

    public void sendResponse(Session session, String templateId, Map<String, Object> parameters) {
        Map<String, Object> params = new HashMap<>();
        if (parameters != null) {
            params.putAll(parameters);
        }
        params.put(TemplateService.USER_ID_PARAM, String.valueOf(session.getUser().getUserId()));
        SendMessage message = templateService.createMessage(templateId, params);
        queueService.pushMessage(message);
    }

}
