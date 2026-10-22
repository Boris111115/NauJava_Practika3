
package ru.boris.naujava.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import ru.boris.naujava.entity.Hospital;

import java.util.List;

@Repository
public class HospitalCriteriaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Hospital> findByName(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Hospital> query = cb.createQuery(Hospital.class);
        Root<Hospital> hospital = query.from(Hospital.class);

        query.select(hospital)
                .where(cb.like(
                        cb.lower(hospital.get("name")),
                        "%" + name.toLowerCase() + "%"
                ));

        return entityManager.createQuery(query).getResultList();
    }

    public List<Hospital> findBySpecialization(String specialization) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Hospital> query = cb.createQuery(Hospital.class);
        Root<Hospital> hospital = query.from(Hospital.class);

        query.select(hospital)
                .where(cb.equal(hospital.get("specialization"), specialization));

        return entityManager.createQuery(query).getResultList();
    }
}
