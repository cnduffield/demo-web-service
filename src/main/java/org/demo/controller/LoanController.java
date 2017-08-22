package org.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.demo.model.Loan;
import org.demo.model.Transaction;
import org.demo.model.Transaction.Type;
import org.demo.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoanController {
	@Autowired
	LoanService svc;
	@GetMapping("/loans")
	public String retrieveLoansByOwnerId(@RequestParam(value = "ownerId", required = false) String ownerId,
			@RequestParam(value = "vin", required = false) String vin, Model model) {
		List<Loan> loans = null;
		if (ownerId != null) loans = svc.retrieveLoansByOwnerId(ownerId);
		else if (vin != null) loans = svc.retrieveLoansByVehicleVin(vin);
		else loans = svc.retrieveAllLoans();
		svc.recalculateCurrentAmount(loans);
		model.addAttribute("loans", loans);
		return "loans";
	}
	@GetMapping("/loan/{id}")
	public String retrieveLoanById(@RequestParam(value = "id") String id, Model model) {
		Loan loan = svc.retrieveLoanById(id);
		svc.recalculateCurrentAmount(loan);
		model.addAttribute("loan", loan);
		return "loan";
	}
	@PostMapping("/loan/new")
	public String saveLoan(@ModelAttribute Loan loan, Model model) {
		svc.saveLoan(loan);
		List<Loan> loans = svc.retrieveAllLoans();
		svc.recalculateCurrentAmount(loans);
		model.addAttribute("loans", loans);
		return "loans";
	}
	@GetMapping("/loan/close")
	public String closeLoan(@RequestParam(value = "id") String id, Model model) {
		model.addAttribute("loan", svc.closeLoan(id));
		return "loans";
	}
	@GetMapping("/loan/new/{ownerId}/{vehicleId}")
	public String newLoanForm(@PathVariable(value = "ownerId") String ownerId,
			@PathVariable(value = "vehicleId") String vehicleId, Model model) {
		Loan loan = new Loan();
		loan.setOwnerId(ownerId);
		loan.setVehicleId(vehicleId);
		model.addAttribute("loan", loan);
		return "loanForm";
	}
	@GetMapping("/transaction/new/{loanId}")
	public String newTransactionForm(@PathVariable(value = "loanId") String loanId, Model model) {
		Transaction transaction = new Transaction();
		transaction.setType(Type.DEBIT);
		transaction.setLoanId(loanId);
		model.addAttribute("transaction", transaction);
		return "transactionForm";
	}
	@PostMapping("/transaction/new")
	public String executeTransaction(@ModelAttribute Transaction transaction, Model model) {
		Loan loan = svc.retrieveLoanById(transaction.getLoanId());
		if (loan.getTransactions() == null) loan.setTransactions(new ArrayList<>());
		loan.getTransactions().add(transaction);
		svc.recalculateCurrentAmount(loan);
		return saveLoan(loan, model);
	}
}
