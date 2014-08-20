package com.noths.shop.services;

import java.util.List;

import com.noths.shop.dao.ShopDao;
import com.noths.shop.exceptions.ShopException;
import com.noths.shop.items.Basket;
import com.noths.shop.items.Product;
import com.noths.shop.items.discount.Discount;

public class Checkout {

    private ShopDao shopDao;

    private final Basket basket = new Basket();

    public void init() {

	basket.setDiscounts(shopDao.getBasketDiscounts());
	basket.addProducts(shopDao.getAvailableProducts());
    }

    public void scan(String... itemIds) throws ShopException {

	for (String itemId : itemIds) {

	    Product product = shopDao.getProduct(itemId);

	    if (null == product)
		throw new ShopException("A Product of that name does not exist");

	    List<Discount> discounts = shopDao.getProductDiscount(product
		    .getId());

	    product.setDiscounts(discounts);
	    basket.addProductItems(product.getId(), 1);

	}
    }

    public Double total() {

	return basket.getDiscountedPrice();
    }

    public void setShopDao(ShopDao shopDao) {

	this.shopDao = shopDao;

    }

}
