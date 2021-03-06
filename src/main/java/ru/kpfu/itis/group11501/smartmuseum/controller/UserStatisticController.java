package ru.kpfu.itis.group11501.smartmuseum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kpfu.itis.group11501.smartmuseum.model.ActionType;
import ru.kpfu.itis.group11501.smartmuseum.model.TableName;
import ru.kpfu.itis.group11501.smartmuseum.model.User;
import ru.kpfu.itis.group11501.smartmuseum.model.UserStatistic;
import ru.kpfu.itis.group11501.smartmuseum.model.enums.ActionTypeName;
import ru.kpfu.itis.group11501.smartmuseum.model.enums.EntityName;
import ru.kpfu.itis.group11501.smartmuseum.service.ActionTypeService;
import ru.kpfu.itis.group11501.smartmuseum.service.TableNameService;
import ru.kpfu.itis.group11501.smartmuseum.service.UserService;
import ru.kpfu.itis.group11501.smartmuseum.service.UserStatisticService;
import ru.kpfu.itis.group11501.smartmuseum.util.Helpers;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by volkov on 26.04.2018.
 */
@Controller
@RequestMapping(path = "/user_statistic")
public class UserStatisticController {

    private UserStatisticService userStatisticService;
    private UserService userService;
    private ActionTypeService actionTypeService;
    private TableNameService tableNameService;

    public UserStatisticController(UserStatisticService userStatisticService, UserService userService, ActionTypeService actionTypeService, TableNameService tableNameService) {
        this.userStatisticService = userStatisticService;
        this.userService = userService;
        this.actionTypeService = actionTypeService;
        this.tableNameService = tableNameService;
    }

    @ModelAttribute("actions")
    public List<ActionType> actionsName() {
        List<ActionType> actionTypes = actionTypeService.findAll();
        for (ActionType actionType : actionTypes) {
            actionType.setName(ActionTypeName.valueOf(actionType.getName()).getRusName());
        }
        return actionTypes;
    }

    @ModelAttribute("entities")
    public List<TableName> entitiesName() {
        List<TableName> tableNames = tableNameService.findAll();
        for (TableName tableName : tableNames) {
            tableName.setName(EntityName.valueOf(tableName.getName()).getRusName());
        }
        return tableNames;
    }

    @ModelAttribute("users")
    public List<User> usersName() {
        return userService.getAllUsers();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String userStatisticSearch(Model model,
                                      @RequestParam(value = "page", required = false) String page,
                                      @RequestParam(value = "searchField", required = false) String searchField,
                                      @RequestParam(value = "actions", required = false) List<Long> actions,
                                      @RequestParam(value = "entities", required = false) List<Long> entities,
                                      @RequestParam(value = "users", required = false) List<Long> users,
                                      HttpSession httpSession) {

        Long currentPage = 0L;
        if (page != null) currentPage = Long.valueOf(page);
        Long lastPage = userStatisticService.getLastPage(users, actions, entities, searchField);
        List<Long> pages = Helpers.getListPages(currentPage, lastPage, 4L);


        List<UserStatistic> userStatistics = userStatisticService.findByParameter(users, actions, entities, searchField, currentPage);
        if (userStatistics == null) return "user_statistic";
        userStatistics = userStatisticService.setRussianNames(userStatistics);

        model.addAttribute("user_statistic", userStatistics);
        model.addAttribute("pages", pages);
        model.addAttribute("page", currentPage);
        model.addAttribute("lastPage", lastPage);
        httpSession.setAttribute("searchField", searchField);
        httpSession.setAttribute("actions", actions);
        httpSession.setAttribute("entities", entities);
        httpSession.setAttribute("users", users);
        return "user_statistic";
    }

    @RequestMapping(value = "/goToAnotherPage", method = RequestMethod.GET)
    public String getPlayingSchedule(@RequestParam(value = "page", required = false) String page,
                                     RedirectAttributes redirectAttributes,
                                     HttpSession httpSession) {


        redirectAttributes.addAttribute("searchField", httpSession.getAttribute("searchField"));
        redirectAttributes.addAttribute("actions", httpSession.getAttribute("actions"));
        redirectAttributes.addAttribute("entities", httpSession.getAttribute("entities"));
        redirectAttributes.addAttribute("users", httpSession.getAttribute("users"));
        redirectAttributes.addAttribute("page", page);
        return "redirect:/user_statistic";
    }

}
