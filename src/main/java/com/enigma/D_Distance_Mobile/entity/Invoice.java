package com.enigma.D_Distance_Mobile.entity;

import com.enigma.D_Distance_Mobile.constant.EInstallemnt;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "t_invoice")
public class Invoice {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;

    @ManyToOne
    @JoinColumn(name = "merchant_id")
    @JsonBackReference
    private Merchant merchant;

    @ManyToOne
    @JoinColumn(name = "distirbutor_id")
    @JsonBackReference
    private Distributor distributor;

    @Column(name = "total_amount")
    private Long totalAmount;

    @CreatedDate
    @Column(updatable = false,name = "waktu_pengajuan")
    private LocalDateTime waktuPengajuan;
    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "physical_invoice")
    private String physicalInvoice;

    @Column
    private Integer interest;

    @Column
    private Long penalty;

    @Enumerated(EnumType.STRING)
    private EInstallemnt installment;

    @Column
    private String rejection;
    @Column(name = "remaining_occurence")
    private Integer remainingOccurence;
    @Column
    private String approval;

    @OneToOne(mappedBy = "invoice")
    @JsonManagedReference
    private Payment payment;
}
