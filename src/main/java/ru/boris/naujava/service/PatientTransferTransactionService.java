
package ru.boris.naujava.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.boris.naujava.entity.Hospital;
import ru.boris.naujava.entity.PatientTransfer;
import ru.boris.naujava.repository.HospitalRepository;
import ru.boris.naujava.repository.PatientTransferRepository;

@Service
public class PatientTransferTransactionService {

    private final HospitalRepository hospitalRepository;
    private final PatientTransferRepository transferRepository;

    public PatientTransferTransactionService(
            HospitalRepository hospitalRepository,
            PatientTransferRepository transferRepository) {
        this.hospitalRepository = hospitalRepository;
        this.transferRepository = transferRepository;
    }

    @Transactional
    public PatientTransfer createTransferWithHospital(
            String patientId,
            String ambulanceNumber,
            String hospitalName,
            String hospitalAddress,
            String status,
            int transferTimeMinutes) {

        if (patientId == null || patientId.isBlank()) {
            throw new IllegalArgumentException(
                    "ID пациента не может быть пустым.");
        }

        if (hospitalName == null || hospitalName.isBlank()) {
            throw new IllegalArgumentException(
                    "Название больницы не может быть пустым.");
        }

        if (hospitalAddress == null || hospitalAddress.isBlank()) {
            throw new IllegalArgumentException(
                    "Адрес больницы не может быть пустым.");
        }

        if (transferTimeMinutes < 0) {
            throw new IllegalArgumentException(
                    "Время передачи не может быть отрицательным.");
        }

        Hospital hospital = new Hospital();
        hospital.setName(hospitalName);
        hospital.setAddress(hospitalAddress);

        Hospital savedHospital = hospitalRepository.save(hospital);

        PatientTransfer transfer = new PatientTransfer();
        transfer.setPatientId(patientId);
        transfer.setAmbulanceNumber(ambulanceNumber);
        transfer.setHospitalName(hospitalName);
        transfer.setStatus(status);
        transfer.setTransferTimeMinutes(transferTimeMinutes);
        transfer.setHospital(savedHospital);

        return transferRepository.saveAndFlush(transfer);
    }
}
