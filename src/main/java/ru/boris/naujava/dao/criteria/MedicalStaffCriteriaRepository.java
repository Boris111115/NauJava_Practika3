package ru.boris.naujava.dao.criteria;

import ru.boris.naujava.entity.MedicalStaff;
import java.util.List;

public interface MedicalStaffCriteriaRepository {
    List<MedicalStaff> findByFullName(String fullName);
    List<MedicalStaff> findByPosition(String position);
}
