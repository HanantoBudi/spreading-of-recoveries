package id.co.askrindo.spreadingofrecoveries.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "penjaminan_kur", schema = "dbo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class PenjaminanKur {
    @Id
    @Column(name = "id", columnDefinition="uniqueidentifier")
    private String id;

    @Column(name = "no_sertifikat")
    private String noSertifikat;

    @Column(name = "no_sertifikat_lama")
    private String noSertifikatLama;

    @Column(name = "tgl_sertifikat")
    private Date tanggalSertifikat;

    @Column(name = "tanggal_pk")
    private Date tanggalPk;

    @Column(name = "tanggal_awal")
    private Date tanggalAwal;

    @Column(name = "tanggal_akhir")
    private Date tanggalAkhir;

    @Column(name = "flag_terbit_acs")
    private String flagTerbitAcs;

    @Column(name = "plafon_kredit")
    private BigDecimal plafonKredit;
}
