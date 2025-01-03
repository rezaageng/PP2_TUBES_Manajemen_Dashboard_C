import controller.MainController;
import model.CourierMapper;
import model.MyBatisUtil;
import model.TrashMapper;
import org.apache.ibatis.session.SqlSession;
import view.CourierView;
import view.MainView;
import view.TrashView;

public class Main {
    public static void main(String[] args) {
        SqlSession session = MyBatisUtil.getSqlSession();

        MainView mainView = new MainView();
        TrashView trashView = new TrashView();
        CourierView courierView = new CourierView();

        TrashMapper trashMapper = session.getMapper(TrashMapper.class);
        CourierMapper courierMapper = session.getMapper(CourierMapper.class);

        new MainController(mainView, trashView, courierView, trashMapper, courierMapper, session);

        mainView.setVisible(true);
    }
}
