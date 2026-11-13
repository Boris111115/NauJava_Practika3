
package ru.boris.naujava.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import ru.boris.naujava.entity.AmbulanceTeam;

import java.util.List;

@Repository
public class AmbulanceTeamCriteriaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<AmbulanceTeam> findByTeamCode(String teamCode) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<AmbulanceTeam> query =
                cb.createQuery(AmbulanceTeam.class);
        Root<AmbulanceTeam> team = query.from(AmbulanceTeam.class);

        query.select(team)
                .where(cb.equal(team.get("teamCode"), teamCode));

        return entityManager.createQuery(query).getResultList();
    }

    public List<AmbulanceTeam> findByShift(String shift) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<AmbulanceTeam> query =
                cb.createQuery(AmbulanceTeam.class);
        Root<AmbulanceTeam> team = query.from(AmbulanceTeam.class);

        query.select(team)
                .where(cb.equal(team.get("shift"), shift));

        return entityManager.createQuery(query).getResultList();
    }
}
