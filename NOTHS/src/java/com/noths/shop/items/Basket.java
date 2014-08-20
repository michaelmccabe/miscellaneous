package com.noths.shop.items;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.noths.shop.exceptions.ShopException;
import com.noths.shop.items.discount.Discount;
import com.noths.shop.items.discount.Discounter;

public class Basket implements Discounter {

    private final int quantity = 1;

    private final Map<String, Product> productMap = new HashMap<String, Product>();

    private List<Discount> discounts;

    public void addProducts(List<Product> products) {

	for (Product product : products) {

	    productMap.put(product.getId(), product);

	}

    }

    public void addProductItems(String productName, int quantity)
	    throws ShopException {

	if (productMap.containsKey(productName)) {
	    Product product = productMap.get(productName);
	    product.setQuantity(product.getQuantity() + quantity);
	} else
	    throw new ShopException("A Product of that name does not exist");

    }

    private Double getTotalPrice() {

	Double totalPrice = 0.0;

	Iterator iterator = productMap.entrySet().iterator();
	while (iterator.hasNext()) {
	    Map.Entry pairs = (Map.Entry) iterator.next();

	    String name = (String) pairs.getKey();
	    Product product = (Product) pairs.getValue();

	    totalPrice += product.getDiscountedPrice();
	}

	return totalPrice;
    }

    @Override
    public Double getDiscountedPrice() {

	Double totalDiscount = 0.0;
	Double price = getTotalPrice();

	for (Discount discount : discounts) {

	    totalDiscount += discount.apply(quantity, price);

	}

	return (price * quantity) - totalDiscount;
    }

    @Override
    public void setDiscounts(List<Discount> discounts) {
	this.discounts = discounts;

    }

}
