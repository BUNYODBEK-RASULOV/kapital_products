package com.example.kapital_products.uploadFile.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "INS_ANKETA_DOCS")
public class AnketaDocs {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "INS_ANKETA_DOCS_SEQ")
    @SequenceGenerator(name = "INS_ANKETA_DOCS_SEQ", allocationSize = 1)
    @Column(name = "INS_ID")
    private Integer insId;

    @Column(name = "ANKETA_ID")
    private Integer anketaId;

    @Column(name = "DOC_TYPE")
    private int docType;

    @Column(name = "POLIS_ID")
    private Integer polisId;

    @Column(name = "DOC_NOTE")
    private String docNote;

    @Column(name = "DOC_DATE")
    private LocalDateTime docDate;

    @Column(name = "DOC")
    private byte[] doc;

    @Column(name = "DOC_FN")
    private String docFn;

    @Column(name = "DOC_MT")
    private String docMt;

    @Column(name = "DOC_CS")
    private String docCs;

    @Column(name = "DOC_UP")
    private LocalDateTime docUp;

}
