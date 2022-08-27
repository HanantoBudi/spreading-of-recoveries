package id.co.askrindo.spreadingofrecoveries.repository;

import id.co.askrindo.spreadingofrecoveries.entity.TSubrogationValidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface TSubrogationValidationRepository extends JpaRepository<TSubrogationValidation, String> {

    Optional<TSubrogationValidation> findById (Integer id);

    Optional<TSubrogationValidation> findByIdAndStatusProses(String id, Integer statusProses);

}