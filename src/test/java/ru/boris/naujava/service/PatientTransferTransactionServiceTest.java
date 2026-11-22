package ru.boris.naujava.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import ru.boris.naujava.entity.PatientTransfer;
import ru.boris.naujava.repository.HospitalRepository;
import ru.boris.naujava.repository.PatientTransferRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "app.console.enabled=false")
class PatientTransferTransactionServiceTest {

    @Autowired
    private PatientTransferTransactionService transactionService;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private PatientTransferRepository transferRepository;

    @Test
    void createTransferWithHospitalShouldSaveBothEntities() {
        String hospitalName = "Transaction Test Hospital " + System.nanoTime();
        String patientId = "TX-P001-" + System.nanoTime();

        PatientTransfer transfer = transactionService.createTransferWithHospital(
                patientId, "AMB-TX-01", hospitalName,
                "Transaction Test Address", "IN_PROGRESS", 12
        );

        assertNotNull(transfer.getId());
        assertNotNull(transfer.getHospital());
        assertEquals(patientId, transfer.getPatientId());
        assertEquals(hospitalName, transfer.getHospital().getName());
        assertEquals(1, hospitalRepository.findByNameContainingIgnoreCase(hospitalName).size());
        assertEquals(1, transferRepository.findByPatientId(patientId).size());
    }

    @Test
    void createTransferWithHospitalShouldRollbackHospitalWhenTransferSaveFails() {
        String hospitalName = "Rollback Hospital " + System.nanoTime();
        String patientId = "TX-ROLLBACK-" + System.nanoTime();

        // Больница сохраняется первой, но INSERT передачи завершается ошибкой:
        // status имеет NOT NULL в БД, поэтому возникает исключение после первой записи.
        assertThrows(DataIntegrityViolationException.class,
                () -> transactionService.createTransferWithHospital(
                        patientId, "AMB-ROLLBACK", hospitalName,
                        "Rollback Test Address", null, 10
                ));

        assertTrue(hospitalRepository.findByNameContainingIgnoreCase(hospitalName).isEmpty(),
                "Больница должна быть удалена откатом транзакции");
        assertTrue(transferRepository.findByPatientId(patientId).isEmpty(),
                "Передача не должна сохраниться");
    }

    @Test
    void createTransferWithHospitalShouldRejectBlankPatientId() {
        assertThrows(IllegalArgumentException.class,
                () -> transactionService.createTransferWithHospital(
                        " ", "AMB-TX-02", "Another Test Hospital",
                        "Another Test Address", "IN_PROGRESS", 10
                ));
    }

    @Test
    void createTransferWithHospitalShouldRejectNegativeTransferTime() {
        assertThrows(IllegalArgumentException.class,
                () -> transactionService.createTransferWithHospital(
                        "TX-P003", "AMB-TX-03", "Negative Time Hospital",
                        "Negative Time Address", "IN_PROGRESS", -1
                ));
    }
}
