package view;

import model.Dropbox;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TrashView extends JFrame {
    private final JTextField idField = new JTextField();
    private final JTextField categoryField = new JTextField();
    private final JTextField totalField = new JTextField();
    private final JComboBox<Dropbox> dropBox = new JComboBox<Dropbox>();
    private final JTable table = new JTable();
    private final JButton addButton = new JButton("Add");
    private final JButton updateButton = new JButton("Update");
    private final JButton deleteButton = new JButton("Delete");
    private final JLabel trashTotal = new JLabel();
    private final JLabel pointTotal = new JLabel();

    public TrashView() {
        setTitle("Trash Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        inputPanel.add(new JLabel("Category"));
        inputPanel.add(categoryField);
        inputPanel.add(new JLabel("Total"));
        inputPanel.add(totalField);
        inputPanel.add(new JLabel("Drop Box"));
        inputPanel.add(dropBox);

        idField.setEditable(false);
        idField.setVisible(false);



        JScrollPane tablePanel = new JScrollPane(table);
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        statusPanel.add(trashTotal);
        statusPanel.add(pointTotal);

        JPanel dataPanel = new JPanel(new BorderLayout());
        dataPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        dataPanel.add(tablePanel, BorderLayout.CENTER);
        dataPanel.add(statusPanel, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

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

    public JTable getTable() {
        return table;
    }

    public void setDropboxes(List<Dropbox> dropboxes) {
        dropBox.removeAllItems();
        for (Dropbox dropbox : dropboxes) {
            dropBox.addItem(dropbox);
        }
    }

    public int getId() {
        if (idField.getText().isEmpty()) {
            return -1;
        }

        return Integer.parseInt(idField.getText());
    }

    public void setId(int id) {
        idField.setText(String.valueOf(id));
    }

    public String getName() {
        return categoryField.getText();
    }

    public void setName(String name) {
        categoryField.setText(name);
    }

    public int getTotal() {
        if (totalField.getText().isEmpty()) {
            return -1;
        }
        return Integer.parseInt(totalField.getText());
    }

    public void setTotal(int total) {
        totalField.setText(String.valueOf(total));
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

    public void setTrashTotal(int value) {
        trashTotal.setText("Total Sampah Keseluruhan: " + value);
    }

    public void setPointTotal(int value) {
        pointTotal.setText("Total Poin Keseluruhan: " + value);
    }

    public void cleanFields() {
        idField.setText("");
        categoryField.setText("");
        totalField.setText("");
        dropBox.setSelectedIndex(0);
    }
}
