<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/head.jsp"%>
<title>運動記録の詳細</title>
</head>
<body>
	<div class="container mt-5">
		<h1 class="text-center text-warning py-2">${selectCategory.categoryName}の記録の詳細</h1>
		<div class="row my-2 justify-content-center">
			<div class="col-10 table-responsive">
				<table>
					<tr class="scope-row">
						<th class="text col-2">日付</th>
						<fmt:formatDate var="date" value="${detailedExerciseModel.implementedDate}"
							pattern="yyyy/MM/dd" />
						<td class="text col-8">${date }</td>
					</tr>
					<tr class="scope-row">
						<th class="text col-2">時間</th>
						<td class="text col-8">${detailedExerciseModel.time }分</td>
					</tr>
					<tr class="scope-row">
						<th class="text col-2">内容</th>
						<td class="text col-8">${detailedExerciseModel.content }</td>
					</tr>
					<tr class="scope-row">
						<th class="text col-2">感想</th>
						<td class="text col-8">${detailedExerciseModel.comment }</td>
				</table>
			</div>
		</div>
	</div>
	<div class="row mt-2">
		<div class="col-9"></div>
		<div>
			<a class="text text-dark pr-4" style="text-decoration: underline;"
				href="./Exercise?categoryId=${selectCategory.id}">戻る</a> <a
				class="text text-dark" style="text-decoration: underline;"
				href="./Main">TOPへ</a>
		</div>
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