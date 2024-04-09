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
    private String name;  
    private double price;

    // Constructor for MenuItems class that initializes name and price
    public MenuItems(String name, double price) {
        this.name = name;
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
   
}


