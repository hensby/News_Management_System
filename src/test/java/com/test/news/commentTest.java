package com.test.news;


import com.test.news.controller.NewsCommentController;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.orm.hibernate5.SessionHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class commentTest {

    @Autowired
    private NewsCommentController newsCommentController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(newsCommentController).build();
    }

    @Test
    public void testNews() throws Exception {

        Map<String, String> map = new HashMap<String, String>();
        map.put("good game", "1040,0");
        map.put("omg!!!! what a wonderful day", "1040,0");
        map.put("fuck it", "1040,0");
        map.put("what the hell are you doing?", "1040,0");


        Object[] l;
        l = map.keySet().toArray();
        for (int i = 0; i < map.size(); i = i + 1) {
            MvcResult mvcResult = mockMvc.perform(
                    MockMvcRequestBuilders.post("/comment/add")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .param("title", l[i].toString())
                            .param("newsId", map.get(l[i]).split(",")[0])
            ).andExpect(MockMvcResultMatchers.content().string(map.get(l[i]).split(",")[1]))
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();

            log.info(mvcResult.getResponse().getContentAsString());
        }
    }
}
