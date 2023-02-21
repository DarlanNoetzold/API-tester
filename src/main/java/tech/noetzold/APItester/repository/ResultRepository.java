package tech.noetzold.APItester.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.noetzold.APItester.model.Result;

public interface ResultRepository extends JpaRepository<Result, Integer> {
}
