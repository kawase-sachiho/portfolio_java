<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/head.jsp"%>
<title>健康状態の記録</title>
</head>
<body>
	<div class="container mt-5">
		<h1 class="text-center text-warning py-2">健康状態の記録一覧</h1>
		<p class="sub-text text-center">
			最大90件表示します。BMI25以上は<span class="text-danger">赤字</span>で表示します。
		</p>
		<c:choose>
			<c:when test="${not empty healthRecordModels}">
				<div class="row my-2 justify-content-center">
					<div class="table-responsive">
						<table class="table-striped table-bordered">
							<tr class="scope-row">
								<th class="text col-3">日付</th>
								<th class="text col-3">体重</th>
								<th class="text col-3">BMI</th>
								<th class="text col-1">編集</th>
								<th class="text col-1">削除</th>
							</tr>
							<c:forEach var="healthRecordModel" items="${healthRecordModels}">
								<tr class="scope-row">
									<fmt:formatDate var="date"
										value="${healthRecordModel.registrationDate}"
										pattern="yyyy/MM/dd" />
									<td class="text col-3"><c:out value="${date}" /></td>
									<td class="text col-3"><c:out
											value="${healthRecordModel.weight}" />㎏</td>
									<td
										class="text col-3<c:if test="${healthRecordModel.bmi>25}"> text-danger</c:if>">${healthRecordModel.bmi}</td>
									<td class="col-1"><form action="./HealthUpdate"
											method="get">
											<input type="hidden" name="id"
												value="<c:out value="${healthRecordModel.id}" />">
											<button type="submit" class="btn btn-warning text-white">編集</button>
										</form></td>
									<td class="col-1"><form action="./HealthDelete"
											method="get">
											<input type="hidden" name="id"
												value="${healthRecordModel.id}">
											<button type="submit" class="btn btn-danger"
												onclick="return confirm('本当に削除しますか？');">削除</button>
										</form></td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<p class="text text-center">※健康状態の記録が登録されていません</p>
			</c:otherwise>
		</c:choose>

		<p class="mt-4 text font-weight-bold">BMI早見表</p>
		<div class="row mb-2">
			<div class="table-responsive">
				<table class="table-striped table-bordered">
					<tr class="scope-row">
						<th class="text col-4">BMI</th>
						<th class="text col-2">体型</th>
					</tr>
					<tr class="scope-row">
						<td class="text col-4">18.49以下</td>
						<td class="text col-2">痩せ</td>
					</tr>
					<tr class="scope-row">
						<td class="text col-4">18.50 ～ 24.99</td>
						<td class="text col-2">標準</td>
					</tr>
					<tr class="scope-row">
						<td class="text text-danger col-4">25.00以上</td>
						<td class="text text-danger col-2">肥満</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="row mt-2">
			<div class="col-9"></div>
			<a class="btn btn-primary" href="./HealthRegister">健康状態を記録する</a>
		</div>
		<div class="row mt-2">
			<div class="col-9"></div>
			<a class="text text-dark pr-4" style="text-decoration: underline;"
				href="./Management">戻る</a><a class="text text-dark"
				style="text-decoration: underline;" href="./Main">TOPへ</a>
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