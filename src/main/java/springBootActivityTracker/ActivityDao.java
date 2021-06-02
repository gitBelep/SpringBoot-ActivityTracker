package springBootActivityTracker;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ActivityDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    public void saveActivity(Activity activity) {
        entityManager.persist(activity);
    }

    @Transactional
    public void saveActivityList(List<Activity> activities) {
        for (Activity actual : activities) {
            entityManager.persist(actual);
        }
    }

    public Activity findActivityById(long id) {
        return entityManager.find(Activity.class, id);
    }

    public List<Activity> listAllActivities(int start, int pageLength) {
        return entityManager.createQuery("select a from Activity a order by a.id", Activity.class)
                .setFirstResult(start)
                .setMaxResults(pageLength)
                .getResultList();
    }

    public Activity findActivityByDescription(String descr) {
        return entityManager.createQuery(
                "select a from Activity a where a.descr = :descr", Activity.class)
                .setParameter("descr", descr)
                .getSingleResult();
    }

    public List<Activity> findActivitiesByPartOfDescription(String part) {
        String toFind = "%" + part + "%";
        return entityManager.createQuery(
                "select a from Activity a where a.descr like :descr order by descr desc", Activity.class)
                .setParameter("descr", toFind)
                .getResultList();
    }


    @Transactional
    public void saveCoordinates(List<Coordinate> c, long id) {
        ActivityWithTrack w = entityManager.find(ActivityWithTrack.class, id);
        for (Coordinate actual : c) {
            actual.setCooActivity(w);
            entityManager.persist(actual);
        }
        //annotation -> writingTime will be updated, so I dont need:
//        w.setWritingTime( LocalDateTime.now() );
//        entityManager.persist( w );
    }

    public List<CoordinateDTO> listCoordinateDTOsByLat(double latitude) {
        double latMinus = latitude - 0.01;
        double latPlus = latitude + 0.01;
        return entityManager.createQuery(
                "select new springBootActivityTracker.CoordinateDTO(c.lat, c.lon) from Coordinate c where lat > :lm AND lat < :lp",
                CoordinateDTO.class)
                .setParameter("lm", latMinus)
                .setParameter("lp", latPlus)
                .getResultList();
    }

    public List<CoordinateDTO> listCoordnateDTOsAfterDate(LocalDateTime afterThis) {
        return entityManager.createQuery(
                "select new springBootActivityTracker.CoordinateDTO(c.lat, c.lon) from Coordinate c where c.cooActivity.startTime > :afterDate",
                CoordinateDTO.class)
                .setParameter("afterDate", afterThis)
                .getResultList();
    }

    public ActivityWithTrack findActivityWithCoordinates(long id) {
        return entityManager.createQuery(
                "select w from ActivityWithTrack w join fetch w.coordinates where w.id = :id", ActivityWithTrack.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Transactional
    public void updateDescr(String updatedText, long id) {
        if (updatedText == null || "".equals(updatedText.trim())) {
            throw new IllegalArgumentException("New Text must not be epty");
        } else {
            Activity oldA = entityManager.find(Activity.class, id);
            oldA.setDescr(updatedText);
            entityManager.merge(oldA);
        }
    }

    @Transactional
    public void deleteActivityByDate(LocalDateTime dateTime) {
        Activity aa = entityManager.createQuery(
                "select a from Activity a where a.startTime between ?1 and ?2", Activity.class)
                .setParameter(1, dateTime.minusMinutes(1))
                .setParameter(2, dateTime.plusMinutes(1))
                .getSingleResult();

        if (aa instanceof ActivityWithTrack) {  //cannot delete Activity with connection to Coordinates
            ActivityWithTrack aaWithCoo = findActivityWithCoordinates( aa.getaId() );
            for (Coordinate c : aaWithCoo.getCoordinates()) {
                c.setCooActivity( null );
                entityManager.merge( c );       //update!
            }
            entityManager.createQuery(
                    "delete from Coordinate c where c.cooActivity = :nix")
                    .setParameter("nix", null)
                    .executeUpdate();
        }
        entityManager.remove(aa);              //now I can remove the Activity
    }

}
