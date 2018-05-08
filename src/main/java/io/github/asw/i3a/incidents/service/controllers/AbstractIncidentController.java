package io.github.asw.i3a.incidents.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import io.github.asw.i3a.incidents.service.services.IncidentsService;

@RestController
public class AbstractIncidentController {

	@Autowired
	protected IncidentsService service;

}
