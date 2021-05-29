package springBootActivityTracker;

import javax.persistence.*;

@Entity
public class Coordinate {

    @Id
    @TableGenerator(name = "CooIdGen", table = "coo_id_gen", pkColumnName = "coo_id_gen", valueColumnName = "coo_id_value")
    @GeneratedValue(generator = "CooIdGen")
    private long cId;

    @Column(name = "latitude")
    private double lat;

    @Column(name = "longitude")
    private double lon;

    @ManyToOne
    @JoinColumn(name = "aId")
    private  ActivityWithTrack cooActivity;


    public Coordinate() {
    }

    public Coordinate(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }


    public long getcId() {
        return cId;
    }

    public void setcId(long cId) {
        this.cId = cId;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public ActivityWithTrack getCooActivity() {
        return cooActivity;
    }

    public void setCooActivity(ActivityWithTrack cooActivity) {
        this.cooActivity = cooActivity;
    }
}
