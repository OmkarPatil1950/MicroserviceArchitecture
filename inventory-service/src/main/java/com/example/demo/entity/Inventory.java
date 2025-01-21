package com.example.demo.entity;

import jakarta.persistence.Entity;

import java.io.Serializable;

import jakarta.persistence.*;


@Entity
@Table(name = "inventory")
public class Inventory implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String skuCode;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double price;

	@Override
	public String toString() {
		return "Inventory [id=" + id + ", skuCode=" + skuCode + ", productName=" + productName + ", quantity="
				+ quantity + ", price=" + price + "]";
	}

	public Inventory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Inventory(Long id, String skuCode, String productName, Integer quantity, Double price) {
		super();
		this.id = id;
		this.skuCode = skuCode;
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
    
    
}
