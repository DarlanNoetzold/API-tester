package tech.noetzold.APItester.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.noetzold.APItester.model.TestPostRequisition;

public interface TestPostRequisitionRepository extends JpaRepository<TestPostRequisition, Integer> {
    Page<TestPostRequisition> findByUser(Pageable pageable, String login);
}
