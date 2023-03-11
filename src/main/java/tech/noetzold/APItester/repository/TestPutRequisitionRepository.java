package tech.noetzold.APItester.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.noetzold.APItester.model.TestPutRequisition;
import tech.noetzold.APItester.model.User;

public interface TestPutRequisitionRepository extends JpaRepository<TestPutRequisition, Integer> {
    Page<TestPutRequisition> findByUser(Pageable pageable, String login);
}
