import controller.MainController;
import model.MyBatisUtil;
import model.TrashMapper;
import org.apache.ibatis.session.SqlSession;
import view.MainView;
import view.TrashView;

public class Main {
    public static void main(String[] args) {
        SqlSession session = MyBatisUtil.getSqlSession();

        MainView mainView = new MainView();
        TrashView trashView = new TrashView();

        TrashMapper trashMapper = session.getMapper(TrashMapper.class);

        new MainController(mainView, trashView, trashMapper, session);

        mainView.setVisible(true);
    }
}
