package com.test.news;

import com.test.news.controller.system.SystemController;
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
import java.util.List;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SystemTests {

    @Autowired
    private SystemController systemController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(systemController).build();
    }

    @Test
    public void testManagerLogin() throws Exception {
        Map<String, String> map = new HashMap<String,String>();
        map.put("hensby", "123,false");map.put("asd", "123,false");map.put("admin", "admin,true");
        map.put(" ", "admin,false");map.put("admin", " ,false");
        Object[] l;
        l = map.keySet().toArray();
        for(int i = 0;i < map.size(); i = i+1) {
            MvcResult mvcResult = mockMvc.perform(
                    MockMvcRequestBuilders.post("/system/login")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .param("name", l[i].toString())
                            .param("pwd", map.get(l[i]).split(",")[0])
            ).andExpect(MockMvcResultMatchers.content().string(map.get(l[i]).split(",")[1]))
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();

            log.info(mvcResult.getResponse().getContentAsString());
        }
    }
}