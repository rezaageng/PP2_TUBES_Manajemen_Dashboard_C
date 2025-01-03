package controller;

import model.CourierMapper;
import model.TrashMapper;
import org.apache.ibatis.session.SqlSession;
import view.CourierView;
import view.MainView;
import view.TrashView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController {
    private final TrashView trashView;
    private final CourierView courierView;
    private final TrashMapper trashMapper;
    private final CourierMapper courierMapper;
    private final SqlSession session;

    public MainController(MainView view, TrashView trashView, CourierView courierView, TrashMapper trashMapper, CourierMapper courierMapper, SqlSession session) {
        this.trashView = trashView;
        this.courierView = courierView;

        this.trashMapper = trashMapper;
        this.courierMapper = courierMapper;
        this.session = session;

        view.goToTrash(new TrashButtonListener());
        view.goToCourier(new CourierButtonListener());
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
}
