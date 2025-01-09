package controller;

import model.User;
import model.UserMapper;
import org.apache.ibatis.session.SqlSession;
import view.UserView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserController {
    private final UserView view;
    private final UserMapper userMapper;
    private final SqlSession session;

    public UserController(UserView userView, UserMapper userMapper, SqlSession session) {
        this.view = userView;
        this.userMapper = userMapper;
        this.session = session;

        getAllData(session);

        if (view.isListenerAdded()) return;

        view.addTableSelectionListener(new TableSelectionListener());
        view.addButtonListener(new AddButtonListener());
        view.updateButtonListener(new UpdateButtonListener());
        view.deleteButtonListener(new DeleteButtonListener());
        view.setListenerAdded(true);
    }

    private void getAllData(SqlSession session) {
        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            List<User> data = mapper.getAllUser();
            Object[][] tableData = new Object[data.size()][3];

            for (int i = 0; i < data.size(); i++) {
                User user = data.get(i);
                tableData[i][0] = user.getId();
                tableData[i][1] = user.getName();
                tableData[i][2] = user.getTotalTransaction();
            }

            Object[] columnNames = {"ID", "Name", "Total Transaction"};

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
                    String name = (String)
                            view.getTable().getValueAt(selectedRow, 1);
                    int totalTransaction = (int)
                            view.getTable().getValueAt(selectedRow, 2);

                    view.setId(id);
                    view.setName(name);
                    view.setTotalTransaction(totalTransaction);
                }
            }
        }
    }

    class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = view.getName();
            int totalTransaction = view.getTotalTransaction();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Name must be filled");
                return;
            }

            if (totalTransaction == -1) {
                JOptionPane.showMessageDialog(view, "Total Transaction must be filled with number");
                return;
            }

            User user = new User();

            user.setName(name);
            user.setTotalTransaction(totalTransaction);

            try {
                userMapper.insertUser(user);
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
            int totalTransaction = view.getTotalTransaction();

            if (id == -1) {
                JOptionPane.showMessageDialog(view, "Please select a Courier");
                return;
            }

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Name must be filled");
                return;
            }

            if (totalTransaction == -1) {
                JOptionPane.showMessageDialog(view, "Total Transaction must be filled with number");
                return;
            }

            User user = new User();
            user.setId(id);
            user.setName(name);
            user.setTotalTransaction(totalTransaction);

            try {
                userMapper.updateUser(user);
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

            int response = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete this record?", "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION);
            if (response != JOptionPane.YES_OPTION) {
                return;
            }

            try {
                userMapper.deleteUser(id);
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
