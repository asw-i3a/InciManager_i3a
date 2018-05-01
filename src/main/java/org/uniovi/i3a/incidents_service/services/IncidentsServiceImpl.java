package org.uniovi.i3a.incidents_service.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uniovi.i3a.incidents_service.repositories.IncidentsRepository;
import org.uniovi.i3a.incidents_service.types.Incident;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IncidentsServiceImpl implements IncidentsService {

    @Autowired
    private IncidentsRepository repository;

    @Override
    public void saveIncident(Incident incident) {
	// To check that we're updating an existing incident
	if(incident.incidentId!=null && incident.incidentId!="") {
	    incident.set_id(new ObjectId(incident.incidentId));
	}
	log.info("Saving incidence in repository: " + incident);
	repository.save(incident);
    }

    @Override
    public List<Incident> findAll() {
	return repository.findAll();
    }

    @Override
    public Incident findById(String id) {
	try {
	    return repository.findById(new ObjectId(id)).get();
	} catch (NoSuchElementException e) {
	    log.error(e.toString());
	    return null;
	}
    }

    @Override
    public List<Incident> findByOperatorId(String id) {
	return repository.findByOperatorId(id);
    }

    @Override
    public List<Incident> findByStatus(String status) {
	return repository.findByStatus(status);
    }

    @Override
    public List<Incident> findByAgentId(String id) {
	return repository.findByAgentId(id);
    }

}
