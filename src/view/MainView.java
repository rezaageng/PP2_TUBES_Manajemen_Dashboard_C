package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainView extends JFrame {
    private final JButton trashButton;
    private final JButton courierButton;
    private final JButton userButton;
    private final JButton historyButton;

    public MainView() {
        trashButton = new JButton("Trash");
        courierButton = new JButton("Courier");
        userButton = new JButton("User");
        historyButton = new JButton("History");

        this.setTitle("Himawari Trash Management System");
        this.setSize(800, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        trashButton.setPreferredSize(new Dimension(150, 50));
        courierButton.setPreferredSize(new Dimension(150, 50));
        userButton.setPreferredSize(new Dimension(150, 50));
        historyButton.setPreferredSize(new Dimension(150, 50));

        panel.add(trashButton);
        panel.add(courierButton);
        panel.add(userButton);
        panel.add(historyButton);


        this.add(panel, BorderLayout.CENTER);
    }

    public void goToTrash(ActionListener listener) {
        trashButton.addActionListener(listener);
    }

    public void goToCourier(ActionListener listener) {
        courierButton.addActionListener(listener);
    }

    public void goToUser(ActionListener listener) {
        userButton.addActionListener(listener);
    }

    public void goToHistory(ActionListener listener) {
        historyButton.addActionListener(listener);
    }

}
