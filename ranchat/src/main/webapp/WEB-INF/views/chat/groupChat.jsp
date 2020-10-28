<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" >
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>로그인</title>
	
	<link rel="stylesheet" type="text/css" href="/resources/lib/css/bootstrap/bootstrap.min.css"/>
	<link rel="stylesheet" href="/resources/template/assets/css/main.css" />
	<link rel="stylesheet" type="text/css" href="/resources/ranchat/css/common.css"/>
	<link rel="stylesheet" type="text/css" href="/resources/lib/css/jquery/jquery-ui.css"/>
	<!-- Scripts -->
	<script src="/resources/template/assets/js/jquery.min.js"></script>
	<script src="/resources/template/assets/js/skel.min.js"></script>
	<script src="/resources/template/assets/js/util.js"></script>
	<script src="/resources/user/loginView.js"></script>
	<script src="/resources/common/common.js"></script>
	<script src="/resources/lib/js/mustache/mustache.js"></script>
	
	<script src="/resources/lib/js/sockjs/sockjs.js"></script>
	<script src="/resources/lib/js/stomp/stomp.js"></script>
	
	
	<script src="/resources/lib/js/bootstrap/bootstrap.bundle.min.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script src="/resources/ranchat/js/groupChat.js"></script>
	
	<style>
		.messages-box, .chat-box {
		  height: 510px;
		  overflow-y: scroll;
		}
		form{
			margin-bottom: 0;
		}
		button{
			height: auto !important;
			background-color: white;
		}
		p{
			margin: 0;
			text-decoration: none;
			word-break: break-all;
		}
		.h5{
			font-size: 1rem ;
		}
		.text-small {
		  font-size: 0.9rem;
		}
		.rounded-lg {
		  border-radius: 0.5rem;
		}
		
		input::placeholder {
		  font-size: 0.9rem;
		  color: #999;
		}
		a{
			text-decoration: none;
			cursor: pointer;
		}
		
		
		.ui-dialog-titlebar{
			background-color: #6cc091;
		}
		.ui-dialog{
			position: fixed;
			padding: 0;
		}
		.ui-dialog-titlebar{
			padding: 5px !important;
		}
		.ui-dialog-title{
			font-size: 0.8rem;
			color: white;
			font-family: Cafe24Dangdanghae;
		}
		.small-btn{
			padding: 10px;
			line-height: 5px;
			font-weight: 400;
			border-radius: 10px;
		}
		.button_area{
			text-align: center;
		}
		.act{
			background-color: #E4F7BA;
		}
		.list-group-item-light.list-group-item-action.act:hover{
			background-color: #E4F7BA;
		}
		.bg-primary{
			background-color: #6cc091 !important;
		}
		tr.selected{
			background-color: rgba(144, 144, 144, 0.075);
		}
	</style>
	
	<script>
	$(function(){
		init();
	});
	</script>
</head>
<body class="subpage" style="background-color: #EAEAEA;">
	<%@ include file="/WEB-INF/views/chat/header.jsp"%>
	
	<div class="container py-5 px-4">
  <!-- For demo purpose-->
  <header class="text-center">
    <p class="lead mb-0" style="color: #6F6F6F">GROUP CHAT</p>
    <p class="lead mb-4">from 
      <a href="https://seya330.github.io" class="">
        <u>seya330</u></a>
    </p>
  </header>

  <div class="row rounded-lg overflow-hidden shadow">
    <!-- Users box-->
    <div class="col-5 px-0">
      <div class="bg-white">

        <div class="bg-gray px-4 bg-light row align-items-center">
        <div class="col-8">
          <p class="h5 mb-0 py-1" style="font-size: 1.1em;">채팅방</p>
        </div>
        <div class="col-4">
          <button id="inviteDialogBtn" class="btn" style="font-size: 1em;"><i class="fa fa-search"></i> <span style="font-size: 0.8em;">채팅초대</span></botton>
        </div>
        </div>

        <div class="messages-box">
        <!-- chatRoomBox -->
          <div id="chatRoomBox" class="list-group rounded-0">
            <!-- <a class="list-group-item list-group-item-action active text-white rounded-0">
              <div class="media"><img src="https://res.cloudinary.com/mhmd/image/upload/v1564960395/avatar_usae7z.svg" alt="user" width="50" class="rounded-circle">
                <div class="media-body ml-4">
                  <div class="d-flex align-items-center justify-content-between mb-1">
                    <h6 class="mb-0">Jason Doe</h6><small class="small font-weight-bold">25 Dec</small>
                  </div>
                  <p class="font-italic mb-0 text-small">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore.</p>
                </div>
              </div>
            </a>

            <a class="list-group-item list-group-item-action list-group-item-light rounded-0">
              <div class="media"><img src="https://res.cloudinary.com/mhmd/image/upload/v1564960395/avatar_usae7z.svg" alt="user" width="50" class="rounded-circle">
                <div class="media-body ml-4">
                  <div class="d-flex align-items-center justify-content-between mb-1">
                    <h6 class="mb-0">Jason Doe</h6><small class="small font-weight-bold">14 Dec</small>
                  </div>
                  <p class="font-italic mb-0 text-small">Lorem ipsum dolor sit amet, consectetur. incididunt ut labore.</p>
                </div>
              </div>
            </a> -->
          </div>
        </div>
      </div>
    </div>
    <!-- Chat Box-->
    <div class="col-7 px-0">
      <div id="chatBox" class="px-4 py-2 chat-box bg-white">
		<div id="chatBoxContents" style="">
		</div>
		<div id="loadingCircle" class="spinning-loader" style="position: absolute; left: 50%; top: 50%; margin-left: -25px; margin-top: -25px; display: none;"></div>
      </div>

      <!-- Typing area -->
      <form action="#" class="bg-light">
        <div class="input-group">
          <input type="text" id="chatText" name="chatText" placeholder="Type a message" aria-describedby="button-addon2" class="form-control rounded-0 border-0 py-4 bg-light">
          <div class="input-group-append">
            <button type="submit" id="sendChatBtn" class="btn btn-link"> <i class="fa fa-paper-plane"></i></button>
          </div>
        </div>
      </form>

    </div>
  </div>
