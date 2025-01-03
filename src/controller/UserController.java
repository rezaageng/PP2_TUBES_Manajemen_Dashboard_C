package controller;

import model.UserMapper;
import org.apache.ibatis.session.SqlSession;
import view.UserView;

public class UserController {
    private final UserView view;
    private final UserMapper userMapper;
    private final SqlSession session;

    public UserController(UserView userView, UserMapper userMapper, SqlSession session) {
        this.view = userView;
        this.userMapper = userMapper;
        this.session = session;
    }
}
