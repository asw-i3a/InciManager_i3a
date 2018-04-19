package org.uniovi.i3a.incidents_service.repositories;


import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.uniovi.i3a.incidents_service.types.Incident;


public interface IncidentsRepository extends MongoRepository<Incident, ObjectId> {

    List<Incident> findByOperatorId(String id);
    List<Incident> findByStatus(String status);
}
