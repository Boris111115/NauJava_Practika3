package ru.boris.naujava.service;

import ru.boris.naujava.entity.PatientTransfer;

import java.util.List;

public interface PatientTransferService {

    void createTransfer(
            Long id,
            String patientId,
            String ambulanceNumber,
            String hospitalName,
            String status,
            int transferTimeMinutes
    );

    PatientTransfer findById(Long id);

    void updateTransfer(
            Long id,
            String patientId,
            String ambulanceNumber,
            String hospitalName,
            String status,
            int transferTimeMinutes
    );

    void deleteById(Long id);

    List<PatientTransfer> findAll();

    boolean isTransferDelayed(Long id);
}