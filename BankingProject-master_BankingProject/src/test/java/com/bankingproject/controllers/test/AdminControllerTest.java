package com.bankingproject.controllers.test;

import static org.junit.Assert.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bankingproject.controllers.config.TestUtils;
import com.bankingprojet.controllers.AdminController;
import com.bankingprojet.controllers.ClientController;
import com.bankingprojet.entities.Agence;
import com.bankingprojet.entities.Recharge;
import com.bankingprojet.entities.Role;
import com.bankingprojet.entities.Rolename;
import com.bankingprojet.entities.User;
import com.bankingprojet.services.AgenceRepository;
import com.bankingprojet.services.RoleRepository;
import com.bankingprojet.services.UserRepository;
import com.google.gson.reflect.TypeToken;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AdminController.class, secure=false)
@ContextConfiguration(classes = {AdminController.class})
public class AdminControllerTest {
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	AgenceRepository agenceRepository;
	
	@MockBean
	UserRepository userRepository;
	
	@MockBean
	RoleRepository roleRepository;
	
	@MockBean
	PasswordEncoder encoder;
	
	
	private List<Agence> buildAgences() {
		Agence a1 = new Agence(1, "Oulfa", "Casa, Oulfa");
		Agence a2 = new Agence(2, "test", "testt");
		List<Agence> agList = Arrays.asList(a1, a2);
		return agList;
	}
	
	private List<User> buildAgents() {
		User a1 = new User("agent1", "agent1", "agent", 20, "0635987458", "casa", 100);
		List<User> agentList = Arrays.asList(a1);
		return agentList;
	}
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAddAgence() throws Exception{
		String URL = "http://localhost:8080/api/banking/addAgence";
		Agence agStub = new Agence("Maarif", "Casa");
		when(agenceRepository.save(any(Agence.class))).thenReturn(agStub);

		// execute
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.objectToJson(agStub))).andReturn();

		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.CREATED.value(), status);

		// verify that service method was called once
		verify(agenceRepository).save(any(Agence.class));

		Agence resultAgence = TestUtils.jsonToObject(result.getResponse().getContentAsString(), Agence.class);
		assertNotNull(resultAgence);
		assertEquals("Maarif", resultAgence.getName());
	}

	@Test
	public void testAllAgences() throws Exception{
		String URL = "http://localhost:8080/api/banking/allagences";
		// prepare data and mock's behaviour
		List<Agence> agList = buildAgences();
		when(agenceRepository.findAll()).thenReturn(agList);

		// execute
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL).accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();

		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

		// verify that service method was called once
		verify(agenceRepository).findAll();

		// get the List<Employee> from the Json response
		TypeToken<List<Agence>> token = new TypeToken<List<Agence>>() {	};
		@SuppressWarnings("unchecked")
		List<Agence> agListResult = TestUtils.jsonToList(result.getResponse().getContentAsString(), token);

		assertNotNull("Agences not found", agListResult);
		assertEquals("Incorrect Agence List", agList.size(), agListResult.size());
	}

	@Test
	public void testAddAgent() throws Exception{
		User user = new User("amr", "amr", "amrlazraq", 25, "0699532707", "casa", 200);
		Role role = new Role(2, Rolename.ROLE_PM);
		when(roleRepository.findByName(any(Rolename.class))).thenReturn(Optional.of(role));
		
		String URL = "http://localhost:8080/api/banking/addAgent";
		when(userRepository.save(any(User.class))).thenReturn(user);

		// execute
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.objectToJson(user))).andReturn();

		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.CREATED.value(), status);

		// verify that service method was called once
		verify(userRepository).save(any(User.class));

		User resultUser = TestUtils.jsonToObject(result.getResponse().getContentAsString(), User.class);
		assertNotNull(resultUser);
		assertEquals("amr", resultUser.getUsername());
	}

	@Test
	public void testAllAgents() throws Exception{
		String URL = "http://localhost:8080/api/banking/allagents";
		// prepare data and mock's behaviour
		List<User> agentList = buildAgents();
		when(userRepository.findBySpecificRoles(any(Long.class))).thenReturn(agentList);

		// execute
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL).accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();

		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

		// verify that service method was called once
		verify(userRepository).findBySpecificRoles(any(Long.class));

		// get the List<Employee> from the Json response
		TypeToken<List<User>> token = new TypeToken<List<User>>() {	};
		@SuppressWarnings("unchecked")
		List<User> agentListResult = TestUtils.jsonToList(result.getResponse().getContentAsString(), token);

		assertNotNull("Agents not found", agentListResult);
		assertEquals("Incorrect Agent List", agentList.size(), agentListResult.size());
	}

	@Test
	public void testDeleteAgent() throws Exception{
		
		String URL = "http://localhost:8080/api/banking/deleteAgent/";
		User a1 = new User("agent1", "agent1", "agent", 20, "0635987458", "casa", 100);
		when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(a1));
		// execute
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(URL + "{username}", new String("agent1"))).andReturn();

		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.GONE.value(), status);

		// verify that service method was called once
		verify(userRepository).deleteByUsername(any(String.class));
	//	verify(userRepository).deleteRoleFromUsersWithRole(any(Long.class), any(Long.class));

	}

	@Test
	public void testDeleteAgence() throws Exception{
		String URL = "http://localhost:8080/api/banking/deleteAgence/";
		Agence agStub = new Agence(1, "Oulfa", "Casa, Oulfa");
	//	doNothing().when(agenceRepository).deleteByName(any(String.class));
		// execute
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(URL + "{name}", new String("Oulfa"))).andReturn();

		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.GONE.value(), status);

		// verify that service method was called once
		verify(agenceRepository).deleteByName(any(String.class));
	}

}
