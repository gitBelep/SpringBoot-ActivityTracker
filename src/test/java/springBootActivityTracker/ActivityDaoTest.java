package springBootActivityTracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
//@ExtendWith(SpringExtension.class)   //this is not needed
class ActivityDaoTest {
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

        dao.saveCoordinates(List.of(
                new Coordinate(18.1, 18.1),
                new Coordinate(18.2, 18.2),
                new Coordinate(18.3, 18.3)), 1L);
        dao.saveCoordinates(List.of(new Coordinate(21.1, 30.2)), 4L);
    }


    @Test
    void testFindActivityById() {
        Activity a1 = dao.findActivityById( 1L);
        assertEquals("Track-es18", a1.getDescr());
        //System.out.println(">>> Megvan a 1L, with"+a1.getDescr());
        assertEquals(8018, ( (ActivityWithTrack)a1 ).getDuration() );
        //System.out.println(">>> Megvan duration: "+ ( (ActivityWithTrack)a1 ).getDuration());
    }

    @Test
    void testFindActivityByDescription() {
        Activity a2 = dao.findActivityByDescription("sima-őű");
        assertEquals(2020, a2.getStartTime().getYear());

        Activity a3 = dao.findActivityByDescription("Simple");
        assertEquals("űrben", ((SimpleActivity) a3).getPlace());
    }

    @Test
    void testListAllActivity(){
        assertEquals(4, dao.listAllActivities().size());
    }

    @Test
    void testFindActivityByPartOfTheDescription() {
        Activity a2 = dao.findActivitiesByPartOfDescription("ima-ő").get(0);
        Activity a3 = dao.findActivitiesByPartOfDescription("mple").get(0);
        List<Activity> myList = dao.findActivitiesByPartOfDescription("-");
//        for(Activity a : myList) {          //descending?
//            System.out.println(a.getDescr());
//        }

        assertEquals(2020, a2.getStartTime().getYear());
        assertEquals("űrben", ((SimpleActivity) a3).getPlace());
        assertEquals(3, myList.size() );
    }

    @Test
    void testSaveCoordinatesAndDTOs() {
        CoordinateDTO cd1 = dao.listCoordinateDTOsByLat(18.1).get(0);
        CoordinateDTO cd2 = dao.listCoordinateDTOsByLat(21.1).get(0);

        assertEquals(18.1, cd1.getLongitude(), 0.01);
        assertEquals(30.2, cd2.getLongitude(), 0.01);
    }

    @Test
    void testFindActivityWithCoordinates() {
        ActivityWithTrack w1 = dao.findActivityWithCoordinates(1L);
        ActivityWithTrack w2 = dao.findActivityWithCoordinates(4L);

        assertEquals(3, w1.getCoordinates().size() );
        assertEquals(18.3, w1.getCoordinates().get(2).getLat(), 0.01);
        assertEquals(30.2, w2.getCoordinates().get(0).getLon(),0.01 );

        //an attribute of an Activity of a Coordinate of w2-Activity
        assertEquals(8021, w2.getCoordinates().get(0).getCooActivity().getDuration() );

        assertTrue(w2.getStartTime().isBefore( w2.getWritingTime()) );
    }

    @Test
    void testListCoordinateDTOsAfterADate(){
        List<CoordinateDTO> cdl1 = dao.listCoordnateDTOsAfterDate(LocalDateTime.of(2017,1,1,2,2,0));
        List<CoordinateDTO> cdl2 = dao.listCoordnateDTOsAfterDate(LocalDateTime.of(2018,8,1,2,2,0));

        assertEquals(4, cdl1.size());
        assertEquals(1, cdl2.size());
    }

}
