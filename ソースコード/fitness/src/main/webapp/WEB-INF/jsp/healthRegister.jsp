<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/jsp/include/head.jsp"%>
<title>健康状態の記録の登録</title>
</head>
<body>
	<div class="container mt-5">
		<h1 class="text-center text-warning py-2">健康状態の記録の作成</h1>
		<c:if test="${not empty Msg}">
			<p class="text-center text-danger">${Msg }</p>
		</c:if>
		<form action="./HealthRegister" method="post">
			<input type="hidden" name="token" value="${token}">
			<div class="row my-2">
				<label class="text text-center col-4 col-form-label"
					for="registrationDate">登録日</label> <input
					class="col-4 form-control" type="date" name="registrationDate"
					id="registrationDate"
					value="<c:out value="${health.registrationDate}"/>">
			</div>

			<p class="text-center text-danger">${errors.registrationDate}</p>
			<div class="row my-2">
				<label class="text text-center col-4 col-form-label" for="weight">体重</label>
				<input class="col-4 form-control" id="weight" type="text"
					name="weight" value="<c:out value="${health.weight}"/>">
			</div>
			<p class="text-center text-danger">${errors.weight}</p>
			<div class="text-right my-2">
				<div class="col-10"></div>
				<button type="submit" class="btn btn-primary">登録</button>
			</div>
	</div>
	<div class="row mt-2">
		<div class="col-9"></div>
		<a class="text text-dark pr-4" style="text-decoration: underline;"
			href="./Health">戻る</a> <a
			class="text text-dark" style="text-decoration: underline;"
			href="./Main">TOPへ</a>
	</div>

	<div class="row mt-2">
		<div class="col-9"></div>
		<div class="font-weight-bold">${loginUser.userName}さん</div>
	</div>
	<div class="row mt-2">
		<div class="col-9"></div>
		<a class="btn btn-secondary" href="./Logout">ログアウト</a>
	</div>
	</div>
</body>
</html>