package com.udacity.boogle.maps;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoogleMapsApplicationTests {

//	@Autowired
//	MockMvc mockMvc;

	@Test
	public void contextLoads() {
	}

//	@Test
//	public void testGetRequest() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders
//						.get("/maps?lat=1&lon=1")
//						.accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk());
//	}

}
