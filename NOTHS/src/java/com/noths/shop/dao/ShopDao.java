package com.noths.shop.dao;

import java.util.List;
import java.util.Map;

import com.noths.shop.items.Product;
import com.noths.shop.items.discount.Discount;

public interface ShopDao {

    List<Product> getAvailableProducts();

    Product getProduct(String itemID);

    List<Discount> getBasketDiscounts();

    Map<String, List<Discount>> getProductDiscountMap();

    List<Discount> getProductDiscount(String itemID);
}
