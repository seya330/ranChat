<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>ranchat</title>
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<link rel="stylesheet" href="/resources/template/assets/css/main.css" />
	<link rel="stylesheet" type="text/css" href="/resources/ranchat/css/common.css"/>
	<!-- Scripts -->
	<script src="/resources/template/assets/js/jquery.min.js"></script>
	<script src="/resources/template/assets/js/skel.min.js"></script>
	<script src="/resources/template/assets/js/util.js"></script>
	<script src="/resources/template/assets/js/main.js"></script>
	<script>
	$(function(){
		$("#randomChatArea").on("click", function(){
			location.href="/randomChat";
		});
	});
	</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/chat/header.jsp"%>

	<!-- Banner -->
		<section id="banner">
			<div class="inner">
				<header>
					<h1>Welcome to RanChat</h1>
				</header>

				<div class="flex ">

					<div id="randomChatArea" style="cursor: pointer;">
						<span class="icon fa-random"></span>
						<h3>랜덤채팅</h3>
						<p>익명의 상대와 1대1 채팅</p>
					</div>

					<div>
						<span class="icon fa-laptop"></span>
						<h3>단체 채팅</h3>
						<p>친구들과 단체 채팅</p>
					</div>

					<div>
						<span class="icon fa-paper-plane"></span>
						<h3>클라우드</h3>
						<p>파일 저장 서비스</p>
					</div>

				</div>

				<c:if test="${empty userInfo}">
					<footer>
						<a href="/view/user/loginView" class="button">Get Started</a>
					</footer>
				</c:if>
			</div>
		</section>


	<!-- Three -->
		<section id="three" class="wrapper align-center">
			<div class="inner">
				<div class="flex flex-2">
					<article>
						<div class="image round">
							<img src="images/pic01.jpg" alt="Pic 01" />
						</div>
						<header>
							<h3>Lorem ipsum<br /> dolor amet nullam</h3>
						</header>
						<p>Morbi in sem quis dui placerat ornare. Pellentesquenisi<br />euismod in, pharetra a, ultricies in diam sed arcu. Cras<br />consequat  egestas augue vulputate.</p>
						<footer>
							<a href="#" class="button">Learn More</a>
						</footer>
					</article>
					<article>
						<div class="image round">
							<img src="images/pic02.jpg" alt="Pic 02" />
						</div>
						<header>
							<h3>Sed feugiat<br /> tempus adipicsing</h3>
						</header>
						<p>Pellentesque fermentum dolor. Aliquam quam lectus<br />facilisis auctor, ultrices ut, elementum vulputate, nunc<br /> blandit ellenste egestagus commodo.</p>
						<footer>
							<a href="#" class="button">Learn More</a>
						</footer>
					</article>
				</div>
			</div>
		</section>

	<!-- Footer -->
		<footer id="footer">
			
		</footer>
</body>
</html>