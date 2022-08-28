package id.co.askrindo.spreadingofrecoveries.repository;

import id.co.askrindo.spreadingofrecoveries.entity.RTreatySchemeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RTreatySchemeDetailRepository extends JpaRepository<RTreatySchemeDetail, String> {
    @Query(value="SELECT*FROM dbo.rei_treaty_scheme_dtl where treaty_scheme_id =:treatySchemeId order by created_date desc OFFSET 0 ROWS FETCH FIRST 1 ROWS ONLY ", nativeQuery = true)
    Optional<RTreatySchemeDetail> findByTreatySchemeId (String treatySchemeId);
}