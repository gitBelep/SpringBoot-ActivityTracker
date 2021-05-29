package springBootActivityTracker;

import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

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

//    public Activity findActivityByDescription(String d){
//        return entityManager.createQuery(
//                "select a from Activty a where descr like %:d%", Activity.class)
//                .setParameter("d", d)
//                .getSingleResult();
//    }



}
