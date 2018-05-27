package ru.kpfu.itis.group11501.smartmuseum.model.enums;

/**
 * Created by volkov on 25.04.2018.
 */
public enum EntityName {
    EXPOSITION("Экспозиция", "/expositions/"),
    PLAYINGSCHEDULE("Расписание", null), // not page for individual playing_schedule
    PROJECTORSVIDEOS("ПроеткорВидео",  null),  //not page for individual projector_video; only add
    VIDEO("Видео", null),   //not page for individual video
    PROJECTOR("Проектор", "/projector/"),
    USER("Пользователь", "/admin/users/");
    private final String rusName;
    private final String link;

    EntityName (String rusName,  String link) {
        this.rusName = rusName;
        this.link = link;
    }

    public String getRusName() {
        return rusName;
    }

    public String getLink() {
        return link;
    }
}
