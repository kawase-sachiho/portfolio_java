package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.ExerciseModel;

public class ExerciseDAO {
	/**
	 * 全ての運動記録の一覧をカテゴリーにもとづいて抽出するメソッド
	 * @param connection Connection型 データベース接続に関する情報を格納
	 * @param userId int型 ユーザーID ログイン中のユーザーの情報から取得
	 * @param categoryId int型 カテゴリーID 選択中のカテゴリーの情報から取得
	 * @return exerciseModels List<ExerciseModel>型 運動記録の一覧の情報が入った多次元配列
	 */
	public List<ExerciseModel> findAllByCategory(Connection connection, int userId, int categoryId) {
		List<ExerciseModel> exerciseModels = new ArrayList<ExerciseModel>();
		//セレクト文を準備する
		String sql = "SELECT"
				+ " ID"
				+ ",IMPLEMENTED_DATE"
				+ ",TIME"
				+ " FROM EXERCISES"
				+ " WHERE USER_ID=?"
				+ " AND CATEGORY_ID=?"
				+ " AND IS_DELETED=0"
				+ " ORDER BY IMPLEMENTED_DATE DESC";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, userId);
			stmt.setInt(2, categoryId);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					ExerciseModel model = new ExerciseModel(
							rs.getInt("id"),
							Date.valueOf(rs.getString("implemented_date")),
							rs.getInt("time"));
					exerciseModels.add(model);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return exerciseModels;
	}

	/**
	 * 指定した運動記録の詳細を表示するメソッド
	 * @param connection Connection型 データベース接続に関する情報を格納
	 * @param id int型 ID 選択された運動記録のレコードのID
	 * @param userId int型 ユーザーID ログイン中のユーザーの情報から取得
	 * @param categoryId int型 カテゴリーID 選択中のカテゴリーの情報から取得
	 * @return model ExerciseModel型 運動記録の情報が入った配列
	 */
	public ExerciseModel show(Connection connection, int id, int userId, int categoryId) {
		ExerciseModel detailedExerciseModel = new ExerciseModel();
		String sql = "SELECT"
				+ " IMPLEMENTED_DATE"
				+ ",TIME"
				+ ",CONTENT"
				+ ",COMMENT"
				+ " FROM"
				+ " EXERCISES"
				+ " WHERE ID=?"
				+ " AND USER_ID=?"
				+ " AND CATEGORY_ID=?"
				+ " AND IS_DELETED=0";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, id);
			stmt.setInt(2, userId);
			stmt.setInt(3, categoryId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					detailedExerciseModel.setImplementedDate(Date.valueOf(rs.getString("implemented_date")));
					detailedExerciseModel.setTime(rs.getInt("time"));
					detailedExerciseModel.setContent(rs.getString("content"));
					detailedExerciseModel.setComment(rs.getString("comment"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return detailedExerciseModel;
	}

	/**
	 * 運動記録を作成するメソッド
	 * @param connection Connection型 データベース接続に関する情報を格納
	 * @param userId int型 ユーザーID ログイン中のユーザーの情報から取得
	 * @param categoryId カテゴリーID 選択中のカテゴリーの情報から取得
	 * @param implementedDate Date型 実施日を登録する
	 * @param time int型 運動時間(分) 正の整数を登録する
	 * @param content String型 運動内容 必須項目
	 * @param comment String型 感想 nullでも可能
	 * @return boolean型 SQLが実行されればtrueを返す
	 */
	public boolean create(Connection connection, int userId, int categoryId, Date implementedDate, int time,
			String content, String comment) {

		String sql = "INSERT INTO EXERCISES"
				+ "(USER_ID"
				+ ",CATEGORY_ID"
				+ ",IMPLEMENTED_DATE"
				+ ",TIME"
				+ ",CONTENT"
				+ ",COMMENT"
				+ ",IS_DELETED)"
				+ " VALUES"
				+ " (?,?,?, ?, ?, ?, 0)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, userId);
			stmt.setInt(2, categoryId);
			stmt.setDate(3, implementedDate);
			stmt.setInt(4, time);
			stmt.setString(5, content);
			stmt.setString(6, comment);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 同一日付の運動記録を調べる
	 * @param connection Connection型 データベース接続に関する情報を格納
	 * @param userId int型 ユーザーID ログイン中のユーザーの情報から取得
	 * @param categoryId int型 カテゴリーID 選択中のカテゴリーの情報から取得
	 * @param implementedDate Date型 実施日を登録する
	 * @return registeredExercise ExerciseModel型 同一日付の運動記録の情報が入った配列
	 */
	public ExerciseModel findByDate(Connection connection, int userId, int categoryId, Date implementedDate) {
		ExerciseModel registeredExercise = null;
		//セレクト文を準備する
		String sql = "SELECT"
				+ " ID"
				+ ",IMPLEMENTED_DATE"
				+ ",TIME"
				+ " FROM EXERCISES"
				+ " WHERE"
				+ " USER_ID=?"
				+ " AND CATEGORY_ID=?"
				+ " AND IMPLEMENTED_DATE=?"
				+ " AND IS_DELETED=0"
				+ " ORDER BY IMPLEMENTED_DATE DESC";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, userId);
			stmt.setInt(2, categoryId);
			stmt.setDate(3, implementedDate);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					registeredExercise = new ExerciseModel(
							rs.getInt("id"),
							Date.valueOf(rs.getString("implemented_date")),
							rs.getInt("time"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return registeredExercise;

	}

	/**
	 * 運動記録を更新するメソッド 
	 * @param connection Connection型 データベース接続に関する情報を格納
	 * @param implementedDate 実施日 Date型の日付を登録する
	 * @param time int型 運動時間(分) 正の整数を登録する
	 * @param content String型 運動内容 必須項目
	 * @param comment String型 感想 nullでも可
	 * @param updatedDateTime Timestamp型 更新した日を登録
	 * @param id int型 運動記録のID
	 * @return boolean型 SQLが実行されればtrueを返す
	 */
	public boolean update(Connection connection, Date implementedDate, int time, String content, String comment, int id) {
		String sql = "UPDATE EXERCISES"
				+ " SET IMPLEMENTED_DATE=?"
				+ ", TIME=?"
				+ ", CONTENT=?"
				+ ", COMMENT=?"
				+ " WHERE ID=?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setDate(1, implementedDate);
			stmt.setInt(2, time);
			stmt.setString(3, content);
			stmt.setString(4, comment);
			stmt.setInt(5, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 運動記録を削除するメソッド
	 * @param connection Connection型 データベース接続に関する情報を格納
	 * @param updatedDateTime Timestamp型 更新した日を登録
	 * @param id int型 ID 運動記録のID
	 * @return boolean型 SQLが実行されればtrueを返す
	 */
	public boolean delete(Connection connection, int id) {
		String sql = "UPDATE EXERCISES"
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
	 * 全てのカテゴリーの運動記録を日付にもとづいて抽出し
	 * 各カテゴリーの運動時間を配列に格納するメソッド 
	 * @param connection Connection型 データベース接続に関する情報を格納
	 * @param userId int型 ユーザーID ログイン中のユーザーの情報から取得
	 * @return allExerciseModels List<ExerciseModel>型 運動記録の一覧の情報が入った多次元配列
	 */
	public List<ExerciseModel> findAllExercises(Connection connection, int userId) {
		List<ExerciseModel> allExerciseModels = new ArrayList<ExerciseModel>();
		//本日の日付を入れる
		LocalDate today = LocalDate.now();
		//日付を入れる配列を用意する
		List<LocalDate> dateList = new ArrayList<>();
		//過去30日分の日付を配列に入れる
		for (int i = 0; i < 30; i++) {
			dateList.add(today.minusDays(i));
		}
		List<java.sql.Date> dates = new ArrayList<>();
		for (int i = 0; i < 30; i++) {
			java.sql.Date sqlDate = java.sql.Date.valueOf(dateList.get(i));
			dates.add(sqlDate);
		}
		//日付を指定して取得
		String sql = "SELECT IMPLEMENTED_DATE"
				+ ",(SELECT TIME"
				+ " FROM EXERCISES"
				+ " WHERE USER_ID=?"
				+ " AND CATEGORY_ID=1"
				+ " AND IMPLEMENTED_DATE=?"
				+ " AND IS_DELETED=0)"
				+ " AS TIME1"
				+ ",(SELECT TIME"
				+ " FROM EXERCISES"
				+ " WHERE USER_ID=?"
				+ " AND CATEGORY_ID=2"
				+ " AND IMPLEMENTED_DATE=?"
				+ " AND IS_DELETED=0)"
				+ " AS TIME2"
				+ ",(SELECT TIME"
				+ " FROM EXERCISES"
				+ " WHERE USER_ID=?"
				+ " AND CATEGORY_ID=3"
				+ " AND IMPLEMENTED_DATE=?"
				+ " AND IS_DELETED=0)"
				+ " AS TIME3"
				+ " FROM EXERCISES"
				+ " WHERE USER_ID=?"
				+ " AND IMPLEMENTED_DATE=?"
				+ " AND IS_DELETED=0"
				+ " LIMIT 1;";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			for (int i = 0; i < 30; i++) {
				stmt.setInt(1, userId);
				stmt.setDate(2, dates.get(i));
				stmt.setInt(3, userId);
				stmt.setDate(4, dates.get(i));
				stmt.setInt(5, userId);
				stmt.setDate(6, dates.get(i));
				stmt.setInt(7, userId);
				stmt.setDate(8, dates.get(i));
				try (ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {
						ExerciseModel model = new ExerciseModel(
								Date.valueOf(rs.getString("implemented_date")),
								rs.getInt("time1"),
								rs.getInt("time2"),
								rs.getInt("time3"));
						allExerciseModels.add(model);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return allExerciseModels;
	}

	/**
	 * 前回運動した日の運動時間を抽出するメソッド
	 * @param connection Connection型 データベース接続に関する情報を格納
	 * @param userId int型 ユーザーID ログイン中のユーザーの情報から取得
	 * @return lastTimeExercise ExerciseModel型 運動実施日・時間の情報が入った配列
	 */
	public ExerciseModel findLastTimeExercise(Connection connection, int userId) {
		ExerciseModel lastTimeExercise = null;
		String sql = "SELECT IMPLEMENTED_DATE,"
				+ "(SELECT TIME"
				+ " FROM EXERCISES"
				+ " WHERE USER_ID=?"
				+ " AND CATEGORY_ID=1"
				+ " AND IS_DELETED=0"
				+ " ORDER BY IMPLEMENTED_DATE DESC"
				+ "  LIMIT 1)"
				+ " AS TIME1"
				+ ",(SELECT TIME"
				+ " FROM EXERCISES"
				+ " WHERE USER_ID=?"
				+ " AND CATEGORY_ID=2"
				+ " AND IS_DELETED=0"
				+ " ORDER BY IMPLEMENTED_DATE DESC"
				+ "  LIMIT 1)"
				+ " AS TIME2"
				+ ",(SELECT TIME"
				+ " FROM EXERCISES"
				+ " WHERE USER_ID=?"
				+ " AND CATEGORY_ID=3"
				+ " AND IS_DELETED=0"
				+ " ORDER BY IMPLEMENTED_DATE DESC"
				+ " LIMIT 1)"
				+ " AS TIME3"
				+ " FROM EXERCISES"
				+ " WHERE USER_ID=?"
				+ " AND IS_DELETED=0"
				+ " ORDER BY IMPLEMENTED_DATE DESC"
				+ "  LIMIT 1";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, userId);
			stmt.setInt(2, userId);
			stmt.setInt(3, userId);
			stmt.setInt(4, userId);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					lastTimeExercise = new ExerciseModel(
							Date.valueOf(rs.getString("implemented_date")),
							rs.getInt("time1"),
							rs.getInt("time2"),
							rs.getInt("time3"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lastTimeExercise;
	}
}
