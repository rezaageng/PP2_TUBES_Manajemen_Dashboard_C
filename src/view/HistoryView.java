package view;

import model.Courier;
import model.Dropbox;
import model.User;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class HistoryView extends JFrame {
    private final JTextField idField = new JTextField();
    private final JTextField transactionField = new JTextField();
    private final JComboBox<Courier> courierBox = new JComboBox<Courier>();
    private final JComboBox<User> userBox = new JComboBox<User>();
    private final JComboBox<Dropbox> dropBox = new JComboBox<Dropbox>();
    private final JTextField timestampField = new JTextField();
    private final JTable table = new JTable();
    private final JButton addButton = new JButton("Add");
    private final JButton updateButton = new JButton("Update");
    private final JButton deleteButton = new JButton("Delete");
    private final JButton printPreviewButton = new JButton("Print Preview");
    private final JButton printButton = new JButton("Print");
    private final JButton exportPdfButton = new JButton("Export to PDF");
    private boolean listenerAdded = false;

    public HistoryView() {
        setTitle("History Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        inputPanel.add(new JLabel("Transaction"));
        inputPanel.add(transactionField);
        inputPanel.add(new JLabel("Courier"));
        inputPanel.add(courierBox);
        inputPanel.add(new JLabel("User"));
        inputPanel.add(userBox);
        inputPanel.add(new JLabel("Drop Box"));
        inputPanel.add(dropBox);

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
        buttonPanel.add(printPreviewButton);
        buttonPanel.add(printButton);
        buttonPanel.add(exportPdfButton);

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(dataPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!table.getBounds().contains(e.getPoint())) {
                    clearTableSelection();
                }
            }
        });
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

    public String getTransaction() {
        return transactionField.getText();
    }

    public void setTransaction(String transaction) {
        transactionField.setText(transaction);
    }

    public int getSelectedCourierId() {
        Courier selectedCourier = (Courier) courierBox.getSelectedItem();
        return selectedCourier != null ? selectedCourier.getId() : -1;
    }

    public void setSelectedCourier(Courier courier) {
        courierBox.setSelectedItem(courier);
    }

    public JComboBox<Courier> getCourierBox() {
        return courierBox;
    }

    public int getSelectedUserId() {
        User selectedUser = (User) userBox.getSelectedItem();
        return selectedUser != null ? selectedUser.getId() : -1;
    }

    public void setSelectedUser(User user) {
        userBox.setSelectedItem(user);
    }

    public JComboBox<User> getUserBox() {
        return userBox;
    }

    public int getSelectedDropboxId() {
        Dropbox selectedDropbox = (Dropbox) dropBox.getSelectedItem();
        return selectedDropbox != null ? selectedDropbox.getIdDropbox() : -1;
    }

    public void setSelectedDropbox(Dropbox dropbox) {
        dropBox.setSelectedItem(dropbox);
    }

    public JComboBox<Dropbox> getDropBox() {
        return dropBox;
    }

    public String getTimestamp() {
        return timestampField.getText();
    }

    public void setTimestamp(String timestamp) {
        timestampField.setText(timestamp);
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

    public void setTableData(Object[][] data, Object[] columns) {
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setModel(model);
    }

    public void clearFields() {
        idField.setText("");
        transactionField.setText("");
        courierBox.setSelectedIndex(0);
        userBox.setSelectedIndex(0);
        dropBox.setSelectedIndex(0);
    }

    public void addTableSelectionListener(ListSelectionListener listener) {
        table.getSelectionModel().addListSelectionListener(listener);
    }

    public void clearTableSelection() {
        table.clearSelection();
        this.clearFields();
        courierBox.setSelectedIndex(0);
        userBox.setSelectedIndex(0);
        dropBox.setSelectedIndex(0);
    }

    public void setCouriers(List<Courier> couriers) {
        courierBox.removeAllItems();
        for (Courier courier : couriers) {
            courierBox.addItem(courier);
        }
    }

    public void setUsers(List<User> users) {
        userBox.removeAllItems();
        for (User user : users) {
            userBox.addItem(user);
        }
    }

    public void setDropboxes(List<Dropbox> dropboxes) {
        dropBox.removeAllItems();
        for (Dropbox dropbox : dropboxes) {
            dropBox.addItem(dropbox);
        }
    }

    public void addPrintPreviewButtonListener(ActionListener listener) {
        printPreviewButton.addActionListener(listener);
    }

    public void addPrintButtonListener(ActionListener listener) {
        printButton.addActionListener(listener);
    }

    public void addExportPdfButtonListener(ActionListener listener) {
        exportPdfButton.addActionListener(listener);
    }
}
