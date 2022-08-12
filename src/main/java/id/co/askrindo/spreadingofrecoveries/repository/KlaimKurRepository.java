package id.co.askrindo.spreadingofrecoveries.repository;

import id.co.askrindo.spreadingofrecoveries.entity.KlaimKur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KlaimKurRepository extends JpaRepository<KlaimKur, String> {

    Optional<KlaimKur> findById (Integer id);

    Optional<KlaimKur> findByNoRekening (String noRekening);

}