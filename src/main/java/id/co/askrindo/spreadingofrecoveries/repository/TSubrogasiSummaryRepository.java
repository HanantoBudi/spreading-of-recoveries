package id.co.askrindo.spreadingofrecoveries.repository;

import id.co.askrindo.spreadingofrecoveries.entity.TSubrogasiSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TSubrogasiSummaryRepository extends JpaRepository<TSubrogasiSummary, String> {

    Optional<TSubrogasiSummary> findByIdSubrogasi (String idSubrogasi);

}