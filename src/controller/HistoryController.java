package controller;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import model.*;
import org.apache.ibatis.session.SqlSession;
import view.HistoryView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class HistoryController {
    private final HistoryView view;
    private final HistoryMapper historyMapper;
    private final SqlSession session;

    public HistoryController(HistoryView historyView, HistoryMapper historyMapper, SqlSession session) {
        this.view = historyView;
        this.historyMapper = historyMapper;
        this.session = session;

        getAllData(session);
        loadCouriers();
        loadUsers();
        loadDropboxes();

        if (view.isListenerAdded()) return;

        view.addTableSelectionListener(new TableSelectionListener());
        view.addButtonListener(new AddButtonListener());
        view.updateButtonListener(new UpdateButtonListener());
        view.deleteButtonListener(new DeleteButtonListener());
        view.addPrintPreviewButtonListener(new PrintPreviewButtonListener());
        view.addPrintButtonListener(new PrintButtonListener());
        view.addExportPdfButtonListener(new ExportPdfButtonListener());

        view.setListenerAdded(true);

    }

    public void getAllData(SqlSession session) {
        try {
            HistoryMapper mapper = session.getMapper(HistoryMapper.class);
            List<History> data = mapper.getAllHistories();
            Object[][] tableData = new Object[data.size()][6];
            for (int i = 0; i < data.size(); i++) {
                History history = data.get(i);
                tableData[i][0] = history.getId();
                tableData[i][1] = history.getTransaction();
                tableData[i][2] = history.getCourierName();
                tableData[i][3] = history.getUserName();
                tableData[i][4] = history.getDropboxName();
                tableData[i][5] = history.getTimestamp();
            }
            Object[] columnNames = {"ID", "Transaction", "Courier", "User", "Dropbox", "Timestamp"};
            view.setTableData(tableData, columnNames);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Failed to get History data");
        }
    }

    public void loadCouriers() {
        try {
            List<Courier> couriers = historyMapper.getAllCourier();
            view.setCouriers(couriers);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Failed to get Courier data");
        }
    }

    public void loadUsers() {
        try {
            List<User> users = historyMapper.getAllUser();
            view.setUsers(users);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Failed to get User data");
        }
    }

    public void loadDropboxes() {
        try {
            List<Dropbox> dropboxes = historyMapper.getAllDropbox();
            view.setDropboxes(dropboxes);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Failed to get Dropbox data");
        }
    }

    class TableSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = view.getTable().getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) view.getTable().getValueAt(selectedRow, 0);
                    String transaction = (String) view.getTable().getValueAt(selectedRow, 1);
                    String courierName = (String) view.getTable().getValueAt(selectedRow, 2);
                    String userName = (String) view.getTable().getValueAt(selectedRow, 3);
                    String dropboxName = (String) view.getTable().getValueAt(selectedRow, 4);
                    String timestamp = (String) view.getTable().getValueAt(selectedRow, 5);
                    view.setId(id);
                    view.setTransaction(transaction);
                    view.setTimestamp(timestamp);
                    for (int i = 0; i < view.getCourierBox().getItemCount(); i++) {
                        Courier courier = view.getCourierBox().getItemAt(i);
                        if (courier.getName().equals(courierName)) {
                            view.setSelectedCourier(courier);
                            break;
                        }
                    }
                    for (int i = 0; i < view.getUserBox().getItemCount(); i++) {
                        User user = view.getUserBox().getItemAt(i);
                        if (user.getName().equals(userName)) {
                            view.setSelectedUser(user);
                            break;
                        }
                    }
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
    }

    class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String transaction = view.getTransaction();
            int courierId = view.getSelectedCourierId();
            int userId = view.getSelectedUserId();
            int dropboxId = view.getSelectedDropboxId();

            if (transaction.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Transaction must be filled");
                return;
            }
            if (courierId == -1) {
                JOptionPane.showMessageDialog(view, "Courier ID must be Selected");
                return;
            }
            if (userId == -1) {
                JOptionPane.showMessageDialog(view, "User must be Selected");
                return;
            }
            if (dropboxId == -1) {
                JOptionPane.showMessageDialog(view, "Dropbox must be selected");
                return;
            }

            History history = new History();

            history.setTransaction(transaction);
            history.setCourierId(courierId);
            history.setUserId(userId);
            history.setDropboxId(dropboxId);

            try {
                historyMapper.insertHistory(history);
                session.commit();
                getAllData(session);
                view.clearFields();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Failed to add History");
            }
        }
    }

    class UpdateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int id = view.getId();
            String transaction = view.getTransaction();
            int courierId = view.getSelectedCourierId();
            int userId = view.getSelectedUserId();
            int dropboxId = view.getSelectedDropboxId();
            String timestamp = view.getTimestamp();

            if (id == -1) {
                JOptionPane.showMessageDialog(view, "Please select a History");
                return;
            }

            if (transaction.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Transaction must be filled");
                return;
            }

            if (courierId == -1) {
                JOptionPane.showMessageDialog(view, "Courier must be selected");
                return;
            }

            if (userId == -1) {
                JOptionPane.showMessageDialog(view, "User must be selected");
                return;
            }

            if (dropboxId == -1) {
                JOptionPane.showMessageDialog(view, "Dropbox must be selected");
                return;
            }

            History history = new History();

            history.setId(id);
            history.setTransaction(transaction);
            history.setCourierId(courierId);
            history.setUserId(userId);
            history.setDropboxId(dropboxId);
            history.setTimestamp(timestamp);

            try {
                historyMapper.updateHistory(history);
                session.commit();
                getAllData(session);
                view.clearFields();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Failed to update History");
            }
        }
    }

    class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int id = view.getId();
            if (id == -1) {
                JOptionPane.showMessageDialog(view, "Please Select a row");
                return;
            }
            try {
                historyMapper.deleteHistory(id);
                session.commit();
                getAllData(session);
                view.clearFields();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Failed to delete History");
            }
        }
    }

    class PrintPreviewButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPrintable(view.getTable().getPrintable(JTable.PrintMode.FIT_WIDTH, null, null));
            boolean doPrint = job.printDialog();
            if (doPrint) {
                try {
                    job.print();
                } catch (PrinterException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(view, "Failed to print preview");
                }
            }
        }
    }

    class PrintButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                boolean complete = view.getTable().print(JTable.PrintMode.FIT_WIDTH, null, null);
                if (complete) {
                    JOptionPane.showMessageDialog(view, "Printing Complete");
                } else {
                    JOptionPane.showMessageDialog(view, "Printing Cancelled");
                }
            } catch (PrinterException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Failed to print");
            }
        }
    }

    class ExportPdfButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Document document = new Document();
            try {
                PdfWriter.getInstance(document, new FileOutputStream("history.pdf"));
                document.open();
                PdfPTable pdfTable = new PdfPTable(view.getTable().getColumnCount());
                for (int i = 0; i < view.getTable().getColumnCount(); i++) {
                    pdfTable.addCell(view.getTable().getColumnName(i));
                }
                for (int rows = 0; rows < view.getTable().getRowCount(); rows++) {
                    for (int cols = 0; cols < view.getTable().getColumnCount(); cols++) {
                        pdfTable.addCell(view.getTable().getModel().getValueAt(rows, cols).toString());
                    }
                }
                document.add(pdfTable);
                document.close();
                JOptionPane.showMessageDialog(view, "Exported to PDF");
            } catch (DocumentException | IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Failed to export to PDF");
            }
        }
    }
}
