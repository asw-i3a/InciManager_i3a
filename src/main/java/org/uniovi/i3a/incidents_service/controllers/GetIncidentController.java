package org.uniovi.i3a.incidents_service.controllers;

import java.util.List;

import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.uniovi.i3a.incidents_service.types.Incident;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class GetIncidentController extends AbstractIncidentController {

    @RequestMapping(value="/incidents")
    public List<Incident> getIncidents(@Nullable @RequestParam("status") String status, @Nullable @RequestParam("operatorId") String operatorId) {
	
	List<Incident> result;
	
	if(status == null && operatorId == null) {
	    result = service.findAll();
	} else if(status != null && operatorId == null) {
	    result = service.findByStatus(status.toUpperCase());
	} else if(status == null && operatorId != null) {
	    result = service.findByOperatorId(operatorId);
	} else {
	    result = service.findByOperatorId(operatorId);
	    result.stream().filter(i -> i.getStatus().equalsIgnoreCase(status.toUpperCase()));
	}
	
	return result;
    }
    
    @RequestMapping(value="/incidents/{id}")
    public Incident getIncident(@PathVariable("id") String id) {
	log.info("Geting incident from service with id: " + id);
	return service.findById(id);
    }
}
