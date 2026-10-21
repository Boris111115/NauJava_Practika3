
package ru.boris.naujava.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.boris.naujava.entity.Hospital;
import ru.boris.naujava.entity.PatientTransfer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties = "app.console.enabled=false")
@Transactional
class PatientTransferRepositoryTest {

    @Autowired
    private PatientTransferRepository transferRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Test
    void findByPatientIdShouldReturnMatchingTransfers() {
        PatientTransfer first = new PatientTransfer();
        first.setPatientId("TEST-P001");
        first.setAmbulanceNumber("AMB-01");
        first.setHospitalName("Test Hospital");
        first.setStatus("IN_PROGRESS");
        first.setTransferTimeMinutes(15);

        PatientTransfer second = new PatientTransfer();
        second.setPatientId("TEST-P002");
        second.setAmbulanceNumber("AMB-02");
        second.setHospitalName("Test Hospital");
        second.setStatus("COMPLETED");
        second.setTransferTimeMinutes(20);

        transferRepository.save(first);
        transferRepository.save(second);
        transferRepository.flush();

        List<PatientTransfer> result =
                transferRepository.findByPatientId("TEST-P001");

        assertEquals(1, result.size());
        assertEquals("TEST-P001", result.get(0).getPatientId());
    }

    @Test
    void findByHospitalNameUsingQueryShouldReturnMatchingTransfers() {
        Hospital hospital = new Hospital();
        hospital.setName("Test Hospital");
        hospital.setAddress("Test Address");

        Hospital savedHospital = hospitalRepository.saveAndFlush(hospital);

        PatientTransfer transfer = new PatientTransfer();
        transfer.setPatientId("TEST-P003");
        transfer.setAmbulanceNumber("AMB-03");
        transfer.setHospitalName("Test Hospital");
        transfer.setStatus("IN_PROGRESS");
        transfer.setTransferTimeMinutes(10);
        transfer.setHospital(savedHospital);

        transferRepository.saveAndFlush(transfer);

        List<PatientTransfer> result =
                transferRepository.findByHospitalNameUsingQuery("test hosp");

        assertEquals(1, result.size());
        assertEquals("TEST-P003", result.get(0).getPatientId());
        assertEquals(
                "Test Hospital",
                result.get(0).getHospital().getName()
        );
    }
}
