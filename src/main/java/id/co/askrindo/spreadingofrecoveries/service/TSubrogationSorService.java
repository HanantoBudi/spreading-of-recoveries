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
    public  final Logger logger = LoggerFactory.getLogger("TClaimSocService endpoints");
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
    public ResponseEntity<?> recoveriesSorProsess(CreateSor createSor) {
        try {
            logger.info("INSERT RECOVERIES PAYLOAD : " + mapper.writeValueAsString(createSor));
            Integer productId = createSor.getProductId();
            String userName = "h2h-kur-bri";
            List<TSubrogationSor> resultDatas = new ArrayList<>();

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

                        for(RTreatyScheme rTreatyScheme : dataRTreatyScheme){
                            if (tanggalAwalPenjaminanKur != null) {
                                if (rTreatyScheme.getStartDate().compareTo(tanggalAwalPenjaminanKur) >= 0 && rTreatyScheme.getEndDate().compareTo(tanggalAwalPenjaminanKur) >= 0) {
                                    Optional<RTreatySchemeDetail> rTreatySchemeDetail = rTreatySchemeDetailRepository.findByTreatyId(rTreatyScheme.getId());
                                    if (rTreatySchemeDetail.isPresent()) {
                                        calendarPenjaminanKur.setTime(tanggalAwalPenjaminanKur);

                                        BigDecimal nominalPokok = (tSubrogationValidation.getNominalPokok() != null ? tSubrogationValidation.getNominalPokok() : BigDecimal.valueOf(0));
                                        BigDecimal nominalDenda= (tSubrogationValidation.getNominalDenda() != null ? tSubrogationValidation.getNominalDenda() : BigDecimal.valueOf(0));
                                        BigDecimal nominalBunga = (tSubrogationValidation.getNominalBunga() != null ? tSubrogationValidation.getNominalBunga() : BigDecimal.valueOf(0));
                                        BigDecimal collectingFeeGross = (tSubrogationValidation.getCollectingFeeGross() != null ? tSubrogationValidation.getCollectingFeeGross() : BigDecimal.valueOf(0));
                                        BigDecimal pctOrLimit = (rTreatySchemeDetail.get().getPctOrLimit() != null ? rTreatySchemeDetail.get().getPctOrLimit() : BigDecimal.valueOf(0));
                                        BigDecimal pctReas = (rTreatySchemeDetail.get().getPctReas() != null ? rTreatySchemeDetail.get().getPctReas() : BigDecimal.valueOf(0));
                                        BigDecimal pctCommision = (rTreatySchemeDetail.get().getPctComm() != null ? rTreatySchemeDetail.get().getPctComm() : BigDecimal.valueOf(0));

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
                                            .lineNo(tSubrogationValidation.getUrutanPengajuan())
                                            .year(calendarPenjaminanKur.get(Calendar.YEAR))
                                            .treatySchemeId(rTreatySchemeDetail.get().getTreatyId())
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
                                            resultDatas.add(saveData);
                                            logger.info("TCLAIMSOC CREATE, SUCCESS : "+ mapper.writeValueAsString(saveData));
                                        } else {
                                            logger.info("TCLAIMSOC CREATE, FAILED : "+ mapper.writeValueAsString(saveData));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return new ResponseEntity<>(resultDatas, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("TCLAIMSOC CREATE, FAILED : "+e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}