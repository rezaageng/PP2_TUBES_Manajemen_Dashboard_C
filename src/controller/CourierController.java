package controller;

import model.Courier;
import model.CourierMapper;
import org.apache.ibatis.session.SqlSession;
import view.CourierView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CourierController {
    private final CourierView view;
    private final CourierMapper courierMapper;
    private final SqlSession session;

    public CourierController(CourierView courierView, CourierMapper courierMapper, SqlSession session) {
        this.view = courierView;
        this.courierMapper = courierMapper;
        this.session = session;

        getAllData(session);

        courierView.addButtonListener(new AddButtonListener());
        courierView.updateButtonListener(new UpdateButtonListener());
        courierView.deleteButtonListener(new DeleteButtonListener());
        courierView.addTableSelectionListener(new TableSelectionListener());
    }

    private void getAllData(SqlSession session) {
        try {
            CourierMapper mapper = session.getMapper(CourierMapper.class);
            List<Courier> data = mapper.getAllCourier();
            Object[][] tableData = new Object[data.size()][4];

            for (int i = 0; i < data.size(); i++) {
                Courier courier = data.get(i);
                tableData[i][0] = courier.getId();
                tableData[i][1] = courier.getName();
                tableData[i][2] = courier.getTransactionDate();
                tableData[i][3] = courier.getTotal();
            }

            Object[] columnNames = {"ID", "Name", "Transaction Date", "Total"};

            view.setTableData(tableData, columnNames);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Failed to get Courier data");
        }
    }

    class TableSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = view.getTable().getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) view.getTable().getValueAt(selectedRow, 0);

                    String name = (String) view.getTable().getValueAt(selectedRow, 1);
                    String transactionDate = (String) view.getTable().getValueAt(selectedRow, 2);
                    int total = (int) view.getTable().getValueAt(selectedRow, 3);

                    view.setId(id);
                    view.setName(name);
                    view.setTransactionDate(transactionDate);
                    view.setTotal(total);
                }
            }
        }
    }

    class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = view.getName();
            String transactionDate = view.getTransactionDate();
            int total = view.getTotal();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Name must be filled");
                return;
            }

            if (transactionDate.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Transaction Date must be filled");
                return;
            }

            if (total == 0) {
                JOptionPane.showMessageDialog(view, "Total must be filled");
                return;
            }

            Courier courier = new Courier();

            courier.setName(name);
            courier.setTransactionDate(transactionDate);
            courier.setTotal(total);

            try {
                courierMapper.insertCourier(courier);
                session.commit();
                getAllData(session);
                view.clearTableSelection();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Failed to add Courier");
            }
        }
    }

    class UpdateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int id = view.getId();
            String name = view.getName();
            String transactionDate = view.getTransactionDate();
            int total = view.getTotal();

            if (id == -1) {
                JOptionPane.showMessageDialog(view, "Please select a Courier");
                return;
            }

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Name must be filled");
                return;
            }

            if (transactionDate.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Transaction Date must be filled");
                return;
            }

            if (total == 0) {
                JOptionPane.showMessageDialog(view, "Total must be filled");
                return;
            }

            Courier courier = new Courier();

            courier.setId(id);
            courier.setName(name);
            courier.setTransactionDate(transactionDate);
            courier.setTotal(total);

            try {
                courierMapper.updateCourier(courier);
                session.commit();
                getAllData(session);
                view.clearTableSelection();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Failed to update Courier");
            }
        }
    }

    class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int id = view.getId();

            if (id == -1) {
                JOptionPane.showMessageDialog(view, "Please select a Courier");
                return;
            }

            int response = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete this record?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (response != JOptionPane.YES_OPTION) {
                return;
            }

            try {
                courierMapper.deleteCourier(id);
                session.commit();
                getAllData(session);
                view.clearTableSelection();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Failed to delete Courier");
            }
        }
    }
}
