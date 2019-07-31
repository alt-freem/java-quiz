package test.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

public class VisitCounter {
    @Autowired
    private JdbcTemplate jdbc;

    @Transactional(isolation = Isolation.DEFAULT)
    public void pageVisited(int pageId) {
        jdbc.update("update pages p set p.visited = p.visited + 1 where p.id=? ", pageId);
    }

}

