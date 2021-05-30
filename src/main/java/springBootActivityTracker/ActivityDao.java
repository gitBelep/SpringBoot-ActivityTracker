package springBootActivityTracker;

import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ActivityDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    public void saveActivity(Activity activity){
        entityManager.persist(activity);
    }

    public Activity findActivityById(long id){
        return entityManager.find(Activity.class, id);
    }

    public List<Activity> listAllActivities(){
        return entityManager.createQuery("select a from Activity a order by a.type", Activity.class)
                .getResultList();
    }

    public Activity findActivityByDescription(String descr){
        return entityManager.createQuery(
                "select a from Activity a where a.descr = :descr", Activity.class)
                .setParameter("descr", descr)
                .getSingleResult();
    }

    public List<Activity> findActivitiesByPartOfDescription(String part){
        String toFind = "%" + part +"%";
        return entityManager.createQuery(
                "select a from Activity a where a.descr like :descr order by descr desc", Activity.class)
                .setParameter("descr", toFind)
                .getResultList();
    }


    @Transactional
    public void saveCoordinates(List<Coordinate> c, long id){
        ActivityWithTrack w = entityManager.find(ActivityWithTrack.class, id);
        for(Coordinate actual : c) {
            actual.setCooActivity( w );
            entityManager.persist( actual );
        }
        w.setWritingTime( LocalDateTime.now() );
        entityManager.persist( w );             //update writingTime
    }

    public List<CoordinateDTO> listCoordinateDTOsByLat(double latitude){
        double latMinus = latitude - 0.01;
        double latPlus = latitude + 0.01;
        return entityManager.createQuery(
                "select new springBootActivityTracker.CoordinateDTO(c.lat, c.lon) from Coordinate c where lat > :lm AND lat < :lp",
                CoordinateDTO.class)
                .setParameter("lm", latMinus)
                .setParameter("lp", latPlus)
                .getResultList();
    }

    public List<CoordinateDTO> listCoordnateDTOsAfterDate(LocalDateTime afterThis){
        return entityManager.createQuery(
                "select new springBootActivityTracker.CoordinateDTO(c.lat, c.lon) from Coordinate c where c.cooActivity.startTime > :afterDate",
                CoordinateDTO.class)
                .setParameter("afterDate", afterThis)
                .getResultList();
    }

    public ActivityWithTrack findActivityWithCoordinates(long id){
        return entityManager.createQuery(
                "select w from ActivityWithTrack w join fetch w.coordinates where w.id = :id", ActivityWithTrack.class)
                .setParameter("id", id)
                .getSingleResult();
    }

}
