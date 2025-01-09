package view;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CourierView extends JFrame {
    private final JTextField idField = new JTextField();
    private final JTextField nameField = new JTextField();
    private final JSpinner transactionDateField;
    private final JTextField totalField = new JTextField();
    private final JTable table = new JTable();
    private final JButton addButton = new JButton("Add");
    private final JButton updateButton = new JButton("Update");
    private final JButton deleteButton = new JButton("Delete");
    private boolean listenerAdded = false;

    public CourierView() {
        setTitle("Courier Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        inputPanel.add(new JLabel("Name"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Transaction Date"));

        SpinnerDateModel model = new SpinnerDateModel();
        transactionDateField = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(transactionDateField, "yyyy-MM-dd");
        transactionDateField.setEditor(editor);
        inputPanel.add(transactionDateField);

        inputPanel.add(new JLabel("Total"));
        inputPanel.add(totalField);

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

    public void addTableSelectionListener(ListSelectionListener listener) {
        table.getSelectionModel().addListSelectionListener(listener);
    }

    public void clearTableSelection() {
        table.clearSelection();
        this.cleanFields();
    }

    public void cleanFields() {
        idField.setText("");
        nameField.setText("");
        transactionDateField.setValue(new Date());
        totalField.setText("");
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

    public String getTransactionDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(transactionDateField.getValue());
    }

    public void setTransactionDate(String transactionDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(transactionDate);
            transactionDateField.setValue(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getTotal() {
        if (totalField.getText().isEmpty() || !totalField.getText().matches("[0-9]+")) {
            return 0;
        }
        return Integer.parseInt(totalField.getText());
    }

    public void setTotal(int total) {
        totalField.setText(String.valueOf(total));
    }

    public JTable getTable() {
        return table;
    }
}