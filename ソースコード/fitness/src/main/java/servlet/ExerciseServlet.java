package servlet;

import java.io.IOException;
import java.util.List;

import org.apache.tomcat.jakartaee.commons.lang3.StringUtils;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logic.CategoryLogic;
import logic.ExerciseLogic;
import model.CategoryModel;
import model.ExerciseModel;
import model.UserModel;

/**
 * Servlet implementation class ExerciseServlet
 */
@WebServlet("/Exercise")
public class ExerciseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//セッションに保存したメッセージの破棄
			HttpSession session = request.getSession();
			session.removeAttribute("Msg");

			//カテゴリーIDの取得
			request.setCharacterEncoding("UTF-8");
			int categoryId;
			//カテゴリーIDが送信されていない場合は、セッションから情報を取得
			if (StringUtils.isEmpty(request.getParameter("categoryId"))) {
				CategoryModel selectCategory = (CategoryModel) session.getAttribute("selectCategory");
				categoryId = selectCategory.getId();
				session.setAttribute("categoryId", categoryId);
				//カテゴリーIDをリクエストパラメータから取得する

			} else {
				categoryId = Integer.parseInt(request.getParameter("categoryId"));
				session.setAttribute("categoryId", categoryId);
			}
			CategoryLogic categoryLogic = new CategoryLogic();

			//選択されているカテゴリーの情報を保持する
			CategoryModel selectCategory = categoryLogic.getCategory(categoryId);
			session.setAttribute("selectCategory", selectCategory);

			//ログイン中のユーザーを取得・保持する
			UserModel loginUser = (UserModel) session.getAttribute("loginUser");
			session.setAttribute("loginUser", loginUser);

			//ログイン中のユーザーの情報がない時
			if (loginUser == null) {
				String errorMsg = "ログインし直して下さい。";
				session.setAttribute("Msg", errorMsg);
			}

			int userId = loginUser.getId();

			//運動記録一覧(カテゴリー毎)を取得する
			ExerciseLogic exerciseLogic = new ExerciseLogic();
			List<ExerciseModel> exerciseModels = exerciseLogic.getAllByCategory(userId, categoryId);
			request.setAttribute("exerciseModels", exerciseModels);

			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/exercise.jsp");
			dispatcher.forward(request, response);

			return;
			//エラーが生じた場合、エラーページに遷移する
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/error.jsp");
			dispatcher.forward(request, response);

			return;
		}
	}
}
