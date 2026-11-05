package ru.boris.naujava.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import ru.boris.naujava.entity.Patient;

import java.util.List;

@Repository
public class PatientCriteriaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Patient> findByFullName(String fullName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Patient> query = cb.createQuery(Patient.class);
        Root<Patient> patient = query.from(Patient.class);

        query.select(patient)
                .where(cb.like(
                        cb.lower(patient.get("fullName")),
                        "%" + fullName.toLowerCase() + "%"
                ));

        return entityManager.createQuery(query).getResultList();
    }

    public List<Patient> findByBloodType(String bloodType) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Patient> query = cb.createQuery(Patient.class);
        Root<Patient> patient = query.from(Patient.class);

        query.select(patient)
                .where(cb.equal(patient.get("bloodType"), bloodType));

        return entityManager.createQuery(query).getResultList();
    }
}
