package tech.noetzold.APItester.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.noetzold.APItester.model.FullPerformanceTest;
import tech.noetzold.APItester.model.User;

public interface FullPerformanceTestRepository extends JpaRepository<FullPerformanceTest, Integer> {
    Page<FullPerformanceTest> findByUser(Pageable pageable, User user);
}
