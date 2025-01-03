package controller;

import model.HistoryMapper;
import org.apache.ibatis.session.SqlSession;
import view.HistoryView;

public class HistoryController {
    private final HistoryView view;
    private final HistoryMapper historyMapper;
    private final SqlSession session;

    public HistoryController(HistoryView historyView, HistoryMapper historyMapper, SqlSession session) {
        this.view = historyView;
        this.historyMapper = historyMapper;
        this.session = session;
    }

}
