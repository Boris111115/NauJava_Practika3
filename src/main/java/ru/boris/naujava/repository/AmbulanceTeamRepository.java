
package ru.boris.naujava.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ru.boris.naujava.entity.AmbulanceTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@RepositoryRestResource(path = "ambulance-teams")
public interface AmbulanceTeamRepository
        extends JpaRepository<AmbulanceTeam, Long> {

    // Производный метод Spring Data JPA
    List<AmbulanceTeam> findByTeamCode(String teamCode);

    // HQL-запрос
    @Query("SELECT a FROM AmbulanceTeam a WHERE a.shift = :shift")
    List<AmbulanceTeam> findByShift(@Param("shift") String shift);
}
