package com.enigma.D_Distance_Mobile.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "m_merchant")
public class Merchant {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;
    private String name;
    private String address;
    @Column(length = 16)
    private String pan;
    @Column(name = "phone_number",length = 16)
    private String phoneNumber;
    private Long balance;
    @OneToOne
    @JoinColumn(name = "user_credential_id", unique = true)
    private UserCredential userCredential;

    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "invoice_id")
    @JsonBackReference
    private Invoice invoice;
}
