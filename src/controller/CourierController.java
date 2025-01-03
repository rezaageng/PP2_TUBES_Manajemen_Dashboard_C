package controller;

import model.CourierMapper;
import org.apache.ibatis.session.SqlSession;
import view.CourierView;

public class CourierController {
    private final CourierView view;
    private final CourierMapper courierMapper;
    private final SqlSession session;

    public CourierController(CourierView courierView, CourierMapper courierMapper, SqlSession session) {
        this.view = courierView;
        this.courierMapper = courierMapper;
        this.session = session;
    }
}
