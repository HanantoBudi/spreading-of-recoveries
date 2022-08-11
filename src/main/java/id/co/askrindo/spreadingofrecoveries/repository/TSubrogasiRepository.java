package id.co.askrindo.spreadingofrecoveries.repository;

import id.co.askrindo.spreadingofrecoveries.entity.TSubrogasi;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TSubrogasiRepository extends JpaRepository<TSubrogasi, String> {
    Optional<TSubrogasi> findByNoRekening (String noRekening);
}