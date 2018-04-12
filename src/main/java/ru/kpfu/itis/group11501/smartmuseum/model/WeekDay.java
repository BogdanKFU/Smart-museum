package ru.kpfu.itis.group11501.smartmuseum.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by volkov on 12.04.2018.
 */

@Entity
@Table(name = "videos")
public class WeekDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public WeekDay(String name ) {
        this.name = name;
    }

    public WeekDay() {
    }

    @Column( nullable = false)
    private String name;


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
}