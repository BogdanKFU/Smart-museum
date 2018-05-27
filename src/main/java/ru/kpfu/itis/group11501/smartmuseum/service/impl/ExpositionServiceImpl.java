package ru.kpfu.itis.group11501.smartmuseum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.group11501.smartmuseum.model.Exposition;
import ru.kpfu.itis.group11501.smartmuseum.model.ExpositionProjector;
import ru.kpfu.itis.group11501.smartmuseum.model.Projector;
import ru.kpfu.itis.group11501.smartmuseum.model.annotation.Action;
import ru.kpfu.itis.group11501.smartmuseum.model.enums.ActionTypeName;
import ru.kpfu.itis.group11501.smartmuseum.repository.ExpositionProjectorRepository;
import ru.kpfu.itis.group11501.smartmuseum.repository.ExpositionRepository;
import ru.kpfu.itis.group11501.smartmuseum.service.ExpositionService;
import ru.kpfu.itis.group11501.smartmuseum.service.ProjectorService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by volkov on 13.04.2018.
 */
@Service
public class ExpositionServiceImpl implements ExpositionService {

    private ExpositionRepository expositionRepository;
    private ExpositionProjectorRepository expositionProjectorRepository;
    private ProjectorService projectorService;
    @Autowired
    private ExpositionService expositionService;

    public ExpositionServiceImpl(ExpositionRepository expositionRepository, ExpositionProjectorRepository expositionProjectorRepository, ProjectorService projectorService) {
        this.expositionRepository = expositionRepository;
        this.expositionProjectorRepository = expositionProjectorRepository;
        this.projectorService = projectorService;
    }

    @Override
    public Exposition getExpositionById(Long id) {
        return expositionRepository.findOne(id);
    }

    @Override
    public Exposition getFirstExposition() {
        return expositionRepository.getFirstExposition();
    }

    @Override
    public List<Exposition> getAllExposition() {
        return expositionRepository.findAll();
    }

    @Override
    @Action(name = ActionTypeName.ADD)
    public Exposition save(String name, List<String> projectorsId) {
        List<Projector> projectors = new ArrayList<>();
        for (String id : projectorsId) {
            projectors.add(projectorService.getOneById(Long.valueOf(id.trim())));
        }
        Exposition exposition = expositionRepository.save(new Exposition(name));
        for (Projector projector : projectors) {
            expositionProjectorRepository.save(new ExpositionProjector(projector, exposition));
        }
        return exposition;
    }

    @Override
    public void editExposition(Long id, String name, List<String> deleteProjectors, List<String> newProjectors) {
        Exposition exposition = expositionRepository.findOne(id);
        exposition.setName(name);
        List<Projector> projectors = exposition.getProjectors();
        List<Projector> willDelete = new ArrayList<>();
        if (deleteProjectors != null) {
            for (String projectorId : deleteProjectors) {
                for (Projector projector : projectors) {
                    if (Long.valueOf(projectorId).equals(projector.getId())) {
                        willDelete.add(projector);
                    }
                }
            }
        }
        projectors.removeAll(willDelete);
        List<Projector> newProjectorList = new ArrayList<>();
        if (newProjectors != null) {
            for (String projector : newProjectors) {
                Projector newProjector = projectorService.getOneById(Long.valueOf(projector));
                newProjectorList.add(newProjector);
                projectors.add(newProjector);
            }
        }
        exposition.setProjectors(newProjectorList);
        if (exposition.getProjectors().size() == 0) {
            expositionRepository.delete(exposition);
        } else {
            expositionService.editExposition(exposition);
        }
    }

    @Override
    @Action(name = ActionTypeName.UPDATE)
    public void editExposition(Exposition exposition) {
        List<Projector> projectors = exposition.getProjectors();
        for (Projector projector : projectors) {
            expositionProjectorRepository.save(new ExpositionProjector(projector, exposition));
        }
    }

    @Override
    @Action(name = ActionTypeName.DELETE)
    public void deleteExposition(Long id) {
        Exposition exposition = expositionRepository.findOne(id);
        exposition.setProjectors(null);
        expositionRepository.save(exposition);
        expositionRepository.delete(exposition);
    }

    @Override
    public Exposition findOneByName(String name) {
        return expositionRepository.findOneByName(name);
    }

    @Override
    public void turnOn(Long id) {
        Exposition exposition = expositionService.getExpositionById(id);
        List<Projector> projectors = exposition.getProjectors();
        for (Projector projector : projectors) {
            if (projector.getStatus() != 'F') {
                projectorService.turnOn(projector.getId());
            }
        }
    }

    @Override
    public void turnOff(Long id) {
        Exposition exposition = expositionService.getExpositionById(id);
        List<Projector> projectors = exposition.getProjectors();
        for (Projector projector : projectors) {
            if (projector.getStatus() != 'F') {
                projectorService.turnOff(projector.getId());
            }
        }
    }

    @Override
    public List<Exposition> getSearchExpositions(String searchField) {
        return expositionRepository.findByName("%" + searchField.toLowerCase() + "%");
    }


}
