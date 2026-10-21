
package ru.boris.naujava.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import ru.boris.naujava.entity.PatientTransfer;

import java.util.List;

@Repository
public class PatientTransferCriteriaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<PatientTransfer> findByPatientId(String patientId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PatientTransfer> query =
                cb.createQuery(PatientTransfer.class);
        Root<PatientTransfer> pt = query.from(PatientTransfer.class);

        query.select(pt)
                .where(cb.equal(pt.get("patientId"), patientId));

        return entityManager.createQuery(query).getResultList();
    }

    public List<PatientTransfer> findByStatus(String status) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PatientTransfer> query =
                cb.createQuery(PatientTransfer.class);
        Root<PatientTransfer> pt = query.from(PatientTransfer.class);

        query.select(pt)
                .where(cb.equal(pt.get("status"), status));

        return entityManager.createQuery(query).getResultList();
    }

    public List<PatientTransfer> findByHospitalName(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PatientTransfer> query =
                cb.createQuery(PatientTransfer.class);
        Root<PatientTransfer> pt = query.from(PatientTransfer.class);

        query.select(pt)
                .where(cb.like(
                        cb.lower(pt.join("hospital").get("name")),
                        "%" + name.toLowerCase() + "%"
                ));

        return entityManager.createQuery(query).getResultList();
    }
}
