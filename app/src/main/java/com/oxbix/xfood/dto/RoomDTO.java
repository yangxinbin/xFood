package com.oxbix.xfood.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 就餐房间类
 * @author AllenZheng 
 * @date 2014年9月25日 上午11:33:56
 */
public class RoomDTO implements Comparable<RoomDTO> {


	/** 房间服务器id **/
	private Long roomId;
	/** 房间名 **/
	private String name;
	/** 房间详情 **/
	private String detail;
	/** 房间中所有的桌位 **/
	private List<TableDTO> diningTables = new ArrayList<TableDTO>(0);

	public RoomDTO() {
	}

	public RoomDTO(Long roomId, String name, String detail) {
		this(roomId, name, detail, null);
	}

	public RoomDTO(Long roomId, String name, String detail, List<TableDTO> diningTables) {
		this.roomId = roomId;
		this.name = name;
		this.detail = detail;
		this.diningTables = diningTables;
	}

	public Long getRoomId() {
		return this.roomId;
	}

	public void ListRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public String getName() {
		return this.name;
	}

	public void ListName(String name) {
		this.name = name;
	}

	public String getDetail() {
		return this.detail;
	}

	public void ListDetail(String detail) {
		this.detail = detail;
	}

	public List<TableDTO> getDiningTables() {
		return this.diningTables;
	}

	public void ListDiningTables(List<TableDTO> diningTables) {
		this.diningTables = diningTables;
	}

	@Override
	public boolean equals(Object obj) {
		return roomId.equals(((RoomDTO) obj).getRoomId());
	}

	@Override
	public int compareTo(RoomDTO o) {
		return name.compareTo(o.name);
	}

}
