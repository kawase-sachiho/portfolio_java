<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/head.jsp"%>
<title>TOPページ</title>
</head>
<body>
	<div class="container mt-4">
		<h1 class="text-center text-warning py-2">フィットネスアプリ</h1>
		<div class="row mb-2 justify-content-center">
			<a class="col-8 btn btn-lg btn-danger" href="./Select">運動記録</a>
		</div>
		<div class="row mb-2 justify-content-center">
			<a class="col-8 btn btn-lg btn-info" href="./History">運動履歴</a>
		</div>
		<div class="row mb-2 justify-content-center">
			<a class="col-8 btn btn-lg btn-success text-white" href="./Management">健康管理</a>
		</div>
		<div class="row mb-2 justify-content-center">
			<c:if test="${lastTimeExercise!=null}">
			<div class="col-8">
				<p class="text text-center">前回の運動記録</p>
				<li style="list-style: none;"><fmt:formatDate var="exerciseDate"
						value="${lastTimeExercise.implementedDate}" pattern="yyyy/MM/dd" />
					<ul class="sub-text">${exerciseDate}
					</ul>
					<ul class="sub-text">有酸素運動：${lastTimeExercise.time1}分
					</ul>
					<ul class="sub-text">筋トレ：${lastTimeExercise.time2 }分
					</ul>
					<ul class="sub-text">ストレッチ：${lastTimeExercise.time3}分
					</ul></li>
			</div>
			</c:if>
			<c:if test="${lastTimeRecord!=null}">
			<div class="col-8">
			<p class="text text-center">健康状態の記録</p>
				<li style="list-style: none;"><fmt:formatDate var="healthDate"
						value="${lastTimeRecord.registrationDate}" pattern="yyyy/MM/dd" />
					<ul class="sub-text">${healthDate}
					</ul>
					<ul class="sub-text">身長：${healthInformation.height }kg
					</ul></li>
					<ul class="sub-text">体重：${lastTimeRecord.weight }kg
					</ul></li>
					<ul class="sub-text">BMI：${convertBmi }
					</ul></li>
					</div>
			</c:if>
		</div>
		<div class="row mt-2 justify-content-center">
			<a class="col-8 btn btn btn-warning text-white" href="./UserUpdate">ユーザー情報編集</a>
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