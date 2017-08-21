package org.demo.controller;

import org.apache.commons.lang3.StringUtils;
import org.demo.model.Person;
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
public class PersonController {

	@Autowired
    DemoService svc;

    @GetMapping("/people")
    public String retrievePeople(@RequestParam(value="firstName", required=false) String firstName, @RequestParam(value="lastName", required=false) String lastName, Model model) {
    	if (firstName !=null || lastName != null) model.addAttribute("people", svc.retrievePeopleByName(firstName, lastName));
    	else  model.addAttribute("people", svc.retrieveAllPeople());
		return "people";
    }

    @GetMapping("/person/{id}")
    public String retrievePersonById(@PathVariable(value="id") String id, Model model) {
		model.addAttribute("person", svc.retrievePersonById(id));
		return "person";
    }

    @GetMapping("/person")
    public String retrievePersonByGovernmentId(@RequestParam(value="governmentId") String governmentId, Model model) {
		model.addAttribute("person", svc.retrievePersonByGovernmentId(governmentId));
		return "person";
    }

    @GetMapping("/person/new")
    public String newPersonForm(Model model) {
		model.addAttribute("person", new Person());
		return "personForm";
    }

    @GetMapping("/person/edit/{id}")
    public String editPersonForm(@PathVariable(value="id") String id, Model model) {
		model.addAttribute("person", svc.retrievePersonById(id));
		return "personForm";
    }

    @PostMapping("/person/new")
    public String savePerson(@ModelAttribute Person person, Model model) {
    	if (StringUtils.isBlank(person.getId())) person.setId(null);
		model.addAttribute("person", svc.savePerson(person));
		return "person";
    }

    @PutMapping("/person/edit")
    public String updatePerson(@ModelAttribute Person person, Model model) {
		model.addAttribute("person", svc.savePerson(person));
		return "person";
    }

    @GetMapping("/person/delete/{id}")
    public String deletePerson(@PathVariable(value="id") String id, Model model) {
		svc.deletePersonById(id);
		return "redirect:/people";
    }
}
