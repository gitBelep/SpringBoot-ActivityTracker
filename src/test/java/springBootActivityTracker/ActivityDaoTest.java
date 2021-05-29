package springBootActivityTracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        dao.saveActivity(aa);
        dao.saveActivity(new ActivityWithTrack(LocalDateTime.of(2021,2,2,2,2,0), "másódík", ActivityType.BIKING, 30));
        dao.saveActivity(new SimpleActivity(LocalDateTime.of(2019,3,3,3,3,0),"Simple", ActivityType.BASKETBALL,"űr"));

        Activity a1 = dao.findActivityById( 1L);


        assertEquals("első", a1.getDescr());



    }

}
