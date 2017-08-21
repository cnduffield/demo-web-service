package org.demo.controller;

import org.apache.commons.lang3.StringUtils;
import org.demo.model.Vehicle;
import org.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VehicleController {
	@Autowired
	DemoService svc;
	@GetMapping("/vehicles")
	public String retrieveVehicles(@RequestParam(value = "make", required = false) String make,
			@RequestParam(value = "model", required = false) String vehicleModel, Model model) {
		if (make != null || vehicleModel != null)
			model.addAttribute("vehicles", svc.retrieveVehiclesByMakeAndModel(make, vehicleModel));
		model.addAttribute("vehicles", svc.retrieveAllVehicles());
		return "vehicles";
	}
	@GetMapping("/vehicle/{id}")
	public String retrieveVehicleById(@PathVariable(value = "id") String id, Model model) {
		model.addAttribute("vehicle", svc.retrieveVehicleById(id));
		return "vehicle";
	}
	@GetMapping("/vehicle")
	public String retrieveVehicleByVin(@RequestParam(value = "vin") String vin, Model model) {
		model.addAttribute("vehicle", svc.retrieveVehicleByVin(vin));
		return "vehicle";
	}
	@GetMapping("/vehicle/new")
	public String newVehicleForm(Model model) {
		model.addAttribute("vehicle", new Vehicle());
		return "vehicleForm";
	}
	@GetMapping("/vehicle/edit/{id}")
	public String editvehicleForm(@PathVariable(value = "id") String id, Model model) {
		model.addAttribute("vehicle", svc.retrieveVehicleById(id));
		return "vehicleForm";
	}
	@PostMapping("/vehicle/new")
	public String saveVehicle(@ModelAttribute Vehicle vehicle, Model model) {
		if (StringUtils.isBlank(vehicle.getId())) vehicle.setId(null);
		model.addAttribute("vehicle", svc.saveVehicle(vehicle));
		return "vehicle";
	}
	@PutMapping("/vehicle/edit")
	public String updateVehicle(@ModelAttribute Vehicle vehicle, Model model) {
		model.addAttribute("vehicle", svc.saveVehicle(vehicle));
		return "vehicle";
	}
	@GetMapping("/vehicle/delete/{id}")
	public String deleteVehicle(@PathVariable(value = "id") String id, Model model) {
		svc.deleteVehicleById(id);
		return "redirect:/vehicles";
	}
}
