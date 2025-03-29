<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/head.jsp"%>
<title>運動記録の追加</title>
</head>
<body>
	<div class="container mt-5">
		<h1 class="text-center text-warning py-2">${selectCategory.categoryName}の記録の作成</h1>
		
			<c:if test="${not empty Msg}">
				<p class="text-center text-danger">${Msg }</p>
			</c:if>
		<form action="./ExerciseRegister" method="post">
			<input type="hidden" name="token" value="${token}">
			<div class="row my-2">
				<label class="text text-center col-4 col-form-label"
					for="implementedDate">実施日</label> <input
					class="col-4 form-control" type="date" name="implementedDate"
					id="implementedDate"
					value="<c:out value="${exercise.implementedDate}"/>">
			</div>
			<p class="text-center text-danger">${errors.implementedDate}</p>
			<div class="row my-2">
				<label class="text text-center col-4 col-form-label" for="time">時間(分)</label>
				<input class="col-4 form-control" id="time" type="text"
					name="time" value="<c:out value="${exercise.time}"/>">
			</div>
			<p class="text-center text-danger">${errors.time}</p>
			<div class="row my-2">
				<label class="text text-center col-4 col-form-label" for="content">内容</label>
				<textarea class="col-6 form-control" id="content" name="content"><c:out
						value="${exercise.content}" /></textarea>
			</div>
				<p class="text-center text-danger">${errors.content}</p>
			<div class="row my-2">
				<label class="text text-center col-4 col-form-label" for="comment">感想</label>
				<textarea class="col-6 form-control" name="comment" id="comment"><c:out
							value="${exercise.comment}" /></textarea>
							</div>
			<div class="text-right my-2">
			<div class="col-10"></div>
				<button type="submit" class="btn btn-primary">登録</button>
			</div>
	</div>
	</div>
	</div>
	<div class="row mt-2">
		<div class="col-9"></div>
		<a class="text text-dark pr-4" style="text-decoration: underline;"
			href="./Exercise?categoryId=${selectCategory.id}">戻る</a> <a
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