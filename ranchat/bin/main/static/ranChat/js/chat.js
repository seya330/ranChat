//사용변수영역
var stompClient = null;
var joinInterval;
var sessionId;
var roomId;

function joinRoom(){
	var name = $("#name").val();
	if(!name){
		alert("이름을 입력해 주세요.");
		return;
	}
	$.ajax({
		url: 		"/join",
		header: 	{"Content-type": "application/json"},
		data: 		{"name": name},
		beforeSend: function(){setConnecting(true)},
		success:	function(chatResponse){
			if(!chatResponse){
				alert("정상적으로 접속 되지 않았습니다.");
				setConnecting(false);
				return;
			}
			
			if(chatResponse.responseResult == "SUCCESS"){
				console.log("SUCCESS");
				sessionId = chatResponse.sessionId;
				roomId = chatResponse.chatRoomId;
				console.log("sessionId : " + sessionId + " roomId : " + roomId);
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
		$("#name").val("");
		$("#sendChatBtn").attr("disabled", true);
	}
}

function connectSockAndSubscribe(){
	var socket = new SockJS("/endPoint");
	stompClient = Stomp.over(socket);
	stompClient.connect({chatRoomId: roomId}, function(frame){
		console.log("connected: " + frame);
		subscribeMessage();
	});
}

function subscribeMessage(){
	setConnected(true);
	stompClient.subscribe('/sub/chat/' + roomId, function(result){
		var chatMessage = JSON.parse(result.body);
		var lenderHtml;
		if(chatMessage.senderSessionId == sessionId){
			renderHtml = $("#sendMsgTemplate").html();
		}else{
			renderHtml = $("#recvMsgTemplate").html();
		}
		
		$("#chatBoxContents").append(Mustache.render(renderHtml, chatMessage));
		$("#chatBox").scrollTop($("#chatBoxContents").height());
	});
}

function disconnect(){
	if(stompClient != null){
		sendBye();
		stompClient.disconnect();
	}
	setConnected(false);
}

function sendMessage(){
	stompClient.send("/msg/chat.message/" + roomId, {}, JSON.stringify({
		senderSessionId: 	sessionId,
		message: 			$("#btn-input").val(), 
		messageType: 		"CHAT"
	}));
	$("#btn-input").val("");
}