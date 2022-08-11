package id.co.askrindo.spreadingofrecoveries.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "dummy_r_treaty_scheme_dtl", schema = "brisurf")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class RTreatySchemeDetail {
    @Id
    @Column(name = "id", columnDefinition="uniqueidentifier")
    private String id;

    @Column(name = "treaty_id", columnDefinition="uniqueidentifier")
    private String treatyId;

    @Column(name = "pct_or_limit")
    private BigDecimal pctOrLimit;

    @Column(name = "pct_reas")
    private BigDecimal pctReas;

    @Column(name = "pct_comm")
    private BigDecimal pctComm;
}