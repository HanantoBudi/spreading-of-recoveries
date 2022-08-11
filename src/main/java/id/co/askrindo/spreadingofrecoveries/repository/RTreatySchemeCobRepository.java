package id.co.askrindo.spreadingofrecoveries.repository;

import id.co.askrindo.spreadingofrecoveries.entity.RTreatySchemeCob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RTreatySchemeCobRepository extends JpaRepository<RTreatySchemeCob, String> {
    List<RTreatySchemeCob> findAllByProductId(Integer productId);
}