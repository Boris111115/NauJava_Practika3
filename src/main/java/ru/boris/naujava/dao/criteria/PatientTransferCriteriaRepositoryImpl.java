
package ru.boris.naujava.dao.criteria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import ru.boris.naujava.entity.PatientTransfer;

import java.util.List;

@Repository
public class PatientTransferCriteriaRepositoryImpl implements PatientTransferCriteriaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PatientTransfer> findByPatientId(String patientId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PatientTransfer> query =
                cb.createQuery(PatientTransfer.class);
        Root<PatientTransfer> pt = query.from(PatientTransfer.class);

        query.select(pt)
                .where(cb.equal(pt.get("patientId"), patientId));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<PatientTransfer> findByStatus(String status) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PatientTransfer> query =
                cb.createQuery(PatientTransfer.class);
        Root<PatientTransfer> pt = query.from(PatientTransfer.class);

        query.select(pt)
                .where(cb.equal(pt.get("status"), status));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<PatientTransfer> findByStatusAndTransferTimeMinutesBetween(
            String status, int minMinutes, int maxMinutes) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PatientTransfer> query =
                cb.createQuery(PatientTransfer.class);
        Root<PatientTransfer> pt = query.from(PatientTransfer.class);

        query.select(pt).where(cb.and(
                cb.equal(pt.get("status"), status),
                cb.between(pt.get("transferTimeMinutes"), minMinutes, maxMinutes)
        ));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
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
