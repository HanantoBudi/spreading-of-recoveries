package id.co.askrindo.spreadingofrecoveries.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "rei_treaty_scheme_cob", schema = "dbo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class RTreatySchemeCob {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "treaty_scheme_id", columnDefinition="uniqueidentifier")
    private String treatySchemeId;

    @Column(name = "product_id")
    private int productId;

    @Column(name = "treaty_year")
    private Integer treatyYear;
}