package ru.kpfu.itis.group11501.smartmuseum.model;

import org.apache.commons.lang3.time.DateUtils;
import ru.kpfu.itis.group11501.smartmuseum.model.interfaces.GettingId;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

/**
 * Created by volkov on 12.04.2018.
 */


@Entity
@Table(name = "projectors")
public class Projector implements GettingId {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    public Projector(String name, char status, Video currentVideo, Long sumTime, Date videoTime, List<Video> videos) {
        this.name = name;
        this.status = status;
        this.currentVideo = currentVideo;
        this.sumTime = sumTime;
        this.videoTime = videoTime;
        this.videos = videos;
    }

    public Projector() {
    }

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private char status;

    @ManyToOne(targetEntity = Video.class)
    @JoinColumn(name = "current_video", referencedColumnName = "id")
    private Video currentVideo;


    @Column(nullable = false, name = "sum_time")
    private Long sumTime;

    @Column(name = "video_time")
    @Temporal(TemporalType.TIME)
    private Date videoTime;

    // need lazy
    @OneToMany(targetEntity = ProjectorsVideos.class,fetch = FetchType.EAGER,mappedBy = "video")
    private List<Video> videos;

    //maybe will needed
    /*@OneToMany(targetEntity = ProjectorsVideos.class,
            fetch= FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true, mappedBy = "projectors")
    private List<ProjectorsVideos> projectorsVideos;
    */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public Video getCurrentVideo() {
        return currentVideo;
    }

    public void setCurrentVideo(Video currentVideo) {
        this.currentVideo = currentVideo;
    }

    public Long getSumTime() {
        return sumTime;
    }

    public void setSumTime(Long sumTime) {
        this.sumTime = sumTime;
    }

    public Date getVideoTime() {
        return videoTime;
    }

    public void setVideoTime(Date videoTime) {
        this.videoTime = videoTime;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public String getCustomSumTime(){
        long allSeconds = getSumTime() / 1000;
        int seconds = (int) (allSeconds % 60);
        int minutes = (int) ((allSeconds % 3600) / 60);
        int hours = (int) (allSeconds / 3600);
        return hours + " ч " + minutes + " м " + seconds + " с";
    }
}