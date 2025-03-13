<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/head.jsp"%>
<title>ログインページ</title>
</head>
<body>
	<div class="container mt-5">
		<h1 class="text-center text-warning py-2">ログイン</h1>
		<form action="./Login" method="post">
			<input type="hidden" name="token" value="${token}">
				<c:if test="${not empty Msg}">
			<p class="text-center text-danger">${Msg }</p></c:if>
			<div class="row mb-3">
				<label for="mail" class="text-center col-sm-4 col-form-label">メールアドレス</label>
				<div class="col-sm-6">
					<input class="form-control" id="mail" type="text" name="mail">
				</div>
			</div>
			<div class="row mb-3">
				<label for="pass" class="text-center col-sm-4 col-form-label">パスワード</label>
				<div class="col-sm-6">
					<input class="form-control" id="pass" type="password" name="pass">
				</div>
			</div>	
			<div class="text-right mb-3">
				<button type="submit" class="btn btn-primary">ログイン</button>
			</div>
		</form>

		<div class="text-right">
			<a class="btn btn-danger" href="./UserRegister">新規ユーザー登録</a>
		</div>
	</div>
</body>
</html>