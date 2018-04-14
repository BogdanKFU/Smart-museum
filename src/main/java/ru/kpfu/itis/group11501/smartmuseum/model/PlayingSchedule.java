package ru.kpfu.itis.group11501.smartmuseum.model;

import javax.persistence.*;

import java.util.Date;

/**
 * Created by volkov on 12.04.2018.
 */

@Entity
@Table(name = "playing_schedule")
public class PlayingSchedule {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public PlayingSchedule(Date begin_time, Date end_time, WeekDay week_day, Projector projector) {
        this.begin_time = begin_time;
        this.end_time = end_time;
        this.week_day = week_day;
        this.projector = projector;
    }
    public PlayingSchedule() {
    }

    @Column( nullable = false)
    @Temporal(TemporalType.TIME)
    private Date begin_time;

    @Column( nullable = false)
    @Temporal(TemporalType.TIME)
    private Date end_time;

    @ManyToOne(optional = false)
    private WeekDay week_day;


    @ManyToOne(optional = false)
    private Projector projector;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(Date begin_time) {
        this.begin_time = begin_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public WeekDay getWeek_day() {
        return week_day;
    }

    public void setWeek_day(WeekDay week_day) {
        this.week_day = week_day;
    }

    public Projector getProjector() {
        return projector;
    }

    public void setProjector(Projector projector) {
        this.projector = projector;
    }
}