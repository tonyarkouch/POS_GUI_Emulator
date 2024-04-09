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
import java.awt.event.ActionEvent; // Import AWT event classes for handling user actions
import java.util.ArrayList; // Import the ArrayList class for dynamic arrays
import java.util.List; // Import the List interface for typed lists

public class PosGUI {
    private JFrame frame; // Main window frame
    private JPanel menuPanel; // Panel for displaying menu item buttons
    private JPanel controlPanel; // Panel for displaying control buttons like start, complete order etc.
    private JTextArea orderTextArea; // Text area to display the order details
    private JButton startOrderButton, completeOrderButton, addMenuItemButton, closeOutDayButton; // Buttons for user interaction
    private List<MenuItems> menuItemsList; // List to hold menu items
    private List<Order> completedOrders; // List to track completed orders
    private Order currentOrder; // Current order being processed
    private int orderCounter = 0; // Counter for orders processed

    // Assume MenuItems and Order classes exist and operate as intended

    public PosGUI(MenuItems[] menuItems) { // Constructor with initial menu items
        this.menuItemsList = new ArrayList<>(List.of(menuItems)); // Initialize menu items list
        this.completedOrders = new ArrayList<>(); // Initialize completed orders list
        this.currentOrder = new Order(); // Create a new order instance
        initializeUI(); // Initialize the user interface
    }

    private void initializeUI() { // Method to set up user interface
        frame = new JFrame("POS System"); // Main frame with title
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close operation
        frame.setLayout(new BorderLayout()); // Layout manager for frame

        controlPanel = new JPanel(new FlowLayout()); // Control panel with flow layout
        menuPanel = new JPanel(new GridLayout(0, 1, 5, 5)); // Menu panel with grid layout

        startOrderButton = new JButton("Start Order"); // Button to start a new order
        startOrderButton.addActionListener(e -> startNewOrder()); // Action listener for button

        completeOrderButton = new JButton("Complete Order"); // Button to complete an order
        completeOrderButton.addActionListener(e -> completeOrder()); // Action listener for button
        completeOrderButton.setVisible(false); // Initially invisible

        addMenuItemButton = new JButton("Add Menu Item"); // Button to add a new menu item
        addMenuItemButton.addActionListener(e -> addMenuItem()); // Action listener for button

        closeOutDayButton = new JButton("Close Out Day"); // Button to close out the day
        closeOutDayButton.addActionListener(e -> closeOutDay()); // Action listener for button
        closeOutDayButton.setVisible(false); // Initially invisible until an order is completed

        controlPanel.add(startOrderButton); // Add start order button to control panel
        controlPanel.add(addMenuItemButton); // Add add menu item button to control panel

        orderTextArea = new JTextArea(10, 30); // Text area for order details
        orderTextArea.setEditable(false); // Make text area non-editable

        frame.add(controlPanel, BorderLayout.NORTH); // Add control panel to frame
        frame.add(new JScrollPane(orderTextArea), BorderLayout.CENTER); // Add scrollable order text area to frame
        frame.add(menuPanel, BorderLayout.SOUTH); // Add menu panel to frame

        menuPanel.setVisible(false); // Initially hide menu panel

        frame.pack(); // Pack the components within the frame
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true); // Make frame visible
    }

    private void startNewOrder() { // Method to start a new order
        this.currentOrder = new Order(); // Reset/Create a new Order instance
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
        // Prompt user for menu item name
        String name = JOptionPane.showInputDialog(frame, "Enter menu item name:");
        // Prompt user for menu item price
        String priceString = JOptionPane.showInputDialog(frame, "Enter price:");
        try {
            double price = Double.parseDouble(priceString); // Parse price input as double
            MenuItems newItem = new MenuItems(name, price); // Create new MenuItems instance
            menuItemsList.add(newItem); // Add new item to menu items list
            JOptionPane.showMessageDialog(frame, "Menu item added successfully."); // Show success message
        } catch (NumberFormatException ex) { // Handle invalid price input
            JOptionPane.showMessageDialog(frame, "Invalid price entered.", "Error", JOptionPane.ERROR_MESSAGE); // Show error message
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
            orderTextArea.setText(""); // Clear order text area
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
        completedOrders.clear(); // Clear completed orders list
        orderCounter = 0; // Reset order counter
        closeOutDayButton.setVisible(false); // Hide close out day button
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
