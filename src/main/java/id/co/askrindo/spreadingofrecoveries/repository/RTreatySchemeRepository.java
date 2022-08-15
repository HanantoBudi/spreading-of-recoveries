package id.co.askrindo.spreadingofrecoveries.repository;

import id.co.askrindo.spreadingofrecoveries.entity.RTreatyScheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RTreatySchemeRepository extends JpaRepository<RTreatyScheme, String> {
    Optional<RTreatyScheme> findByTreatySchemeId (Integer id);
}