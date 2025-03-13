<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/head.jsp"%>
<title>ユーザー登録</title>
</head>
<body>
	<div class="container mt-5">
		<h1 class="text-center text-warning py-2">ユーザー登録</h1>

		<form action="./UserRegister" method="post">
			<input type="hidden" name="token" value="${token}">

				<c:if test="${not empty Msg}">
				<p class="text-center text-danger">	${Msg }</p>
			</c:if>
			<div class="row my-2">
				<label class="text-center col-sm-4 col-form-label" for="mail">メールアドレス</label>
				<input class="col-sm-6 form-control" type="text" name="mail"
					id="mail" value="<c:out value="${user.mail}"/>">
			</div>
			<p class="text-center text-danger">${errors.mail}</p>
			<div class="row my-2">
				<label class="text-center col-sm-4 col-form-label" for="pass">パスワード</label>
				<input class="col-sm-6 form-control" type="password" name="pass"
					id="pass" value="<c:out value="${user.pass}"/>">
			</div>

			<p class="text-center text-danger">${errors.pass}</p>


			<div class="row my-2">
				<label class="text-center col-sm-4 col-form-label" for="iserName">ユーザー名：</label>
				<input class="col-sm-6 form-control" type="text" name="userName"
					id="userName" value="<c:out value="${user.userName}"/>">
				</td>
			</div>

			<p class="text-center text-danger">${errors.userName}</p>

			<div class="text-right my-2">
			<div class="col-sm-10"></div>
				<button type="submit" class="btn btn-primary">登録</button>
			</div>

		</form>
		</table>
	</div>
	</div>
	<div class="row mt-2">
		<div class="col-sm-10"></div>
		<a class="text text-dark" style="text-decoration: underline;"
			href="./Login">戻る</a>
	</div>
	</div>
</body>
</html>