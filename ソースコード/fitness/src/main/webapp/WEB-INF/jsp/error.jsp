<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/head.jsp"%>
<title>エラー発生</title>
</head>
<body>
	<div class="container mt-5">
		<h1 class="text-center text-warning py-2">エラー発生</h1>
		<p class="text text-center">エラーが発生しました。</p>					
						<c:if test="${not empty Msg}">
							<p class="text text-center text-danger">${Msg }</p>
						</c:if>
		<div class="row text-right my-2">
			<div class="col-8"></div>
			<a class="btn btn-primary" href="./Login">ログイン</a>
		</div>
	</div>
</body>
</html>