package model;

import java.sql.Timestamp;

public class UserModel {
	private int id;
	private String mail;
	private String pass;
	private String userName;
	private int isDeleted;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	public UserModel(
			String mail,
			String pass,
			String userName) {
		this.mail = mail;
		this.pass = pass;
		this.userName = userName;
	}

	public UserModel(
			int id,
			String mail,
			String pass,
			String userName) {
		this.id = id;
		this.pass = pass;
		this.mail = mail;
		this.userName = userName;
	}

	public UserModel() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPass() {
		return this.pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public Timestamp getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
}
