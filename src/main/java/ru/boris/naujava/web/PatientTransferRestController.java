package ru.boris.naujava.web;

import org.springframework.web.bind.annotation.*;
import ru.boris.naujava.entity.PatientTransfer;
import ru.boris.naujava.repository.PatientTransferRepository;

import java.util.List;

@RestController
@RequestMapping("/custom/patient-transfers")
public class PatientTransferRestController {
    private final PatientTransferRepository repository;

    public PatientTransferRestController(PatientTransferRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/findByPatientId")
    public List<PatientTransfer> findByPatientId(@RequestParam String patientId) {
        return repository.findByPatientId(patientId);
    }

    @GetMapping("/findByHospitalName")
    public List<PatientTransfer> findByHospitalName(@RequestParam String name) {
        return repository.findByHospitalNameUsingQuery(name);
    }
}
