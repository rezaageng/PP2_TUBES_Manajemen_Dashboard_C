package controller;

import model.CourierMapper;
import model.HistoryMapper;
import model.TrashMapper;
import model.UserMapper;
import org.apache.ibatis.session.SqlSession;
import view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController {
    private final TrashView trashView;
    private final CourierView courierView;
    private final UserView userView;
    private final HistoryView historyView;
    private final TrashMapper trashMapper;
    private final CourierMapper courierMapper;
    private final UserMapper userMapper;
    private final HistoryMapper historyMapper;
    private final SqlSession session;

    public MainController(MainView view, TrashView trashView, CourierView courierView, UserView userView, HistoryView historyView, TrashMapper trashMapper, CourierMapper courierMapper, UserMapper userMapper, HistoryMapper historyMapper, SqlSession session) {
        this.trashView = trashView;
        this.courierView = courierView;
        this.userView = userView;
        this.historyView = historyView;

        this.trashMapper = trashMapper;
        this.courierMapper = courierMapper;
        this.userMapper = userMapper;
        this.historyMapper = historyMapper;

        this.session = session;


        view.goToTrash(new TrashButtonListener());
        view.goToCourier(new CourierButtonListener());
        view.goToUser(new UserButtonListener());
        view.goToHistory(new HistoryButtonListener());
    }

    class TrashButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new TrashController(trashView, trashMapper, session);

            trashView.setVisible(true);
        }
    }

    class CourierButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new CourierController(courierView, courierMapper, session);

            courierView.setVisible(true);
        }
    }

    class UserButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new UserController(userView, userMapper, session);

            userView.setVisible(true);
        }
    }

    class HistoryButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new HistoryController(historyView, historyMapper, session);

            historyView.setVisible(true);
        }
    }
}
