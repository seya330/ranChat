package com.seya330.ranchat.core.chat.domain;

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
