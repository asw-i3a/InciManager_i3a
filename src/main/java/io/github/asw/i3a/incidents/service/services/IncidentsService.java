package io.github.asw.i3a.incidents.service.services;

import java.util.List;

import io.github.asw.i3a.incidents.service.types.Incident;

public interface IncidentsService {

	void saveIncident( Incident incident );

	List<Incident> findAll();

	Incident findById( String id );

	List<Incident> findByOperatorId( String id );

	List<Incident> findByAgentId( String id );

	List<Incident> findByStatus( String status );
}
