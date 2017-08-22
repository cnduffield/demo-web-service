package org.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("/")
	public String retrieveNotes(Model model) {
		return "home";
	}
	@GetMapping("/hello-world")
	public String helloWorld(Model model) {
		return "hello-world";
	}
}
