<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="userInfo" value='<%=com.seya330.ranchat.user.util.UserUtil.getUserInSession()%>' />
<header id="header">
	<div class="inner">
		<a href="/" class="logo"><strong>Sourced</strong> by seya330</a>
		<nav id="nav">
			<a href="/">Home</a>
			<a href="/randomChat">랜덤채팅</a>
			<c:choose>
				<c:when test="${empty userInfo}">
					<a href="/view/user/loginView">로그인</a>
					<a href="/view/user/add">회원가입</a>
				</c:when>
				<c:otherwise>
					<a href="/view/chat/groupChat">단체채팅</a>
					<a href="/view/user/logout">로그아웃</a>
				</c:otherwise>
			</c:choose>
		</nav>
		<a href="#navPanel" class="navPanelToggle"><span class="fa fa-bars"></span></a>
	</div>
</header>