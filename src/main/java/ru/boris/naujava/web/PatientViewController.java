package ru.boris.naujava.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.boris.naujava.repository.PatientRepository;

@Controller
@RequestMapping("/patients/view")
public class PatientViewController {
    private final PatientRepository repository;

    public PatientViewController(PatientRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("patients", repository.findAll());
        return "patients";
    }
}
