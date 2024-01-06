package com.example.kapital_products.commons.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "LOG_API_SERVER")
public class LogEntity {
    @Id
    @Column(name = "LOG_ID")
    private int id;

    @Column(name = "LOG_DATE")
    private Date logDate;

    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "REQ_CONTENT",columnDefinition = "CLOB")
    private String reqContent;

    @Column(name = "RES_CODE")
    private int resCode;

    @Column(name = "RES_MESSAGE")
    private String resMessage;

    @Column(name = "RES_CONTENT",columnDefinition = "CLOB")
    private String resContent;

    @Column(name = "TIMESPENT")
    private Double timeSpent;

    @Column(name = "TB_ANKETA")
    private Integer anketaId;

    @Column(name = "METHOD")
    private String method;


}
