package com.william.local.transation.model;

import java.util.Date;

/**
 * 
 * @author william.zhang
 *
 */
public class IPWhiteList {
	
	private Integer id;
	private String ipAddress;
	private Integer libraryId;
	private Date createdAt;
	private Date updatedAt;
	
	public IPWhiteList() {
	}

	public IPWhiteList(String ipAddress) {
		super();
		this.ipAddress = ipAddress;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Integer getLibraryId() {
		return libraryId;
	}

	public void setLibraryId(Integer libraryId) {
		this.libraryId = libraryId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "IPWhiteList {id=" + id + ", ipAddress=" + ipAddress
				+ ", libraryId=" + libraryId + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "}";
	}
	
}
