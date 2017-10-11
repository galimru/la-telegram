package org.lizaalert.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.galimru.telegram.methods.SendMessage;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TemplateChecker {

    @Test
    public void checkTemplates() throws IOException {
        List<File> templates = getTemplates();
        for(File template : templates) {
            checkTemplate(template);
        }
    }

    private void checkTemplate(File file) throws IOException {
        assertTrue(file.getName().endsWith(".json"));
        String content = new String(Files.toByteArray(file), Charset.forName("UTF-8"));
        SendMessage message = convertToMessage(content);
        assertNotNull(file.getName(), message);

    }

    private SendMessage convertToMessage(String templateContent) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(templateContent, SendMessage.class);
    }

    private List<File> getTemplates() {
        File templatesDir = new File("src/main/resources/templates");
        File[] files = templatesDir.listFiles();
        if (files != null) {
            return Arrays.asList(files);
        }
        return Collections.emptyList();
    }

}
