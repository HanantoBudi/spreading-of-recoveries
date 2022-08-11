package id.co.askrindo.spreadingofrecoveries.repository;

import id.co.askrindo.spreadingofrecoveries.entity.TSubrogationValidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface TSubrogationValidationRepository extends JpaRepository<TSubrogationValidation, String> {

    @Query(value="SELECT*from brisurf.t_subrogation_validation where f_id_program =:productId and status_proses =:statusProses order by created_date asc", nativeQuery = true)
    List<TSubrogationValidation> findAllByFIdProgramAndStatusProses(@Param("productId") Integer productId, @Param("statusProses") Integer statusProses);

}