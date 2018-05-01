package org.uniovi.i3a.incidents_service.controllers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.uniovi.i3a.incidents_service.Service;
import org.uniovi.i3a.incidents_service.repositories.IncidentsRepository;
import org.uniovi.i3a.incidents_service.services.IncidentsService;
import org.uniovi.i3a.incidents_service.types.Comment;
import org.uniovi.i3a.incidents_service.types.Incident;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.bson.types.ObjectId;
import org.json.JSONObject;

import TestKit.IntegrationTest;

@SpringBootTest(classes = { Service.class })
@RunWith(SpringJUnit4ClassRunner.class)
@Category(IntegrationTest.class)
public class SaveIncidentControllerTest {

    @Autowired
    private WebApplicationContext context;
    private MockHttpSession session;
    private MockMvc mockMvc;

    @Autowired
    private IncidentsRepository repository;

    @Autowired
    private IncidentsService service;

    private Incident incidente;
    private String indidenteID = "";

    @Before
    public void setUp() throws Exception {

	this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();

	session = new MockHttpSession();

	incidente = new Incident();
	incidente.setTitle("New incident");
	incidente.setDescription("Awesome description");
	incidente.setLocation("Somewhere");
	incidente.setStatus("OPEN");
	Comment[] comments = { new Comment() };
	incidente.setComments(comments);

	service.saveIncident(incidente);
	indidenteID = incidente.get_id().toString();

    }

    @After
    public void tearDown() {
	repository.delete(incidente);
    }

    @Test
    public void saveNewIncidentTest() {
	Incident incident = new Incident();
	incident.setTitle("New incident by willy");
	incident.setDescription("This is an awesome description");
	incident.setLocation("10N10W");
	incident.setStatus("OPEN");
	Comment[] comments = { new Comment() };
	incident.setComments(comments);

	// service.saveIncident(incident);

	// Object to JSON in String
	String jsonInString = "";
	try {
	    jsonInString = new ObjectMapper().writeValueAsString(incident);
	} catch (JsonProcessingException e) {
	    e.printStackTrace();
	}

	MockHttpServletRequestBuilder request = post("/save").session(session).contentType(MediaType.APPLICATION_JSON)
		.content(jsonInString.getBytes());
	try {
	    MvcResult reqResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
	    incident.set_id(
		    new ObjectId(new JSONObject(reqResult.getResponse().getContentAsString()).getString("incidentId")));
	} catch (Exception e) {
	    e.printStackTrace();
	    fail();
	}

	Incident result = service.findById(incident.get_id().toString());
	assertEquals(incident.getTitle(), result.getTitle());

	repository.delete(incident);
    }

    @Test
    public void updateIncidentTest() {
	try {
	    // Getting the original incident object from the controller.
	    MockHttpServletRequestBuilder request = post("/incidents/" + indidenteID).session(session).contentType(MediaType.APPLICATION_JSON);
	    MvcResult reqResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
	    Incident result = new ObjectMapper().readValue(reqResult.getResponse().getContentAsString(), Incident.class);

	    // Checking that the object we've find is the correct one.
	    System.out.println(reqResult.getResponse().getContentAsString());
	    System.out.println(" original _id: " + incidente.get_id().toString() + "; db incidentId: " + result.incidentId);
	    assertEquals(incidente.get_id().toString(), result.incidentId);
	    assertEquals(incidente.getIncidentId(), result.incidentId);
	    
	    // Checking they have the same title.
	    assertEquals(incidente.getTitle(), result.getTitle());
	    incidente.setTitle("NEW TITLE");
	    
	    // Checking they have different titles.
	    assertNotEquals(incidente.getTitle(), result.getTitle());
	    
	    
	    // Calling the save/update controller.
	    request = post("/save").session(session).contentType(MediaType.APPLICATION_JSON)
			.content(new ObjectMapper().writeValueAsString(incidente).toString());
	    reqResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
	    
	    
	    // Updating the local object in memory
	    request = post("/incidents/" + indidenteID).session(session).contentType(MediaType.APPLICATION_JSON);
	    reqResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
	    Incident resultAfterInsert = new ObjectMapper().readValue(reqResult.getResponse().getContentAsString(), Incident.class);
	    
	    // Checking that the object we've find is the correct one.
	    assertEquals(incidente.get_id().toString(), resultAfterInsert.incidentId);
	    assertEquals(incidente.getIncidentId(), resultAfterInsert.incidentId);
	    
	    // Checking they have the same title.
	    assertEquals(incidente.getTitle(), resultAfterInsert.getTitle());
	    assertEquals(incidente.getTitle(), "NEW TITLE");
	    
	    // Checking they have different titles.
	    assertNotEquals(incidente.getTitle(), result.getTitle());
	    
	    
	} catch (Exception e) {
	    e.printStackTrace();
	    fail();
	}
    }
}
