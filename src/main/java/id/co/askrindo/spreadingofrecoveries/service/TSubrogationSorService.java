package id.co.askrindo.spreadingofrecoveries.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.askrindo.spreadingofrecoveries.entity.*;
import id.co.askrindo.spreadingofrecoveries.errors.exception.InternalServerErrorException;
import id.co.askrindo.spreadingofrecoveries.model.CreateSor;
import id.co.askrindo.spreadingofrecoveries.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TSubrogationSorService {
    public  final Logger logger = LoggerFactory.getLogger("TClaimSocService endpoints");
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    TSubrogasiRepository tSubrogasiRepository;

    @Autowired
    TSubrogationSorRepository tSubrogationSorRepository;

    @Autowired
    TSubrogationValidationRepository tSubrogationValidationRepository;

    @Autowired
    RTreatySchemeCobRepository rTreatySchemeCobRepository;

    @Autowired
    RTreatySchemeRepository rTreatySchemeRepository;

    @Autowired
    PenjaminanKurRepository penjaminanKurRepository;

    @Autowired
    PenjaminanKurSprRepository penjaminanKurSprRepository;

    @Autowired
    RTreatySchemeDetailRepository rTreatySchemeDetailRepository;

    @Transactional
    public ResponseEntity<?> RecoveriesSorProsess(CreateSor createSor) {
        try {
            logger.info("INSERT RECOVERIES PAYLOAD : " + mapper.writeValueAsString(createSor));
            Integer productId = createSor.getProductId();

            List<TSubrogationValidation> tSubrogationValidations = tSubrogationValidationRepository.findAllByFIdProgramAndStatusProses(productId, 3);
            List<RTreatySchemeCob> rTreatySchemeCobs = rTreatySchemeCobRepository.findAllByProductId(productId);

            List<RTreatyScheme> dataRTreatyScheme = new ArrayList<>();
            for(RTreatySchemeCob rTreatySchemeCob : rTreatySchemeCobs){
                Optional<RTreatyScheme> rTreatyScheme = rTreatySchemeRepository.findById(rTreatySchemeCob.getTreatyId());
                if (rTreatyScheme.isPresent()){
                    dataRTreatyScheme.add(rTreatyScheme.get());
                }
            }

            for(TSubrogationValidation tSubrogationValidation: tSubrogationValidations){
                Optional<TSubrogasi> tSubrogasi = tSubrogasiRepository.findByNoRekening(tSubrogationValidation.getNomorPeserta());
                if (tSubrogasi.isPresent()){
                    if (tSubrogasi.get().getNomorPeserta() != "") {
                        Date tanggalAwalPenjaminanKur = null;
                        Optional<PenjaminanKurSpr> penjaminanKurSpr = penjaminanKurSprRepository.findByNoSertifikatSprAndFlagTerbitAcs
                                (tSubrogasi.get().getNomorPeserta(), "3");
                        if (penjaminanKurSpr.isPresent()){
                            tanggalAwalPenjaminanKur = penjaminanKurSpr.get().getTanggalAwalSpr();
                        } else{
                            Optional<PenjaminanKur> penjaminanKur = penjaminanKurRepository.findByNoSertifikatAndFlagTerbitAcs
                                (tSubrogasi.get().getNomorPeserta(), "3");
                            if (penjaminanKur.isPresent()){
                                tanggalAwalPenjaminanKur = penjaminanKur.get().getTanggalAwal();
                            }
                        }

                        for(RTreatyScheme rTreatyScheme : dataRTreatyScheme){
                            if (tanggalAwalPenjaminanKur != null) {
                                if (rTreatyScheme.getStartDate().compareTo(tanggalAwalPenjaminanKur) >= 0 && rTreatyScheme.getEndDate().compareTo(tanggalAwalPenjaminanKur) >= 0) {
                                    Optional<RTreatySchemeDetail> rTreatySchemeDetail = rTreatySchemeDetailRepository.findByTreatyId(rTreatyScheme.getId());
                                    if (rTreatySchemeDetail.isPresent()) {

                                        TSubrogationSor newData = new TSubrogationSor();//Belom mapping data
                                        TSubrogationSor saveData = tSubrogationSorRepository.save(newData);
                                        logger.info("TCLAIMSOC CREATE, SUCCESS : "+ mapper.writeValueAsString(saveData));
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return new ResponseEntity<>("", HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}