package model;

import java.sql.Date;
import java.sql.Timestamp;

public class ExerciseModel {

	private int id;
	private int categoryId;
	private int userId;
	private Date implementedDate;
	private int time;
	private int time1;
	private int time2;
	private int time3;
	private String content;
	private String comment;
	private int isDeleted;
	private Timestamp createdAt;
	private Timestamp updateDateTime;

	public ExerciseModel(
			int id,
			Date implementedDate,
			int time) {
		this.id = id;
		this.implementedDate = implementedDate;
		this.time = time;
	}

	public ExerciseModel(
			Date implementedDate,
			int time1,
			int time2,
			int time3) {
		this.implementedDate = implementedDate;
		this.time1 = time1;
		this.time2 = time2;
		this.time3 = time3;
	}

	public ExerciseModel(
			int userId,
			int categoryId,
			Date implementedDate,
			int time,
			String content,
			String comment) {
		this.userId = userId;
		this.categoryId = categoryId;
		this.implementedDate = implementedDate;
		this.time = time;
		this.content = content;
		this.comment = comment;
	}

	public ExerciseModel() {
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

	public int getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public Date getImplementedDate() {
		return this.implementedDate;
	}

	public void setImplementedDate(Date implemetedDate) {
		this.implementedDate = implemetedDate;
	}

	public int getTime1() {
		return this.time1;
	}

	public void setTime1(int time1) {
		this.time1 = time1;
	}

	public int getTime2() {
		return this.time2;
	}

	public void setTime2(int time2) {
		this.time2 = time2;
	}

	public int getTime3() {
		return this.time3;
	}

	public void setTime3(int time3) {
		this.time3 = time3;
	}

	public int getTime() {
		return this.time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getContent() {
		return this.content;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return this.comment;
	}

	public void setContent(String content) {
		this.content = content;
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
