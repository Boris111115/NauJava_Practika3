package ru.boris.naujava.repository;

import ru.boris.naujava.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.time.LocalDate;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    // Производный метод: поиск по ФИО
    List<Patient> findByFullNameContainingIgnoreCase(String fullName);

    // HQL-запрос: поиск по группе крови
    @Query("SELECT p FROM Patient p WHERE p.bloodType = :bloodType")
    List<Patient> findByBloodType(@Param("bloodType") String bloodType);

    // Производный метод: поиск по дате рождения
    List<Patient> findByBirthDateBefore(LocalDate date);
}
