package springBootActivityTracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;

@SpringBootTest
public class ActivityDaoTest {
    @Autowired
    private ActivityDao dao;

//    @BeforeEach
//    void setUp() {
//    }

    @Test
    public void saveAndFindActivity() {
        Activity aa = new Activity(LocalDateTime.of(2020,1,1,1,1,0), "első", ActivityType.BASKETBALL);
        System.out.println(">>>> aa: "+ aa.getStartTime().getYear());
        dao.saveActivity(aa);
//        dao.saveActivity(new ActivityWithTrack(LocalDateTime.of(2021,2,2,2,2,0), "másódík", ActivityType.BIKING, 30));

        Activity a1 = dao.findActivityById( 1L);
        System.out.println(a1.getDescr());
    }

}
