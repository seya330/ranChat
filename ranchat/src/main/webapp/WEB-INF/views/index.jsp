<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
	ul {list-style: none;}
</style>
<script src="/static/lib/js/jquery/jquery-3.5.1.js"></script>
<script src="/static/lib/js/sockjs/sockjs.js"></script>
<script src="/static/lib/js/stomp/stomp.js"></script>

<!-- ranChat -->
<script src="/static/ranChat/js/chat/chat.js"></script>
<title>랜덤채팅</title>
</head>
<body>
	<div>채팅</div>
	<div>
	<ul id="chat"></ul>
	<input type="text" id="btn-input" ><button id="sendChatBtn" disabled>send</button>
	</div>
	<br/><br/>
	<div>
	이름 : <input type="text" id="name">
	</div>
	<div>
	<button id="connectBtn">채팅 접속</button>
	<button id="disConnectBtn" disabled>접속 해제</button>
	</div>
</body>
</html>