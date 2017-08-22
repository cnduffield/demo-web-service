package org.demo.service;

import org.springframework.stereotype.Service;

@Service
public class DemoService {
	// @Autowired
	// private RestTemplate restTemplate;
	// @Autowired
	// private ParamService params;
	// @Autowired
	// private PeopleService peopleService;
	// private static final String VEHICLES_URL = "vehicles.url";
	// public List<Vehicle> retrieveVehiclesByMakeAndModel(String make, String
	// model) {
	// Vehicle[] vehicles = null;
	// if (!StringUtils.isBlank(make) && !StringUtils.isBlank(model)) {
	// vehicles = restTemplate.getForObject(
	// params.getParam(VEHICLES_URL) + "/vehicles?make=" + make + "&model=" +
	// model, Vehicle[].class);
	// } else if (!StringUtils.isBlank(make)) {
	// vehicles = restTemplate.getForObject(params.getParam(VEHICLES_URL) +
	// "/vehicles?make=" + make,
	// Vehicle[].class);
	// } else if (!StringUtils.isBlank(model)) {
	// vehicles = restTemplate.getForObject(params.getParam(VEHICLES_URL) +
	// "/vehicles?model=" + model,
	// Vehicle[].class);
	// }
	// if (vehicles == null) { return
	// addOwnersToVehicles(Arrays.asList(vehicles)); }
	// return Collections.emptyList();
	// }
	// public Vehicle retrieveVehicleById(String id) {
	// if (!StringUtils.isBlank(id)) { return addOwnerToVehicle(
	// restTemplate.getForObject(params.getParam(VEHICLES_URL) + "/vehicles/" +
	// id, Vehicle.class)); }
	// return null;
	// }
	// public Vehicle retrieveVehicleByVin(String vin) {
	// if (!StringUtils.isBlank(vin)) { return addOwnerToVehicle(
	// restTemplate.getForObject(params.getParam(VEHICLES_URL) +
	// "/vehicles?vin=" + vin, Vehicle.class)); }
	// return null;
	// }
	// public Vehicle saveVehicle(Vehicle vehicle) {
	// return restTemplate.postForObject(params.getParam(VEHICLES_URL) +
	// "/vehicles", vehicle, Vehicle.class);
	// }
	// public List<Vehicle> retrieveAllVehicles() {
	// return addOwnersToVehicles(
	// new ArrayList<>(
	// restTemplate
	// .exchange(params.getParam(VEHICLES_URL) + "/vehicles", HttpMethod.GET,
	// null,
	// new ParameterizedTypeReference<PagedResources<Vehicle>>() {})
	// .getBody().getContent()));
	// }
	// private List<Vehicle> addOwnersToVehicles(List<Vehicle> vehicles) {
	// vehicles.forEach(vehicle -> addOwnerToVehicle(vehicle));
	// return vehicles;
	// }
	// private Vehicle addOwnerToVehicle(Vehicle vehicle) {
	// vehicle.setOwner(peopleService.retrievePersonById(vehicle.getOwnerId()));
	// return vehicle;
	// }
	// public void deleteVehicleById(String id) {
	// restTemplate.exchange(params.getParam(VEHICLES_URL) + "/vehicles/" + id,
	// HttpMethod.DELETE, null,
	// new ParameterizedTypeReference<PagedResources<Vehicle>>() {});
	// }
}
