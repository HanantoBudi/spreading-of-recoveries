package id.co.askrindo.spreadingofrecoveries.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "dummy_r_treaty_scheme_cob", schema = "brisurf")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class RTreatySchemeCob {
    @Id
    @Column(name = "id", columnDefinition="uniqueidentifier")
    private String id;

    @Column(name = "product_id")
    private int productId;

    @Column(name = "treaty_id", columnDefinition="uniqueidentifier")
    private String treatyId;

    @Column(name = "tahun")
    private Integer year;
}
