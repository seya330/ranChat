package com.seya330.ranchat.common.vo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PagingVO {
    private int blockSize = 10;
    private int pageNo = 1;
    private int totalCnt;
    private int maxPageNo;
    
    private String orderBy;
    
	public int getStartIdx() {
		int startIdx = (this.getMaxPageNo()-pageNo) * blockSize;
		return startIdx;
	}
	
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
		if(maxPageNo == 0)
			maxPageNo = totalCnt/blockSize + (totalCnt%blockSize != 0 ? 1 : 0);
	}
}
