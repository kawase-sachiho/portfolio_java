package model;

import java.sql.Date;
import java.sql.Timestamp;

public class HealthRecordModel {
	private int id;
	private int userId;
	private Date registrationDate;
	private double weight;
	private int isDeleted;
	private Timestamp createdAt;
	private Timestamp updateDateTime;
	private double bmi;

	public HealthRecordModel() {
	}
	
	public HealthRecordModel(int id,Date registrationDate,double weight) {
		this.id=id;
		this.registrationDate=registrationDate;
		this.weight=weight;
	}
	
	public double getBmi() {
		return this.bmi;
	}
	
	public void setBmi(double bmi) {
		this.bmi=bmi;
	}
	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public Date getRegistrationDate() {
		return this.registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public double getWeight() {
		return this.weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public int getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Timestamp getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedDateTime() {
		return this.updateDateTime;
	}

	public void setUpdatedDateTime(Timestamp updateDateTime) {
		this.updateDateTime = updateDateTime;
	}
	
}
