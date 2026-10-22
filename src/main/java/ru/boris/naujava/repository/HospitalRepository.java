package ru.boris.naujava.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ru.boris.naujava.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@RepositoryRestResource(path = "hospitals")
public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    List<Hospital> findByNameContainingIgnoreCase(String name);

    @Query("SELECT h FROM Hospital h WHERE h.specialization = :specialization")
    List<Hospital> findBySpecialization(
            @Param("specialization") String specialization
    );
}
