package com.bankingproject.controllers.test;

import static org.junit.Assert.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bankingproject.controllers.config.TestUtils;
import com.bankingprojet.controllers.ClientController;
import com.bankingprojet.entities.Agence;
import com.bankingprojet.entities.Compte;
import com.bankingprojet.entities.Recharge;
import com.bankingprojet.entities.User;
import com.bankingprojet.entities.Virement;
import com.bankingprojet.services.AgenceRepository;
import com.bankingprojet.services.CompteRepository;
import com.bankingprojet.services.RechargeRepository;
import com.bankingprojet.services.UserRepository;
import com.bankingprojet.services.VirementRepository;
import com.google.gson.reflect.TypeToken;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ClientController.class, secure=false)
@ContextConfiguration(classes = {ClientController.class})
public class ClientControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	UserRepository userRepository;
	
	@MockBean
	VirementRepository virementRepository;
	
	@MockBean
	RechargeRepository rechargeRepository;
	
	@MockBean
	CompteRepository compteRepository;

	@MockBean
	AgenceRepository agenceRepository;

	private List<Recharge> buildRecharges() {
		Recharge r1 = new Recharge(1, "0637759884", 1, 210, "08/01/19 17:29:28");
		Recharge r2 = new Recharge(2, "0653288749", 1, 1020, "08/01/19 17:29:28");
		List<Recharge> rechList = Arrays.asList(r1, r2);
		return rechList;
	}
	
	private List<Virement> buildVirements() {
		Virement v1 = new Virement(1, "Virement 1", 3, 1, "Compte courant", 200, "08/01/19 17:29:28");
		Virement v2 = new Virement(2, "test", 2, 1, "Compte courant", 200, "08/01/19 17:29:28");
		Virement v3 = new Virement(3, "testt", 2, 1, "Compte courant", 10, "08/01/19 17:29:28");
		List<Virement> vireList = Arrays.asList(v1, v2, v3);
		return vireList;
	}
	
	private List<Compte> buildComptes() {
		Compte c1 = new Compte(1, 1, "Compte courant", "31/12/2018", 199278, false, 1);
		Compte c2 = new Compte(2, 1, "Compte d'intérêts", "31/12/2018", 2210, true, 2);
		List<Compte> comList = Arrays.asList(c1, c2);
		return comList;
	}
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testPostVirement() throws Exception{
		User verseur = new User(1, "najlaa", "najlaa", "najlaa", 22, "0699532707", "casa", 200);
		when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(verseur));
		
		Compte c1 = new Compte(1, 1, "Compte courant", "31/12/2018", 199278, true, 1);
		when(compteRepository.findByIduserAndType(any(Long.class), any(String.class))).thenReturn(c1);
		
		Compte c2 = new Compte(2, 1, "Compte d'intérêts", "31/12/2018", 2210, true, 2);
		when(compteRepository.findById(any(Long.class))).thenReturn(Optional.of(c2));
		
		String URL = "http://localhost:8080/api/banking/addvirement/";
		// prepare data and mock's behaviour
		Virement vireStub = new Virement("Virement Test Creation", 2, verseur.getId(), "Compte courant", 200, "08/01/19 17:29:28");
		when(virementRepository.save(any(Virement.class))).thenReturn(vireStub);

		// execute
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URL  + "{username}", new String("najlaa"))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.objectToJson(vireStub))).andReturn();
		System.out.println("testtttt" + result);

		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.CREATED.value(), status);

		// verify that service method was called once
		verify(virementRepository).save(any(Virement.class));

		Virement resultVirement = TestUtils.jsonToObject(result.getResponse().getContentAsString(), Virement.class);
		assertNotNull(resultVirement);
		assertEquals("Virement Test Creation", resultVirement.getMotif());
	}

	@Test
	public void testPostRecharge() throws Exception{
		User verseur = new User(1, "najlaa", "najlaa", "najlaa", 22, "0699532707", "casa", 200);
		when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(verseur));
		
		User benef = new User(3, "benef", "benef", "benef", 24, "0637759885", "casa", 100);
		when(userRepository.findByPhone(any(String.class))).thenReturn(Optional.of(benef));
		
		Compte c1 = new Compte(1, 1, "Compte courant", "31/12/2018", 199278, true, 1);
		when(compteRepository.findByIduserAndType(any(Long.class), any(String.class))).thenReturn(c1);
		
		
		String URL = "http://localhost:8080/api/banking/addrecharge/";
		// prepare data and mock's behaviour
		Recharge rechStub = new Recharge(benef.getPhone(), verseur.getId(), 40, "31/12/2018");
		when(rechargeRepository.save(any(Recharge.class))).thenReturn(rechStub);

		// execute
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URL  + "{username}", new String("najlaa"))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.objectToJson(rechStub))).andReturn();
		System.out.println("testtttt" + result);

		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.CREATED.value(), status);

		// verify that service method was called once
		verify(rechargeRepository).save(any(Recharge.class));

		Recharge resultRecharge = TestUtils.jsonToObject(result.getResponse().getContentAsString(), Recharge.class);
		assertNotNull(resultRecharge);
		assertEquals("0637759885", resultRecharge.getPhone());
	}

	@Test
	public void testGetComptes() throws Exception{
		String URLTemp = "http://localhost:8080/api/banking/getclient/";
		User user = new User(1, "najlaa", "najlaa", "najlaa", 22, "0699532707", "casa", 200);
		when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));
		
		MvcResult resultTemp = mockMvc
				.perform(MockMvcRequestBuilders.get(URLTemp + "{username}", new String("najlaa"))
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();
		
		// verify
		int statusTemp = resultTemp.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), statusTemp);

		// verify that service method was called once
		verify(userRepository).findByUsername(any(String.class));

		User resultUser = TestUtils.jsonToObject(resultTemp.getResponse()
												.getContentAsString(), User.class);
		assertNotNull(resultUser);
		assertEquals("najlaa", resultUser.getUsername().toString());
		
		// prepare data and mock's behaviour
		List<Compte> comList = buildComptes();
		when(compteRepository.findByIduser(any(Long.class))).thenReturn(comList);

		String URL = "http://localhost:8080/api/banking/comptesuser/";
		
		// execute
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL + "{username}", new String(resultUser.getUsername()))
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();

		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

		// verify that service method was called once
		verify(compteRepository).findByIduser(any(Long.class));

		// get the List<Employee> from the Json response
		TypeToken<List<Compte>> token = new TypeToken<List<Compte>>() {	};
		
		@SuppressWarnings("unchecked")
		List<Compte> comListResult = TestUtils.jsonToList(result.getResponse().getContentAsString(), token);

		assertNotNull("Comptes not found", comListResult);
		assertEquals("Incorrect Compte List", comList.size(), comListResult.size());
	}

	@Test
	public void testGetAllVirements() throws Exception{
		String URLTemp = "http://localhost:8080/api/banking/getclient/";
		User user = new User(1, "najlaa", "najlaa", "najlaa", 22, "0699532707", "casa", 200);
		when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));
		
		MvcResult resultTemp = mockMvc
				.perform(MockMvcRequestBuilders.get(URLTemp + "{username}", new String("najlaa"))
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();
		
		// verify
		int statusTemp = resultTemp.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), statusTemp);

		// verify that service method was called once
		verify(userRepository).findByUsername(any(String.class));

		User resultUser = TestUtils.jsonToObject(resultTemp.getResponse()
												.getContentAsString(), User.class);
		assertNotNull(resultUser);
		assertEquals("najlaa", resultUser.getUsername().toString());
		
		// prepare data and mock's behaviour
		List<Virement> vireList = buildVirements();
		when(virementRepository.findByIdverseur(any(Long.class))).thenReturn(vireList);

		String URL = "http://localhost:8080/api/banking/allvirements/";
		
		// execute
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL + "{username}", new String(resultUser.getUsername()))
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();

		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

		// verify that service method was called once
		verify(virementRepository).findByIdverseur(any(Long.class));

		// get the List<Employee> from the Json response
		TypeToken<List<Virement>> token = new TypeToken<List<Virement>>() {	};
		
		@SuppressWarnings("unchecked")
		List<Recharge> vireListResult = TestUtils.jsonToList(result.getResponse().getContentAsString(), token);

		assertNotNull("Virements not found", vireListResult);
		assertEquals("Incorrect Virement List", vireList.size(), vireListResult.size());
	}

	
	@Test
	public void testGetAllRecharges() throws Exception{
		String URLTemp = "http://localhost:8080/api/banking/getclient/";
		User user = new User(1, "najlaa", "najlaa", "najlaa", 22, "0699532707", "casa", 200);
		when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));
		
		MvcResult resultTemp = mockMvc
				.perform(MockMvcRequestBuilders.get(URLTemp + "{username}", new String("najlaa"))
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();
		
		// verify
		int statusTemp = resultTemp.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), statusTemp);

		// verify that service method was called once
		verify(userRepository).findByUsername(any(String.class));

		User resultUser = TestUtils.jsonToObject(resultTemp.getResponse()
												.getContentAsString(), User.class);
		assertNotNull(resultUser);
		assertEquals("najlaa", resultUser.getUsername().toString());
		
		// prepare data and mock's behaviour
		List<Recharge> rechList = buildRecharges();
		when(rechargeRepository.findByIduser(any(Long.class))).thenReturn(rechList);

		String URL = "http://localhost:8080/api/banking/allrecharges/";
		
		// execute
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL + "{username}", new String(resultUser.getUsername()))
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();

		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

		// verify that service method was called once
		verify(rechargeRepository).findByIduser(any(Long.class));

		// get the List<Employee> from the Json response
		TypeToken<List<Recharge>> token = new TypeToken<List<Recharge>>() {	};
		
		@SuppressWarnings("unchecked")
		List<Recharge> rechListResult = TestUtils.jsonToList(result.getResponse().getContentAsString(), token);

		assertNotNull("Recharges not found", rechListResult);
		assertEquals("Incorrect Recharge List", rechList.size(), rechListResult.size());
	}

	
	
	@Test
	public void testGetClient() throws Exception{
		String URL = "http://localhost:8080/api/banking/getclient/";
		User user = new User(9, "amr", "amr", "amr", 25, "0699532887", "casa",
				250);
		when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(URL + "{username}", new String("amr"))
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
		assertEquals("amr", resultUser.getUsername().toString());
	}

	@Test
	public void testGetCompte() throws Exception{
		String URL = "http://localhost:8080/api/banking/getCompteById/";
		Compte compte = new Compte(1, 1, "Compte courant", "31/12/2018", 194020, false, 1);
		when(compteRepository.findById(any(Long.class))).thenReturn(Optional.of(compte));
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(URL + "{idcompte}", new Long(1))
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();
		
		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

		// verify that service method was called once
		verify(compteRepository).findById(any(Long.class));

		Compte resultCompte = TestUtils.jsonToObject(result.getResponse()
												.getContentAsString(), Compte.class);
		assertNotNull(resultCompte);
		assertEquals(1, resultCompte.getIdcompte());
	}

	@Test
	public void testGetCompteByIdandType() throws Exception{
		String URLTemp = "http://localhost:8080/api/banking/getclient/";
		User user = new User(9, "amr", "amr", "amr", 25, "0699532887", "casa",
				250);
		when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));
		
		MvcResult resultTemp = mockMvc
				.perform(MockMvcRequestBuilders.get(URLTemp + "{username}", new String("amr"))
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();
		
		// verify
		int statusTemp = resultTemp.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), statusTemp);

		// verify that service method was called once
		verify(userRepository).findByUsername(any(String.class));

		User resultUser = TestUtils.jsonToObject(resultTemp.getResponse()
												.getContentAsString(), User.class);
		assertNotNull(resultUser);
		assertEquals("amr", resultUser.getUsername().toString());
		
		
		String URL = "http://localhost:8080/api/banking/getcomptebyidandtype/";
		Compte compte = new Compte(4, 9, "Compte courant", "31/12/2018", 200000, true, 1);
		when(compteRepository.findByIduserAndType(any(Long.class), any(String.class))).thenReturn(compte);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(URL + "{username}" + "/" + "{type}", new String(resultUser.getUsername()), new String("Compte courant"))
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();
		
		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

		// verify that service method was called once
		verify(compteRepository).findByIduserAndType(any(Long.class), any(String.class));

		Compte resultCompte = TestUtils.jsonToObject(result.getResponse()
												.getContentAsString(), Compte.class);
		assertNotNull(resultCompte);
		assertEquals(4, resultCompte.getIdcompte());
	}

	@Test
	public void testGetUserByPhone() throws Exception{
		String URL = "http://localhost:8080/api/banking/getuserbyphone/";
		User user = new User(9, "amr", "amr", "amr", 25, "0699532887", "casa",
				250);
		when(userRepository.findByPhone(any(String.class))).thenReturn(Optional.of(user));
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(URL + "{phone}", new String("0699532887"))
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();
		
		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

		// verify that service method was called once
		verify(userRepository).findByPhone(any(String.class));

		User resultUser = TestUtils.jsonToObject(result.getResponse()
												.getContentAsString(), User.class);
		assertNotNull(resultUser);
		assertEquals("0699532887", resultUser.getPhone().toString());
	}

	@Test
	public void testGetAgence() throws Exception{
		String URL = "http://localhost:8080/api/banking/getagence/";
		Agence agence = new Agence(1, "Oulfa", "Casa, Oulfa");
		when(agenceRepository.findById(any(Long.class))).thenReturn(Optional.of(agence));
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(URL + "{idagence}", new Long(1))
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();
		
		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

		// verify that service method was called once
		verify(agenceRepository).findById(any(Long.class));

		Agence resultAgence = TestUtils.jsonToObject(result.getResponse()
												.getContentAsString(), Agence.class);
		assertNotNull(resultAgence);
		assertEquals(1, resultAgence.getIdagence());
	}

}
