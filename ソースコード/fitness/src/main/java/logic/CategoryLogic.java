package logic;

import java.sql.Connection;
import java.sql.SQLException;

import common.database.DBConnection;
import dao.CategoryDAO;
import model.CategoryModel;

public class CategoryLogic {
	/**
	 * 選択したカテゴリーの情報を取得するメソッド
	 * @param categoryId int型 カテゴリーのID 1,2,3の整数によって管理される
	 * @return selectCategory CategoryModel型 カテゴリーの情報が入った配列
	 */
	public CategoryModel getCategory(int categoryId) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			CategoryDAO dao = new CategoryDAO();
			CategoryModel selectCategory = dao.select(conn, categoryId);
			return selectCategory;
		}
	}
}
