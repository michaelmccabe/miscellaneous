package com.noths.shop.items.discount;

import java.util.List;

public interface Discounter {

    void setDiscounts(List<Discount> discounts);;

    public Double getDiscountedPrice();

}
