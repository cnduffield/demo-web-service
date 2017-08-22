package org.demo.model;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.annotation.Transient;

public class Loan {
	public enum Status {
		CLOSED("Closed"), OPEN("Open");
		private String display;
		private Status(String display) {
			this.display = display;
		}
		public String getDisplay() {
			return display;
		}
	}
	private String id;
	private String ownerId;
	@Transient
	private Person owner;
	private String vehicleId;
	@Transient
	private Vehicle vehicle;
	private BigDecimal originalAmount;
	private List<Transaction> transactions;
	@Transient
	private BigDecimal currentAmount;
	private Status status = Status.OPEN;
	public Person getOwner() {
		return owner;
	}
	public void setOwner(Person owner) {
		this.owner = owner;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	public void setCurrentAmount(BigDecimal currentAmount) {
		this.currentAmount = currentAmount;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public BigDecimal getOriginalAmount() {
		return originalAmount;
	}
	public void setOriginalAmount(BigDecimal originalAmount) {
		this.originalAmount = originalAmount;
	}
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	public String getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}
	public BigDecimal getCurrentAmount() {
		return currentAmount;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
}
