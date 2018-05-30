package ru.kpfu.itis.group11501.smartmuseum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kpfu.itis.group11501.smartmuseum.model.User;
import ru.kpfu.itis.group11501.smartmuseum.service.PositionService;
import ru.kpfu.itis.group11501.smartmuseum.service.RoleService;
import ru.kpfu.itis.group11501.smartmuseum.service.UserService;
import ru.kpfu.itis.group11501.smartmuseum.util.*;
import ru.kpfu.itis.group11501.smartmuseum.util.Transoformers.EditProfileFormToUserTransformer;
import ru.kpfu.itis.group11501.smartmuseum.validator.ChangePasswordFormValidator;
import ru.kpfu.itis.group11501.smartmuseum.validator.EditProfileFormValidator;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by Amir Kadyrov
 * Date: 27.04.2018
 */
@Controller
public class UserController {
    private UserService userService;
    private RoleService roleService;
    private PositionService positionService;
    private EditProfileFormToUserTransformer transformer;
    private EditProfileFormValidator editProfileFormValidator;
    private ChangePasswordFormValidator changePasswordFormValidator;

    @Autowired
    public UserController(UserService userService, RoleService roleService, PositionService positionService,
                          EditProfileFormToUserTransformer transformer, EditProfileFormValidator editProfileFormValidator,
                          ChangePasswordFormValidator changePasswordFormValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.positionService = positionService;
        this.transformer = transformer;
        this.editProfileFormValidator = editProfileFormValidator;
        this.changePasswordFormValidator = changePasswordFormValidator;
    }


    @RequestMapping(value = "/profile/edit", method = RequestMethod.GET)
    public String getNormalEditProfile(Model model) {

        User editableUser = Helpers.getCurrentUser();
        if (editableUser.getRole().getName().equals("ADMIN")) {
            return "redirect:/admin/users/" + editableUser.getId() + "/edit";
        }
        if (!model.containsAttribute("editForm")) {
            EditProfileForm editProfileForm = new EditProfileForm(editableUser.getId(), editableUser.getLogin(), editableUser.getName(),
                    editableUser.getSurname(), editableUser.getThirdName(), editableUser.getPhoto());
            model.addAttribute("editForm", editProfileForm);
        }
        if (!model.containsAttribute("changePasswordForm")) {
            model.addAttribute("changePasswordForm", new ChangePasswordForm());
        }
        model.addAttribute("u", Helpers.getCurrentUser());
        return "edit_profile";
    }

    @RequestMapping(value = "/edit_profile", method = RequestMethod.POST)
    public String postEditProfile(@ModelAttribute("editForm") @Valid EditProfileForm editProfileForm,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {
        editProfileForm.setId(Helpers.getCurrentUser().getId());
        editProfileFormValidator.validate(editProfileForm, bindingResult);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editForm", bindingResult);
            redirectAttributes.addFlashAttribute("editForm", editProfileForm);
            return "redirect:/profile/edit";
        }

        User editableUser = transformer.apply(editProfileForm);
        userService.updateUser(editableUser);
        userService.updateCurrentSession();
        return "redirect:/profile";
    }

    @RequestMapping(value = "/change_password", method = RequestMethod.POST)
    public String changePassword(@ModelAttribute("changePasswordForm") @Valid ChangePasswordForm changePasswordForm,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        changePasswordFormValidator.validate(changePasswordForm, bindingResult);
        if (bindingResult.hasErrors()) {
            BeanPropertyBindingResult result = new BeanPropertyBindingResult(changePasswordForm,bindingResult.getObjectName());
            for (FieldError error: bindingResult.getFieldErrors()){
                result.addError(new FieldError(error.getObjectName(),error.getField(),null,error.isBindingFailure(),error.getCodes(),error.getArguments(),error.getDefaultMessage()));
            }
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.changePasswordForm", result);
            redirectAttributes.addFlashAttribute("changePasswordForm", new ChangePasswordForm());
            return "redirect:/profile/edit";
        }

        userService.changePassword(changePasswordForm.getNewPassword(), Helpers.getCurrentUser().getId());
        userService.updateCurrentSession();
        return "redirect:/profile";
    }

    @RequestMapping(value = "/admin/users/{id}/edit", method = RequestMethod.GET)
    public String getAdminEditProfile(Model model, @PathVariable(value = "id", required = true) Long id) {
        User editableUser = userService.getUser(id);


        if (!model.containsAttribute("editForm")) {
            AdminEditProfileForm editProfileForm = new AdminEditProfileForm(editableUser.getId(), editableUser.getLogin(), editableUser.getName(),
                    editableUser.getSurname(), editableUser.getThirdName(), editableUser.getPhoto(),
                    editableUser.getPosition().getId(), editableUser.getRole().getId());
            model.addAttribute("editForm", editProfileForm);
        }
        if (!model.containsAttribute("changePasswordForm")) {
            model.addAttribute("changePasswordForm", new ChangePasswordForm());
        }
        model.addAttribute("positions", positionService.getAllPositions());
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("u", Helpers.getCurrentUser());
        return "edit_profile";
    }

    @RequestMapping(value = "/admin/users/{id}/edit_profile", method = RequestMethod.POST)
    public String postAdminEditProfile(@PathVariable("id") Long id,
                                       @ModelAttribute("editForm") @Valid AdminEditProfileForm editProfileForm,
                                       BindingResult bindingResult,
                                       RedirectAttributes redirectAttributes) {
        editProfileForm.setId(id);
        editProfileFormValidator.validate(editProfileForm, bindingResult);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editForm", bindingResult);
            redirectAttributes.addFlashAttribute("editForm", editProfileForm);
            return "redirect:/admin/users/" + id + "/edit";
        }

        User editableUser = transformer.apply(editProfileForm);
        userService.updateUser(editableUser);

        if (editProfileForm.getId().equals(Helpers.getCurrentUser().getId())) {
            userService.updateCurrentSession();
        }
        return "redirect:/admin/users";
    }


    @RequestMapping(value = "/admin/users/{id}/change_password", method = RequestMethod.POST)
    public String changePasswordAdmin(@PathVariable("id") Long id,
                                      @ModelAttribute("changePasswordForm") @Valid ChangePasswordForm changePasswordForm,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            BeanPropertyBindingResult result = new BeanPropertyBindingResult(changePasswordForm,bindingResult.getObjectName());
            for (FieldError error: bindingResult.getFieldErrors()){
                result.addError(new FieldError(error.getObjectName(),error.getField(),null,error.isBindingFailure(),error.getCodes(),error.getArguments(),error.getDefaultMessage()));
            }
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.changePasswordForm", result);
            redirectAttributes.addFlashAttribute("changePasswordForm", new ChangePasswordForm());
            return "redirect:/admin/users/" + id + "/edit";
        }

        userService.changePassword(changePasswordForm.getNewPassword(), id);
        if (id.equals(Helpers.getCurrentUser().getId())) {
            userService.updateCurrentSession();
        }
        return "redirect:/admin/users";
    }

    @RequestMapping(value = "/admin/users/delete", method = RequestMethod.POST)
    public String deleteUser(@RequestParam(value = "id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    @RequestMapping(path = "/profile", method = RequestMethod.GET)
    public String get(Model model) {
        User user = Helpers.getCurrentUser();
        model.addAttribute("u", user);
        return "private_page";
    }
}
