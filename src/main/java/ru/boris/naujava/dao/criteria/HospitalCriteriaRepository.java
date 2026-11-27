package ru.boris.naujava.dao.criteria;

import ru.boris.naujava.entity.Hospital;
import java.util.List;

public interface HospitalCriteriaRepository {
    List<Hospital> findByName(String name);
    List<Hospital> findBySpecialization(String specialization);
}
