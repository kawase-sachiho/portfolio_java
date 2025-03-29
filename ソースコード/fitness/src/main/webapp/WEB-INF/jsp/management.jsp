<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
	crossorigin="anonymous">
<script th:src="@{/webjars/jquery/3.5.1/jquery.min.js}"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.bundle.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>健康管理</title>
</head>
<body>
	<div class="container text-center my-4">
		<h1 class="text-warning py-2">健康管理</h1>
		<div class="row mt-2 justify-content-center">
			<c:if test="${ not empty labels && not empty daily_data}">
				<canvas id="myLineChart"></canvas>
				<script type="text/javascript" th:inline="javascript">
					var ctx = document.getElementById("myLineChart");
					var myLineChart = new Chart(ctx, {
						type : 'line',
						data : {
							labels : [
				${labels}
					],
							datasets : [ {
								label : '体重',
								data : [
				${daily_data}
					],
								borderColor : "rgba(255,0,0,1)",
								backgroundColor : "rgba(0,0,0,0)"
							}, {
								label : '目標体重',
								data : [
				${target_data}
					],
								borderColor : "rgba(0,0,255,1)",
								backgroundColor : "rgba(0,0,0,0)"
							} ],
						},
						options : {
							title : {
								display : true,
								text : '体重推移'
							},
							scales : {
								yAxes : [ {
									ticks : {
										suggestedMax : 100,
										suggestedMin : 0,
										stepSize : 10,
										callback : function(value, index,
												values) {
											return value + 'kg'
										}
									}
								} ]
							},
						}
					});
				</script>
			</c:if>
		</div>
		<div class="row mt-2 justify-content-center">
			<a class="btn btn-lg btn-danger text col-8" href="./Health">健康状態の記録</a>
		</div>
		<div class="row mt-2 justify-content-center">
			<a class="btn btn-lg btn-primary text col-8" href="./BasicInfo">基本情報の管理</a>
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