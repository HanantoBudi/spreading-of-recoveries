package id.co.askrindo.spreadingofrecoveries.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "t_subrogation_validation", schema = "brisurf")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class TSubrogationValidation {
    @Id
    @GenericGenerator(name = "generator", strategy = "guid", parameters = {})
    @GeneratedValue(generator = "generator")
    @Column(name = "id", columnDefinition="uniqueidentifier")
    private String id;

    @Column(name = "f_id_program", columnDefinition="uniqueidentifier")
    private String fIdProgram;

    @Column(name = "nomor_peserta", length = 50)
    private String nomorPeserta;

    @Column(name = "nama_peserta", length = 50)
    private String namaPeserta;

    @Column(name = "nomor_rekening_peminjaman", length = 50)
    private String nomorRekeningPeminjaman;

    @Column(name = "urutan_pengajuan")
    private Integer urutanPengajuan;

    @Column(name = "angsuran_teller_id", length = 50)
    private String angsuranTellerId;

    @Column(name = "angsuran_journal_sequence", length = 50)
    private String angsuranJournalSequence;

    @Column(name = "angsuran_tanggal")
    @CreatedDate
    private Date angsuranTanggal;

    @Column(name = "angsuran_pokok")
    private BigDecimal angsuranPokok;

    @Column(name = "angsuran_bunga")
    private BigDecimal angsuranBunga;

    @Column(name = "angsuran_denda")
    private BigDecimal angsuranDenda;

    @Column(name = "f_id_jenis_subrogasi")
    private Integer fIdJenisSubrogasi;

    @Column(name = "jenis_subrogasi", length = 50)
    private String jenisSubrogasi;

    @Column(name = "claim_source", length = 50)
    private String claimSource;

    @Column(name = "nama_debitur", length = 50)
    private String namaDebitur;

    @Column(name = "net_klaim")
    private BigDecimal netKlaim;

    @Column(name = "no_cl", length = 50)
    private String noCl;

    @Column(name = "tanggal_cl")
    @CreatedDate
    private Date tanggalCl;

    @Column(name = "collecting_fee_gross")
    private BigDecimal collectingFeeGross;

    @Column(name = "collecting_fee_net")
    private BigDecimal collectingFeeNet;

    @Column(name = "pajak_collecting_fee")
    private BigDecimal pajakCollectingFee;

    @Column(name = "status_proses", length = 50)
    private String statusProses;

    @Column(name = "created_date")
    @CreatedDate
    private Date createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private Date modifiedDate;

    @Column(name = "remark", length = 50)
    private String remark;

    @Column(name = "nominal_sisa_klaim")
    private BigDecimal nominalSisaKlaim;

    @Column(name = "nominal_pokok")
    private BigDecimal nominalPokok;

    @Column(name = "nominal_bunga")
    private BigDecimal nominalBunga;

    @Column(name = "nominal_denda")
    private BigDecimal nominalDenda;
}