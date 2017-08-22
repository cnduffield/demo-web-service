package org.demo.model;

import java.math.BigDecimal;

public class Transaction {
	public enum Type {
		DEBIT, CREDIT
	}
	private String loanId;
	private Type type;
	private BigDecimal amount;
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
