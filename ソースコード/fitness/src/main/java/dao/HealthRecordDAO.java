package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.HealthRecordModel;

public class HealthRecordDAO {
	/**
	 * 全ての健康状態の記録を取得するメソッド
	 * @param connection Connection データベースに関する情報を格納
	 * @param userId int ログイン中のユーザーから取得
	 * @return List<HealthRecordModel>型 指定したユーザーの全ての健康状態の記録が格納された多次元配列
	 */
	public List<HealthRecordModel> findAll(Connection connection, int userId) {
		List<HealthRecordModel> healthRecordModels = new ArrayList<HealthRecordModel>();
		//セレクト文を準備する
		String sql = "SELECT"
				+ " ID"
				+ ",REGISTRATION_DATE"
				+ ",WEIGHT"
				+ " FROM HEALTH_RECORDS"
				+ " WHERE USER_ID=?"
				+ " AND IS_DELETED=0"
				+ " ORDER BY REGISTRATION_DATE DESC"
				+ " LIMIT 90";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, userId);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					HealthRecordModel model = new HealthRecordModel(
							rs.getInt("id"),
							Date.valueOf(rs.getString("registration_date")),
							rs.getDouble("weight"));
					healthRecordModels.add(model);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return healthRecordModels;
	}

	/**
	 * 健康状態の記録を挿入するメソッド
	 * @param connection Connection型 データベースに関する情報を格納
	 * @param userId int型 ログイン中のユーザーから取得
	 * @param registrationDate Date型 登録日 
	 * @param weight double型 体重
	 * @return bool 処理が成功すればtrue
	 */
	public boolean create(Connection connection, int userId, Date registrationDate, double weight) {

		String sql = "INSERT INTO HEALTH_RECORDS"
				+ "(USER_ID"
				+ ",REGISTRATION_DATE"
				+ ",WEIGHT"
				+ ",IS_DELETED)"
				+ " VALUES"
				+ " (?,?,?,0)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, userId);
			stmt.setDate(2, registrationDate);
			stmt.setDouble(3, weight);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 同一日付の健康状態の記録がないか調べる
	 * @param connection Connection データベースに関する情報を格納
	 * @param userId int型 ログイン中のユーザーから取得
	 * @param registrationDate Date型 登録日
	 * @return bool 処理が成功すればtrue
	 */
	public HealthRecordModel findByDate(Connection connection, int userId, Date registrationDate) {
		HealthRecordModel registeredHealth = null;
		//セレクト文を準備する
		String sql = "SELECT"
				+ " ID"
				+ ",REGISTRATION_DATE"
				+ ",WEIGHT"
				+ " FROM HEALTH_RECORDS"
				+ " WHERE"
				+ " USER_ID=?"
				+ " AND REGISTRATION_DATE=?"
				+ " AND IS_DELETED=0";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, userId);
			stmt.setDate(2, registrationDate);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					registeredHealth = new HealthRecordModel(
							rs.getInt("id"),
							Date.valueOf(rs.getString("registration_date")),
							rs.getDouble("weight"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return registeredHealth;
	}

	public boolean update(Connection connection, Date registrationDate, double weight, int id) {
		String sql = "UPDATE HEALTH_RECORDS"
				+ " SET REGISTRATION_DATE=?"
				+ ", WEIGHT=?"
				+ " WHERE ID=?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setDate(1, registrationDate);
			stmt.setDouble(2, weight);
			stmt.setInt(3, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public HealthRecordModel findOneById(Connection connection, int id, int userId) {
		HealthRecordModel selectedHealthRecordModel = new HealthRecordModel();
		String sql = "SELECT"
				+ " REGISTRATION_DATE"
				+ ",WEIGHT"
				+ " FROM HEALTH_RECORDS "
				+ " WHERE ID=?"
				+ " AND USER_ID=?"
				+ " AND IS_DELETED=0";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, id);
			stmt.setInt(2, userId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					selectedHealthRecordModel.setRegistrationDate(Date.valueOf(rs.getString("registration_date")));
					selectedHealthRecordModel.setWeight(rs.getDouble("weight"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return selectedHealthRecordModel;
	}

	public boolean delete(Connection connection, int id) {
		String sql = "UPDATE HEALTH_RECORDS"
				+ " SET IS_DELETED=1"
				+ " WHERE ID=?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 最新7つのデータを取り出す
	 * @param connection
	 * @param userId
	 * @return
	 */
	public List<HealthRecordModel> findLatestRecords(Connection connection, int userId) {
		List<HealthRecordModel> latestRecords = new ArrayList<HealthRecordModel>();
		//セレクト文を準備する
		String sql = "SELECT"
				+ " ID"
				+ ",REGISTRATION_DATE"
				+ ",WEIGHT"
				+ " FROM HEALTH_RECORDS"
				+ " WHERE USER_ID=?"
				+ " AND IS_DELETED=0"
				+ " ORDER BY REGISTRATION_DATE DESC"
				+ " LIMIT 7";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, userId);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					HealthRecordModel model = new HealthRecordModel(
							rs.getInt("id"),
							Date.valueOf(rs.getString("registration_date")),
							rs.getDouble("weight"));
					latestRecords.add(model);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return latestRecords;
	}

	/**
	 * 最新の健康状態の記録を検索する
	 * @param connection Connection型 データベースに関する情報を格納
	 * @param userId int型 ログイン中のユーザーから取得
	 * @return HealthRecordModel型 最新の健康状態の記録の情報が入った配列
	 */
	public HealthRecordModel findLastTimeRecord(Connection connection, int userId) {
		HealthRecordModel lastTimeRecord = null;
		String sql = "SELECT"
				+ " ID"
				+ ",REGISTRATION_DATE"
				+ ",WEIGHT"
				+ " FROM HEALTH_RECORDS"
				+ " WHERE USER_ID=?"
				+ " AND IS_DELETED=0"
				+ " ORDER BY REGISTRATION_DATE DESC"
				+ " LIMIT 1";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, userId);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					lastTimeRecord = new HealthRecordModel(
							rs.getInt("id"),
							Date.valueOf(rs.getString("registration_date")),
							rs.getDouble("weight"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return lastTimeRecord;
	}
}
