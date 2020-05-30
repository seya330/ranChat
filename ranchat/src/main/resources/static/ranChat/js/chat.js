//사용변수영역
var stompClient = null;
var joinInterval;
var sessionId;
var roomId;

function joinRoom(){
	var userName = $("#userName").val();
	if(!userName){
		alert("이름을 입력해 주세요.");
		return;
	}
	$.ajax({
		url: 		"/join",
		header: 	{"Content-type": "application/json"},
		data: 		{"userName": userName},
		beforeSend: function(){setConnecting(true)},
		success:	function(chatResponse){
			if(!chatResponse){
				alert("정상적으로 접속 되지 않았습니다.");
				setConnecting(false);
				return;
			}
			
			if(chatResponse.responseResult == "SUCCESS"){
				sessionId = chatResponse.sessionId;
				roomId = chatResponse.chatRoomId;
				connectSockAndSubscribe();
			}else if(chatResponse.reponseResult == "CANCEL"){
				
			}else if(chatResponse.reponseResult == "TIMEOUT"){
				
			}
		}
	});
}
function setConnecting(isConnecting){
	if(isConnecting){
		$("#connectBtn").attr("disabled", true);
		$("#disConnectBtn").attr("disabled", false);
		$("#name").attr("disabled", true);
		//접속중입니다 메세지 출력도 하면 좋음.
	}else{
		$("#connectBtn").attr("disabled", false);
		$("#disConnectBtn").attr("disabled", true);
		$("#name").attr("disabled", false);
		//접속중입니다 메세지 없애야함.
	}
}
function setConnected(isConnect){
	if(isConnect){
		$("#connectBtn").attr("disabled", true);
		$("#disConnectBtn").attr("disabled", false);
		$("#name").attr("disabled", true);
		$("#sendChatBtn").attr("disabled", false);
	}else{
		$("#connectBtn").attr("disabled", false);
		$("#disConnectBtn").attr("disabled", true);
		$("#name").attr("disabled", false);
		$("#sendChatBtn").attr("disabled", true);
	}
}

function connectSockAndSubscribe(){
	var socket = new SockJS("/endPoint");
	stompClient = Stomp.over(socket);
	stompClient.connect({chatRoomId: roomId}, function(frame){
		console.log("connected: " + frame);
		subscribeMessage();
		$("#chatBoxContents").html("");
	});
}

function subscribeMessage(){
	setConnected(true);
	stompClient.subscribe('/sub/chat/' + roomId, function(result){
		var chatMessage = JSON.parse(result.body);
		
		if(chatMessage.messageType == "CHAT"){
			drawChatMessage(chatMessage);
		}else if(chatMessage.messageType == "DISCONNECTED"){
			disconnect();
		}else{
			alert("unavailable Message Type");
		}
	});
}

//접속해제
function disconnect(){
	if(stompClient != null){
		stompClient.disconnect();
	}
	setConnected(false);
}

//메세지 전송
function sendMessage(){
	stompClient.send("/msg/chat.message/" + roomId, {}, JSON.stringify({
		senderSessionId: 	sessionId,
		message: 			$("#btn-input").val(), 
		messageType: 		"CHAT",
		sendUserName: 		$("#userName").val() 
	}));
	$("#btn-input").val("");
}

function drawChatMessage(chatMessage){
	var lenderHtml;
	if(chatMessage.senderSessionId == sessionId){
		renderHtml = $("#sendMsgTemplate").html();
	}else{
		renderHtml = $("#recvMsgTemplate").html();
	}
	
	$("#chatBoxContents").append(Mustache.render(renderHtml, chatMessage));
	$("#chatBox").scrollTop($("#chatBoxContents").height());
}