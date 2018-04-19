package org.uniovi.i3a.incidents_service.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.uniovi.i3a.incidents_service.types.Incident;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class SaveIncidentController extends AbstractIncidentController {

    @RequestMapping(value = "/saveObject", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> save(@RequestBody Incident incident) {
	Map<String, String> responseMap = new HashMap<String, String>();
	HttpStatus responseStatus;

	if (incident != null) {
	    service.saveIncident(incident);
	    responseMap.put("response", "incident created");
	    responseStatus = HttpStatus.OK;
	} else {
	    responseStatus = HttpStatus.BAD_REQUEST;
	    responseMap.put("response", "incident not created");
	}

	return new ResponseEntity<JSONObject>(new JSONObject(responseMap), responseStatus);
    }

    @RequestMapping(value = "/saveMap", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> save(@RequestBody Map<String, Object> payload) {
	Map<String, String> responseMap = new HashMap<String, String>();
	HttpStatus responseStatus;

	if (isPayLoadCorrect(payload)) {
	    ObjectMapper mapper = new ObjectMapper();
	    Incident incidentToInsert;
	    try {
		incidentToInsert = mapper.readValue(new JSONObject(payload).toString(), Incident.class);
		service.saveIncident(incidentToInsert);
		responseMap.put("response", "incident created");
		responseStatus = HttpStatus.OK;
	    } catch (IOException e) {
		e.printStackTrace();
		responseStatus = HttpStatus.BAD_REQUEST;
		responseMap.put("response", "incident not created");
	    }
	} else {
	    responseStatus = HttpStatus.BAD_REQUEST;
	    responseMap.put("response", "incident not created");
	}

	return new ResponseEntity<JSONObject>(new JSONObject(responseMap), responseStatus);
    }

    private boolean isPayLoadCorrect(Map<String, Object> payload) {
	return true;
    }
}
