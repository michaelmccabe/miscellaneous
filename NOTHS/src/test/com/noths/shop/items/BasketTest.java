package com.noths.shop.items;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.noths.shop.ShopDaoTestStub;
import com.noths.shop.dao.ShopDao;
import com.noths.shop.exceptions.ShopException;
import com.noths.shop.items.discount.Discount;

public class BasketTest {
    Basket underTest = new Basket();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    public void setupForAddProdLogic() {

    }

    @Test
    public void addProductItemsShouldThrowExceptionIfItemNotInInventory()
	    throws Exception {

	// setup
	expectedException.expect(ShopException.class);
	expectedException
		.expectMessage("A Product of that name does not exist");

	// exercise and verify
	underTest.addProductItems("no item", 1);

    }

    @Test
    public void noDiscountsAddedGetFullPrice() throws Exception {

	// setup
	ShopDao shopDao = new ShopDaoTestStub();
	underTest.setDiscounts(new ArrayList<Discount>());
	Product newProduct = new Product("005", "description", 100.0);
	List<Product> products = shopDao.getAvailableProducts();

	products.add(newProduct);
	underTest.addProducts(products);
	underTest.addProductItems("005", 2);

	// exercise
	Double totalCostNotDiscounted = underTest.getDiscountedPrice();

	// verify
	assertTrue(totalCostNotDiscounted.compareTo(200.0) == 0);

    }

    @Test
    public void discountAddedThresholdReachedGivesDiscount() throws Exception {

	// setup
	ShopDao shopDao = new ShopDaoTestStub();
	underTest.setDiscounts(new ArrayList<Discount>());
	Product newProduct = new Product("005", "description", 100.0);
	List<Product> products = shopDao.getAvailableProducts();
	newProduct.setDiscounts(shopDao.getProductDiscount("001"));

	products.add(newProduct);
	underTest.addProducts(products);
	underTest.addProductItems("005", 2);

	// exercise
	Double totalCostNotDiscounted = underTest.getDiscountedPrice();

	// verify
	assertTrue(totalCostNotDiscounted.compareTo(183.78) == 0);

    }

    @Test
    public void discountAddedThresholdNotReachedGivesNoDiscount()
	    throws Exception {
	// setup
	ShopDao shopDao = new ShopDaoTestStub();
	underTest.setDiscounts(new ArrayList<Discount>());
	Product newProduct = new Product("005", "description", 100.0);
	List<Product> products = shopDao.getAvailableProducts();
	newProduct.setDiscounts(shopDao.getProductDiscount("001"));
	products.add(newProduct);
	underTest.addProducts(products);
	underTest.addProductItems("005", 1);

	// exercise
	Double totalCostNotDiscounted = underTest.getDiscountedPrice();

	// verify
	assertTrue(totalCostNotDiscounted.compareTo(100.0) == 0);

    }

}
