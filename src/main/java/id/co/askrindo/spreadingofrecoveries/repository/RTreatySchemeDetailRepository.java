package id.co.askrindo.spreadingofrecoveries.repository;

import id.co.askrindo.spreadingofrecoveries.entity.RTreatySchemeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RTreatySchemeDetailRepository extends JpaRepository<RTreatySchemeDetail, String> {
    Optional<RTreatySchemeDetail> findByTreatyId (String treatyId);
}