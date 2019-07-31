package test.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

public class JpaVisitCounter {

    @Autowired
    private EntityManager db;

    @Transactional(isolation = Isolation.DEFAULT)
    public void pageVisited(int pageId) {
        PageCountEntity pageCount = db.find(PageCountEntity.class, pageId);
        pageCount.visit();
        db.merge(pageCount);
        db.flush();
    }


    @Entity
    static class PageCountEntity {

        @Id
        @GeneratedValue
        private Integer id;

        private int visitedCount;

        public void visit() {
            visitedCount++;
        }
    }
}
