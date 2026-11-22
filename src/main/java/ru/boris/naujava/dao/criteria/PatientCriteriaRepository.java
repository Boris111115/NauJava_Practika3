package ru.boris.naujava.dao.criteria;

import ru.boris.naujava.entity.Patient;
import java.util.List;

public interface PatientCriteriaRepository {
    List<Patient> findByFullName(String fullName);
    List<Patient> findByBloodType(String bloodType);
}
