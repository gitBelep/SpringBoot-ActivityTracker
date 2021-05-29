package springBootActivityTracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
//@ExtendWith(SpringExtension.class)
public class ActivityDaoTest {
    @Autowired
    private ActivityDao dao;

    @BeforeEach
    void setUp() {
        Activity aw = new ActivityWithTrack(LocalDateTime.of(2018,2,2,2,2,0), "Track-es18", ActivityType.BIKING, 8018);
        dao.saveActivity( aw );
        Activity as = new SimpleActivity(LocalDateTime.of(2019,3,3,3,3,0),"Simple", ActivityType.BASKETBALL,"űrben");
        dao.saveActivity( as );
        Activity aa = new Activity(LocalDateTime.of(2020,1,1,1,1,0), "sima-őű", ActivityType.BASKETBALL);
        dao.saveActivity(aa);
        Activity aw2 = new ActivityWithTrack(LocalDateTime.of(2021,2,2,2,2,0), "Track-es21", ActivityType.HIKING, 8021);
        dao.saveActivity( aw2 );
    }


    @Test
    public void testFindActivityById() {
        Activity a1 = dao.findActivityById( 1L);
        assertEquals("Track-es18", a1.getDescr());
        //System.out.println(">>> Megvan a 1L, with"+a1.getDescr());
        assertEquals(8018, ( (ActivityWithTrack)a1 ).getDuration() );
        //System.out.println(">>> Megvan duration: "+ ( (ActivityWithTrack)a1 ).getDuration());
    }

    @Test
    public void testFindActivityByDescription() {
        Activity a2 = dao.findActivityByDescription("sima-őű");
        assertEquals(2020, a2.getStartTime().getYear());

        Activity a3 = dao.findActivityByDescription("Simple");
        assertEquals("űrben", ((SimpleActivity) a3).getPlace());
    }

    @Test
    public void testFindActivityByPartOfTheDescription() {
        Activity a2 = dao.findActivitiesByPartOfDescription("ima-ő").get(0);
        assertEquals(2020, a2.getStartTime().getYear());

        Activity a3 = dao.findActivitiesByPartOfDescription("mple").get(0);
        assertEquals("űrben", ((SimpleActivity) a3).getPlace());

        List<Activity> myList = dao.findActivitiesByPartOfDescription("-");
        System.out.println(">>>"+ myList.toString() );
        assertEquals(3, myList.size() );
    }

//    @Test
//    void testCoordinates(){
//        ActivityWithTrack w1 = (ActivityWithTrack) dao.findActivityByDescription("Track-es18");
//        ActivityWithTrack w2 = (ActivityWithTrack) dao.findActivityByDescription("Track-es21");
//
//        dao.saveCoordinates(List.of(
//                new Coordinate(18.1, 18.1),
//                new Coordinate(18.2, 18.2),
//                new Coordinate(18.3, 18.3)), w1);
//
//        dao.saveCoordinates(List.of(new Coordinate(21.1, 21.2)), w2);
//    }

}
