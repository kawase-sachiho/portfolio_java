package logic;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import common.database.DBConnection;
import dao.HealthRecordDAO;
import model.HealthRecordModel;

public class HealthRecordLogic {
	/**
	 * 健康状態の記録の新規作成
	 * @param userId int型 ログイン中のユーザーから取得
	 * @param registrationDate Date型 登録日
	 * @param weight double型 体重
	 * @return daoの実行結果を返す
	 */
	public boolean create(int userId, Date registrationDate, double weight)
			throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			HealthRecordDAO dao = new HealthRecordDAO();
			return dao.create(conn, userId, registrationDate, weight);
		}
	}

	/**
	 * 指定した日付の健康状態の記録がないか検索する
	 * @param userId int型 ログイン中のユーザーから取得
	 * @param registrationDate Date型 登録日 
	 * @return daoの実行結果を返す
	 */
	public HealthRecordModel findByDate(int userId, Date registrationDate)
			throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			HealthRecordDAO dao = new HealthRecordDAO();
			return dao.findByDate(conn, userId, registrationDate);
		}
	}

	/**
	 * ログイン中のユーザーの健康状態の記録を取得
	 * @param userId int型 ログイン中のユーザーから取得
	 * @return daoの実行結果を返す
	 */
	public List<HealthRecordModel> findAll(int userId)
			throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			HealthRecordDAO dao = new HealthRecordDAO();
			return dao.findAll(conn, userId);
		}
	}

	/**
	 * ログイン中のユーザーの健康状態の記録を新規7件分取得
	 * @param userId int型 ログイン中のユーザーから取得
	 * @return daoの実行結果を返す
	 */
	public List<HealthRecordModel> findLatestRecords(int userId)
			throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			HealthRecordDAO dao = new HealthRecordDAO();
			return dao.findLatestRecords(conn, userId);
		}
	}

	/**
	 * 健康状態の記録を更新する
	 * @param registrationDate Date型 登録日
	 * @param weight double型 体重
	 * @param id int型 レコードのID
	 * @return daoの実行結果を返す
	 */
	public boolean update(Date registrationDate, double weight,
			int id) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			HealthRecordDAO dao = new HealthRecordDAO();
			return dao.update(conn, registrationDate, weight, id);
		}
	}

	/**
	 * 指定したIDの健康状態の記録を取得する
	 * @param id int型 レコードのID
	 * @param userId int型 ログイン中のユーザーから取得
	 * @return daoの実行結果を返すを返す
	 */
	public HealthRecordModel findOneById(int id, int userId) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			HealthRecordDAO dao = new HealthRecordDAO();
			return dao.findOneById(conn, id, userId);
		}
	}

	/**
	 * 指定したIDの健康状態の記録を削除する
	 * @param id int型 レコードのID
	 * @return daoの実行結果を返すを返す
	 */
	public boolean delete(int id) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			HealthRecordDAO dao = new HealthRecordDAO();
			return dao.delete(conn, id);
		}
	}

	/**
	 * 最新の健康状態の記録を獲得する
	 * @param userId int型 ログイン中のユーザーから取得
	 * @return daoの実行結果
	 */
	public HealthRecordModel findLastTimeRecord(int userId) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			HealthRecordDAO dao = new HealthRecordDAO();
			return dao.findLastTimeRecord(conn, userId);
		}
	}

}
