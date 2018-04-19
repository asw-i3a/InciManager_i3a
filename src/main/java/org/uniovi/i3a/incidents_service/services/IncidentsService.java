package org.uniovi.i3a.incidents_service.services;

import java.util.List;

import org.uniovi.i3a.incidents_service.types.Incident;

public interface IncidentsService {

    void saveIncident(Incident incident);
    List<Incident> findAll();
    Incident findById(String id);
    List<Incident> findByOperatorId(String id);
    List<Incident> findByStatus(String status);
}
