package ru.kpfu.itis.group11501.smartmuseum.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.group11501.smartmuseum.model.Projector;
import ru.kpfu.itis.group11501.smartmuseum.model.ProjectorStatistic;
import ru.kpfu.itis.group11501.smartmuseum.model.annotation.Action;
import ru.kpfu.itis.group11501.smartmuseum.model.enums.ActionTypeName;
import ru.kpfu.itis.group11501.smartmuseum.repository.ProjectorRepository;
import ru.kpfu.itis.group11501.smartmuseum.service.ProjectorService;
import ru.kpfu.itis.group11501.smartmuseum.service.ProjectorStatisticService;

import java.time.Duration;
import java.util.Date;
import java.util.List;

/**
 * Created by volkov on 12.04.2018.
 */
@Service
@Transactional
public class ProjectorServiceImpl implements ProjectorService {

    private ProjectorRepository projectorRepository;
    private ProjectorStatisticService projectorStatisticService;

    public ProjectorServiceImpl(ProjectorRepository projectorRepository,
                                ProjectorStatisticService projectorStatisticService) {
        this.projectorRepository = projectorRepository;
        this.projectorStatisticService = projectorStatisticService;
    }

    @Override
    public Projector getOneById(Long projectorId){
        return projectorRepository.findOne(projectorId);
    }

    @Override
    public List<Projector> getFreeProjectors() {
        return projectorRepository.findAllFree();
    }

    @Override
    public List<Projector> getAllProjectors() {
        return projectorRepository.findAll();
    }

    @Override
    @Action(name = ActionTypeName.DELETE)
    public void deleteProjector(Long id) {
        projectorRepository.delete(id);
    }

    @Override
    @Action(name = ActionTypeName.TURNON)
    public void turnOn(Long id) {
        projectorRepository.changeStatus(id, 'E');
        projectorStatisticService.addStatistic(getOneById(id));
    }

    @Override
    @Action(name = ActionTypeName.TURNOFF)
    public void turnOff(Long id) {
        projectorRepository.changeStatus(id, 'D');
        Projector projector = getOneById(id);
        projector.setSumTime(projectorStatisticService.addEndDate(projector));
        projectorRepository.save(projector);
    }

    @Override
    @Action(name = ActionTypeName.FAULT)
    public void projectorFault(Long id) {
        projectorRepository.changeStatus(id, 'F');
        Projector projector = getOneById(id);
        projector.setSumTime(projectorStatisticService.addEndDate(projector));
        projectorRepository.save(projector);
    }


    @Action(name = ActionTypeName.ADD)
    public Projector add(Projector projector) {
        return projectorRepository.save(projector);
    }

}
