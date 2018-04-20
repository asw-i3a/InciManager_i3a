package org.uniovi.i3a.incidents_service.services;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.uniovi.i3a.incidents_service.Service;
import org.uniovi.i3a.incidents_service.repositories.IncidentsRepository;
import org.uniovi.i3a.incidents_service.services.IncidentsService;
import org.uniovi.i3a.incidents_service.types.Comment;
import org.uniovi.i3a.incidents_service.types.Incident;

import TestKit.IntegrationTest;

@SpringBootTest(classes = { Service.class })
@RunWith(SpringJUnit4ClassRunner.class)
@Category(IntegrationTest.class)
public class IncidentsServiceTest {
    
    @Autowired
    private IncidentsService service;
    
    @Autowired
    private IncidentsRepository repository;
    
    private Incident i1;
    
    @Before
    public void setUp() {
	i1 = new Incident();
	i1.setTitle("New incident by willy");
	i1.setDescription("This is an awesome description");
	i1.setLocation("10N10W");
	i1.setStatus("OPEN");
	Comment[] comments = {new Comment()};
	i1.setComments(comments);
	
	service.saveIncident(i1);
	
	Incident result = service.findById(i1.get_id().toString());
	assertEquals(i1.get_id().toString(), result.get_id().toString());
    }
    
    @After
    public void tearDown() {
	repository.delete(i1);
    }
    
    @Test
    public void insertAndDeleteNewIncidentTest() {
	Incident incident = new Incident();
	incident.setTitle("New incident by willy");
	incident.setDescription("This is an awesome description");
	incident.setLocation("10N10W");
	incident.setStatus("OPEN");
	Comment[] comments = {new Comment()};
	incident.setComments(comments);
	
	service.saveIncident(incident);
	
	Incident result = service.findById(incident.get_id().toString());
	
	assertEquals(incident.get_id().toString(), result.get_id().toString());
	repository.delete(result);
	assertNull(service.findById(incident.get_id().toString()));
    }
    
    @Test
    public void updateAnExistingIncidentTest() {
	Incident result = service.findById(i1.get_id().toString());
	assertEquals(i1.getDescription(), result.getDescription());
	assertNotEquals("New description", result.getDescription());
	result.setDescription("New description");
	service.saveIncident(result);
	result = service.findById(i1.get_id().toString());
	assertEquals("New description", result.getDescription());
    }

}
