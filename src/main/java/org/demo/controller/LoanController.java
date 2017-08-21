package org.demo.controller;

import org.demo.model.Loan;
import org.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoanController {

	@Autowired
    DemoService svc;

    @GetMapping("/loans")
    public String retrieveLoansByOwnerId(@RequestParam(value="ownerId", required=false) String ownerId, @RequestParam(value="vin", required=false) String vin, Model model) {
		if (ownerId != null) model.addAttribute("loans", svc.retrieveLoansByOwnerId(ownerId));
		else if (vin != null) model.addAttribute("loans", svc.retrieveLoansByVehicleVin(vin));
		return "loans";
    }

    @PostMapping("/loan")
    public String saveLoan(@ModelAttribute Loan loan, Model model) {
		model.addAttribute("loan", svc.saveLoan(loan));
		return "loan";
    }

    @GetMapping("/loan/close")
    public String closeLoan(@RequestParam(value="id") String id, Model model) {
		model.addAttribute("loan", svc.closeLoan(id));
		return "loan";
    }
}
