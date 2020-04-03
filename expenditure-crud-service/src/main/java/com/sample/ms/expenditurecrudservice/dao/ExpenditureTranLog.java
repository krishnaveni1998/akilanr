package com.sample.ms.expenditurecrudservice.dao;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.ToString;

@ToString
@Entity
public class ExpenditureTranLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int tranId;
	@OneToOne
	@JoinColumn(name = "expenditure_id",nullable = false)
	private Expenditure expenditure;
	private double amount;
	private Timestamp tranDate;
	private int tranFlag;

	public int getTranId() {
		return tranId;
	}

	public void setTranId(int tranId) {
		this.tranId = tranId;
	}

	public Expenditure getExpenditure() {
		return expenditure;
	}

	public void setExpenditure(Expenditure expenditure) {
		this.expenditure = expenditure;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Timestamp getTranDate() {
		return tranDate;
	}

	public void setTranDate(Timestamp tranDate) {
		this.tranDate = tranDate;
	}

	public int getTranFlag() {
		return tranFlag;
	}

	public void setTranFlag(int tranFlag) {
		this.tranFlag = tranFlag;
	}
}
