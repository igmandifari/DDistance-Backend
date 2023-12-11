package com.enigma.D_Distance_Mobile.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "t_insurance_request")
public class InsuranceRequest {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;
    @ManyToOne
    @JoinColumn(name = "merchant_id")
    @JsonBackReference
    private Merchant merchant;

    @ManyToOne
    @JoinColumn(name = "credit_analyst_id")
    @JsonBackReference
    private CreditAnalyst creditAnalyst;

    @ManyToOne
    @JoinColumn(name = "distirbutor_id")
    @JsonBackReference
    private Distributor distributor;

    @Column(name = "status_survey")
    private Boolean statusSurvey;
    private String ktp;
    private String siu;
    private String agunan;
    @Column(name = "waktu_pengajuan")
    private LocalDateTime waktuPengajuan;

}
