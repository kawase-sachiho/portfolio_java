<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/head.jsp"%>
<title>運動記録の選択</title>
</head>
<body>
	<div class="container text-center my-4">
		<h1 class="text-warning py-2">運動記録の選択</h1>

		<div class="row mt-2 justify-content-center">			
			<a class="btn btn-lg btn-primary text col-8"
				href="./Exercise?categoryId=1">有酸素運動</a>
			</div>
		<div class="row mt-2 justify-content-center">
			<a class="btn btn-lg btn-danger text col-8"
				href="./Exercise?categoryId=2">筋トレ</a>
		</div>
		<div class="row mt-2 justify-content-center">
			<a class="btn btn-lg btn-warning text-white text col-8"
				href="./Exercise?categoryId=3">ストレッチ</a>
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
</body>
</html>