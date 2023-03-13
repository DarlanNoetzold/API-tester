package tech.noetzold.APItester.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.noetzold.APItester.model.FullPerformanceTest;

public interface FullPerformanceTestRepository extends JpaRepository<FullPerformanceTest, Integer> {
}
