/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.finalproject;

import javax.swing.SwingUtilities;

/**
 *
 * @author tonya
 */
public class FinalProject {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MenuItems[] menuItems = {
                    new MenuItems("Coffee", 1.99),
                    new MenuItems("Tea", 1.49),
                    new MenuItems("Sandwich", 5.49),
                    new MenuItems("Cake", 3.99)
                };
                new PosGUI(menuItems);
            }
        });
    }
}

