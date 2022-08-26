package id.co.askrindo.spreadingofrecoveries.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.askrindo.spreadingofrecoveries.entity.*;
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
import java.util.*;

@Service
public class TSubrogationSorService {
    public  final Logger logger = LoggerFactory.getLogger("TSubrogationSorService endpoints");
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    KlaimKurRepository klaimKurRepository;

    @Autowired
    TSubrogasiRepository tSubrogasiRepository;

    @Autowired
    TSubrogasiSummaryRepository tSubrogasiSummaryRepository;

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
    public ResponseEntity<?> recoveriesSorProsess(String message) {
        try {
            logger.info("INSERT RECOVERIES PAYLOAD : " + message);
            String userName = "h2h-kur-bri";
            TSubrogationSor result = new TSubrogationSor();

            Optional<TSubrogationValidation> tSubrogationValidation = tSubrogationValidationRepository.findByIdAndStatusProses(Integer.valueOf(message), 3);
            if (tSubrogationValidation.isPresent()) {
                List<RTreatySchemeCob> rTreatySchemeCobs = rTreatySchemeCobRepository.findAllByProductId(tSubrogationValidation.get().getFIdProgram());

                List<RTreatyScheme> dataRTreatyScheme = new ArrayList<>();
                for(RTreatySchemeCob rTreatySchemeCob : rTreatySchemeCobs){
                    Optional<RTreatyScheme> rTreatyScheme = rTreatySchemeRepository.findById(rTreatySchemeCob.getTreatySchemeId());
                    if (rTreatyScheme.isPresent()){
                        dataRTreatyScheme.add(rTreatyScheme.get());
                    }
                }

                Optional<TSubrogasi> tSubrogasi = tSubrogasiRepository.findByNoRekening(tSubrogationValidation.get().getNomorPeserta());
                Optional<TSubrogasiSummary> tSubrogasiSummary = tSubrogasiSummaryRepository.findByIdSubrogasi(tSubrogasi.get().getId());
                Optional<KlaimKur> klaimKur = klaimKurRepository.findById(tSubrogasi.get().getIdKlaim());
                if (klaimKur.isPresent()){
                    if (klaimKur.get().getNoSertifikat() != "") {
                        Calendar calendarPenjaminanKur = Calendar.getInstance();
                        BigDecimal penjaminanKurPlafon = null;
                        Date tanggalAwalPenjaminanKur = null;

                        Optional<PenjaminanKurSpr> penjaminanKurSpr = penjaminanKurSprRepository.findByNoSertifikatSprAndFlagTerbitAcs
                                (klaimKur.get().getNoSertifikat(), "3");
                        if (penjaminanKurSpr.isPresent()){
                            tanggalAwalPenjaminanKur = penjaminanKurSpr.get().getTanggalAwalSpr();
                            penjaminanKurPlafon = penjaminanKurSpr.get().getPlafonSpr();
                        } else{
                            Optional<PenjaminanKur> penjaminanKur = penjaminanKurRepository.findByNoSertifikatAndFlagTerbitAcs
                                    (klaimKur.get().getNoSertifikat(), "3");
                            if (penjaminanKur.isPresent()){
                                tanggalAwalPenjaminanKur = penjaminanKur.get().getTanggalAwal();
                                penjaminanKurPlafon = penjaminanKur.get().getPlafonKredit();
                            }
                        }

                        if (!dataRTreatyScheme.isEmpty()) {
                            for(RTreatyScheme rTreatyScheme : dataRTreatyScheme){
                                if (tanggalAwalPenjaminanKur != null) {
                                    if (rTreatyScheme.getStartDate().compareTo(tanggalAwalPenjaminanKur) >= 0 && rTreatyScheme.getEndDate().compareTo(tanggalAwalPenjaminanKur) >= 0) {
                                        Optional<RTreatySchemeDetail> rTreatySchemeDetail = rTreatySchemeDetailRepository.findByTreatySchemeId(rTreatyScheme.getTreatySchemeId());
                                        if (rTreatySchemeDetail.isPresent()) {
                                            calendarPenjaminanKur.setTime(tanggalAwalPenjaminanKur);
    
                                            BigDecimal nominalPokok = (tSubrogationValidation.get().getNominalPokok() != null ? tSubrogationValidation.get().getNominalPokok() : BigDecimal.valueOf(0));
                                            BigDecimal nominalDenda= (tSubrogationValidation.get().getNominalDenda() != null ? tSubrogationValidation.get().getNominalDenda() : BigDecimal.valueOf(0));
                                            BigDecimal nominalBunga = (tSubrogationValidation.get().getNominalBunga() != null ? tSubrogationValidation.get().getNominalBunga() : BigDecimal.valueOf(0));
                                            BigDecimal collectingFeeGross = (tSubrogationValidation.get().getCollectingFeeGross() != null ? tSubrogationValidation.get().getCollectingFeeGross() : BigDecimal.valueOf(0));
                                            BigDecimal pctOrLimit = (rTreatySchemeDetail.get().getPctOrLimit() != null ? rTreatySchemeDetail.get().getPctOrLimit() : BigDecimal.valueOf(0));
                                            BigDecimal pctReas = (rTreatySchemeDetail.get().getPctTreatyLimit() != null ? rTreatySchemeDetail.get().getPctTreatyLimit() : BigDecimal.valueOf(0));
                                            BigDecimal pctCommision = (rTreatySchemeDetail.get().getPctCommission() != null ? rTreatySchemeDetail.get().getPctCommission() : BigDecimal.valueOf(0));
    
                                            BigDecimal plafon = penjaminanKurPlafon;
                                            BigDecimal recoveries = nominalPokok
                                                    .add(nominalDenda)
                                                    .add(nominalBunga)
                                                    .add(collectingFeeGross);
                                            BigDecimal tsiDistributionOr = pctOrLimit.multiply(plafon);
                                            BigDecimal recoveriesDistributionOr = pctOrLimit.multiply(recoveries);
                                            BigDecimal tsiDistributionQs = pctReas.multiply(plafon);
                                            BigDecimal recoveriesDistributionQs = pctReas.multiply(recoveries);
                                            BigDecimal commission = (pctCommision.multiply(recoveriesDistributionQs)).divide(BigDecimal.valueOf(100));
    
                                            TSubrogationSor newData = TSubrogationSor.builder()
                                                    .idRecoveries(tSubrogasiSummary.get().getId())
                                                    .lineNo(tSubrogationValidation.get().getUrutanPengajuan())
                                                    .year(calendarPenjaminanKur.get(Calendar.YEAR))
                                                    .treatySchemeId(rTreatySchemeDetail.get().getTreatySchemeId())
                                                    .tsi(plafon)
                                                    .recoveries(recoveries)
                                                    .pctOr(pctOrLimit)
                                                    .tsiDistributionOr(tsiDistributionOr)
                                                    .recoveriesDistributionOr(recoveriesDistributionOr)
                                                    .pctQs(pctReas)
                                                    .tsiDistributionQs(tsiDistributionQs)
                                                    .recoveriesDistributionQs(recoveriesDistributionQs)
                                                    .pctCommision(pctCommision)
                                                    .commission(commission)
                                                    .isJurnal(false)
                                                    .idJurnal(null)
                                                    .jurnalNo(null)
                                                    .tglJurnal(null)
                                                    .createdBy(userName)
                                                    .createdDate(new Date())
                                                    .modifiedBy(null)
                                                    .modifiedDate(null)
                                                    .build();
    
                                            TSubrogationSor saveData = tSubrogationSorRepository.save(newData);
                                            if(saveData.getId() != "") {
                                                result = saveData;
                                                logger.info("SPREADING OF RECOVERIES CREATE, SUCCESS : "+ mapper.writeValueAsString(saveData));
                                            } else {
                                                logger.info("SPREADING OF RECOVERIES CREATE, FAILED : "+ mapper.writeValueAsString(saveData));
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            logger.info("SPREADING OF RECOVERIES CREATE, INFO : Get dataRTreatyScheme by noRekening " + tSubrogationValidation.get().getNomorPeserta() + " IS EMPTY");
                        }
                    }
                }
            }

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("SPREADING OF RECOVERIES CREATE, FAILED : "+e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}