package ru.boris.naujava.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import ru.boris.naujava.dao.PatientTransferRepository;
import ru.boris.naujava.entity.PatientTransfer;

import java.util.List;

@Service
public class PatientTransferServiceImpl
        implements PatientTransferService {

    private final PatientTransferRepository repository;

    public PatientTransferServiceImpl(
            PatientTransferRepository repository) {

        this.repository = repository;
    }

    @PostConstruct
    public void init() {
        System.out.println(
                "Сервис PatientTransferService успешно инициализирован."
        );
    }

    @Override
    public void createTransfer(
            Long id,
            String patientId,
            String ambulanceNumber,
            String hospitalName,
            String status,
            int transferTimeMinutes) {

        if (id == null) {
            throw new IllegalArgumentException(
                    "ID не может быть null."
            );
        }

        if (patientId == null || patientId.isBlank()) {
            throw new IllegalArgumentException(
                    "ID пациента не может быть пустым."
            );
        }

        if (transferTimeMinutes < 0) {
            throw new IllegalArgumentException(
                    "Время передачи не может быть отрицательным."
            );
        }

        PatientTransfer transfer = new PatientTransfer(
                id,
                patientId,
                ambulanceNumber,
                hospitalName,
                status,
                transferTimeMinutes
        );

        repository.create(transfer);
    }

    @Override
    public PatientTransfer findById(Long id) {
        return repository.read(id);
    }

    @Override
    public void updateTransfer(
            Long id,
            String patientId,
            String ambulanceNumber,
            String hospitalName,
            String status,
            int transferTimeMinutes) {

        if (transferTimeMinutes < 0) {
            throw new IllegalArgumentException(
                    "Время передачи не может быть отрицательным."
            );
        }

        PatientTransfer transfer = new PatientTransfer(
                id,
                patientId,
                ambulanceNumber,
                hospitalName,
                status,
                transferTimeMinutes
        );

        repository.update(transfer);
    }

    @Override
    public void deleteById(Long id) {
        repository.delete(id);
    }

    @Override
    public List<PatientTransfer> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean isTransferDelayed(Long id) {

        PatientTransfer transfer = repository.read(id);

        if (transfer == null) {
            throw new IllegalArgumentException(
                    "Передача с id " + id + " не найдена."
            );
        }

        return transfer.getTransferTimeMinutes() > 10;
    }
}