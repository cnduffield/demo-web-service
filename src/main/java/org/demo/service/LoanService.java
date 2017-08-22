package org.demo.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.demo.model.Loan;
import org.demo.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LoanService {
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private PeopleService peopleService;
	@Autowired
	private VehiclesService vehiclesService;
	@Autowired
	private ParamService params;
	private static final String URL = "loans.url";
	public Loan retrieveLoanById(String id) {
		return addOwnerAndVehicleToLoan(restTemplate.getForObject(params.getParam(URL) + "/loans/" + id, Loan.class));
	}
	public List<Loan> retrieveAllLoans() {
		return addOwnersAndVehiclesToLoans(
				new ArrayList<>(
						restTemplate
								.exchange(params.getParam(URL) + "/loans", HttpMethod.GET, null,
										new ParameterizedTypeReference<PagedResources<Loan>>() {})
								.getBody().getContent()));
	}
	public List<Loan> retrieveLoansByOwnerId(String ownerId) {
		Loan[] loans = restTemplate.getForObject(params.getParam(URL) + "/loans?ownerId=" + ownerId, Loan[].class);
		return addOwnersAndVehiclesToLoans(Arrays.asList(loans));
	}
	public List<Loan> retrieveLoansByVehicleVin(String vin) {
		Loan[] loans = restTemplate.getForObject(params.getParam(URL) + "/loans?vin=" + vin, Loan[].class);
		return addOwnersAndVehiclesToLoans(Arrays.asList(loans));
	}
	private List<Loan> addOwnersAndVehiclesToLoans(List<Loan> loans) {
		loans.forEach(loan -> addOwnerAndVehicleToLoan(loan));
		return loans;
	}
	private Loan addOwnerAndVehicleToLoan(Loan loan) {
		loan.setOwner(peopleService.retrievePersonById(loan.getOwnerId()));
		loan.setVehicle(vehiclesService.retrieveVehicleById(loan.getVehicleId()));
		return loan;
	}
	public Loan saveLoan(Loan loan) {
		return restTemplate.postForObject(params.getParam(URL) + "/loans", loan, Loan.class);
	}
	public Loan closeLoan(String id) {
		Loan loan = retrieveLoanById(id);
		loan.setStatus(Loan.Status.CLOSED);
		restTemplate.put(params.getParam(URL) + "/loan", loan);
		return retrieveLoanById(loan.getId());
	}
	public void recalculateCurrentAmount(List<Loan> loans) {
		loans.forEach(loan -> recalculateCurrentAmount(loan));
	}
	public void recalculateCurrentAmount(Loan loan) {
		if (loan == null || loan.getOriginalAmount() == null) return;
		loan.setCurrentAmount(new BigDecimal(loan.getOriginalAmount().toString()));
		if (loan.getTransactions() != null) {
			loan.getTransactions().forEach(transaction -> {
				if (Transaction.Type.DEBIT == transaction.getType()) {
					loan.setCurrentAmount(loan.getCurrentAmount().subtract(transaction.getAmount()));
				} else {
					loan.setCurrentAmount(loan.getCurrentAmount().add(transaction.getAmount()));
				}
			});
		}
	}
	public Loan makePayment(Transaction transaction) {
		Loan loan = retrieveLoanById(transaction.getLoanId());
		loan.getTransactions().add(transaction);
		return saveLoan(loan);
	}
}
