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
import model.UserModel;

/**
 * Servlet implementation class ExerciseDeleteServlet
 */
@WebServlet("/ExerciseDelete")
public class ExerciseDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			request.setCharacterEncoding("UTF-8");
			//送信されてきたidを取得する
			HttpSession session = request.getSession();

			//指定された運動記録のIDを取得する
			int id = Integer.parseInt(request.getParameter("id"));
			session.setAttribute("id", id);

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

			ExerciseLogic logic;
			logic = new ExerciseLogic();

			boolean result = logic.delete(id);

			//削除処理が成功した場合
			if (result) {
				int categoryId = selectCategory.getId();
				response.sendRedirect("./Exercise?categoryId=" + categoryId);

				return;
			//削除処理が失敗した場合
			} else {
				// エラーページへフォワードする。
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/error.jsp");
				dispatcher.forward(request, response);

				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/error.jsp");
			dispatcher.forward(request, response);

			return;
		}
	}
}