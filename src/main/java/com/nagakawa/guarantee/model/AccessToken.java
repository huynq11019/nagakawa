/*
 * AccessToken.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.nagakawa.guarantee.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * 31/05/2021
 *
 * @author LinhLH
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "access_token")
public class AccessToken extends AbstractAuditingEntity implements Serializable{
    /** The Constant serialVersionUID */
    private static final long serialVersionUID = -4919595429111542320L;

    @Id
    @Column(length = 400)
    private String token;
    
    @Column(name = "expired_date")
    private Instant expiredDate;
    
    @Column(name = "expired", length = 1)
    private boolean expired;
    
    
}
