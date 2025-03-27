<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/head.jsp"%>
<title>運動記録</title>

</head>
<body>
	<div class="container mt-5">
		<h1 class="text-center text-warning py-2">${selectCategory.categoryName}の運動記録</h1>
		<c:choose>
  <c:when test="${not empty exerciseModels}">
		<div class="row my-2 justify-content-center">
			<div class="col-10 table-responsive">
				<table class="table-striped table-bordered">
					<tr class="scope-row">
						<th class="text col-4">日付</th>
						<th class="text col-3">時間</th>
						<th class="text col-1">詳細</th>
						<th class="text col-1">編集</th>
						<th class="text col-1">削除</th>
					</tr>
					<c:forEach var="exerciseModel" items="${exerciseModels}">
						<tr class="scope-row">
							<fmt:formatDate var="date" value="${exerciseModel.implementedDate}"
								pattern="yyyy/MM/dd" />
							<td class="text col-4"><c:out value="${date}" /></td>
							<td class="text col-3"><c:out value="${exerciseModel.time}" />分</td>
							<td class="col-1"><form action="./ExerciseConfirm"
									method="get">
									<input type="hidden" name="id"
										value="<c:out value="${exerciseModel.id}" />">
									<button type="submit" class="btn btn-success">詳細</button>
								</form></td>
							<td class="col-1"><form
									action="./ExerciseUpdate?categoryId=${categoryId}" method="get">
									<input type="hidden" name="id"
										value="<c:out value="${exerciseModel.id}" />">
									<button type="submit" class="btn btn-warning text-white">編集</button>
								</form></td>
							<td class="col-1"><form action="./ExerciseDelete"
									method="get">
									<input type="hidden" name="id"
										value="<c:out value="${exerciseModel.id}" />">
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
  <p class="text text-center">※運動記録が登録されていません</p>
  </c:otherwise>
</c:choose>
		<div class="row mt-2">
		<div class="col-9"></div>
			<a class="btn btn-primary"
				href="./ExerciseRegister?categoryId=${categoryId}">記録作成</a>
		</div>
		<div class="row mt-2">
			<div class="col-9"></div>
			<a class="text text-dark pr-4" style="text-decoration: underline;"
				href="./Select">戻る</a><a class="text text-dark"
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