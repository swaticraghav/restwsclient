package com.rest.web.services;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rest.web.services.model.Patient;

public class PatientWSClient {

	private static final String PATIENTS = "/patients";
	private static final String PATIENT_SERVICE_URL = "http://localhost:7070/restws/services/patientservice";

	public static void main(String[] args) {

		/* from the client builder, get the client.
		 * 
		 *  Follwing steps vary for each method.
		 * from the client, get the target url :
		 * from the target url, get the request.
		 * from the request, get the http method.
		 * print the response or patient data as it is metioned in server.
		 * clean up resources
		 * */

		// step 1
		Client client = ClientBuilder.newClient();
		
		// Get the patient by ID
		// Update the patient
		// Create the patient
		// Delete the resource
		// print all patients
		
		// Get the patient by ID: 
		// Method: GET, INPUT: ID, Output: Patient data
		WebTarget target = client.target(PATIENT_SERVICE_URL).path(PATIENTS).path("/{id}").resolveTemplate("id", 1);
		Builder request = target.request();
		Patient patient = request.get(Patient.class);
		System.err.println("Get Patient By ID:");
		System.out.println("ID: " + patient.getId());
		System.out.println("Name: " + patient.getName());
		
		// Update the patient
		// we are updating the patient we already have.
		// Method: Put, Input: patient object, Output: Response
		WebTarget updateTarget = client.target(PATIENT_SERVICE_URL).path(PATIENTS);
		Builder updateRequest = updateTarget.request();
		patient.setName("Swati");
		Response response = updateRequest.put(Entity.entity(patient, MediaType.APPLICATION_XML));
		System.err.println("Update Patient By ID:");
		System.out.println(response.getStatus());
		response.close();
		
		// Create a patient.
		// Method: Post, input: Patient's name, output: New Patient info.
		WebTarget createTarget = client.target(PATIENT_SERVICE_URL).path(PATIENTS);
		Builder createRequest = createTarget.request();
		Patient newPatient = new Patient();
		newPatient.setName("Zebra");
		Patient postPatient = createRequest.post(Entity.entity(newPatient, MediaType.APPLICATION_XML), Patient.class);
		System.err.println("Created New Patient");
		System.out.println("new id: " + postPatient.getId());
		System.out.println("new name: " + postPatient.getName());
		
		
		// Delete a resourcse
		// Method: Delete, Input: nothing, Output: Response okay.
		WebTarget deleteTarget = client.target(PATIENT_SERVICE_URL).path(PATIENTS).path("/{id}").resolveTemplate("id", 10);
		Builder deleterequest = deleteTarget.request();
		Response deleteResponse = deleterequest.delete();
		System.err.println("Deleted 4th Patient");
		System.out.println(deleteResponse.getStatus());
		deleteResponse.close();
		
		
		// Print all patients
		// Method: Get, Input: Nothing, Output: List<Patient>
		WebTarget getTarget = client.target(PATIENT_SERVICE_URL).path(PATIENTS);
		Builder getRequest = getTarget.request();
		List<Patient> patientList = getRequest.get(new GenericType<List<Patient>>() {});
		System.err.println("Listing Patients");
		for(int i = 0; i< patientList.size(); i++) {
			System.out.println("This is the patient id" + patientList.get(i).getId());
			System.out.println("This is the patient name" + patientList.get(i).getName());
		}
		
		client.close();
	}

}
