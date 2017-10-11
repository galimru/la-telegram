package org.lizaalert.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.galimru.telegram.methods.AbstractMethod;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

@Service
public class TemplateService {

    private final Log log = LogFactory.getLog(TemplateService.class);

    private static final String TEMPLATE_EXTENSION = ".json";

    @Autowired private Configuration freemarkerConfiguration;

    public <T extends AbstractMethod> T fromTemplate(String templateName, Class<T> templateClass, Map<String, Object> params) {
        String templateContent = processTemplate(templateName, params);
        return transformTemplate(templateContent, templateClass);
    }

    private String processTemplate(String templateId, Map<String, Object> params) {
            String templateName = templateId + TEMPLATE_EXTENSION;
            StringWriter writer = new StringWriter();
            try {
                freemarkerConfiguration.getTemplate(templateName)
                        .process(params, writer);
            } catch (IOException | TemplateException e) {
                log.error(String.format("Error on processing template with name %s", templateName), e);
            }
            return writer.toString();
    }

    private <T extends AbstractMethod> T transformTemplate(String templateContent, Class<T> templateClass) {
        ObjectMapper mapper = new ObjectMapper();
        T message = null;
        try {
            message = mapper.readValue(templateContent, templateClass);
        } catch (IOException e) {
            log.error("Error on transforming template to class", e);
        }
        return message;
    }
}
