package com.test.news;

import com.test.news.controller.NewsController;
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
public class NewsTests {
    @Autowired
    private NewsController newsController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(newsController).build();
    }

    @Test
    public void testNews() throws Exception {
        Map<String, String> map = new HashMap<String,String>();
        map.put("INVESTIGATING HUMAN INFERTILITY VIA THE WATER FLEA","127,1,TUESDAY OCT 15 2019 • DANA JENNINGS : UT ARLINGTON MEDIA RELATIONS,<p><br></p>,true");
        map.put("INVESTIGATING HUMAN INFERTILITY VIA THE WATER FLEA1","127,2,TUESDAY OCT 15 2019 • DANA JENNINGS : UT ARLINGTON MEDIA RELATIONS,<p><br></p>,true");
        map.put("INVESTIGATING HUMAN INFERTILITY VIA THE WATER FLEA2","127,2,TUESDAY OCT 15 2019 • DANA JENNINGS : UT ARLINGTON MEDIA RELATIONS,<p><br></p>,true");
        map.put("INVESTIGATING HUMAN INFERTILITY VIA THE WATER FLEA3","127,1,test,<p><br></p>,true");
        //edit news
        map.put("test",",1,test,<p>test</p>,true");             //add news

        Object[] l;
        l = map.keySet().toArray();
        for(int i = 0;i < map.size(); i = i+1) {
            MvcResult mvcResult = mockMvc.perform(
                    MockMvcRequestBuilders.post("/news/save")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .param("title", l[i].toString())
                            .param("id", map.get(l[i]).split(",")[0])
                            .param("typeid",map.get(l[i]).split(",")[1])
                            .param("author",map.get(l[i]).split(",")[2])
                            .param("content",map.get(l[i]).split(",")[3])
            ).andExpect(MockMvcResultMatchers.content().string(map.get(l[i]).split(",")[4]))
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();

            log.info(mvcResult.getResponse().getContentAsString());
        }
    }
}
