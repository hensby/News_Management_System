package com.test.news;

import com.test.news.controller.NewsController;
import com.test.news.controller.system.UserController;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class EditUserTests {
    @Autowired
    private UserController userController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testEditUser() throws Exception {
        Map<String, String> map1 = new HashMap<String, String>();
        Map<String, String> map2 = new HashMap<String, String>();
        map1.put("77", "hensby,123456,true");
        map1.put("77", "hensby1,123456,true");
        map1.put("77", "hensby,123,true");
        map1.put("77", "hensby,,false");
        map1.put("77", ",123456,false");             //edit user
        map2.put("test", ",123456,true");
        map2.put("test", ",,false");                 //add news

        Object[] l;
        Object[] l2;
        l = map1.keySet().toArray();
        l2 = map2.keySet().toArray();
        for (int i = 0; i < map1.size(); i = i + 1) {
            MvcResult mvcResult = mockMvc.perform(
                    MockMvcRequestBuilders.post("/user/save")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .param("id", l[i].toString())
                            .param("name", map1.get(l[i]).split(",")[0])
                            .param("pwd", map1.get(l[i]).split(",")[1])
            ).andExpect(MockMvcResultMatchers.content().string(map1.get(l[i]).split(",")[2]))
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();

            log.info(mvcResult.getResponse().getContentAsString());
        }
        for (int i = 0; i < map2.size(); i = i + 1) {
            MvcResult mvcResult = mockMvc.perform(
                    MockMvcRequestBuilders.post("/user/save")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .param("name", l2[i].toString())
                            .param("id", map2.get(l2[i]).split(",")[0])
                            .param("pwd", map2.get(l2[i]).split(",")[1])
            ).andExpect(MockMvcResultMatchers.content().string(map2.get(l2[i]).split(",")[2]))
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();

            log.info(mvcResult.getResponse().getContentAsString());
        }

    }
}
