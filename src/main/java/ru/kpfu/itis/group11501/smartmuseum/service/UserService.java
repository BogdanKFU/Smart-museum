package ru.kpfu.itis.group11501.smartmuseum.service;

//import javafx.util.Pair;
import ru.kpfu.itis.group11501.smartmuseum.model.User;
import ru.kpfu.itis.group11501.smartmuseum.model.UserDto;
import ru.kpfu.itis.group11501.smartmuseum.model.annotation.Action;
import ru.kpfu.itis.group11501.smartmuseum.model.annotation.CoherentEntity;
import ru.kpfu.itis.group11501.smartmuseum.model.enums.ActionTypeName;
import ru.kpfu.itis.group11501.smartmuseum.model.enums.EntityName;
import ru.kpfu.itis.group11501.smartmuseum.util.EditProfileForm;

import java.util.AbstractMap;
import java.util.List;

/**
 * Created by Bogdan Popov on 26.03.2018.
 */
@CoherentEntity(name = EntityName.USER)
public interface UserService {
    AbstractMap.SimpleEntry<User, String> registerNewUserAccount(UserDto accountDto);
//            throws EmailExistsException;

    User getUser(String login);

    User getUser(Long id);

    @Action(name = ActionTypeName.ADD)
    User addUser(User user);

    @Action(name = ActionTypeName.UPDATE)
    User updateUser(User user);

    List<User> getUsersByParameters(String searchField, String role, String position, String status);

    List<User> getAllUsers();

    void updateCurrentSession();

    void blockUser(long id, double blockTime);

    void changePassword(String newPassword, Long id);

    void deleteUser(Long id);

    void unblockUser(Long id);

    boolean loginExists(String login);
}
