package com.bankingproject.controllers;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bankingproject.config.TestUtils;
import com.bankingprojet.entities.Compte;
import com.bankingprojet.entities.User;
import com.bankingprojet.entities.Virement;
import com.bankingprojet.services.UserRepository;
import com.bankingprojet.services.VirementRepository;



public class TestClientController{
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	@MockBean
	VirementRepository virementRepository;
	
	@Autowired
	@MockBean
	UserRepository userRepository;
	
	@Before
	public void setUp() {
	}

	@Test
	public void testPostVirement() {
		fail("Not yet implemented");
        /*String uri = "/addvirement/najlaa";
		
		Virement virement = new Virement();
		virement.setIdvirement(20);
		virement.setIdbeneficiaire(2);
		virement.setIdverseur(1);
		virement.setDate("04/01/2019");
		virement.setMontant(2000);
		virement.setMotif("Test");
		virement.setTypecompte("Compte courant");
		
		Mockito.when(virementRepository.save(Mockito.any(Virement.class))).thenReturn(virement);
		
		MvcResult result = mockMvc.p MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();*/
	}

	@Test
	public void testPostRecharge() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetComptes() throws Exception{
		/*String uri = "/comptesuser/najlaa";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
			      .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
			   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Compte[] ComptesListOfUser = super.mapFromJson(content,Compte[].class);
		assertTrue(ComptesListOfUser.length > 0);*/
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

}
