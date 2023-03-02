package tech.noetzold.APItester.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.noetzold.APItester.model.TestGetRequisition;
import tech.noetzold.APItester.model.User;

public interface TestGetRequisitionRepository extends JpaRepository<TestGetRequisition, Integer> {
    Page<TestGetRequisition> findByUser(Pageable pageable, User user);
}
