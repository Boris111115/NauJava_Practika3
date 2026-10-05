package ru.boris.naujava.dao;

import org.springframework.stereotype.Component;
import ru.boris.naujava.entity.PatientTransfer;

import java.util.ArrayList;
import java.util.List;

@Component
public class PatientTransferRepository
        implements CrudRepository<PatientTransfer, Long> {

    private final List<PatientTransfer> patientTransferContainer;

    public PatientTransferRepository(
            List<PatientTransfer> patientTransferContainer) {

        this.patientTransferContainer = patientTransferContainer;
    }

    @Override
    public void create(PatientTransfer entity) {

        if (read(entity.getId()) != null) {
            throw new IllegalArgumentException(
                    "Передача с id " + entity.getId() + " уже существует."
            );
        }

        patientTransferContainer.add(entity);
    }

    @Override
    public PatientTransfer read(Long id) {

        return patientTransferContainer.stream()
                .filter(transfer -> transfer.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(PatientTransfer entity) {

        for (int i = 0; i < patientTransferContainer.size(); i++) {

            if (patientTransferContainer.get(i)
                    .getId()
                    .equals(entity.getId())) {

                patientTransferContainer.set(i, entity);
                return;
            }
        }

        throw new IllegalArgumentException(
                "Передача с id " + entity.getId() + " не найдена."
        );
    }

    @Override
    public void delete(Long id) {

        boolean removed = patientTransferContainer.removeIf(
                transfer -> transfer.getId().equals(id)
        );

        if (!removed) {
            throw new IllegalArgumentException(
                    "Передача с id " + id + " не найдена."
            );
        }
    }

    public List<PatientTransfer> findAll() {
        return new ArrayList<>(patientTransferContainer);
    }
}