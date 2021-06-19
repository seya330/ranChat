package com.seya330.ranchat.core.chatroom.domain;

import com.seya330.ranchat.share.util.IDGeneratorUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class RandomChatRoomBean {
  private String roomId;
  private String name;
  private Map<String, ChatUserBean> joinUserMap;

  public RandomChatRoomBean() {
    this.initRoom();
  }

  public void initRoom() {
    joinUserMap = new LinkedHashMap<String, ChatUserBean>();
    roomId = IDGeneratorUtil.generateId("R", 19);
  }
}
