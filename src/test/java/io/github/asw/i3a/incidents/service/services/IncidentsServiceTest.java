package io.github.asw.i3a.incidents.service.services;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import TestKit.IntegrationTest;
import io.github.asw.i3a.incidents.service.Service;
import io.github.asw.i3a.incidents.service.repositories.IncidentsRepository;
import io.github.asw.i3a.incidents.service.services.IncidentsService;
import io.github.asw.i3a.incidents.service.types.Comment;
import io.github.asw.i3a.incidents.service.types.Incident;

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
		i1.setTitle( "New incident by willy" );
		i1.setDescription( "This is an awesome description" );
		i1.setLocation( "10N10W" );
		i1.setStatus( "OPEN" );
		Comment[] comments = { new Comment() };
		i1.setComments( comments );

		service.saveIncident( i1 );

		Incident result = service.findById( i1.get_id().toString() );
		assertEquals( i1.get_id().toString(), result.get_id().toString() );
	}

	@After
	public void tearDown() {
		repository.delete( i1 );
	}

	@Test
	public void insertAndDeleteNewIncidentTest() {
		Incident incident = new Incident();
		incident.setTitle( "New incident by willy" );
		incident.setDescription( "This is an awesome description" );
		incident.setLocation( "10N10W" );
		incident.setStatus( "OPEN" );
		Comment[] comments = { new Comment() };
		incident.setComments( comments );

		service.saveIncident( incident );

		Incident result = service.findById( incident.get_id().toString() );

		assertEquals( incident.get_id().toString(), result.get_id().toString() );
		repository.delete( result );
		assertNull( service.findById( incident.get_id().toString() ) );
	}

	@Test
	public void updateAnExistingIncidentTest() {
		Incident result = service.findById( i1.get_id().toString() );
		assertEquals( i1.getDescription(), result.getDescription() );
		assertNotEquals( "New description", result.getDescription() );
		result.setDescription( "New description" );
		result.incidentId = result.getIncidentId();
		service.saveIncident( result );
		result = service.findById( i1.get_id().toString() );
		assertEquals( "New description", result.getDescription() );
	}
	
	@Test
	public void findAllTest() {
		assertNotNull( service.findAll() );
	}
	
	@Test
	public void findByOperatorIdTest() {
		assertNotNull( service.findByOperatorId( "" ) );
	}
	
	@Test
	public void findByStatuTest() {
		assertNotNull( service.findByStatus( "OPEN" ) );
	}
	
	@Test
	public void findByAgentIdTest() {
		assertNotNull( service.findByAgentId( "" ) );
	}

}
