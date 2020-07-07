<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sign Up</title>
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<link rel="stylesheet" href="/resources/template/assets/css/main.css" />
	<link rel="stylesheet" type="text/css" href="/resources/ranchat/css/common.css"/>
	<!-- Scripts -->
	<script src="/resources/template/assets/js/jquery.min.js"></script>
	<script src="/resources/template/assets/js/skel.min.js"></script>
	<script src="/resources/template/assets/js/util.js"></script>
	<script src="/resources/template/assets/js/main.js"></script>
	<script src="/resources/user/addUserView.js"></script>
	<script src="/resources/common/common.js"></script>
	<script>
	$(function(){
		$("#signUpBtn").off().on("click", function(event){event.preventDefault();addUser()});
		$("#overlapChkBtn").off().on("click", function(event){event.preventDefault();isOverlap()});
		$("#userId").off().on("keyup", function(event){event.preventDefault();overlapCheck=false;});
	});
	</script>
</head>
<body class="subpage">
	<jsp:include page="/WEB-INF/views/chat/header.jsp" />
	<!-- Three -->
	<div style="">
		<section class="wrapper">
			<div class="inner" style="max-width: 35em; padding : 3em; border-radius: 2em;">
				<form id="addUserForm" class="">
					<div class="" style="padding-bottom: 1.5em; font-size: 30px; color: black;">
						Sign Up
					</div>

					<span class="">
						User ID
					</span>
					<div class="row">
						<div class="9u 12u" style="margin-bottom: 2em;">
							<input id="userId" name="userId" class="form-control" type="text" style="color: black;" placeholder="write user id">
						</div>
						<div class="3u 12u">
					    	<button id="overlapChkBtn" class="fit" type="button" id="button-addon2" style="padding: 0;">중복</button>
						</div>
					</div>
					
					<span class="">
						Password
					</span>
					<div class="" style="margin-bottom: 2em;">
						<input id="password" class="password" type="password" name="password" style="color: black;" placeholder="write password">
					</div>
					
					<span class="">
						Confirm Password
					</span>
					<div class="" style="margin-bottom: 2em;">
						<input id="password2" class="" type="password" name="password2" style="color: black;" placeholder="write confirm password">
					</div>
					
					<div class="">
						<button id="signUpBtn" class="">
							Sign Up
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