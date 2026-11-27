
package ru.boris.naujava.service;

import org.springframework.stereotype.Service;
import ru.boris.naujava.entity.PatientTransfer;
import ru.boris.naujava.repository.PatientTransferCriteriaRepository;
import ru.boris.naujava.repository.PatientTransferRepository;

import java.util.List;

@Service
public class PatientTransferSearchService {

    private final PatientTransferRepository repository;
    private final PatientTransferCriteriaRepository criteriaRepository;

    public PatientTransferSearchService(
            PatientTransferRepository repository,
            PatientTransferCriteriaRepository criteriaRepository) {
        this.repository = repository;
        this.criteriaRepository = criteriaRepository;
    }

    // Derived Query — поиск по ID пациента
    public List<PatientTransfer> findByPatientId(String patientId) {
        return repository.findByPatientId(patientId);
    }

    // JPQL — поиск по названию связанной больницы
    public List<PatientTransfer> findByHospitalNameUsingQuery(String name) {
        return repository.findByHospitalNameUsingQuery(name);
    }

    // Criteria API — поиск по названию связанной больницы
    public List<PatientTransfer> findByHospitalNameUsingCriteria(String name) {
        return criteriaRepository.findByHospitalName(name);
    }
}
