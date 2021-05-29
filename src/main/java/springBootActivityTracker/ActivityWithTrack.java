package springBootActivityTracker;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ActivityWithTrack extends Activity{

    private int duration;

    @OneToMany(mappedBy = "cooActivity", cascade = CascadeType.ALL)
    private List<Coordinate> coordinates = new ArrayList<>();


    public ActivityWithTrack() {
    }

    public ActivityWithTrack(LocalDateTime startTime, String description, ActivityType type, int duration){
        super(startTime, description, type);
        this.duration = duration;
    }


    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }
}
