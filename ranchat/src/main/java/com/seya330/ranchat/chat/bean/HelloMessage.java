package com.seya330.ranchat.chat.bean;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter	@Setter
public class HelloMessage {
	private String name;
	private String contents;
	private Date sendDate;
	private String roomId;
}
