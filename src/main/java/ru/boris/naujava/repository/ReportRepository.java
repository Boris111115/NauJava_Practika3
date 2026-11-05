package ru.boris.naujava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.boris.naujava.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
