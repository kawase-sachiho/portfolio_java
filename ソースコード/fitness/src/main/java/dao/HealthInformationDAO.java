package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.HealthInformationModel;

public class HealthInformationDAO {	
	/**
	 * ユーザーIDから基本情報を取得する
	 * @param connection Connection型 データベース接続に関する情報を格納
	 * @param userId int型 ログイン中のユーザーから取得
	 * @return HealthInformationModel型 指定したユーザーの健康状態の基本情報が入った配列
	 */
	public HealthInformationModel findOneByUserId(Connection connection, int userId) {
		HealthInformationModel HealthInformation = new HealthInformationModel();
		String sql = "SELECT"
				+ " HEIGHT"
				+ ",TARGET_WEIGHT"
				+ " FROM HEALTH_INFORMATIONS"
				+ " WHERE USER_ID=?"
				+ " AND IS_DELETED=0";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, userId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					HealthInformation.setHeight(rs.getDouble("height"));
					HealthInformation.setTargetWeight(rs.getDouble("target_weight"));
				}
				else {
					return null;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return HealthInformation;
	}
	
	/**
	 * 健康状態の基本情報のデータを新規作成するメソッド
	 * @param connection Connection型 
	 * @param userId int型 ログイン中のユーザーから取得
	 * @param height double型 身長の値
	 * @param targetWeight double型 目標体重の値
	 * @return bool 処理が成功すればtrue
	 */
	public boolean create(Connection connection, int userId, double height, double targetWeight) {

		String sql = "INSERT INTO HEALTH_INFORMATIONS"
				+ "(USER_ID"
				+ ",HEIGHT"
				+ ",TARGET_WEIGHT"
				+ ",IS_DELETED)"
				+ " VALUES"
				+ " (?,?,?,0)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, userId);
			stmt.setDouble(2, height);
			stmt.setDouble(3, targetWeight);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 健康状態の基本情報のデータを更新するメソッド
	 * @param connection Connection型 データベース接続に関する情報を格納
	 * @param height double型 身長の値
	 * @param targetWeight double型 目標体重の値
	 * @param userId int型 ログイン中のユーザーから取得
	 * @return bool 処理が成功すればtrue
	 */
	public boolean update(Connection connection, double height, double targetWeight,int userId) {
		String sql = "UPDATE HEALTH_INFORMATIONS"
				+ " SET HEIGHT=?"
				+ ",TARGET_WEIGHT=?"
				+ " WHERE USER_ID=?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setDouble(1, height);
			stmt.setDouble(2, targetWeight);
			stmt.setInt(3, userId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}	
}
