/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalproject;

/**
 *
 * @author tonya
 * */
import java.util.ArrayList; 


public class Order {
    // Declare a private ArrrayList to store MenuItems objects
    private ArrayList<MenuItems> items;

   
    public Order() {
        this.items = new ArrayList<>();
    }

    // Method to add a MenuItem object to the items list
    public void addItem(MenuItems item) {
        items.add(item); // Add the given MenuItem to the items list
    }

    // Method to get the list of MenuItem objects
    public ArrayList<MenuItems> getItems() {
        return items; // Return the items list
    }

    // Method to calculate the total price of all MenuItems in the list
    public double calculateTotal() {
        double total = 0; // Initialize total to 0
        for (MenuItems item : items) { // Iterate over each MenuItem in items
            total += item.getPrice(); // Add the price of the current MenuItem to total
        }
        return total; // Return the calculated total
    }
}

