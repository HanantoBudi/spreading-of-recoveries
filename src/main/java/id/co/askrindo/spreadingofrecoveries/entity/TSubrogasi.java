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
@Table(name = "t_subrogasi", schema = "dbo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class TSubrogasi {
    @Id
    @GenericGenerator(name = "generator", strategy = "guid", parameters = {})
    @GeneratedValue(generator = "generator")
    @Column(name = "id", columnDefinition="uniqueidentifier")
    private String id;

    @Column(name = "id_klaim", columnDefinition="uniqueidentifier")
    private String idKlaim;

    @Column(name = "no_rekening", length = 50)
    private String noRekening;

    @Column(name = "nama_peserta", length = 50)
    private String namaPeserta;

    @Column(name = "nominal_claim")
    private BigDecimal nominalClaim;

    @Column(name = "akumulasi_subrogasi")
    private BigDecimal akumulasiSubrogasi;

    @Column(name = "sisa_kewajiban_subrogasi")
    private BigDecimal sisaKewajibanSubrogasi;

    @Column(name = "persentase_coverage")
    private BigDecimal persentaseCoverage;

    @Column(name = "persentase_collecting_fee")
    private BigDecimal persentaseCollectingFee;

    @Column(name = "persentase_pajak")
    private BigDecimal persentasePajak;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "created_date")
    @CreatedDate
    private Date createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private Date modifiedDate;

    @Column(name = "created_by", length = 50)
    @CreatedBy
    private String createdBy;

    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedBy;
}