package com.pinguela.yourpc.web.model;

import com.pinguela.yourpc.model.dto.LocalizedProductDTO;

public class CartItem {
	
	private Long productId;
	private LocalizedProductDTO productDto;
	private Integer quantity;
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public LocalizedProductDTO getProductDto() {
		return productDto;
	}
	public void setProductDto(LocalizedProductDTO productDto) {
		this.productDto = productDto;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
}
