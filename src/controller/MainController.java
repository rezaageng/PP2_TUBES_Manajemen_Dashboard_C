package controller;

import model.TrashMapper;
import org.apache.ibatis.session.SqlSession;
import view.MainView;
import view.TrashView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController {
    private TrashView trashView;
    private TrashMapper trashMapper;
    private SqlSession session;

    public MainController(MainView view, TrashView trashView, TrashMapper trashMapper, SqlSession session) {
        this.trashView = trashView;
        this.trashMapper = trashMapper;
        this.session = session;

        view.goToTrash(new TrashButtonListener());
    }

    class TrashButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new TrashController(trashView, trashMapper, session);

            trashView.setVisible(true);
        }
    }
}
