package tech.noetzold.APItester.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.noetzold.APItester.model.TestDeleteRequisition;
import tech.noetzold.APItester.model.User;

public interface TestDeleteRequisitionRepository extends JpaRepository<TestDeleteRequisition, Integer> {

    Page<TestDeleteRequisition> findByUser(Pageable pageable, User user);
}
