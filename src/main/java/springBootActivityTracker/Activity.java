package springBootActivityTracker;

import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long aId;

    private LocalDateTime startTime;

    @UpdateTimestamp
    private LocalDateTime writingTime;

    @Column(name="description", nullable = false, length = 200)
    private String descr;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private ActivityType type;


    public Activity() {
    }

    public Activity(LocalDateTime startTime, String descr, ActivityType type) {
        if(descr == null || type == null || "".equals(descr.trim())) {
            throw new IllegalArgumentException("Provided data must not be epty");
        }
        this.descr = descr;
        this.startTime = startTime;
        this.type = type;
    }

    public long getaId() {
        return aId;
    }

    public void setaId(long aId) {
        this.aId = aId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getWritingTime() {
        return writingTime;
    }

    public void setWritingTime(LocalDateTime writingTime) {
        this.writingTime = writingTime;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

}
