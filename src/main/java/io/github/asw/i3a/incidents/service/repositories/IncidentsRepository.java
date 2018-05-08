package io.github.asw.i3a.incidents.service.repositories;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import io.github.asw.i3a.incidents.service.types.Incident;

public interface IncidentsRepository extends MongoRepository<Incident, ObjectId> {

	List<Incident> findByOperatorId( String id );

	List<Incident> findByAgentId( String id );

	List<Incident> findByStatus( String status );
}
