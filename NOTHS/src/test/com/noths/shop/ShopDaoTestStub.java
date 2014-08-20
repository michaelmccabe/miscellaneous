package com.noths.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.noths.shop.dao.ShopDao;
import com.noths.shop.items.Product;
import com.noths.shop.items.discount.Discount;

public class ShopDaoTestStub implements ShopDao {

    public static List<Product> products;

    public static List<Discount> basketDiscounts;

    static Map<String, List<Discount>> productDiscounts;

    {

	products = new ArrayList<>();
	basketDiscounts = new ArrayList<Discount>();
	productDiscounts = new HashMap<String, List<Discount>>();

	products.add(new Product("001", " Travel Card Holder", 9.25));
	products.add(new Product("002", " Personalised cufflinks", 45.00));
	products.add(new Product("003", " Kids T-shirt", 19.95));

	basketDiscounts.add(new Discount(60.00, 0, 0.1));

	List<Discount> travelCardDiscounts = new ArrayList<>();
	travelCardDiscounts.add(new Discount(0.0, 2, 0.08108));

	productDiscounts.put("001", travelCardDiscounts);

    }

    @Override
    public List<Product> getAvailableProducts() {
	return products;
    }

    @Override
    public List<Discount> getBasketDiscounts() {
	return basketDiscounts;
    }

    @Override
    public List<Discount> getProductDiscount(String itemID) {
	return productDiscounts.get(itemID);
    }

    @Override
    public Product getProduct(String itemID) {

	for (Product product : products) {

	    if (itemID.equals(product.getId()))
		return product;

	}

	return null;

    }

    @Override
    public Map<String, List<Discount>> getProductDiscountMap() {
	return productDiscounts;
    }

}
