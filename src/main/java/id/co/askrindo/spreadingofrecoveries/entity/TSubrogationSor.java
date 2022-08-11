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
@Table(name = "t_subrogation_sor", schema = "brisurf")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class TSubrogationSor {
    @Id
    @GenericGenerator(name = "generator", strategy = "guid", parameters = {})
    @GeneratedValue(generator = "generator")
    @Column(name = "id", columnDefinition="uniqueidentifier")
    private String id;

    @Column(name = "id_recoveries", columnDefinition="uniqueidentifier")
    private String idRecoveries;

    @Column(name = "treaty_scheme_id", columnDefinition="uniqueidentifier")
    private String treatySchemeId;

    @Column(name = "line_no")
    private Integer lineNo;

    @Column(name = "year")
    private Integer year;

    @Column(name = "tsi")
    private BigDecimal tsi;

    @Column(name = "recoveries")
    private BigDecimal recoveries;

    @Column(name = "pct_or")
    private BigDecimal pctOr;

    @Column(name = "tsi_distribution_or")
    private BigDecimal tsiDistributionOr;

    @Column(name = "recoveries_distribution_or")
    private BigDecimal recoveriesDistributionOr;

    @Column(name = "pct_qs")
    private BigDecimal pctQs;

    @Column(name = "tsi_distribution_qs")
    private BigDecimal tsiDistributionQs;

    @Column(name = "recoveries_distribution_qs")
    private BigDecimal recoveriesDistributionQs;

    @Column(name = "pct_commision")
    private BigDecimal pctCommision;

    @Column(name = "commission")
    private BigDecimal commission;

    @Column(name = "is_jurnal")
    private Boolean isJurnal;

    @Column(name = "id_jurnal", columnDefinition="uniqueidentifier")
    private String idJurnal;

    @Column(name = "jurnal_no", length = 50)
    private String jurnalNo;

    @Column(name = "tgl_jurnal")
    private Date tglJurnal;

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