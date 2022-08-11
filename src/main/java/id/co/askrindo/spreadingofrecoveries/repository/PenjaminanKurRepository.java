package id.co.askrindo.spreadingofrecoveries.repository;

import id.co.askrindo.spreadingofrecoveries.entity.PenjaminanKur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PenjaminanKurRepository extends JpaRepository<PenjaminanKur, String> {
    @Query(value="select*from penjaminan_kur where no_sertifikat = :noSertifikat and flag_terbit_acs = :flagTerbitAcs order by created_date desc limit 1", nativeQuery = true)
    Optional<PenjaminanKur> findByNoSertifikatAndFlagTerbitAcs(String noSertifikat, String flagTerbitAcs);
}