package id.co.askrindo.spreadingofrecoveries.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "dummy_r_treaty_scheme_dtl", schema = "brisurf")
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
    private Integer pctOrLimit;

    @Column(name = "pct_reas")
    private Integer pctReas;

    @Column(name = "pct_comm")
    private Integer pctComm;
}
