<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/jsp/include/head.jsp"%>
<title>基本情報の登録</title>
</head>
<body>
	<div class="container mt-5">
		<h1 class="text-center text-warning py-2">基本情報の登録</h1>
		<c:if test="${not empty Msg}">
			<p class="text-center text-danger">${Msg }</p>
		</c:if>
		<form action="./BasicInfoRegister" method="post">
			<input type="hidden" name="token" value="${token}">
<div class="row my-2">
				<label class="text text-center col-4 col-form-label" for="height">身長</label>
				<input class="col-4 form-control" id="height" type="text"
					name="height" value="<c:out value="${information.height}"/>">
			</div>

<div class="row my-2">
				<label class="text text-center col-4 col-form-label" for="targetWeight">目標体重</label>
				<input class="col-4 form-control" id="targetWeight" type="text"
					name="targetWeight" value="<c:out value="${information.targetWeight}"/>">
			</div>
			<p class="text-center text-danger">${errors.targetWeight}</p>
			<div class="text-right my-2">
				<div class="col-10"></div>
				<button type="submit" class="btn btn-primary">登録</button>
			</div>
	</div>
	<div class="row mt-2">
		<div class="col-9"></div>
		<a class="text text-dark pr-4" style="text-decoration: underline;"
			href="./Management">戻る</a> <a
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