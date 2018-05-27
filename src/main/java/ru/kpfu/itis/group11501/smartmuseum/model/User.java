package ru.kpfu.itis.group11501.smartmuseum.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.kpfu.itis.group11501.smartmuseum.model.interfaces.GettingId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created by Bogdan Popov on 26.03.2018.
 */
@Entity
@Table(name = "users")
public class User implements UserDetails, GettingId{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    public User(String password, String login, String name, String surname, String thirdName, String photo, boolean status, Role role, Position position, Date blockDate) {
        this.password = password;
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.thirdName = thirdName;
        this.photo = photo;
        this.status = status;
        this.role = role;
        this.position = position;
        this.blockDate = blockDate;
    }

    public User() {
    }

    @JsonIgnore
    private String password;
    @Column( nullable = false)
    private String login;
    @Column( nullable = false)
    private String name;
    @Column( nullable = false)
    private String surname;

    @Column( name = "third_name")
    private String thirdName;

    private String photo;

    @Column( nullable = false)
    private boolean status;


    @ManyToOne(targetEntity = Role.class)
    @JoinColumn(name = "rolesid", referencedColumnName = "id")
//    @ManyToOne(optional = false)
    private Role role;

    @ManyToOne(targetEntity = Position.class)
    @JoinColumn(name = "positionsid", referencedColumnName = "id")
    private Position position;

    @Column( name = "block_date")
    private Date blockDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getThirdName() {
        return thirdName;
    }

    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Date getBlockDate() {
        return blockDate;
    }

    public void setBlockDate(Date blockDate) {
        this.blockDate = blockDate;
    }

    public String getFullName(){
        return this.surname + " " + this.name + " " + this.thirdName;
    }
}
