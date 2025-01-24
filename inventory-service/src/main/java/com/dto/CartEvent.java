package com.dto;

public class CartEvent {

	private String userId;
	private Long productId;
	private Integer quantity;

	public CartEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CartEvent(String userId, Long productId, Integer quantity) {
		super();
		this.userId = userId;
		this.productId = productId;
		this.quantity = quantity;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "CartEvent [userId=" + userId + ", productId=" + productId + ", quantity=" + quantity + "]";
	}
}
