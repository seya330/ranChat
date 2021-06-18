package com.seya330.ranchat.core.user.vo;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegUserSearchVO {
	private String userId;
	private ArrayList<String> exceptUniqId;
	
	public RegUserSearchVO() {
		exceptUniqId = new ArrayList<String>();
	}
	
	public void addExceptUniqId(String uniqId) {
		this.exceptUniqId.add(uniqId);
	}
}
