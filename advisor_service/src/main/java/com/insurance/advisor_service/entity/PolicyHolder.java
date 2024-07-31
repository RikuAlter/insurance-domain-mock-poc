package com.insurance.advisor_service.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "policyholder",schema = "policyholder")
public class PolicyHolder {

    @Id
    @Column(name="holder_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long holderId;

    @Column(name = "holder_name")
    private String holderName;

    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "email")
    private String emailId;

    @Column(name="dateofbirth")
    private LocalDate dob;

    @Column(name="marital_status")
    private MaritalStatus maritalStatus;

    @Column(name="pan_no")
    private String panNo;

    @CreationTimestamp
    private LocalDate createdAt;

    @LastModifiedDate
    private LocalDate modifiedAt;

}
