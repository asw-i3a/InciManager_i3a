package io.github.asw.i3a.incidents.service.controllers;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.github.asw.i3a.incidents.service.types.Incident;

@RestController
public class SaveIncidentController extends AbstractIncidentController {

	/**
	 * Saves an object in the data base. If the object already exists will
	 * update the new fields.
	 * 
	 * @param incident to be saved in the database.
	 * @return a response entity with the status with the results.
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<String> save( @Nullable @RequestBody Incident incident ) {
		Map<String, String> responseMap = new HashMap<String, String>();
		HttpStatus responseStatus;

		if (incident != null) {
			service.saveIncident( incident );
			responseMap.put( "response", "incident updated" );
			responseMap.put( "incidentId", incident.get_id().toString() );
			responseStatus = HttpStatus.OK;
		} else {
			responseStatus = HttpStatus.BAD_REQUEST;
			responseMap.put( "response", "incident not created" );
		}

		return new ResponseEntity<String>( new JSONObject( responseMap ).toString(),
				responseStatus );
	}
}
