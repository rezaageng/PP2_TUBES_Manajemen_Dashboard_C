package view;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class UserView extends JFrame {
    private final JTextField idField = new JTextField();
    private final JTextField nameField = new JTextField();
    private final JTextField totalTransactionField = new JTextField();
    private final JButton addButton = new JButton("Add");
    private final JButton updateButton = new JButton("Update");
    private final JButton deleteButton = new JButton("Delete");
    private final JTable table = new JTable();
    private boolean listenerAdded = false;

    public UserView() {
        setTitle("User Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        inputPanel.add(new JLabel("Name"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Total Transaction"));
        inputPanel.add(totalTransactionField);

        idField.setEditable(false);
        idField.setVisible(false);

        JScrollPane tablePanel = new JScrollPane(table);

        JPanel dataPanel = new JPanel(new BorderLayout());
        dataPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        dataPanel.add(tablePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        add(inputPanel, BorderLayout.NORTH);
        add(dataPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public boolean isListenerAdded() {
        return listenerAdded;
    }

    public void setListenerAdded(boolean listenerAdded) {
        this.listenerAdded = listenerAdded;
    }

    public int getId() {
        if (idField.getText().isEmpty() || !idField.getText().matches("[0-9]+")) {
            return -1;
        }

        return Integer.parseInt(idField.getText());
    }

    public void setId(int id) {
        idField.setText(String.valueOf(id));
    }

    public String getName() {
        return nameField.getText();
    }

    public void setName(String name) {
        nameField.setText(name);
    }

    public int getTotalTransaction() {
        if (totalTransactionField.getText().isEmpty() || !totalTransactionField.getText().matches("[0-9]+")) {
            return -1;
        }

        return Integer.parseInt(totalTransactionField.getText());
    }

    public void setTotalTransaction(int totalTransaction) {
        totalTransactionField.setText(String.valueOf(totalTransaction));
    }

    public JTable getTable() {
        return table;
    }

    public void addButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void updateButtonListener(ActionListener listener) {
        updateButton.addActionListener(listener);
    }

    public void deleteButtonListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

    public void clearFields() {
        idField.setText("");
        nameField.setText("");
        totalTransactionField.setText("");
    }

    public void setTableData(Object[][] data, Object[] columns) {
        table.setModel(new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    public void addTableSelectionListener(ListSelectionListener listener) {
        table.getSelectionModel().addListSelectionListener(listener);
    }

    public void clearTableSelection() {
        table.clearSelection();
        this.clearFields();
    }
}
