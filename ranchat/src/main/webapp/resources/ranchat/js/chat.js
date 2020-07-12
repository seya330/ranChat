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
		url: 		"/chat/join",
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
		$("#chatBoxContents").html("");
		$("#connectBtn").attr("disabled", true);
		$("#disConnectBtn").attr("disabled", false);
		$("#userName").attr("disabled", true);
		
		$("#loadingCircle").show();
		//접속중입니다 메세지 출력도 하면 좋음.
	}else{
		$("#connectBtn").attr("disabled", false);
		$("#disConnectBtn").attr("disabled", true);
		$("#userName").attr("disabled", false);
		//접속중입니다 메세지 없애야함.
	}
}
function setConnected(isConnect){
	if(isConnect){
		$("#loadingCircle").hide();
		$("#connectBtn").attr("disabled", true);
		$("#disConnectBtn").attr("disabled", false);
		$("#userName").attr("disabled", true);
		$("#sendChatBtn").attr("disabled", false);
		$("#chatBox").fadeTo(300, 1);
	}else{
		$("#loadingCircle").hide();
		$("#connectBtn").attr("disabled", false);
		$("#disConnectBtn").attr("disabled", true);
		$("#userName").attr("disabled", false);
		$("#sendChatBtn").attr("disabled", true);
		$("#chatBox").fadeTo(300, 0.6);
	}
}

function connectSockAndSubscribe(){
	var socket = new SockJS("/endPoint");
	stompClient = Stomp.over(socket);
	stompClient.connect({chatType: "RANDOM", chatRoomId: roomId}, function(frame){
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
	drawDisconnectMessage();
}

//메세지 전송
function sendMessage(){
	var chatMsg = $("#chatText").val();
	if(!chatMsg)
		return;
	
	stompClient.send("/msg/chat.message/" + roomId, {}, JSON.stringify({
		senderSessionId: 	sessionId,
		message: 			chatMsg, 
		messageType: 		"CHAT",
		sendUserName: 		$("#userName").val() 
	}));
	$("#chatText").val("");
}

function drawChatMessage(chatMessage){
	var renderHtml;
	if(chatMessage.senderSessionId == sessionId){
		renderHtml = $("#sendMsgTemplate").html();
	}else{
		renderHtml = $("#recvMsgTemplate").html();
	}
	
	$("#chatBoxContents").append(Mustache.render(renderHtml, chatMessage));
	$("#chatBox").scrollTop($("#chatBoxContents").height());
}

function keyDownMapping(){
	if(window.event.keyCode == 13){
		sendMessage();
	}
}

function drawDisconnectMessage(){
	var renderHtml = $("#recvMsgTemplate").html();
	var today = new Date();
	var chatMessage = {
			"sendUserName": "SYSTEM", 
			"message": "상대방과 접속이 종료 되었습니다.", 
			"sendDateString": today.getFullYear()+"-"+ (today.getMonth()+1).toString().zf(2) + "-" + today.getDate().toString().zf(2)
			+ " " + today.getHours().toString().zf(2) + ":" + today.getMinutes().toString().zf(2) + ":" 
			+ today.getSeconds().toString().zf(2)
	};
	
	$("#chatBoxContents").append(Mustache.render(renderHtml, chatMessage));
	$("#chatBox").scrollTop($("#chatBoxContents").height());
}

String.prototype.zf = function(len){
	var tmp = "";
	for(var i=0; i<len-this.length; i++){
		tmp += "0";
	}
	return tmp + this;
};