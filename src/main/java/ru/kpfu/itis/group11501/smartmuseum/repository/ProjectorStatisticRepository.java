package ru.kpfu.itis.group11501.smartmuseum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kpfu.itis.group11501.smartmuseum.model.Projector;
import ru.kpfu.itis.group11501.smartmuseum.model.ProjectorStatistic;

import java.util.List;

/**
 * Created by Bogdan Popov on 22.04.2018.
 */
public interface ProjectorStatisticRepository extends JpaRepository<ProjectorStatistic, Long> {
    List<ProjectorStatistic> findAllByProjector(Projector projector);

    @Query("select p from ProjectorStatistic p where p.projector = ?1 and p.endDate IS NULL")
    ProjectorStatistic getLastStatistics(Projector projector);
}
