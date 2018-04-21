package ru.kpfu.itis.group11501.smartmuseum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.group11501.smartmuseum.model.Exposition;
import ru.kpfu.itis.group11501.smartmuseum.model.ExpositionProjector;
import ru.kpfu.itis.group11501.smartmuseum.model.Projector;
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
    public ExpositionServiceImpl(ExpositionRepository expositionRepository,
                                 ProjectorService projectorService,
                                 ExpositionProjectorRepository expositionProjectorRepository) {
        this.expositionRepository = expositionRepository;
        this.projectorService = projectorService;
        this.expositionProjectorRepository = expositionProjectorRepository;
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
    public Exposition save(String name, List<String> projectorsId) {
        List<Projector> projectors = new ArrayList<>();
        for (String id : projectorsId) {
            projectors.add(projectorService.getOneById(Long.valueOf(id.trim())));
        }
        return expositionRepository.save(new Exposition(name, projectors));
    }

    @Override
    public void deleteProjector(Long expositionId, Long projectorId) {
        ExpositionProjector expositionProjector = expositionProjectorRepository.findByProjectorAndAndExposition(
                projectorService.getOneById(projectorId),
                this.getExpositionById(expositionId));
        expositionProjectorRepository.delete(expositionProjector);
    }

}
