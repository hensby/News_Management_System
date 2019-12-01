package com.test.news;

import com.test.news.controller.NewsController;
import com.test.news.model.User;
import com.test.news.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.google.common.primitives.Ints.asList;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class recommendationTest {
    @Autowired
    private NewsController newsController;

    @Autowired
    private UserService userService;

    private MockMvc mockMvc;
    private MockHttpSession session;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(newsController).build();

    }

    @Test
    public void recommendationTest() throws Exception {
        session = new MockHttpSession();
        User user = new User();
        user.setName("hensby");
        user.setPwd("123456");
        user = userService.findByName("hensby");
        session.setAttribute("user", user);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/news/newsUser")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();

    }

}
