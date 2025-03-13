<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/head.jsp"%>
<title>ユーザー情報編集</title>
</head>
<body>
	<div class="container mt-5">
		<h1 class="text-center text-warning py-2">ユーザー情報編集</h1>
			<c:if test="${not empty Msg}">
				<p class="text-center text-danger">${Msg }</p>
			</c:if>
		<form action="./UserUpdate" method="post">
			<input type="hidden" name="token" value="${token}">
			<div class="row my-2">
				<label for="mail" class="text-center col-sm-4 col-form-label">メールアドレス</label>
				<input class="form-control col-sm-6" type="text" name="mail"
					placeholder="${loginUser.mail}"
					value="<c:out value="${user.mail}"/>">
			</div>
			<p class="text-center text-danger">${errors.mail}</>
			<div class="row my-2">
				<label class="text-center col-sm-4 col-form-label">パスワード</label> <input
					class="form-control col-sm-6" type="password" name="pass">
				</th>
			</div>
			<p class="text-center text-danger">${errors.pass}
			<div class="row my-2">
				<label class="text-center col-sm-4 col-form-label">ユーザー名：</label> <input
					class="form-control col-sm-6" type="text" name="userName"
					placeholder="${loginUser.userName}"
					value="<c:out value="${user.userName}"/>">
			</div>
			<p class="text-center text-danger">${errors.userName}</p>
			<div class="row text-right mb-3">
				<div class="col-sm-10"></div>
				<button type="submit" class="btn btn-primary">送信</button>
			</div>
	</div>
	<div class="row mt-2">
		<div class="col-9"></div>
		<a class="text text-dark" style="text-decoration: underline;"
			href="./Main">TOPへ</a>
	</div>
	<div class="row mt-2">
		<div class="col-lg-9 col-7"></div>
		<div class="font-weight-bold">${loginUser.userName}さん</div>
	</div>
	<div class="row mt-2">
		<div class="col-lg-9 col-7"></div>
		<a class="btn btn-secondary" href="./Logout">ログアウト</a>
	</div>
	</div>
</body>
</html>