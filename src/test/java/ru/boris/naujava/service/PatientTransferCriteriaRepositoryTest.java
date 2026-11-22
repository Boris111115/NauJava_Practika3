
package ru.boris.naujava.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.boris.naujava.entity.Hospital;
import ru.boris.naujava.entity.PatientTransfer;
import ru.boris.naujava.repository.HospitalRepository;
import ru.boris.naujava.dao.criteria.PatientTransferCriteriaRepository;
import ru.boris.naujava.repository.PatientTransferRepository;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = "app.console.enabled=false")
@Transactional
class PatientTransferCriteriaRepositoryTest {

    @Autowired
    private PatientTransferCriteriaRepository criteriaRepository;

    @Autowired
    private PatientTransferRepository transferRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Test
    void findByPatientIdShouldReturnMatchingTransfers() {
        PatientTransfer transfer = createTransfer(
                "CRITERIA-P001", "IN_PROGRESS", "Criteria Hospital"
        );

        transferRepository.saveAndFlush(transfer);

        List<PatientTransfer> result =
                criteriaRepository.findByPatientId("CRITERIA-P001");

        assertEquals(1, result.size());
        assertEquals("CRITERIA-P001", result.get(0).getPatientId());
    }

    @Test
    void findByStatusShouldReturnMatchingTransfers() {
        transferRepository.saveAndFlush(
                createTransfer("CRITERIA-P002", "IN_PROGRESS", "Criteria Hospital")
        );
        transferRepository.saveAndFlush(
                createTransfer("CRITERIA-P003", "COMPLETED", "Criteria Hospital")
        );

        List<PatientTransfer> result =
                criteriaRepository.findByStatus("COMPLETED");

        assertEquals(1, result.size());
        assertEquals("CRITERIA-P003", result.get(0).getPatientId());
    }

    @Test
    void findByStatusAndTransferTimeMinutesBetweenShouldReturnOnlyMatchingTransfers() {
        String uniqueId = UUID.randomUUID().toString();
        String matchingPatientId = "CRITERIA-RANGE-IN-" + uniqueId;
        String outsideRangePatientId = "CRITERIA-RANGE-OUT-" + uniqueId;
        String differentStatusPatientId = "CRITERIA-RANGE-STATUS-" + uniqueId;

        transferRepository.saveAndFlush(
                createTransfer(matchingPatientId, "IN_PROGRESS", "Range Hospital", 30)
        );
        transferRepository.saveAndFlush(
                createTransfer(outsideRangePatientId, "IN_PROGRESS", "Range Hospital", 90)
        );
        transferRepository.saveAndFlush(
                createTransfer(differentStatusPatientId, "COMPLETED", "Range Hospital", 30)
        );

        List<PatientTransfer> result =
                criteriaRepository.findByStatusAndTransferTimeMinutesBetween(
                        "IN_PROGRESS", 10, 50
                );

        assertTrue(result.stream().anyMatch(
                transfer -> matchingPatientId.equals(transfer.getPatientId())
        ));
        assertFalse(result.stream().anyMatch(
                transfer -> outsideRangePatientId.equals(transfer.getPatientId())
        ));
        assertFalse(result.stream().anyMatch(
                transfer -> differentStatusPatientId.equals(transfer.getPatientId())
        ));
    }

    @Test
    void findByHospitalNameShouldReturnMatchingTransfers() {
        Hospital hospital = new Hospital();
        hospital.setName("Criteria Central Hospital");
        hospital.setAddress("Criteria Test Address");

        Hospital savedHospital = hospitalRepository.saveAndFlush(hospital);

        PatientTransfer transfer = createTransfer(
                "CRITERIA-P004",
                "IN_PROGRESS",
                "Criteria Central Hospital"
        );
        transfer.setHospital(savedHospital);

        transferRepository.saveAndFlush(transfer);

        List<PatientTransfer> result =
                criteriaRepository.findByHospitalName("central");

        assertEquals(1, result.size());
        assertEquals("CRITERIA-P004", result.get(0).getPatientId());
    }

    @Test
    void findByPatientIdShouldReturnEmptyListWhenNoMatch() {
        List<PatientTransfer> result =
                criteriaRepository.findByPatientId("NO-SUCH-PATIENT");

        assertEquals(0, result.size());
    }

    private PatientTransfer createTransfer(
            String patientId,
            String status,
            String hospitalName) {
        return createTransfer(patientId, status, hospitalName, 10);
    }

    private PatientTransfer createTransfer(
            String patientId,
            String status,
            String hospitalName,
            int transferTimeMinutes) {

        PatientTransfer transfer = new PatientTransfer();
        transfer.setPatientId(patientId);
        transfer.setAmbulanceNumber("AMB-TEST");
        transfer.setHospitalName(hospitalName);
        transfer.setStatus(status);
        transfer.setTransferTimeMinutes(transferTimeMinutes);

        return transfer;
    }
}
