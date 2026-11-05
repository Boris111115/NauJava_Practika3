package ru.boris.naujava.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.boris.naujava.entity.PatientTransfer;
import ru.boris.naujava.repository.HospitalRepository;
import ru.boris.naujava.repository.PatientTransferRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = "app.console.enabled=false")
@Transactional
class PatientTransferTransactionServiceTest {

    @Autowired
    private PatientTransferTransactionService transactionService;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private PatientTransferRepository transferRepository;

    @Test
    void createTransferWithHospitalShouldSaveBothEntities() {
        PatientTransfer transfer =
                transactionService.createTransferWithHospital(
                        "TX-P001",
                        "AMB-TX-01",
                        "Transaction Test Hospital",
                        "Transaction Test Address",
                        "IN_PROGRESS",
                        12
                );

        assertNotNull(transfer.getId());
        assertNotNull(transfer.getHospital());
        assertEquals("TX-P001", transfer.getPatientId());
        assertEquals(
                "Transaction Test Hospital",
                transfer.getHospital().getName()
        );

        assertEquals(1, hospitalRepository
                .findByNameContainingIgnoreCase("Transaction Test Hospital")
                .size());

        assertEquals(1, transferRepository
                .findByPatientId("TX-P001")
                .size());
    }

    @Test
    void createTransferWithHospitalShouldRejectBlankPatientId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> transactionService.createTransferWithHospital(
                        " ",
                        "AMB-TX-02",
                        "Another Test Hospital",
                        "Another Test Address",
                        "IN_PROGRESS",
                        10
                )
        );
    }

    @Test
    void createTransferWithHospitalShouldRejectNegativeTransferTime() {
        assertThrows(
                IllegalArgumentException.class,
                () -> transactionService.createTransferWithHospital(
                        "TX-P003",
                        "AMB-TX-03",
                        "Negative Time Hospital",
                        "Negative Time Address",
                        "IN_PROGRESS",
                        -1
                )
        );
    }

    @Test
    void createTransferWithHospitalShouldRejectBlankHospitalName() {
        assertThrows(
                IllegalArgumentException.class,
                () -> transactionService.createTransferWithHospital(
                        "TX-P004",
                        "AMB-TX-04",
                        " ",
                        "Some Address",
                        "IN_PROGRESS",
                        10
                )
        );
    }
}
