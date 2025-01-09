package controller;

import model.Dropbox;
import model.Trash;
import model.TrashMapper;
import org.apache.ibatis.session.SqlSession;
import view.TrashView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TrashController {
    private final TrashView view;
    private final TrashMapper mapper;
    private final SqlSession session;

    public TrashController(TrashView view, TrashMapper mapper, SqlSession session) {
        this.view = view;
        this.mapper = mapper;
        this.session = session;

        getAllData(session);
        loadDropbox();

        if (view.getListenerAdded()) return;

        view.addTableSelectionListener(new TableSelectionListener());
        view.addButtonListener(new AddButtonListener());
        view.updateButtonListener(new UpdateButtonListener());
        view.deleteButtonListener(new DeleteButtonListener());
        view.setListenerAdded(true);
    }

    private void getAllData(SqlSession session) {
        try {
            TrashMapper mapper = session.getMapper(TrashMapper.class);
            List<Trash> trashes = mapper.getAllTrash();
            Object[][] data = new Object[trashes.size()][5];
            int trashTotal = 0;
            int pointTotal = 0;
            for (int i = 0; i < trashes.size(); i++) {
                Trash trash = trashes.get(i);
                data[i][0] = trash.getId();
                data[i][1] = trash.getCategoryName();
                data[i][2] = trash.getTotal();
                data[i][3] = trash.getPoint();
                data[i][4] = trash.getDropboxName();
                trashTotal += trash.getTotal();
                pointTotal += trash.getPoint();
            }
            Object[] columnNames = {"id", "Nama Kategori", "Total Sampah Dijemput", "Poin", "Dropbox"};
            view.setTableData(data, columnNames);
            view.setTrashTotal(trashTotal);
            view.setPointTotal(pointTotal);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Failed to get Trash data");
        }
    }

    private void loadDropbox() {
        try {
            List<Dropbox> dropboxes = mapper.getAllDropbox();
            view.setDropboxes(dropboxes);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Failed to load dropboxes");
        }
    }

    class TableSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            int selectedRow = view.getTable().getSelectedRow();
            if (selectedRow != -1) {
                int id = (int) view.getTable().getValueAt(selectedRow, 0);
                String categoryName = (String) view.getTable().getValueAt(selectedRow, 1);
                int total = (int) view.getTable().getValueAt(selectedRow, 2);
                String dropboxName = (String) view.getTable().getValueAt(selectedRow, 4);

                view.setId(id);
                view.setName(categoryName);
                view.setTotal(total);


                // Find the Dropbox object by name
                for (int i = 0; i < view.getDropBox().getItemCount(); i++) {
                    Dropbox dropbox = view.getDropBox().getItemAt(i);
                    if (dropbox.getDropboxName().equals(dropboxName)) {
                        view.setSelectedDropbox(dropbox);
                        break;
                    }
                }
            }
        }
    }

    class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String categoryName = view.getName();
            int total = view.getTotal();
            int point = total * 100;
            int idDropbox = view.getSelectedDropboxId();

            if (categoryName.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Please fill category name");
                return;
            }

            if (total == 0) {
                JOptionPane.showMessageDialog(view, "Please fill total");
                return;
            }

            if (idDropbox == -1) {
                JOptionPane.showMessageDialog(view, "Please select a dropbox");
                return;
            }


            Trash trash = new Trash();
            trash.setCategoryName(categoryName);
            trash.setTotal(total);
            trash.setPoint(point);
            trash.setIdDropbox(idDropbox);

            try {
                TrashMapper mapper = session.getMapper(TrashMapper.class);
                mapper.insertTrash(trash);
                session.commit();
                getAllData(session);
                view.cleanFields();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Failed to add trash");
            }
        }
    }

    class UpdateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int id = view.getId();
            String categoryName = view.getName();
            int total = view.getTotal();
            int point = total * 100;
            int idDropbox = view.getSelectedDropboxId();

            if (id == -1) {
                JOptionPane.showMessageDialog(view, "Please select a row");
                return;
            }

            if (categoryName.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Please fill category name");
                return;
            }

            if (total == 0) {
                JOptionPane.showMessageDialog(view, "Please fill total");
                return;
            }

            if (idDropbox == -1) {
                JOptionPane.showMessageDialog(view, "Please select a dropbox");
                return;
            }

            Trash trash = new Trash();
            trash.setId(id);
            trash.setCategoryName(categoryName);
            trash.setTotal(total);
            trash.setPoint(point);
            trash.setIdDropbox(idDropbox);

            try {
                TrashMapper mapper = session.getMapper(TrashMapper.class);
                mapper.updateTrash(trash);
                session.commit();
                getAllData(session);
                view.cleanFields();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Failed to update trash");
            }
        }
    }

    class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int id = view.getId();
            if (id == -1) {
                JOptionPane.showMessageDialog(view, "Please select a row");
                return;
            }

            int response = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete this record?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (response != JOptionPane.YES_OPTION) {
                return;
            }

            try {
                mapper.deleteTrash(id);
                session.commit();
                getAllData(session);
                view.cleanFields();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Failed to delete trash");
            }
        }
    }

}
