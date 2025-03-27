package logic;

import java.sql.Connection;
import java.sql.SQLException;

import common.database.DBConnection;
import dao.HealthInformationDAO;
import model.HealthInformationModel;

public class HealthInformationLogic {
/**
 * 健康状態の基本情報をユーザーIDから取得する
 * @param userId int型 ログイン中のユーザーから取得
 * @return daoの実行結果を返す
 */
	public HealthInformationModel findOneByUserId(int userId) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			HealthInformationDAO dao = new HealthInformationDAO();
			return dao.findOneByUserId(conn, userId);
		}
	}
	
/**
 * 健康状態の基本情報を新規登録する
 * @param userId int型 ログイン中のユーザーから取得
 * @param height double型 身長
 * @param targetWeight double型 目標体重
 * @return daoの実行結果を返す
 */
	public boolean create(int userId,double height,double targetWeight)
			throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			HealthInformationDAO dao = new HealthInformationDAO();
			return dao.create(conn, userId,height,targetWeight);
		}
	}
	/**
	 * 健康状態の基本情報を更新する
	 * @param height double型 身長
	 * @param targetWeight double型 目標体重
	 * @param userId int型 ログイン中のユーザーから取得
	 * @return daoの実行結果を返す
	 */
	public boolean update(double height, double targetWeight,
			int userId) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			HealthInformationDAO dao = new HealthInformationDAO();
			return dao.update(conn, height,targetWeight, userId);
		}
	}
	
}
