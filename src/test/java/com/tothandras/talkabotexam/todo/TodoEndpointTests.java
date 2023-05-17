package com.tothandras.talkabotexam.todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tothandras.talkabotexam.todo.controller.TodoRestController;
import com.tothandras.talkabotexam.todo.model.Priority;
import com.tothandras.talkabotexam.todo.model.TodoDTO;
import com.tothandras.talkabotexam.todo.services.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoRestController.class)
class TodoEndpointTests {
private static final String CREATION_ENDPOINT = "/api/v1/create";

private static final String DELETION_ENDPOINT = "/api/v1/delete";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private TodoService todoService;

	@Test
	public void testCreationEndpointResponseStatus() throws Exception {
		final TodoDTO todoDto = new TodoDTO();
		todoDto.setName("TestItem");
		todoDto.setPriority(Priority.MEDIUM);

		String requestBody = objectMapper.writeValueAsString(todoDto);

		mockMvc.perform(post(CREATION_ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
				.andExpect(status().isCreated());
	}

	@Test
	public void testCreationEndpointResponseStatusError() throws Exception {
		final TodoDTO todoDto = new TodoDTO();
		todoDto.setPriority(Priority.MEDIUM);

		String requestBody = objectMapper.writeValueAsString(todoDto);

		mockMvc.perform(post(CREATION_ENDPOINT)
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestBody))
				.andExpect(status().isBadRequest());
	}

}
