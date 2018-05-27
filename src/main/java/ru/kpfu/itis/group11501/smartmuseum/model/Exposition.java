package ru.kpfu.itis.group11501.smartmuseum.model;

import ru.kpfu.itis.group11501.smartmuseum.model.interfaces.GettingId;

import javax.persistence.*;
import java.util.List;

/**
 * Created by volkov on 13.04.2018.
 */
@Entity
@Table(name = "expositions")
public class Exposition implements GettingId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( nullable = false , unique = true)
    private String name;

    //@ManyToMany(fetch = FetchType.LAZY) need do working
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( name = "expositions_projectors",
            joinColumns = {@JoinColumn(name = "expositionsid")},
            inverseJoinColumns = {@JoinColumn(name = "projectorsid")}
    )
    private List<Projector> projectors;

    public Exposition() {
    }

    public Exposition(String name) {
        this.name = name;
    }

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

    public List<Projector> getProjectors() {
        return projectors;
    }

    public void setProjectors(List<Projector> projectors) {
        this.projectors = projectors;
    }
}
