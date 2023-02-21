package tech.noetzold.APItester.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.noetzold.APItester.model.Requisition;

public interface RequisitionRepository extends JpaRepository<Requisition, Integer> {
}
