package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import filter.OneTimeTokenCheckFilter;
import filter.OneTimeTokenFilter;
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
import validation.ExerciseValidation;

/**
 * Servlet implementation class ExerciseRegisterServlet
 */
@WebServlet("/ExerciseRegister")
public class ExerciseRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 運動記録の新規作成ページを表示
		try {
			//トークンの生成
			OneTimeTokenFilter.tokenGenerate(request, response);

			HttpSession session = request.getSession();

			//選択されているカテゴリーの情報を取得する
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

			//運動記録の登録ページに遷移する
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/exerciseRegister.jsp");
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			OneTimeTokenCheckFilter.tokenCheck(request, response);
			//セッションに保存されたメッセージを破棄する
			HttpSession session = request.getSession();
			session.removeAttribute("Msg");
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

			//リクエストパラメータの取得
			request.setCharacterEncoding("UTF-8");
			//セッションから値を取得する
			int userId = loginUser.getId();
			int categoryId = selectCategory.getId();
			String content = request.getParameter("content");
			String comment = request.getParameter("comment");

			//Exerciseバリデーションを行う
			ExerciseValidation validate = new ExerciseValidation(request);
			Map<String, String> errors = validate.validate();

			//バリデーションエラーがあった場合
			if (validate.hasErrors()) {
				request.setAttribute("errors", errors);
				//inputタグの初期値に保存する
				Map<String, String> exercise = new HashMap<String, String>();
				exercise.put("implementedDate", request.getParameter("implementedDate"));
				exercise.put("time", request.getParameter("time"));
				exercise.put("content", content);
				exercise.put("comment", comment);
				request.setAttribute("exercise", exercise);

				// 登録ページへフォワードする
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/exerciseRegister.jsp");
				dispatcher.forward(request, response);

				return;
			}

			ExerciseLogic logic;
			logic = new ExerciseLogic();

			//登録された日付を取得する
			java.sql.Date registeredDate = java.sql.Date.valueOf(request.getParameter("implementedDate"));

			//登録された日付と同じ日付の運動記録がないかを確認する
			//ユーザー・カテゴリーも一致しているか同時に確認する
			ExerciseModel registeredExercise = logic.findByDate(userId, categoryId, registeredDate);
			if (registeredExercise != null) {
				String errorMsg = "同一の日付の運動記録が既に存在します";
				session.setAttribute("Msg", errorMsg);
				Map<String, String> exercise = new HashMap<String, String>();
				exercise.put("implementedDate", request.getParameter("implementedDate"));
				exercise.put("time", request.getParameter("time"));
				exercise.put("content", content);
				exercise.put("comment", comment);
				request.setAttribute("exercise", exercise);
				// 登録ページへフォワードする
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/exerciseRegister.jsp");
				dispatcher.forward(request, response);

				return;
			}
			java.sql.Date implementedDate = java.sql.Date.valueOf(request.getParameter("implementedDate"));
			int time = Integer.parseInt(request.getParameter("time"));

			//運動記録を登録する
			boolean result = logic.create(userId, categoryId, implementedDate, time, content, comment);

			//登録処理に成功した場合
			if (result) {
				//運動記録の一覧画面に遷移する
				response.sendRedirect("./Exercise?categoryId=" + categoryId);
			//登録処理に失敗した場合
			} else {
				//エラーページに遷移する
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/error.jsp");
				dispatcher.forward(request, response);

				return;
			}
			//エラーが生じた場合、エラーページに遷移する
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/error.jsp");
			dispatcher.forward(request, response);
			return;
		}

	}
}
