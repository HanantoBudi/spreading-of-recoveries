package id.co.askrindo.spreadingofrecoveries.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "rei_treaty_scheme_dtl", schema = "dbo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class RTreatySchemeDetail {
    @Id
    @Column(name = "scheme_dtl_id", columnDefinition="uniqueidentifier")
    private String schemeDtlId;

    @Column(name = "treaty_scheme_id", columnDefinition="uniqueidentifier")
    private String treatySchemeId;

    @Column(name = "pct_or_limit")
    private BigDecimal pctOrLimit;

    @Column(name = "pct_treaty_limit")
    private BigDecimal pctTreatyLimit;

    @Column(name = "pct_commission")
    private BigDecimal pctCommission;
}