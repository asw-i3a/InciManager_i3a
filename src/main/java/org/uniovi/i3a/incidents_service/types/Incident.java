package org.uniovi.i3a.incidents_service.types;

import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Document(collection = "incidents")
public class Incident {

    @JsonIgnore
    @Id
    private ObjectId _id;

    @JsonProperty("incidentId")
    public String incidentId = "";
    private String title = "";
    private String description = "";
    private String status = "";
    private String location = "";
    private String[] tags = { "" };
    private String[] multimedia = { "" };
    private Map<String, String> propertyVal = new HashMap<String, String>();
    private Comment[] comments;
    private String agentId = "";
    private String operatorId = "";

    public String getIncidentId() {
	if (this._id != null)
	    return this._id.toString();
	return "";
    }

    public void set_id(ObjectId id) {
	System.out.println("Using the willt setter");
	this.incidentId = id.toString();
	this._id = id;
    }

}
