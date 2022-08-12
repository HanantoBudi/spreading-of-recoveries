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
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "t_subrogasi_summary", schema = "dbo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class TSubrogasiSummary {
    @Id
    @GenericGenerator(name = "generator", strategy = "guid", parameters = {})
    @GeneratedValue(generator = "generator")
    @Column(name = "id", columnDefinition="uniqueidentifier")
    private String id;

    @Column(name = "id_subrogasi", columnDefinition="uniqueidentifier")
    private String idSubrogasi;

    @Column(name = "line_no")
    private Integer lineNo;

    @Column(name = "nominal_subrogasi_pokok")
    private BigDecimal nominalSubrogasiPokok;

    @Column(name = "nominal_subrogasi_bunga")
    private BigDecimal nominalSubrogasiBunga;

    @Column(name = "nominal_subrogasi_denda")
    private BigDecimal nominalSubrogasiDenda;

    @Column(name = "nominal_pajak")
    private BigDecimal nominalPajak;

    @Column(name = "nominal_fee_gross")
    private BigDecimal nominalFeeGross;

    @Column(name = "nominal_fee_net")
    private BigDecimal nominalFeeNet;

    @Column(name = "nominal_subrogasi_total")
    private BigDecimal nominalSubrogasiTotal;

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
