package com.oxbix.xfood.dto;

/**
 * 就餐桌位类
 * @author AllenZheng 
 * @date 2014年9月25日 上午11:39:34
 */
public class TableDTO implements Comparable<TableDTO> {

	/** 桌位服务器id **/
	private Long tableId;
	/** 所属的房间服务器id **/
	private Long diningRoom;
	/** 桌位名 **/
	private String name;
	/** 桌位状态  **/
	private Integer status;

	public TableDTO() {
	}

	public TableDTO(Long tableId, Long roomId, String name, Integer status) {
		this.tableId = tableId;
		this.diningRoom = roomId;
		this.name = name;
		this.status = status;
	}

	public Long getTableId() {
		return this.tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public Long getDiningRoom() {
		return this.diningRoom;
	}

	public void setDiningRoom(Long diningRoom) {
		this.diningRoom = diningRoom;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object obj) {
		return tableId.equals(((TableDTO) obj).getTableId());
	}

	@Override
	public int compareTo(TableDTO o) {
		return name.compareTo(o.name);
	}
}
