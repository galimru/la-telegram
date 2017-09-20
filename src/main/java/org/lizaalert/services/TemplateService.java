package org.lizaalert.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.galimru.telegram.actions.SendMessage;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.UUID;

@Service
public class TemplateService {

    private final Log log = LogFactory.getLog(TemplateService.class);

    private static final String TEMPLATE_EXTENSION = ".json";

    @Autowired private Configuration freemarkerConfiguration;

    public SendMessage createMessage(UUID templateId, Map<String, Object> params) {
        String templateContent = processTemplate(templateId, params);
        return transformMessage(templateContent);
    }

    private String processTemplate(UUID templateId, Map<String, Object> params) {
            String templateName = templateId + TEMPLATE_EXTENSION;
            StringWriter writer = new StringWriter();
            try {
                freemarkerConfiguration.getTemplate(templateName).process(params, writer);
            } catch (IOException | TemplateException e) {
                log.error(String.format("Error on processing template with name %s", templateName), e);
            }
            return writer.toString();
    }

    private SendMessage transformMessage(String templateContent) {
        ObjectMapper mapper = new ObjectMapper();
        SendMessage message = null;
        try {
            message = mapper.readValue(templateContent, SendMessage.class);
        } catch (IOException e) {
            log.error("Error on converting template to message", e);
        }
        return message;
    }
}
