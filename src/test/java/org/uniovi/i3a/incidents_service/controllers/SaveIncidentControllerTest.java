package org.uniovi.i3a.incidents_service.controllers;

import static org.junit.Assert.*;

import org.junit.Before;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Before
    public void setUp() throws Exception {

	this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();

	session = new MockHttpSession();
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

	//service.saveIncident(incident);
	
	//Object to JSON in String
	String jsonInString = "";
	try {
	    jsonInString = new ObjectMapper().writeValueAsString(incident);
	} catch (JsonProcessingException e) {
	    e.printStackTrace();
	}
	
	MockHttpServletRequestBuilder request = post("/save").session(session)
		.contentType(MediaType.APPLICATION_JSON).content(jsonInString.getBytes());
	try {
	    MvcResult reqResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
	    incident.set_id(new ObjectId(new JSONObject(reqResult.getResponse().getContentAsString()).getString("incidentId")));
	} catch (Exception e) {
	    e.printStackTrace();
	}
	
	
	
	Incident result = service.findById(incident.get_id().toString());
	assertEquals(incident.getTitle(), result.getTitle());
	
	repository.delete(incident);
    }
}
