/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalproject;

import javax.swing.*; // Import Swing components for the GUI
import java.awt.*; // Import AWT container and component classes
import java.util.ArrayList; // Import the ArrayList class for dynamic arrays
import java.util.Arrays;


public class PosGUI {
    private JFrame frame;
    private JPanel menuPanel, controlPanel;
    private JTextArea orderTextArea;
    private JButton startDayButton, startOrderButton, completeOrderButton, addMenuItemButton, closeOutDayButton;
    private ArrayList<MenuItems> menuItemsList;
    private ArrayList<Order> completedOrders;
    private Order currentOrder;
    private int orderCounter = 0;
    int day = 1;

    public PosGUI(MenuItems[] menuItems) {
        this.menuItemsList = new ArrayList<>(Arrays.asList(menuItems));
        this.completedOrders = new ArrayList<>();
        this.currentOrder = new Order();
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("POS System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        controlPanel = new JPanel(new FlowLayout());
        menuPanel = new JPanel(new GridLayout(0, 1, 5, 5));

        startDayButton = new JButton("Start Day");
        startDayButton.addActionListener(e -> startDay());

        startOrderButton = new JButton("Start Order");
        startOrderButton.addActionListener(e -> startNewOrder());
        startOrderButton.setVisible(false); // Initially hide Start Order button

        completeOrderButton = new JButton("Complete Order");
        completeOrderButton.addActionListener(e -> completeOrder());
        completeOrderButton.setVisible(false);

        addMenuItemButton = new JButton("Add Menu Item");
        addMenuItemButton.addActionListener(e -> addMenuItem());

        closeOutDayButton = new JButton("Close Out Day");
        closeOutDayButton.addActionListener(e -> closeOutDay());
        closeOutDayButton.setVisible(false);

        controlPanel.add(startDayButton);
        controlPanel.add(addMenuItemButton);

        orderTextArea = new JTextArea(10, 30);
        orderTextArea.setEditable(false);
        orderTextArea.setText("Day: " + Integer.toString(day));

        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(orderTextArea), BorderLayout.CENTER);
        frame.add(menuPanel, BorderLayout.SOUTH);

        menuPanel.setVisible(false);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void startDay() {
        
        startDayButton.setVisible(false);// Hide Start Day button
        addMenuItemButton.setVisible(false);// Hide Add Menu Button 
        controlPanel.add(startOrderButton); // Add Start Order button
        startOrderButton.setVisible(true); // Show Start Order button
        orderTextArea.setText("Day: " + Integer.toString(day));
        frame.pack();
    }

    private void startNewOrder() { // Method to start a new order
        this.currentOrder = new Order(); // Reset/Create a new Order instance
        orderTextArea.setText("");
        if (!menuPanel.isVisible()) { // Check if menu panel is not visible
            buildMenuItems(); // Call to populate menu panel with menu items
            menuPanel.setVisible(true); // Make menu panel visible
            controlPanel.remove(startOrderButton); // Remove start order button
            controlPanel.remove(addMenuItemButton); // Remove add menu item button
            controlPanel.remove(closeOutDayButton); // Remove close out day button
            controlPanel.add(completeOrderButton); // Add complete order button
            completeOrderButton.setVisible(true); // Make complete order button visible
            frame.pack(); // Repack frame components
        }
    }

    private void buildMenuItems() { // Method to build menu items buttons
        menuPanel.removeAll(); // Clear existing buttons in menu panel
        for (MenuItems item : menuItemsList) { // Iterate through menu items list
            JButton itemButton = new JButton(item.getName() + " - $" + item.getPrice()); // Create button for each menu item
            itemButton.addActionListener(e -> { // Add action listener to button
                currentOrder.addItem(item); // Add menu item to current order
                updateOrderDisplay(); // Update the order display text area
            });
            menuPanel.add(itemButton); // Add item button to menu panel
        }
        frame.revalidate(); // Revalidate frame layout
        frame.repaint(); // Repaint frame to reflect changes
    }

    private void addMenuItem() { // Method to add a new menu item
    boolean itemAdded = false; // Flag to check if item has been added successfully
    while (!itemAdded) {
        // Prompt user for menu item name
        String name = JOptionPane.showInputDialog(frame, "Enter menu item name:");
        if (name == null || name.isEmpty()) { // Check if the user cancelled the input or entered an empty string
            return; // Exit if no name is provided
        }

        // Convert input name to lowercase and check for existing item with the same name (also in lowercase)
        boolean nameExists = menuItemsList.stream()
                          .anyMatch(item -> item.getName().toLowerCase().equals(name.toLowerCase()));
        if (nameExists) {
            JOptionPane.showMessageDialog(frame, "Item with this name already exists. Please enter a different name.", "Error", JOptionPane.ERROR_MESSAGE);
            continue; // Continue looping if name exists
        }

        // Prompt user for menu item price
        String priceString = JOptionPane.showInputDialog(frame, "Enter price:");
        if (priceString == null || priceString.isEmpty()) { // Check if the user cancelled the input or entered an empty string
            return; // Exit if no price is provided
        }

        try {
            double price = Double.parseDouble(priceString); // Parse price input as double
            MenuItems newItem = new MenuItems(name, price); // Create new MenuItems instance
            menuItemsList.add(newItem); // Add new item to menu items list
            JOptionPane.showMessageDialog(frame, "Menu item added successfully."); // Show success message
            itemAdded = true; // Set flag to true to exit loop
        } catch (NumberFormatException ex) { // Handle invalid price input
            JOptionPane.showMessageDialog(frame, "Invalid price entered. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE); // Show error message
        }
    }
}



    private void completeOrder() { // Method to complete an order
        // Format total price to 2 decimal places
        String formattedTotal = String.format("%.2f", currentOrder.calculateTotal());
        // Confirm dialog to complete order
        int result = JOptionPane.showConfirmDialog(frame, 
            "Complete the order? Total: $" + formattedTotal,
            "Complete Order", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) { // If user confirms
            // Show order completion message with total
            JOptionPane.showMessageDialog(frame, 
                "Order Completed. Total: $" + formattedTotal, 
                "Order Complete", JOptionPane.INFORMATION_MESSAGE);
            completedOrders.add(currentOrder); // Add current order to completed orders list
            orderCounter++; // Increment order counter

            menuPanel.setVisible(false); // Hide menu panel
            menuPanel.removeAll(); // Clear menu panel
            controlPanel.remove(completeOrderButton); // Remove complete order button from control panel
            controlPanel.add(startOrderButton); // Add start order button to control panel
            startOrderButton.setVisible(true); // Make start order button visible
            controlPanel.add(closeOutDayButton); // Add close out day button to control panel
            closeOutDayButton.setVisible(true); // Make close out day button visible
            orderTextArea.setText("Day: " + Integer.toString(day));
            currentOrder = new Order(); // Reset current order
            frame.pack(); // Repack frame components
        }
    }

    private void updateOrderDisplay() { // Method to update order details display
        StringBuilder sb = new StringBuilder(); // StringBuilder for order text
        for (MenuItems item : currentOrder.getItems()) { // Iterate over items in current order
            // Append item name and price to StringBuilder
            sb.append(item.getName()).append(" - $").append(String.format("%.2f", item.getPrice())).append("\n");
        }
        // Append total price to StringBuilder
        sb.append("Total: $").append(String.format("%.2f", currentOrder.calculateTotal()));
        orderTextArea.setText(sb.toString()); // Set text area text to StringBuilder content
    }

    private void closeOutDay() { // Method to close out sales for the day
        // Calculate total sales for the day
        double totalSales = calculateTotalSales(0, 0.0);
        // Show total sales message
        JOptionPane.showMessageDialog(frame, "Total sales for the day: $" + String.format("%.2f", totalSales),
                "Day Closed", JOptionPane.INFORMATION_MESSAGE);
        day++;
        completedOrders.clear(); // Clear completed orders list
        orderCounter = 0; // Reset order counter
        closeOutDayButton.setVisible(false); // Hide close out day button
        startOrderButton.setVisible(false);
        startDayButton.setVisible(true);
        controlPanel.add(addMenuItemButton);
        addMenuItemButton.setVisible(true);
        orderTextArea.setText("Day: " + Integer.toString(day));
        frame.pack(); // Repack frame components
    }

    // Method to recursively calculate total sales
    private double calculateTotalSales(int index, double total) {
        if (index < completedOrders.size()) { // Check if index is within completed orders list size
            total += completedOrders.get(index).calculateTotal(); // Add order total to cumulative total
            return calculateTotalSales(index + 1, total); // Recursive call with next index
        } else {
            return total; // Return total when all orders have been processed
        }
    }
}
