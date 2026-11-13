package ru.boris.naujava.web;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.boris.naujava.entity.Patient;
import ru.boris.naujava.repository.PatientRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/custom/patients")
public class PatientRestController {
    private final PatientRepository repository;

    public PatientRestController(PatientRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/findByName")
    public List<Patient> findByName(@RequestParam String name) {
        return repository.findByFullNameContainingIgnoreCase(name);
    }

    @GetMapping("/findByBloodType")
    public List<Patient> findByBloodType(@RequestParam String bloodType) {
        return repository.findByBloodType(bloodType);
    }

    @GetMapping("/bornBefore")
    public List<Patient> bornBefore(@RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return repository.findByBirthDateBefore(date);
    }
}
