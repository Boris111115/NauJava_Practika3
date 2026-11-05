package ru.boris.naujava.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ru.boris.naujava.entity.MedicalStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@RepositoryRestResource(path = "medical-staff")
public interface MedicalStaffRepository
        extends JpaRepository<MedicalStaff, Long> {

    List<MedicalStaff> findByFullNameContainingIgnoreCase(String fullName);

    @Query("SELECT m FROM MedicalStaff m WHERE m.position = :position")
    List<MedicalStaff> findByPosition(
            @Param("position") String position
    );
}
