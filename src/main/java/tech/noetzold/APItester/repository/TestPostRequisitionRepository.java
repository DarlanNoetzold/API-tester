package tech.noetzold.APItester.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.noetzold.APItester.model.TestPostRequisition;

public interface TestPostRequisitionRepository extends JpaRepository<TestPostRequisition, Integer> {
}
