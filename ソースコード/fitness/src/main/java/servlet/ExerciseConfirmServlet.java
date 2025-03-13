package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logic.ExerciseLogic;
import model.CategoryModel;
import model.ExerciseModel;
import model.UserModel;

/**
 * Servlet implementation class ExerciseConfirmServlet
 */
@WebServlet("/ExerciseConfirm")
public class ExerciseConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("UTF-8");
			//送信されてきたidを取得する
			int id = Integer.parseInt(request.getParameter("id"));

			HttpSession session = request.getSession();
			//カテゴリーを取得・保持する
			CategoryModel selectCategory = (CategoryModel) session.getAttribute("selectCategory");
			session.setAttribute("selectCategory", selectCategory);

			//ログイン中のユーザーを取得・保持する
			UserModel loginUser = (UserModel) session.getAttribute("loginUser");
			session.setAttribute("loginUser", loginUser);

			//ログイン中のユーザーの情報がない時
			if (loginUser == null) {
				String errorMsg = "ログインし直して下さい。";
				session.setAttribute("Msg", errorMsg);
			}

			//ログイン中のユーザー、選択されたカテゴリーのIDを取得
			int userId = loginUser.getId();
			int categoryId = selectCategory.getId();

			ExerciseLogic logic;
			logic = new ExerciseLogic();

			//指定したレコードの運動記録の詳細を獲得し、セッションに保存する
			ExerciseModel detailedExerciseModel = logic.show(id, userId, categoryId);
			request.setAttribute("detailedExerciseModel", detailedExerciseModel);

			//運動記録の詳細ページに遷移する
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/exerciseConfirm.jsp");
			dispatcher.forward(request, response);

			return;
		}
		//エラーが生じた場合、エラーページに遷移する
		catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/error.jsp");
			dispatcher.forward(request, response);

			return;
		}
	}
}