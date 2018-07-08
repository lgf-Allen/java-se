package com.allen.lambda.methodReference;

/**
 * 
 * @author first
 *
 */
public class Cat {
    private String brand;
    private String color;

    public Cat(String brand, String color) {
        super();
        this.brand = brand;
        this.color = color;
    }

    public Cat() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getBrand() {
        return brand;
    }

    public String getColor() {
        return color;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
