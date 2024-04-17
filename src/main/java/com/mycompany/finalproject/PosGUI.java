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
import java.util.Arrays; // Import the Arrays utility class

public class PosGUI {
    private JFrame frame; // Frame for the GUI window
    private JPanel menuPanel, controlPanel; // Panels for menu items and controls
    private JTextArea orderTextArea; // Text area to display order details
    private JButton startDayButton, startOrderButton, completeOrderButton, addMenuItemButton, closeOutDayButton; // Buttons for various functionalities
    private ArrayList<MenuItems> menuItemsList; // List to store menu items
    private ArrayList<Order> completedOrders; // List to store completed orders
    private Order currentOrder; // Current order being processed
    private int orderCounter = 0; // Counter to track number of orders
    int day = 1; // Tracks the current day of operations

    public PosGUI(MenuItems[] menuItems) {
        this.menuItemsList = new ArrayList<>(Arrays.asList(menuItems)); // Initialize menu items list
        this.completedOrders = new ArrayList<>(); // Initialize completed orders list
        this.currentOrder = new Order(); // Initialize current order
        initializeUI(); // Call to initialize the user interface
    }

    private void initializeUI() {
        frame = new JFrame("POS System"); // Create main frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation
        frame.setLayout(new BorderLayout()); // Set layout manager for frame

        controlPanel = new JPanel(new FlowLayout()); // Panel for control buttons with flow layout
        menuPanel = new JPanel(new GridLayout(0, 1, 5, 5)); // Panel for menu items with grid layout

        startDayButton = new JButton("Start Day"); // Button to start the day
        startDayButton.addActionListener(e -> startDay()); // Add action listener to start day

        startOrderButton = new JButton("Start Order"); // Button to start a new order
        startOrderButton.addActionListener(e -> startNewOrder()); // Add action listener to start new order
        startOrderButton.setVisible(false); // Initially hide start order button

        completeOrderButton = new JButton("Complete Order"); // Button to complete the order
        completeOrderButton.addActionListener(e -> completeOrder()); // Add action listener to complete order
        completeOrderButton.setVisible(false); // Initially hide complete order button

        addMenuItemButton = new JButton("Add Menu Item"); // Button to add a new menu item
        addMenuItemButton.addActionListener(e -> addMenuItem()); // Add action listener to add menu item

        closeOutDayButton = new JButton("Close Out Day"); // Button to close out the day
        closeOutDayButton.addActionListener(e -> closeOutDay()); // Add action listener to close out the day
        closeOutDayButton.setVisible(false); // Initially hide close out day button

        controlPanel.add(startDayButton); // Add start day button to control panel
        controlPanel.add(addMenuItemButton); // Add add menu item button to control panel

        orderTextArea = new JTextArea(10, 30); // Initialize order text area
        orderTextArea.setEditable(false); // Set text area to non-editable
        orderTextArea.setText("Day: " + Integer.toString(day)); // Display the current day in text area

        frame.add(controlPanel, BorderLayout.NORTH); // Add control panel to the north of the frame
        frame.add(new JScrollPane(orderTextArea), BorderLayout.CENTER); // Add scroll pane with order text area to the center
        frame.add(menuPanel, BorderLayout.SOUTH); // Add menu panel to the south of the frame

        menuPanel.setVisible(false); // Initially hide menu panel

        frame.pack(); // Pack the frame components
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true); // Make the frame visible
    }

    private void startDay() {
        startDayButton.setVisible(false); // Hide start day button
        addMenuItemButton.setVisible(false); // Hide add menu item button
        controlPanel.add(startOrderButton); // Add start order button to control panel
        startOrderButton.setVisible(true); // Make start order button visible
        orderTextArea.setText("Day: " + Integer.toString(day)); // Update text area to show current day
        frame.pack(); // Pack the frame components
    }

    private void startNewOrder() {
        this.currentOrder = new Order(); // Reset current order
        orderTextArea.setText(""); // Clear the order text area
        if (!menuPanel.isVisible()) { // Check if menu panel is not visible
            buildMenuItems(); // Build menu items
            menuPanel.setVisible(true); // Make menu panel visible
            controlPanel.remove(startOrderButton); // Remove start order button from control panel
            controlPanel.remove(addMenuItemButton); // Remove add menu item button from control panel
            controlPanel.remove(closeOutDayButton); // Remove close out day button from control panel
            controlPanel.add(completeOrderButton); // Add complete order button to control panel
            completeOrderButton.setVisible(true); // Make complete order button visible
            frame.pack(); // Repack frame components
        }
    }

    private void buildMenuItems() {
        menuPanel.removeAll(); // Clear all components from menu panel
        for (MenuItems item : menuItemsList) { // Loop through each menu item in the list
            JButton itemButton = new JButton(item.getName() + " - $" + item.getPrice()); // Create a button for each menu item
            itemButton.addActionListener(e -> { // Add action listener to button
                currentOrder.addItem(item); // Add item to current order
                updateOrderDisplay(); // Update the order display area
            });
            menuPanel.add(itemButton); // Add button to menu panel
        }
        frame.revalidate(); // Revalidate the frame layout
        frame.repaint(); // Repaint the frame
    }

    private void addMenuItem() {
        boolean itemAdded = false; // Flag to indicate whether item was added
        while (!itemAdded) {
            String name = JOptionPane.showInputDialog(frame, "Enter menu item name:"); // Prompt user to enter a menu item name
            if (name == null || name.isEmpty()) { // Check if name is null or empty
                return; // Exit if no name is provided
            }

            boolean nameExists = menuItemsList.stream()
                              .anyMatch(item -> item.getName().toLowerCase().equals(name.toLowerCase())); // Check if item name already exists
            if (nameExists) {
                JOptionPane.showMessageDialog(frame, "Item with this name already exists. Please enter a different name.", "Error", JOptionPane.ERROR_MESSAGE);
                continue; // Continue prompting if name exists
            }

            String priceString = JOptionPane.showInputDialog(frame, "Enter price:"); // Prompt user to enter price
            if (priceString == null || priceString.isEmpty()) { // Check if price string is null or empty
                return; // Exit if no price is provided
            }

            try {
                double price = Double.parseDouble(priceString); // Convert price string to double
                MenuItems newItem = new MenuItems(name, price); // Create new menu item
                menuItemsList.add(newItem); // Add new item to list
                JOptionPane.showMessageDialog(frame, "Menu item added successfully."); // Display success message
                itemAdded = true; // Set item added flag to true
            } catch (NumberFormatException ex) { // Handle number format exception
                JOptionPane.showMessageDialog(frame, "Invalid price entered. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE); // Display error message
            }
        }
    }

    private void completeOrder() {
        String formattedTotal = String.format("%.2f", currentOrder.calculateTotal()); // Format total price to 2 decimal places
        int result = JOptionPane.showConfirmDialog(frame, 
            "Complete the order? Total: $" + formattedTotal,
            "Complete Order", JOptionPane.YES_NO_OPTION); // Show confirmation dialog

        if (result == JOptionPane.YES_OPTION) { // Check if user confirmed
            JOptionPane.showMessageDialog(frame, 
                "Order Completed. Total: $" + formattedTotal, 
                "Order Complete", JOptionPane.INFORMATION_MESSAGE); // Show order completion message
            completedOrders.add(currentOrder); // Add current order to completed orders list
            orderCounter++; // Increment order counter

            menuPanel.setVisible(false); // Hide menu panel
            menuPanel.removeAll(); // Clear menu panel
            controlPanel.remove(completeOrderButton); // Remove complete order button
            controlPanel.add(startOrderButton); // Add start order button
            startOrderButton.setVisible(true); // Make start order button visible
            controlPanel.add(closeOutDayButton); // Add close out day button
            closeOutDayButton.setVisible(true); // Make close out day button visible
            orderTextArea.setText("Day: " + Integer.toString(day)); // Update day display
            currentOrder = new Order(); // Reset current order
            frame.pack(); // Repack frame components
        }
    }

    private void updateOrderDisplay() {
        StringBuilder sb = new StringBuilder(); // Initialize StringBuilder for display text
        for (MenuItems item : currentOrder.getItems()) { // Loop through each item in current order
            sb.append(item.getName()).append(" - $").append(String.format("%.2f", item.getPrice())).append("\n"); // Append item details
        }
        sb.append("Total: $").append(String.format("%.2f", currentOrder.calculateTotal())); // Append total cost
        orderTextArea.setText(sb.toString()); // Set text area text to StringBuilder content
    }

    private void closeOutDay() {
        double totalSales = calculateTotalSales(0, 0.0); // Calculate total sales for the day
        JOptionPane.showMessageDialog(frame, "Total sales for the day: $" + String.format("%.2f", totalSales),
                "Day Closed", JOptionPane.INFORMATION_MESSAGE); // Display total sales message
        day++; // Increment day counter
        completedOrders.clear(); // Clear completed orders list
        orderCounter = 0; // Reset order counter
        closeOutDayButton.setVisible(false); // Hide close out day button
        startOrderButton.setVisible(false); // Hide start order button
        startDayButton.setVisible(true); // Show start day button
        controlPanel.add(addMenuItemButton); // Add add menu item button to control panel
        addMenuItemButton.setVisible(true); // Make add menu item button visible
        orderTextArea.setText("Day: " + Integer.toString(day)); // Update day display
        frame.pack(); // Repack frame components
    }

    // Recursive method to calculate total sales
    private double calculateTotalSales(int index, double total) {
        if (index < completedOrders.size()) { // Check if index is within the size of completed orders list
            total += completedOrders.get(index).calculateTotal(); // Add current order total to cumulative total
            return calculateTotalSales(index + 1, total); // Recur with next index
        } else {
            return total; // Return total if all orders are processed
        }
    }
}
