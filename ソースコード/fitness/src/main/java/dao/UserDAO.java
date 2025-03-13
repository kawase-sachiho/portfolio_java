package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.UserModel;

public class UserDAO {
	/**
	 * ログインしたユーザの情報を獲得するメソッド
	 * @param connection Connection型 データベース接続に関する情報を格納
	 * @param mail String型 メールアドレスを照合
	 * @param pass String型 パスワードを照合
	 * @return loginUser UserModel型 ログイン中のユーザーの情報が入った配列
	 */
	public UserModel findByLogin(Connection connection, String mail, String pass) {
		UserModel loginUser = null;
		//セレクト文を準備する
		String sql = "SELECT"
				+ " ID"
				+ ",PASS"
				+ ",MAIL"
				+ ",USER_NAME"
				+ " FROM USERS"
				+ " WHERE MAIL=?"
				+ " AND PASS=?";
		// SQLを実行する準備をする。
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, mail);
			stmt.setString(2, pass);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					loginUser = new UserModel(
							rs.getInt("id"),
							rs.getString("mail"),
							rs.getString("pass"),
							rs.getString("user_name"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return loginUser;
	}

	/**
	 * 同一のメールアドレスのユーザーを検索するメソッド
	 * @param connection Connection型 データベース接続に関する情報を格納
	 * @param mail String型 メールアドレスを照合
	 * @return registeredUser UserModel型 登録済のユーザーの情報が入った配列
	 */
	public UserModel findByMail(Connection connection, String mail) {
		UserModel registeredUser = null;
		//セレクト文を準備する
		String sql = "SELECT ID"
				+ ",MAIL"
				+ ",PASS"
				+ ",USER_NAME"
				+ " FROM USERS"
				+ " WHERE MAIL=?"
				+ " AND IS_DELETED=0";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, mail);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					registeredUser = new UserModel(
							rs.getInt("id"),
							rs.getString("mail"),
							rs.getString("pass"),
							rs.getString("user_name"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return registeredUser;
	}

	/**
	 * 新規ユーザーを作成するメソッド
	 * @param connection Connection型 データベース接続に関する情報を格納
	 * @param mail String型 メールアドレスを登録
	 * @param pass String型 パスワードを登録
	 * @param userName String型 ユーザー名を登録
	 * @return boolean型 処理が成功すればtrue
	 */
	public boolean create(Connection connection, String mail, String pass, String userName) {
		try {
			String sql = "INSERT INTO"
					+ " USERS (MAIL"
					+ ",PASS"
					+ ",USER_NAME"
					+ ",IS_DELETED)"
					+ " VALUES"
					+ " (?, ?, ?, 0)";
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				stmt.setString(1, mail);
				stmt.setString(2, pass);
				stmt.setString(3, userName);
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * ユーザー情報を更新するメソッド
	 * @param connection Connection型 データベース接続に関する情報を格納
	 * @param mail String型 メールアドレスを登録
	 * @param pass String型 パスワードを登録
	 * @param userName String型 ユーザー名を登録
	 * @param id int型 ユーザーのID ログイン中のユーザーから取得
	 * @return boolean型 処理が成功すればtrue
	 */
	public boolean update(Connection connection, String mail, String pass, String userName, int id) {
		try {
			String sql = "UPDATE USERS"
					+ " SET MAIL=?"
					+ ",PASS=?"
					+ ",USER_NAME=?"
					+ " WHERE ID=?"
					+ " AND IS_DELETED=0";
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				stmt.setString(1, mail);
				stmt.setString(2, pass);
				stmt.setString(3, userName);
				stmt.setInt(4, id);
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	/**
	 * 更新されたユーザーの情報を獲得するメソッド
	 * @param connection Connection型 データベース接続に関する情報を格納
	 * @param id int型 ユーザーのID ログイン中のユーザーから取得
	 * @return updatedUser UserModel型 更新されたユーザーの情報が入った配列
	 */
	public UserModel findUpdatedUser(Connection connection, int id) {
		UserModel updatedUser = null;
		String sql = "SELECT ID"
				+ ",MAIL"
				+ ",PASS"
				+ ",USER_NAME"
				+ " FROM USERS"
				+ " WHERE ID=?"
				+ " AND IS_DELETED=0";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					updatedUser = new UserModel(rs.getInt("id"), rs.getString("mail"), rs.getString("pass"),
							rs.getString("user_name"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return updatedUser;
	}
}
