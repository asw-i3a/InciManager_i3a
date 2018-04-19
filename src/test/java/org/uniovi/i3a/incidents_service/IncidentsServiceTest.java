package org.uniovi.i3a.incidents_service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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

    @Test
    public void saveTest() {
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
    }

}
