package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.CategoryModel;

public class CategoryDAO {
	/**
	 * 選択したカテゴリーの情報を取得するメソッド
	 * @param connection Connection型 データベース接続に関する情報を格納
	 * @param categoryId int型 カテゴリーのID 1,2,3の整数によって管理される
	 * @return selectCategory CategoryModel型 カテゴリーの情報が入った配列
	 */
	public CategoryModel select(Connection connection, int categoryId) {
		CategoryModel selectCategory = null;
		String sql = "SELECT ID"
				+ ",CATEGORY_NAME"
				+ " FROM CATEGORIES"
				+ " WHERE ID=?"
				+ " AND IS_DELETED=0";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, categoryId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					selectCategory = new CategoryModel(
							rs.getInt("id"),
							rs.getString("category_name"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return selectCategory;
	}
}