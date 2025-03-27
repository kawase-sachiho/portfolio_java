package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logic.HealthRecordLogic;
import model.UserModel;

/**
 * Servlet implementation class HealthDeleteServlet
 */
@WebServlet("/HealthDelete")
public class HealthDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HealthDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {

			request.setCharacterEncoding("UTF-8");
			//送信されてきたidを取得する
			HttpSession session = request.getSession();

			//指定された健康状態の記録のIDを取得する
			int id = Integer.parseInt(request.getParameter("id"));
			session.setAttribute("id", id);

			//ログイン中のユーザーを取得・保持する
			UserModel loginUser = (UserModel) session.getAttribute("loginUser");
			session.setAttribute("loginUser", loginUser);

			//ログイン中のユーザーの情報がない時
			if (loginUser == null) {
				String errorMsg = "ログインし直して下さい。";
				session.setAttribute("Msg", errorMsg);
			}
			
			HealthRecordLogic logic;
			logic = new HealthRecordLogic();

			boolean result = logic.delete(id);
			//削除処理が成功した場合
			if (result) {
				response.sendRedirect("./Health");

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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
