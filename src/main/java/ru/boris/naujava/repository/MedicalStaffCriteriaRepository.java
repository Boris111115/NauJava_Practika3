package ru.boris.naujava.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import ru.boris.naujava.entity.MedicalStaff;

import java.util.List;

@Repository
public class MedicalStaffCriteriaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<MedicalStaff> findByFullName(String fullName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MedicalStaff> query =
                cb.createQuery(MedicalStaff.class);
        Root<MedicalStaff> staff = query.from(MedicalStaff.class);

        if (fullName == null || fullName.isBlank()) {
            query.select(staff);
        } else {
            query.select(staff)
                    .where(cb.like(
                            cb.lower(staff.get("fullName")),
                            "%" + fullName.trim().toLowerCase() + "%"
                    ));
        }

        return entityManager.createQuery(query).getResultList();
    }

    public List<MedicalStaff> findByPosition(String position) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MedicalStaff> query =
                cb.createQuery(MedicalStaff.class);
        Root<MedicalStaff> staff = query.from(MedicalStaff.class);

        if (position == null || position.isBlank()) {
            query.select(staff);
        } else {
            query.select(staff)
                    .where(cb.equal(staff.get("position"), position.trim()));
        }

        return entityManager.createQuery(query).getResultList();
    }
}
