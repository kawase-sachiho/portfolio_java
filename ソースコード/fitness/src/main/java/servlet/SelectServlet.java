package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UserModel;

/**
 * Servlet implementation class SelectServlet
 */
@WebServlet("/Select")
public class SelectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
		HttpSession session = request.getSession();			
		//ログイン中のユーザーを取得・保持する
		UserModel loginUser = (UserModel) session.getAttribute("loginUser");
		session.setAttribute("loginUser", loginUser);

		if (loginUser == null) {
			String errorMsg = "ログインし直して下さい。";
			session.setAttribute("Msg", errorMsg);
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/error.jsp");
			dispatcher.forward(request, response);

			return;
		}
		
		//運動記録のセレクト画面へ遷移
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/select.jsp");
		dispatcher.forward(request, response);

		return;
	}catch (Exception e) {
		e.printStackTrace();
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/error.jsp");
		dispatcher.forward(request, response);

		return;
	}
		}

}
