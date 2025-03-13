<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/head.jsp"%>
<title>運動履歴</title>
</head>
<body>
	<div class="container mt-5">
		<h1 class="text-center text-warning py-2">運動履歴</h1>
		<p class="sub-text text-center">最大30日分表示します。現在<span class="font-weight-bold">${totalDays}日分</span>登録されています。</p>
			<div class="table-responsive justify-content-center">
				<table>
				<thead class="table-secondary">
					<tr class="scope-row">
						<th class="table-text no-wrap col-2 table-bordered">日付</th>
						<th class="table-text no-wrap col-2 table-bordered">有酸素運動</th>
						<th class="table-text no-wrap col-2 table-bordered">筋トレ</th>
						<th class="table-text no-wrap col-2 table-bordered">ストレッチ</th>
						<th class="table-text no-wrap col-2 table-bordered">合計</th>
					</tr>
					</thead>
					<c:forEach var="allExerciseModel" items="${allExerciseModels}">
						<fmt:formatDate var="date" value="${allExerciseModel.implementedDate}"
							pattern="yyyy/MM/dd" />
						<tr class="scope-row">
							<td class="text col-2 table-bordered"><c:out value="${date}" /></td>
							
							<td class="text col-2 table-bordered"><c:out value="${allExerciseModel.time1}分" /></td>
							<td class="text col-2 table-bordered"><c:out value="${allExerciseModel.time2}分" /></td>
							<td class="text col-2 table-bordered"><c:out value="${allExerciseModel.time3}分" /></td>
							<td class="text col-2 table-bordered"><c:out
									value="${allExerciseModel.time1+allExerciseModel.time2+allExerciseModel.time3}分" /></td>
						</tr>
					</c:forEach>
					<tr class="scope-row">
							<th class="table-text col-2 table-bordered"><c:out value="合計" /></td>
							<td class="text col-2 table-bordered"><c:out value="${convertedTime1Sum}" /></td>
							<td class="text col-2 table-bordered"><c:out value="${convertedTime2Sum}" /></td>
							<td class="text col-2 table-bordered"><c:out value="${convertedTime3Sum}" /></td>
							<td class="text col-2 table-bordered"><c:out
									value="${convertedAllTimeSum}" /></td>
						</tr>
				</table>
			</div>
		<div class="row mt-2">
			<div class="col-9"></div>
			<a class="text text-dark" style="text-decoration: underline;"
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