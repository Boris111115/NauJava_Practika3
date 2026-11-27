
package ru.boris.naujava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.boris.naujava.entity.PatientTransfer;

import java.util.List;

public interface PatientTransferRepository
        extends JpaRepository<PatientTransfer, Long> {

    // Derived Query
    List<PatientTransfer> findByPatientId(String patientId);

    // JPQL: поиск через связанную сущность Hospital
    @Query("""
           SELECT pt
           FROM PatientTransfer pt
           WHERE LOWER(pt.hospital.name)
           LIKE LOWER(CONCAT('%', :name, '%'))
           """)
    List<PatientTransfer> findByHospitalNameUsingQuery(
            @Param("name") String name
    );
}
