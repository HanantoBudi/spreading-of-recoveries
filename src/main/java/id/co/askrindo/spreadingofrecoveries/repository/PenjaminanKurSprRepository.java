package id.co.askrindo.spreadingofrecoveries.repository;

import id.co.askrindo.spreadingofrecoveries.entity.PenjaminanKurSpr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PenjaminanKurSprRepository extends JpaRepository<PenjaminanKurSpr, String> {

    @Query(value="select*from dbo.penjaminan_kur_spr where no_sertifikat_spr = :noSertifikatSpr and flag_terbit_acs = :flagTerbitAcs order by created_date desc OFFSET 0 ROWS FETCH FIRST 1 ROWS ONLY", nativeQuery = true)
    Optional<PenjaminanKurSpr> findByNoSertifikatSprAndFlagTerbitAcs(String noSertifikatSpr, String flagTerbitAcs);

}