var stompClient
var viewingRoomId;

function init(){
	createToken();
	getMyChatRoomList();
	wsConnect();
	$("#inviteDialog").dialog({
		dialogClass: "",
	      autoOpen: false,
	      width: 350,
	      modal: true,
	      closeOnEscape: false,
	      show: "fade",
	      hide: "fade",
	      open: function(event, ui){
	    	  $(".ui-dialog-titlebar-close").hide();
	      },
	      close: function() {
	      }
	    });
	
	$("#inviteDialogBtn").on("click", function(){$("#inviteDialog").dialog("open")});
	$("#inviteBtn").on("click", function(e){e.preventDefault();addInvitingUser();});
	$("#sendChatBtn").on("click", function(e){e.preventDefault();sendChat();});
}

function getMyChatRoomList(){
	$.ajax({
		url					: "/chat/groupChat/chatRoomList",
		method				: "get",
		contentType			: "application/json; charset=utf-8",
		headers				: {"auth-token":getCookie("auth-token")},
		header				: {"Content-type": "application/json"},
		dataType			: "json",
		success				: function(result){
			var param = {
				list: result
			};
			
			for(i=0; i<param.list.length; i++){
				$.extend(param.list[i], {
					summaryCutDown: function(){
						if(!this.summary)
							return "채팅 메세지가 없습니다.";
						
						return this.summary.substring(0, 30);
					},
					joinnerNameCutDown: function(){
						var value="";
						for(i=0; i<this.joinnerList.length; i++){
							if(i>=3){
								value += "외 " + (this.joinnerList.length - 3) + "명";
								break;
							}
							value += (i>0?",":"") + this.joinnerList[i].userId;
							
						}
						return value;
					},
					isUnreadCnt: function(){
						if(parseInt(this.unreadCnt) > 0)
							return true;
						else
							return false;
					}
				});
			}
			
			$("#chatRoomBox").html("");
			$("#chatRoomBox").append(Mustache.render($("#chatRoomTemplate").html(), param));
		},
		fail			: function(){}
	})
}

function createToken(){
	$.ajax({
		url				: "/chat/groupChat/createToken",
		header			: {"Content-type": "application/json"},
		method			: "post",
		dataType		: "json",
		async			: false,
		success			: function(result){
			console.log(result.uniqId);
			setCookie("auth-token", result.token);
			setCookie("uniqId", result.uniqId);
			console.log(getCookie("uniqId"));
		},
		fail			: function(){}
	});
}

function wsConnect(){
	var socket = new SockJS("/endPoint");
	stompClient = Stomp.over(socket);
	stompClient.connect({chatType: "GROUPCHAT", "auth-token": getCookie("auth-token")}, function(frame){
		baseSubscribe();
	});
}

function baseSubscribe(){
	stompClient.subscribe("/sub/public." + getCookie("uniqId") + ".message", function(result){
		publicMessageHandler(result);
	});
}


function addInvitingUser(){
	var userId = $("#invitingUserId").val();
	$.ajax({
		url: 		"/chat/invite/" + userId,
		method:		"post",
		contentType: "application/json; charset=utf-8",
		headers: 	{"auth-token":getCookie("auth-token")},
		data: 		"",
		beforeSend: function(){},
		success:	function(groupChatVO){
			if(groupChatVO.result == "INVALID_USER"){
				alert("유효하지 않은 사용자 ID 입니다.");
				$("#invitingUserId").focus();
				return;
			}
			if(groupChatVO.result == "SAME_USER_ID"){
				alert("자신은 초대할 수 없습니다.");
				$("#invitingUserId").focus();
				return;
			}
			
			getMyChatRoomList();
			$("#inviteDialog").dialog("close");
		}
	});
}

function publicMessageHandler(result){
	var chatMessage = JSON.parse(result.body);
	
	chatMessage = appendingProcess(chatMessage);
	
	if(viewingRoomId == chatMessage.chatRoomId)
		viewingChatroomMessageHandler(chatMessage);
	else
		notViewingChatroomMessageHandler(chatMessage);
	
	chatRoomRefresh(chatMessage);
}

