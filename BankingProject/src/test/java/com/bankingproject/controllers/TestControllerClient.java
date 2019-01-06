package com.bankingproject.controllers;

import static org.junit.Assert.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bankingproject.config.TestUtils;
import com.bankingprojet.controllers.ClientController;
import com.bankingprojet.entities.User;
import com.bankingprojet.services.UserRepository;
import com.bankingprojet.services.VirementRepository;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ClientController.class })
@AutoConfigureMockMvc
public class TestControllerClient {

	@Autowired
	private MockMvc mockMvc;
	

	@Mock
	VirementRepository virementRepository;
	

	@Mock
	UserRepository userRepository;
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testPostVirement() {
		fail("Not yet implemented");
	}

	@Test
	public void testPostRecharge() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetComptes() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllVirements() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllRecharges() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetClient() throws Exception{
		String URL = "/api/banking/getclient/";
		User user = new User("najlaa", "najlaa", "najlaachennani", 22, "0637759834", "casa",
				220);
		when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(URL + "{username}", new String("najlaa"))
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();
		
		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

		// verify that service method was called once
		verify(userRepository).findByUsername(any(String.class));

		User resultUser = TestUtils.jsonToObject(result.getResponse()
												.getContentAsString(), User.class);
		assertNotNull(resultUser);
		assertEquals("najlaa", resultUser.getUsername().toString());
	}

	@Test
	public void testGetCompte() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCompteByIdandType() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUserByPhone() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAgence() {
		fail("Not yet implemented");
	}

}
