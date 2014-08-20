package com.noths.shop.items.discount;

import java.text.DecimalFormat;

public class Discount {

    private final Double priceThreshold;
    private final int quantityThreshold;
    private final Double percentage;

    public Discount(Double priceThreshold, int quantityThreshold,
	    Double percentage) {
	super();
	this.priceThreshold = priceThreshold;
	this.quantityThreshold = quantityThreshold;
	this.percentage = percentage;
    }

    public double apply(int quantity, Double price) {

	Double fullPrice = quantity * price;

	if (quantity >= quantityThreshold && price >= priceThreshold) {

	    Double answer = fullPrice * percentage;
	    return RoundTo2Decimals(answer);
	} else
	    return 0;
    }

    double RoundTo2Decimals(double val) {
	DecimalFormat df2 = new DecimalFormat("###.##");
	return Double.valueOf(df2.format(val));
    }

}
