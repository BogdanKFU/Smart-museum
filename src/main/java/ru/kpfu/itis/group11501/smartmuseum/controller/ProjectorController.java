package ru.kpfu.itis.group11501.smartmuseum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kpfu.itis.group11501.smartmuseum.model.Projector;
import ru.kpfu.itis.group11501.smartmuseum.model.ProjectorStatistic;
import ru.kpfu.itis.group11501.smartmuseum.model.ProjectorsVideos;
import ru.kpfu.itis.group11501.smartmuseum.service.ProjectorService;
import ru.kpfu.itis.group11501.smartmuseum.service.ProjectorStatisticService;
import ru.kpfu.itis.group11501.smartmuseum.service.ProjectorsVideosService;
import ru.kpfu.itis.group11501.smartmuseum.util.ProjectorAddForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by Bogdan Popov on 15.04.2018.
 */
@Controller
@RequestMapping("/projector")
public class ProjectorController {

    private ProjectorService projectorService;
    private ProjectorsVideosService projectorsVideosService;
    private ProjectorStatisticService projectorStatisticService;

    @Autowired
    public ProjectorController(ProjectorService projectorService, ProjectorsVideosService projectorsVideosService, ProjectorStatisticService projectorStatisticService) {
        this.projectorService = projectorService;
        this.projectorsVideosService = projectorsVideosService;
        this.projectorStatisticService = projectorStatisticService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String addProjector(@ModelAttribute("projectorForm") @Valid ProjectorAddForm form,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors() || projectorService.findOneByName(form.getName()) != null) {
            redirectAttributes.addAttribute("error", "Неправильное название");
            return "redirect:/projector/all";
        }
        Projector projector = new Projector();
        projector.setName(form.getName());
        projector.setStatus('D');
        projector.setSumTime(0L);
        projectorService.add(projector);
        redirectAttributes.addAttribute("success", "Новый проектор успешно добавлен");
        return "redirect:/projector/all";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String allProjectors(Model model,
                                @RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "success", required = false) String success) {
        if (error != null) {
            model.addAttribute("error", error);
        }
        if (success != null) {
            model.addAttribute("success", success);
        }
        List<Projector> projectors = projectorService.getAllProjectors();
        model.addAttribute("projectors", projectors);
        model.addAttribute("projectorForm", new ProjectorAddForm());
        return "projectors";
    }

    @RequestMapping(value = "/{id}")
    public String getProjector(Model model,
                               @PathVariable(value = "id") Long id,
                               @RequestParam(value = "error", required = false) String error) {
        Projector projector = projectorService.getOneById(id);
        List<ProjectorsVideos> projectorsVideos = projectorsVideosService.getProjectorVideos(projector);
        List<ProjectorStatistic> projectorStatistics = projectorStatisticService.getAllStatistic(projector);
        model.addAttribute("projectorVideos", projectorsVideos);
        model.addAttribute("projector", projector);
        model.addAttribute("error", error);
        model.addAttribute("projectorStatistics", projectorStatistics);
        return "projector";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteProjector(@RequestParam(value = "id") Long id,
                                  RedirectAttributes redirectAttributes) {
        projectorService.deleteProjector(id);
        redirectAttributes.addAttribute("success", "Проектор успешно удален");
        return "redirect:/projector/all";
    }

    @RequestMapping(value = "/{id}/deleteVideo", method = RequestMethod.POST)
    public String getProjector(@PathVariable(value = "id") Long projectorId,
                               @RequestParam(value = "video_id") Long videoId) {
        projectorsVideosService.deleteByProjectorIdByVideoId(projectorId, videoId);
        return "redirect:/projector/" + projectorId;
    }

    @RequestMapping(value = "/{id}/modifyVideo", method = RequestMethod.POST)
    public String getProjector(Model model,
                               @PathVariable(value = "id") Long projectorId,
                               @RequestParam(value = "video_id") Long videoId,
                               @RequestParam(value = "num") Long num) {
        if (projectorsVideosService.getOneByProjectorIdWhereLastNum(projectorId).getNum() < num || 0 >= num) {
            model.addAttribute("error", "Номер указан не верно");
            return "redirect:/projector/" + projectorId;
        }
        ProjectorsVideos projectorsVideos = projectorsVideosService.getOneByProjectorIdByVideoId(projectorId, videoId);
        projectorsVideosService.updateNum(projectorsVideos, num);
        return "redirect:/projector/" + projectorId;
    }


    // Возможно, лучше будет сделать через AJAX
    @RequestMapping(value = "/{id}/turn_on", method = RequestMethod.POST)
    public String turnOn(@PathVariable(value = "id") Long id, HttpServletRequest request) {
        projectorService.turnOn(id);
        return "redirect:" + request.getHeader("Referer");
    }

    @RequestMapping(value = "/{id}/turn_off", method = RequestMethod.POST)
    public String turnOff(@PathVariable(value = "id") Long id, HttpServletRequest request) {
        projectorService.turnOff(id);
        return "redirect:" + request.getHeader("Referer");
    }

    @RequestMapping(value = "/{id}/fault", method = RequestMethod.POST)
    public String brokenProjector(@PathVariable(value = "id") Long id,
                                  HttpServletRequest request){
        Projector projector = projectorService.getOneById(id);
        if (projector.getStatus() == 'F'){
            projectorService.turnOff(id);
        }
        else {
            projectorService.projectorFault(id);
        }
        return "redirect:" + request.getHeader("Referer");
    }
}
