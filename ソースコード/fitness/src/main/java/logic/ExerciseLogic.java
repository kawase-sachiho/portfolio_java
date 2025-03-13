package logic;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import common.database.DBConnection;
import dao.ExerciseDAO;
import model.ExerciseModel;

public class ExerciseLogic {
	/**
	 * 前回運動した日の運動時間を抽出するメソッド
	 * @param userId int型 ユーザーID ログイン中のユーザーの情報から取得
	 * @return daoの実行結果を返す
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ExerciseModel findLastTimeExercise(int userId) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			ExerciseDAO dao = new ExerciseDAO();
			return dao.findLastTimeExercise(conn, userId);
		}
	}

	/**
	 * 全ての運動記録の一覧をカテゴリーにもとづいて抽出するメソッド
	 * @param userId int型 ユーザーID ログイン中のユーザーの情報から取得
	 * @param categoryId int型 カテゴリーID 選択中のカテゴリーの情報から取得
	 * @return daoの実行結果を返す
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<ExerciseModel> getAllByCategory(int userId, int categoryId)
			throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			ExerciseDAO dao = new ExerciseDAO();
			return dao.findAllByCategory(conn, userId, categoryId);
		}
	}

	/**
	 * 指定した運動記録の詳細を表示するメソッド
	 * @param id int型 ID 選択された運動記録のレコードのID
	 * @param userId int型 ユーザーID ログイン中のユーザーの情報から取得
	 * @param categoryId int型 カテゴリーID 選択中のカテゴリーの情報から取得
	 * @return daoの実行結果を返す
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ExerciseModel show(int id, int userId, int categoryId) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			ExerciseDAO dao = new ExerciseDAO();
			return dao.show(conn, id, userId, categoryId);
		}
	}

	/**
	 * 運動記録を作成するメソッド
	 * @param userId int型 ユーザーID ログイン中のユーザーの情報から取得
	 * @param categoryId int型 カテゴリーID 選択中のカテゴリーの情報から取得
	 * @param implementedDate Date型 実施日を登録する
	 * @param time int型 運動時間(分) 正の整数を登録する
	 * @param content 運動内容 必須項目
	 * @param comment 感想 nullでも可能
	 * @return daoの実行結果を返す
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean create(int userId, int categoryId, Date implementedDate, int time, String content, String comment)
			throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			ExerciseDAO dao = new ExerciseDAO();
			return dao.create(conn, userId, categoryId, implementedDate, time, content, comment);
		}
	}

	/**
	 * 同一日付の運動記録を調べる
	 * @param userId int型 ユーザーID ログイン中のユーザーの情報から取得
	 * @param categoryId int型 カテゴリーID 選択中のカテゴリーの情報から取得
	 * @param implementedDate Date型 実施日を登録する
	 * @return daoの実行結果を返す
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ExerciseModel findByDate(int userId, int categoryId, Date implementedDate)
			throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			ExerciseDAO dao = new ExerciseDAO();
			return dao.findByDate(conn, userId, categoryId, implementedDate);
		}
	}

	/**
	 * 運動記録を更新するメソッド 
	 * @param implementedDate 実施日 Date型の日付を登録する
	 * @param time int型 運動時間(分) 正の整数を登録する
	 * @param content String型 運動内容 必須項目
	 * @param comment String型 感想 nullでも可
	 * @param updatedDateTime Timestamp型 更新した日を登録
	 * @param id int型 運動記録のID
	 * @return daoの実行結果を返す
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean update(Date implementedDate, int time, String content, String comment, Timestamp updateDateTime,
			int id) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			ExerciseDAO dao = new ExerciseDAO();
			return dao.update(conn, implementedDate, time, content, comment, updateDateTime, id);
		}
	}

	/**
	 * 運動記録を削除するメソッド
	 * @param updatedDateTime 更新した日をTimestamp型で登録
	 * @param id int型 ID 運動記録のID
	 * @return daoの実行結果を返す
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean delete(Timestamp updateDateTime, int id) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			ExerciseDAO dao = new ExerciseDAO();
			return dao.delete(conn, updateDateTime, id);
		}
	}

	/**	
	 * 全てのカテゴリーの運動記録を日付にもとづいて抽出し
	 * 各カテゴリーの運動時間を配列に格納するメソッド 
	 * @param userId int型 ユーザーID ログイン中のユーザーの情報から取得
	 * @return daoの実行結果を返す
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<ExerciseModel> findAllExercises(int userId) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			ExerciseDAO dao = new ExerciseDAO();
			return dao.findAllExercises(conn, userId);
		}
	}
}
