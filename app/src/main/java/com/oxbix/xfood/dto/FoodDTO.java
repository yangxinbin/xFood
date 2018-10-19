package com.oxbix.xfood.dto;

import java.io.Serializable;
import java.util.List;

/**
 * é£Ÿç‰©ç±»
 * @author AllenZheng
 * @date 2014å¹´9æœˆ25æ—¥ ä¸Šå�ˆ11:28:25
 */
public class FoodDTO implements Comparable<FoodDTO>, Serializable {

	/** é£Ÿç‰©æœ�åŠ¡å™¨id **/
	private Long itemId;
	/** é£Ÿç‰©å�� **/
	private String name;
	/** é£Ÿç‰©å��ç¿»è¯‘ **/
	private String nameT;
	/** ç¼“å­˜ç � **/
	private String tagCode;
	/** é£Ÿç‰©å®žé™…ä»·æ ¼ **/
	private Double defaultPrice;
	/** é£Ÿç‰©æ��è¿° **/
	private String description;
	/** é£Ÿç‰©æ��è¿°ç¿»è¯‘ **/
	private String descriptionT;
	/** é£Ÿç‰©å›¾æ ‡ **/
	private String icon;
	/** é£Ÿç‰©çŠ¶æ€� **/
	private Integer status;
	/** é£Ÿç‰©å¹³å�‡éœ€è¦�å‡†å¤‡çš„æ—¶é—´ **/
	private Integer avgMinToPrepare;
	/** è®¢å�•ç´¢å¼•å�· **/
	private int orderIndex;
	/** é£Ÿç‰©ç›¸å…³å›¾ç‰‡ **/
	private List<String> images;
	
	private boolean isRecommended;

	public FoodDTO(Long itemId, String name, String nameT, String tagCode, Double defaultPrice, String description, String descriptionT, String icon,
			Integer status, Integer avgMinToPrepare, int orderIndex, boolean isRecommended, List<String> images) {
		super();
		this.itemId = itemId;
		this.name = name;
		this.nameT = nameT;
		this.tagCode = tagCode;
		this.defaultPrice = defaultPrice;
		this.description = description;
		this.descriptionT = descriptionT;
		this.icon = icon;
		this.status = status;
		this.avgMinToPrepare = avgMinToPrepare;
		this.orderIndex = orderIndex;
		this.isRecommended=isRecommended;
		this.images = images;
	}

	public FoodDTO() {
	}



	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameT() {
		return nameT;
	}

	public void setNameT(String nameT) {
		this.nameT = nameT;
	}

	public String getTagCode() {
		return tagCode;
	}

	public void setTagCode(String tagCode) {
		this.tagCode = tagCode;
	}

	public Double getDefaultPrice() {
		return defaultPrice;
	}

	public void setDefaultPrice(Double defaultPrice) {
		this.defaultPrice = defaultPrice;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescriptionT() {
		return descriptionT;
	}

	public void setDescriptionT(String descriptionT) {
		this.descriptionT = descriptionT;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getAvgMinToPrepare() {
		return avgMinToPrepare;
	}

	public void setAvgMinToPrepare(Integer avgMinToPrepare) {
		this.avgMinToPrepare = avgMinToPrepare;
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}
	
	public boolean isRecommended() {
		return isRecommended;
	}

	public void setRecommended(boolean isRecommended) {
		this.isRecommended = isRecommended;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	@Override
	public int compareTo(FoodDTO o) {
		return new Integer(orderIndex).compareTo(o.orderIndex);
	}

}
