package logic;

import java.sql.Connection;
import java.sql.SQLException;

import common.database.DBConnection;
import dao.UserDAO;
import model.UserModel;

public class UserLogic {

	/**
	 * ログイン中のユーザーの情報を獲得するメソッド
	 * @param mail String型 メールアドレスを照合
	 * @param pass String型 パスワードを照合
	 * @return loginUser UserModel型 ログイン中のユーザーの情報が入った配列
	*/
	public UserModel login(String mail, String pass) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			UserDAO dao = new UserDAO();
			UserModel loginUser = dao.findByLogin(conn, mail, pass);
			return loginUser;
		}
	}

	/**
	 * 新規ユーザーを作成するメソッド
	 * @param connection
	 * @param mail String型 メールアドレスを登録
	 * @param pass String型 パスワードを登録
	 * @param userName String型 ユーザー名を登録
	 * @return daoの実行結果を返す
	 */
	public boolean create(String mail, String pass, String userName) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			UserDAO dao = new UserDAO();
			return dao.create(conn, mail, pass, userName);
		}
	}

	/**
	 * 同一のメールアドレスのユーザーを検索するメソッド
	 * @param connection
	 * @param mail String型 メールアドレスを照合
	 * @return registeredUser UserModel型 登録済のユーザーの情報が入った配列
	 */
	public UserModel findByMail(String mail) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			UserDAO dao = new UserDAO();
			UserModel registeredUser = dao.findByMail(conn, mail);
			return registeredUser;
		}
	}

	/**
	 * ユーザー情報を更新するメソッド
	 * @param connection
	 * @param mail String型 メールアドレスを登録
	 * @param pass String型 パスワードを登録
	 * @param userName String型 ユーザー名を登録
	 * @param id int型 ユーザーのID ログイン中のユーザーから取得
	 * @return daoの実行結果を返す
	 */
	public boolean update(String mail, String pass, String userName, int id)
			throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			UserDAO dao = new UserDAO();
			return dao.update(conn, mail, pass, userName, id);
		}
	}

	/**
	 * 更新されたユーザーの情報を獲得するメソッド
	 * @param connection
	 * @param id int型 ユーザーのID ログイン中のユーザーから取得
	 * @return updatedUser UserModel型 更新されたユーザーの情報が入った配列
	 */
	public UserModel findUpdatedUser(int id) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			UserDAO dao = new UserDAO();
			UserModel updatedUser = dao.findUpdatedUser(conn, id);
			return updatedUser;
		}
	}
}
