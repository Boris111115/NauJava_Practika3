package ru.boris.naujava.dao.criteria;

import ru.boris.naujava.entity.PatientTransfer;
import java.util.List;

public interface PatientTransferCriteriaRepository {
    List<PatientTransfer> findByPatientId(String patientId);
    List<PatientTransfer> findByStatus(String status);
    List<PatientTransfer> findByStatusAndTransferTimeMinutesBetween(
            String status, int minMinutes, int maxMinutes
    );
    List<PatientTransfer> findByHospitalName(String name);
}
