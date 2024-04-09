/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalproject;

/**
 *
 * @author tonya
 */
public class MenuItems {
    // Declare a private String variable to store the name of the menu item
    private String name;
    // Declare a private double variable to store the price of the menu item
    private double price;

    // Constructor for MenuItems class that initializes name and price
    public MenuItems(String name, double price) {
        // Assign the value of the parameter name to the instance variable name
        this.name = name;
        // Assign the value of the parameter price to the instance variable price
        this.price = price;
    }

    // Public method to get the name of the menu item
    public String getName() {
        // Return the value of the instance variable name
        return name;
    }

    // Public method to get the price of the menu item
    public double getPrice() {
        // Return the value of the instance variable price
        return price;
    }
    // End of MenuItems class
}


