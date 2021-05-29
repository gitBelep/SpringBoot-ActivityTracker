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

    public Activity findActivityByDescription(String descr){
        return entityManager.createQuery(
                "select a from Activity a where a.descr = :descr", Activity.class)
                .setParameter("descr", descr)
                .getSingleResult();
    }

    public List<Activity> findActivitiesByPartOfDescription(String part){
        String toFind = "%" + part +"%";
        return entityManager.createQuery(
                "select a from Activity a where a.descr like :descr", Activity.class)
                .setParameter("descr", toFind)
                .getResultList();
    }

//    @Transactional
//    public void saveCoordinates(List<Coordinate> c, ActivityWithTrack w){
//        for(Coordinate actual : c) {
//            actual.setCooActivity( w );
//            w.setWritingTime( LocalDateTime.now() );
//            entityManager.persist( actual );
//        }
//    }



}
