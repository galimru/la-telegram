package org.lizaalert.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.galimru.telegram.objects.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lizaalert.controllers.TelegramController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@WebMvcTest(controllers = TelegramController.class)
public class ExampleTest {

    @Autowired private MockMvc mockMvc;

    private Update createTextUpdate(String text) {
        return new Update()
                .setMessage(new Message()
                        .setChat(new Chat().setId(300000000))
                        .setFrom(new User().setId(300000000))
                        .setText(text));
    }

    private Update createCallbackUpdate(String data) {
        return new Update()
                .setCallbackQuery(new CallbackQuery()
                        .setFrom(new User().setId(300000000))
                        .setData(data));
    }

    @Test
    public void searchNow() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(post("/api/update/123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createTextUpdate("/start"))));
    }
}
