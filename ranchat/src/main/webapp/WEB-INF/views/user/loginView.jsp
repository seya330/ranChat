<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<link rel="stylesheet" href="/resources/template/assets/css/main.css" />
	<link rel="stylesheet" type="text/css" href="/resources/ranchat/css/common.css"/>
	<!-- Scripts -->
	<script src="/resources/template/assets/js/jquery.min.js"></script>
	<script src="/resources/template/assets/js/skel.min.js"></script>
	<script src="/resources/template/assets/js/util.js"></script>
	<script src="/resources/template/assets/js/main.js"></script>
	<script src="/resources/user/loginView.js"></script>
	<script src="/resources/common/common.js"></script>
	<script>
		$(function(){
			$("#loginBtn").off().on("click", function(event){event.preventDefault();login();});
		});
	</script>
</head>
<body class="subpage">
	<%@ include file="/WEB-INF/views/chat/header.jsp"%>
	<!-- Three -->
	<div style="">
		<section class="wrapper">
			<div class="inner" style="max-width: 35em; padding : 3em; border-radius: 2em;">
				<form id="loginForm" class="">
					<div class="" style="padding-bottom: 1.5em; font-size: 30px; color: black;">
						Account Login
					</div>

					<span class="">
						Username
					</span>
					<div class="" style="margin-bottom: 2em;">
						<input id="userId" name="userId" class="" type="text" style="color: black;">
					</div>
					
					<span class="">
						Password
					</span>
					<div class="" style="margin-bottom: 2em;">
						<input id="password" name="password" class="" type="password" style="color: black;">
					</div>
					
					<div class="">
						<span class="">
							<input class="" id="ckb1" type="checkbox" name="remember-me">
							<label class="" for="ckb1">
								Remember me
							</label>
						</span>
					</div>

					<div class="">
						<button id="loginBtn" class="">
							Login
						</button>
					</div>

				</form>
			</div>
		</section>
	</div>
		
	<!-- Scripts -->
	<script src="/resources/template/assets/js/skel.min.js"></script>
	<script src="/resources/template/assets/js/util.js"></script>
	<script src="/resources/template/assets/js/main.js"></script>
</body>
</html>