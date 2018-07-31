package org.shest;

import javax.swing.*;
import java.awt.*;

public class Checker extends JFrame {

    private static final String LOADED_URL = "https://www.investing.com/equities/europe";

    public Checker() {
        setTitle("Rates");
        setSize(1000, 1000);
        JDesktopPane desktopPane = new JDesktopPane();
        JButton button = new JButton("Monitor Stock Quotes");
        this.add(desktopPane, BorderLayout.CENTER);
        this.add(button, BorderLayout.NORTH);

        button.addActionListener(new BrowserAction(desktopPane, LOADED_URL, button.getHeight()));

        this.setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    /**
     * set it visible or invisible
     * @param active
     */
    public void activate(boolean active) {
        this.setVisible(active);
    }

}