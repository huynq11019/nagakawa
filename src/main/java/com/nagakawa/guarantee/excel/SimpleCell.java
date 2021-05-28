/**
 * 
 */
package com.nagakawa.guarantee.excel;

/**
 * @author LinhLH
 *
 */
public class SimpleCell {
	private String content;
	private String textColor;
	private String bgColor;
	private String textSize;
	private String textWeight;

	public SimpleCell() {
	}

	public SimpleCell(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public String getTextSize() {
		return textSize;
	}

	public void setTextSize(String textSize) {
		this.textSize = textSize;
	}

	public String getTextWeight() {
		return textWeight;
	}

	public void setTextWeight(String textWeight) {
		this.textWeight = textWeight;
	}
}
