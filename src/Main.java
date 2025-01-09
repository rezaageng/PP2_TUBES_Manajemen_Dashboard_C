import controller.MainController;
import model.*;
import org.apache.ibatis.session.SqlSession;
import view.*;

public class Main {
    public static void main(String[] args) {
        SqlSession session = MyBatisUtil.getSqlSession();

        MainView mainView = new MainView();
        TrashView trashView = new TrashView();
        CourierView courierView = new CourierView();
        UserView userView = new UserView();
        HistoryView historyView = new HistoryView();

        TrashMapper trashMapper = session.getMapper(TrashMapper.class);
        CourierMapper courierMapper = session.getMapper(CourierMapper.class);
        UserMapper userMapper = session.getMapper(UserMapper.class);
        HistoryMapper historyMapper = session.getMapper(HistoryMapper.class);

        new MainController(mainView, trashView, courierView, userView, historyView, trashMapper, courierMapper, userMapper, historyMapper, session);

        mainView.setVisible(true);
    }
}