function viewChatRoom(chatRoomId){
	$("#chatBoxContents").html("");
	$("#chatroom_" + viewingRoomId).removeClass("act");
	$("#chatroom_" + chatRoomId).addClass("act");
	viewingRoomId = chatRoomId;
	drawChatList(chatRoomId);
}

function drawChatList(chatRoomId){
	$.ajax({
		url					: "/chat/groupChat/chatList",
		method				: "get",
		contentType			: "application/json; charset=utf-8",
		headers				: {"auth-token":getCookie("auth-token")},
		data				: {
			"chatRoomId"	: chatRoomId,
			"readYn"		: "Y"
			},
		dataType			: "json",
		beforeSend			: function(){},
		success				: function(messageList){
			if(!messageList || messageList.length < 1){
				$("#chatBoxContents").html("아무것도엄슴~");
			}
			for(i=0; i<messageList.length; i++){
				$.extend(messageList[i], {isSender: function(){
					if(this.senderId == getCookie("uniqId"))
						return true;
					else
						return false;
				}});
			}
			
			var param = {"list":messageList};
			$("#chatBoxContents").append(Mustache.render($("#msgTemplate").html(), param));
			$("#unreadCnt_" + chatRoomId).html("0").hide();
			chatBoxScrollToBottom();
		}
	});
}

function sendChat(){
	var chatText = $("#chatText").val();
	if(!chatText){
		alert("메세지를 입력해 주세요.");
		$("#chatText").focus();
		return;
	}
	
	
	stompClient.send("/msg/groupChat.message/send", {"auth-token":getCookie("auth-token")}, JSON.stringify({
		"messageContents"	: chatText,
		"chatRoomId"		: viewingRoomId
	}));
	$("#chatText").val("");
}

function viewingChatroomMessageHandler(chatMessage){
	var param = {};
	param.list = chatMessage;
	$("#chatBoxContents").append(Mustache.render($("#msgTemplate").html(), param));
	chatBoxScrollToBottom();
	
	$.ajax({
		url					: "/chat/groupChat/message/view",
		method				: "get",
		contentType			: "application/json; charset=utf-8",
		headers				: {"auth-token":getCookie("auth-token")},
		data				: {
			"chatRoomId"	: chatMessage.chatRoomId,
			"readYn"		: "Y"
			},
		//dataType			: "json",
		beforeSend			: function(){},
		success				: function(){}
	});
}

function notViewingChatroomMessageHandler(chatMessage){
	$("#unreadCnt_" + chatMessage.chatRoomId).show();
	$("#unreadCnt_" + chatMessage.chatRoomId).html(parseInt($("#unreadCnt_" + chatMessage.chatRoomId).html()) + 1);
}

function appendingProcess(chatMessage){
	$.extend(chatMessage, {
		isSender: function(){
			if(this.senderId == getCookie("uniqId"))
				return true;
			else
				return false;
		},
		summaryCutDown: function(){
			if(!this.messageContents)
				return "채팅 메세지가 없습니다.";
			
			return this.messageContents.substring(0, 30);
		}
	});
	return chatMessage;
}

function chatBoxScrollToBottom(){
	$("#chatBox").scrollTop($("#chatBoxContents").height());
}

function chatRoomRefresh(chatMessage){	
	//해당 chatRoomId의 summary 가 존재할 경우.
	if($("#summary_" + chatMessage.chatRoomId).length > 0){
		$("#summary_" + chatMessage.chatRoomId).html(chatMessage.summaryCutDown());
	}else{
	//존재하지 않을 경우
		getMyChatRoomList();
	}
}
/*(function(a){
	a.x = 1;
	console.log(a);
}(window.aaa = window.aaa || {}));


function addInvitingUser(){
	console.log(aaa);
	//$.Deferred();
	setTimeout(function(){
		console.log("시작");
	}, 3000);
	
	console.log("종료");
}*/