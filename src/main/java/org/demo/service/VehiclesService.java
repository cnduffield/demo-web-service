package org.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.demo.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class VehiclesService {
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private PeopleService peopleService;
	@Autowired
	private ParamService params;
	private static final String URLS = "vehicles.url";
	public List<Vehicle> retrieveVehiclesByMakeAndModel(String make, String model) {
		Vehicle[] vehicles = null;
		if (!StringUtils.isBlank(make) && !StringUtils.isBlank(model)) {
			vehicles = restTemplate.getForObject(
					params.getParam(URLS) + "/vehicles?make=" + make + "&model=" + model, Vehicle[].class);
		} else if (!StringUtils.isBlank(make)) {
			vehicles = restTemplate.getForObject(params.getParam(URLS) + "/vehicles?make=" + make,
					Vehicle[].class);
		} else if (!StringUtils.isBlank(model)) {
			vehicles = restTemplate.getForObject(params.getParam(URLS) + "/vehicles?model=" + model,
					Vehicle[].class);
		}
		if (vehicles == null) { return addOwnersToVehicles(Arrays.asList(vehicles)); }
		return Collections.emptyList();
	}
	public Vehicle retrieveVehicleById(String id) {
		if (!StringUtils.isBlank(id)) { return addOwnerToVehicle(
				restTemplate.getForObject(params.getParam(URLS) + "/vehicles/" + id, Vehicle.class)); }
		return null;
	}
	public Vehicle retrieveVehicleByVin(String vin) {
		if (!StringUtils.isBlank(vin)) { return addOwnerToVehicle(
				restTemplate.getForObject(params.getParam(URLS) + "/vehicles?vin=" + vin, Vehicle.class)); }
		return null;
	}
	public Vehicle saveVehicle(Vehicle vehicle) {
		return restTemplate.postForObject(params.getParam(URLS) + "/vehicles", vehicle, Vehicle.class);
	}
	public List<Vehicle> retrieveAllVehicles() {
		return addOwnersToVehicles(
				new ArrayList<>(
						restTemplate
								.exchange(params.getParam(URLS) + "/vehicles", HttpMethod.GET, null,
										new ParameterizedTypeReference<PagedResources<Vehicle>>() {})
								.getBody().getContent()));
	}
	private List<Vehicle> addOwnersToVehicles(List<Vehicle> vehicles) {
		vehicles.forEach(vehicle -> addOwnerToVehicle(vehicle));
		return vehicles;
	}
	private Vehicle addOwnerToVehicle(Vehicle vehicle) {
		vehicle.setOwner(peopleService.retrievePersonById(vehicle.getOwnerId()));
		return vehicle;
	}
	public void deleteVehicleById(String id) {
		restTemplate.exchange(params.getParam(URLS) + "/vehicles/" + id, HttpMethod.DELETE, null,
				new ParameterizedTypeReference<PagedResources<Vehicle>>() {});
	}
}