</div>
	
	<!-- Scripts -->
	<script src="/resources/template/assets/js/skel.min.js"></script>
	<script src="/resources/template/assets/js/util.js"></script>
	<script src="/resources/template/assets/js/main.js"></script>
	

<!-- 초대 다이얼로그 -->
<div class="" id="inviteDialog" title="사용자 초대">
	<div class="row">
		<div class="col-6">
			<form>
				<div class="input-group">
			       <input type="text" id="invitingUserId" name="invitingUserId" placeholder="초대할 사용자 입력" aria-describedby="button-addon2" class="form-control rounded-0 border-0 py-4 bg-light">
			       <div class="input-group-append">
			         <button id="inviteBtn" type="submit" class="btn btn-link" style="border: none;"> <i class="fa fa-paper-plane"></i></button>
			       </div>
			    </div>
			</form>
			
			<div style="margin-top: 2rem; height: 200px; overflow: scroll;">
				<table>
					<tbody id="invSearchUserTable">
					</tbody>
				</table>
			</div>
			
		</div>
		<div class="col-6" style="">
			<div style="font-family: Cafe24Dangdanghae; color: #6f6f6f;">
				초대 사용자 목록
			</div>
			<div style="margin-top: 52px; height: 200px; overflow: scroll;">
				<table>
					<tbody id="invSelectedUserTable">
					
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="button_area col-12">
		<button class="button small small-btn" onclick='addInvitingUser();'>초 대</button>
		<button class="button small small-btn" onclick='$("#inviteDialog").dialog("close");'>닫 기</button>
	</div>
</div>


<script id="chatRoomTemplate" type="text/template">
{{#list}}
	<a id="chatroom_{{chatRoomId}}" class="list-group-item list-group-item-action list-group-item-light rounded-0" onclick="viewChatRoom('{{chatRoomId}}');">
         <div class="media"><img src="https://res.cloudinary.com/mhmd/image/upload/v1564960395/avatar_usae7z.svg" alt="user" width="50" class="rounded-circle">
              <div class="media-body ml-4 container">
                <div class="d-flex align-items-center justify-content-between mb-1">
                   <h6 class="mb-0">{{joinnerNameCutDown}}</h6><small class="small font-weight-bold">14 Dec</small>
               </div>
				<div class="row">
            	<p id="summary_{{chatRoomId}}" class="font-italic mb-0 text-small col-11">{{summaryCutDown}}</p>
				<span id="unreadCnt_{{chatRoomId}}" class="col-1" style="text-align: center; font-size: 0.9rem; color: white; background-color: #FF9595; border-radius: 8px; padding: 1px 10px 1px 10px; {{^isUnreadCnt}}display: none;{{/isUnreadCnt}}">{{unreadCnt}}</span>
				</div>
        	</div>
    	</div>
	</a>
{{/list}}
</script>
<!-- 받은 메세지 -->
<script id="msgTemplate" type="text/template">
{{#list}}
	{{#isSender}}
		<p class="small text-muted" style="text-align: right">{{sendUserId}}</p>
		<div class="media w-50 ml-auto mb-3">
	    	<div class="media-body">
	     		<div class="bg-primary rounded py-2 px-3 mb-2">
	       		<p class="text-small mb-0 text-white">{{messageContents}}</p>
	    	</div>
	    	<p class="small text-muted">{{regDate}}</p>
	    	</div>
		</div>
	{{/isSender}}
	{{^isSender}}
		<p class="small text-muted">{{sendUserId}}</p>
		<div class="media w-50 mb-3"><img src="https://res.cloudinary.com/mhmd/image/upload/v1564960395/avatar_usae7z.svg" alt="user" width="50" class="rounded-circle">
    		<div class="media-body ml-3">
     			<div class="bg-light rounded py-2 px-3 mb-2">
					<p class="text-small mb-0 text-muted">{{messageContents}}</p>
      			</div>
      			<p class="small text-muted">{{regDate}}</p>
    		</div>
		</div>
	{{/isSender}}
{{/list}}
</script>

<script id="regUserListTemplate" type="text/template">
{{#regUserList}}
	<tr data-uniq-id="{{uniqId}}" data-user-id="{{userId}}" style="border-top: 1px solid #dbdbdb; border-bottom: 1px solid #dbdbdb;"><td style="width: 80%;">{{userId}}</td><td style="width: 20%;"><button class="button small small-btn" >선택</button></td></tr>
{{/regUserList}}
</script>

<script id="selectedUserTemplate" type="text/template">
	<tr data-uniq-id="{{uniqId}}" data-user-id="{{userId}}" style="border-top: 1px solid #dbdbdb; border-bottom: 1px solid #dbdbdb;"><td style="width: 80%;">{{userId}}</td><td style="width: 20%;"><button class="button small small-btn" >취소</button></td></tr>
</script>
</body>
</html>