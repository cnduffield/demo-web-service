package org.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.demo.model.Loan;
import org.demo.model.Person;
import org.demo.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DemoService {
	@Autowired
	private RestTemplate restTemplate;
	public Loan retrieveLoansById(String id) {
		return restTemplate.getForObject("http://localhost:8070/loans?id=" + id, Loan.class);
	}
	public List<Loan> retrieveLoansByOwnerId(String ownerId) {
		Loan[] loans = restTemplate.getForObject("http://localhost:8070/loans?ownerId=" + ownerId, Loan[].class);
		return Arrays.asList(loans);
	}
	public List<Loan> retrieveLoansByVehicleVin(String vin) {
		Loan[] loans = restTemplate.getForObject("http://localhost:8070/loans?vin=" + vin, Loan[].class);
		return Arrays.asList(loans);
	}
	public Loan saveLoan(Loan loan) {
		return restTemplate.postForObject("http://localhost:8070/loan", loan, Loan.class);
	}
	public Loan closeLoan(String id) {
		Loan loan = retrieveLoansById(id);
		loan.setStatus(Loan.Status.CLOSED);
		restTemplate.put("http://localhost:8070/loan", loan);
		return retrieveLoansById(loan.getId());
	}
	public List<Person> retrievePeopleByName(String firstName, String lastName) {
		Person[] people = null;
		if (!StringUtils.isBlank(firstName) && !StringUtils.isBlank(lastName)) {
			people = restTemplate.getForObject(
					"http://localhost:8071/people?firstName=" + firstName + "&lastName=" + lastName, Person[].class);
		} else if (!StringUtils.isBlank(firstName)) {
			people = restTemplate.getForObject("http://localhost:8071/people?firstName=" + firstName, Person[].class);
		} else if (!StringUtils.isBlank(lastName)) {
			people = restTemplate.getForObject("http://localhost:8071/people?lastName=" + lastName, Person[].class);
		}
		if (people != null) { return Arrays.asList(people); }
		return Collections.emptyList();
	}
	public Person savePerson(Person person) {
		return restTemplate.postForObject("http://localhost:8071/people", person, Person.class);
	}
	public Person retrievePersonById(String id) {
		if (!StringUtils
				.isBlank(id)) { return restTemplate.getForObject("http://localhost:8071/people/" + id, Person.class); }
		return null;
	}
	public Person retrievePersonByGovernmentId(String governmentId) {
		if (!StringUtils.isBlank(governmentId)) { return restTemplate.getForObject(
				"http://localhost:8071/people/search/findByGovernmentId?governmentId=" + governmentId, Person.class); }
		return null;
	}
	public List<Vehicle> retrieveVehiclesByMakeAndModel(String make, String model) {
		Vehicle[] vehicles = null;
		if (!StringUtils.isBlank(make) && !StringUtils.isBlank(model)) {
			vehicles = restTemplate.getForObject("http://localhost:8072/vehicles?make=" + make + "&model=" + model,
					Vehicle[].class);
		} else if (!StringUtils.isBlank(make)) {
			vehicles = restTemplate.getForObject("http://localhost:8072/vehicles?make=" + make, Vehicle[].class);
		} else if (!StringUtils.isBlank(model)) {
			vehicles = restTemplate.getForObject("http://localhost:8072/vehicles?model=" + model, Vehicle[].class);
		}
		if (vehicles == null) { return addOwnersToVehicles(Arrays.asList(vehicles)); }
		return Collections.emptyList();
	}
	public Vehicle retrieveVehicleById(String id) {
		if (!StringUtils.isBlank(id)) { return addOwnerToVehicle(
				restTemplate.getForObject("http://localhost:8072/vehicles/" + id, Vehicle.class)); }
		return null;
	}
	public Vehicle retrieveVehicleByVin(String vin) {
		if (!StringUtils.isBlank(vin)) { return addOwnerToVehicle(
				restTemplate.getForObject("http://localhost:8072/vehicles?vin=" + vin, Vehicle.class)); }
		return null;
	}
	public Vehicle saveVehicle(Vehicle vehicle) {
		return restTemplate.postForObject("http://localhost:8072/vehicles", vehicle, Vehicle.class);
	}
	public List<Vehicle> retrieveAllVehicles() {
		return addOwnersToVehicles(
				new ArrayList<>(
						restTemplate
								.exchange("http://localhost:8072/vehicles", HttpMethod.GET, null,
										new ParameterizedTypeReference<PagedResources<Vehicle>>() {})
								.getBody().getContent()));
	}
	private List<Vehicle> addOwnersToVehicles(List<Vehicle> vehicles) {
		vehicles.forEach(vehicle -> addOwnerToVehicle(vehicle));
		return vehicles;
	}
	private Vehicle addOwnerToVehicle(Vehicle vehicle) {
		vehicle.setOwner(retrievePersonById(vehicle.getOwnerId()));
		return vehicle;
	}
	public List<Person> retrieveAllPeople() {
		return new ArrayList<>(restTemplate.exchange("http://localhost:8071/people", HttpMethod.GET, null,
				new ParameterizedTypeReference<PagedResources<Person>>() {}).getBody().getContent());
	}
	public void deletePersonById(String id) {
		restTemplate.exchange("http://localhost:8071/people/" + id, HttpMethod.DELETE, null,
				new ParameterizedTypeReference<PagedResources<Person>>() {});
	}
	public void deleteVehicleById(String id) {
		restTemplate.exchange("http://localhost:8072/vehicles/" + id, HttpMethod.DELETE, null,
				new ParameterizedTypeReference<PagedResources<Vehicle>>() {});
	}
}
