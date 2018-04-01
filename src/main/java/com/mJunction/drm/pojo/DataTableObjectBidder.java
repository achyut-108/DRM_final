package com.mJunction.drm.pojo;

import java.util.List;

public class DataTableObjectBidder {
	
	int iTotalRecords;
	int iTotalDisplayRecords;
	String sEcho;
	String sColumns;
	List<BidderPojo> aaData;

	public int getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public String getsEcho() {
		return sEcho;
	}

	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	public String getsColumns() {
		return sColumns;
	}

	public void setsColumns(String sColumns) {
		this.sColumns = sColumns;
	}

	public List<BidderPojo> getAaData() {
		return aaData;
	}

	public void setAaData(List<BidderPojo> aaData) {
		this.aaData = aaData;
	}

	
	
}
