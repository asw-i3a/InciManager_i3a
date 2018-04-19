package org.uniovi.i3a.incidents_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.uniovi.i3a.incidents_service.services.IncidentsService;

@RestController
public class AbstractIncidentController {
    
    @Autowired
    protected IncidentsService service;

}
