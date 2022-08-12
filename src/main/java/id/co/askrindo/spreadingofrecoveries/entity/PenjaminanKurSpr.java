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
@Table(name = "penjaminan_kur_spr", schema = "dbo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class PenjaminanKurSpr {
    @Id
    @Column(name = "id", columnDefinition="uniqueidentifier")
    private String id;

    @Column(name = "no_sertifikat_spr")
    private String noSertifikatSpr;

    @Column(name = "no_sertifikat_awal")
    private String noSertifikatAwal;

    @Column(name = "no_sertifikat_lama")
    private String noSertifikatLama;

    @Column(name = "tanggal_sertifikat_spr")
    private Date tanggalSertifikatSpr;

    @Column(name = "tanggal_pk_spr")
    private Date tanggalPkSpr;

    @Column(name = "tanggal_awal_spr")
    private Date tanggalAwalSpr;

    @Column(name = "tanggal_akhir_spr")
    private Date tanggalAkhirSpr;

    @Column(name = "flag_terbit_acs")
    private String flagTerbitAcs;

    @Column(name = "flag_update_acs")
    private String flagUpdateAcs;

    @Column(name = "flag_pembatalan")
    private String flagPembatalan;

    @Column(name = "plafon_spr")
    private BigDecimal plafonSpr;
}