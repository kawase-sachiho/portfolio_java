package servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
 * Servlet implementation class ExerciseUpdateServlet
 */
@WebServlet("/ExerciseUpdate")
public class ExerciseUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			OneTimeTokenFilter.tokenGenerate(request, response);
			request.setCharacterEncoding("UTF-8");
			//送信されてきたidを取得する
			HttpSession session = request.getSession();

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

			//ユーザー・カテゴリーのIDを獲得する
			int userId = loginUser.getId();
			int categoryId = selectCategory.getId();
			
			ExerciseLogic logic;
			logic = new ExerciseLogic();

			//指定したIDの運動記録の情報を獲得する
			ExerciseModel exercise = logic.show(id, userId, categoryId);
			request.setAttribute("exercise", exercise);

			//運動記録の更新画面に遷移する
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/exerciseUpdate.jsp");
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
			//トークンのチェックを行う
			OneTimeTokenCheckFilter.tokenCheck(request, response);

			//エラーメッセージの削除を行う
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
			int id = Integer.parseInt(request.getParameter("id"));
			String content = request.getParameter("content");
			String comment = request.getParameter("comment");
			LocalDateTime nowDate = LocalDateTime.now();
			Timestamp updatedDateTime = Timestamp.valueOf(nowDate);

			//Exerciseバリデーションを行う
			ExerciseValidation validate = new ExerciseValidation(request);
			Map<String, String> errors = validate.validate();

			//バリデーションエラーがあった場合
			if (validate.hasErrors()) {
				request.setAttribute("errors", errors);
				//inputタグの初期値に保存する
				Map<String, String> exercise = new HashMap<String, String>();
				exercise.put("id", String.valueOf(id));
				exercise.put("implementedDate", request.getParameter("implementedDate"));
				exercise.put("time", request.getParameter("time"));
				exercise.put("content", content);
				exercise.put("comment", comment);
				request.setAttribute("exercise", exercise);

				// 登録ページへフォワードする
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/exerciseUpdate.jsp");
				dispatcher.forward(request, response);

				return;
			}
			java.sql.Date implementedDate = java.sql.Date.valueOf(request.getParameter("implementedDate"));
			int time = Integer.parseInt(request.getParameter("time"));

			ExerciseLogic logic;
			logic = new ExerciseLogic();

			java.sql.Date registeredDate = java.sql.Date.valueOf(request.getParameter("implementedDate"));

			//ユーザー・カテゴリーのIDを取得する
			int userId = loginUser.getId();
			int categoryId = selectCategory.getId();

			//登録された日付と同じ日付の運動記録がないかを確認する
			//ユーザー・カテゴリーも一致しているか同時に確認する
			ExerciseModel registeredExercise = logic.findByDate(userId, categoryId, registeredDate);

			//登録済の運動記録があった場合は、レコードのIDを取得
			if (registeredExercise != null) {
				int registeredId = registeredExercise.getId();
				//現在更新する運動記録と、登録済の運動記録のIDが一致しない場合
				if (registeredId != id) {
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
			}

			//運動記録の更新処理を行う
			boolean result = logic.update(implementedDate, time, content, comment, updatedDateTime, id);

			//更新処理に成功した場合
			if (result) {
				//運動記録の一覧画面にリダイレクトする
				response.sendRedirect("./Exercise?categoryId=" + categoryId);
			} else {
				//運動記録の更新画面にリダイレクトする
				response.sendRedirect("./ExerciseUpdate");
			}
			//エラーが発生した場合、エラーページに遷移する
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/error.jsp");
			dispatcher.forward(request, response);

			return;
		}

	}
}