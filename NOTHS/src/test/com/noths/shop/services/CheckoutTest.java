package com.noths.shop.services;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.noths.shop.ShopDaoTestStub;
import com.noths.shop.dao.ShopDao;
import com.noths.shop.exceptions.ShopException;
import com.noths.shop.items.Product;
import com.noths.shop.items.discount.Discount;

public class CheckoutTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    Checkout underTest;

    @Before
    public void setupForTestCases() {
	underTest = new Checkout();

	ShopDao shopDao = new ShopDaoTestStub();

	underTest.setShopDao(shopDao);
	underTest.init();

    }

    @Test
    public void scanItemShouldThrowExceptionIfItemNotInInventory()
	    throws Exception {

	// setup
	expectedException.expect(ShopException.class);
	expectedException
		.expectMessage("A Product of that name does not exist");

	// exercise and verify
	underTest.scan("non-existantItem");

    }

    @Test
    public void initAndScanShouldCallShopDao() throws Exception {
	Product expectedProduct = new Product("001", "description", 0.0);
	List<Product> products = new ArrayList<>();
	products.add(expectedProduct);

	Discount discount = new Discount(0.0, 1, 0.0);
	List<Discount> discounts = new ArrayList<>();
	discounts.add(discount);

	ShopDao mockShopDao = createMock(ShopDao.class);
	expect(mockShopDao.getAvailableProducts()).andReturn(products);
	expect(mockShopDao.getBasketDiscounts()).andReturn(discounts);
	expect(mockShopDao.getProduct(("001"))).andReturn(expectedProduct);
	expect(mockShopDao.getProductDiscount(("001"))).andReturn(discounts);
	replay(mockShopDao);
	underTest.setShopDao(mockShopDao);
	underTest.init();

	// exercise
	underTest.scan("001");

	// verify
	verify(mockShopDao);
    }

    // Test data
    //
    // ---------
    //
    // Basket: 001,002,003
    //
    // Total price expected: £66.78
    //
    // Basket: 001,003,001
    //
    // Total price expected: £36.95
    //
    // Basket: 001,002,001,003
    //
    // Total price expected: £73.76

    @Test
    public void testcase1() throws Exception {

	// setup

	// exercise
	underTest.scan("001", "002", "003");
	Double total = underTest.total();

	// verify
	assertTrue(total.compareTo(66.78) == 0);

    }

    @Test
    public void testcase2() throws Exception {

	// setup

	// exercise , "003", "001"
	underTest.scan("001", "003", "001");
	Double total = underTest.total();

	// verify
	assertTrue(total.compareTo(36.95) == 0);

    }
}
