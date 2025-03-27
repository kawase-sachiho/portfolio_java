package model;

import java.sql.Timestamp;

public class HealthInformationModel {
private int id;
private int userId;
private double height;
private double targetWeight;
private int isDeleted;
private Timestamp createdAt;
private Timestamp updateDateTime;

public HealthInformationModel() {
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

public double getHeight() {
	return this.height;
}

public void setHeight(double height) {
	this.height = height;
}

public double getTargetWeight() {
	return this.targetWeight;
}

public void setTargetWeight(double targetWeight) {
	this.targetWeight = targetWeight;
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
