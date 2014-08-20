package com.noths.shop.items;

import java.util.ArrayList;
import java.util.List;

import com.noths.shop.items.discount.Discount;
import com.noths.shop.items.discount.Discounter;

public class Product implements Discounter {

    private final Double price;
    private int quantity = 0;
    private final String description;

    private final String id;

    private List<Discount> discounts = new ArrayList<Discount>();

    public Product(String id, String description, Double price) {
	super();
	this.price = price;
	this.description = description;
	this.id = id;
    }

    public void setQuantity(int i) {

	quantity = i;
    }

    public int getQuantity() {
	return quantity;
    }

    public String getDescription() {
	return description;
    }

    public String getId() {
	return id;
    }

    public Double getTotalPrice() {

	return (price * quantity);
    }

    @Override
    public Double getDiscountedPrice() {

	Double totalDiscount = 0.0;

	if (discounts != null) {
	    for (Discount discount : this.discounts) {

		totalDiscount += discount.apply(quantity, price);

	    }
	}

	return (price * quantity) - totalDiscount;
    }

    @Override
    public void setDiscounts(List<Discount> discounts) {
	this.discounts = discounts;

    }
}
