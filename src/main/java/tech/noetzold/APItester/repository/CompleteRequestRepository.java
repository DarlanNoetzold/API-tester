package tech.noetzold.APItester.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.noetzold.APItester.model.CompleteRequest;
import tech.noetzold.APItester.model.User;

public interface CompleteRequestRepository extends JpaRepository<CompleteRequest, Integer> {
    Page<CompleteRequest> findByUser(Pageable pageable, User user);
}