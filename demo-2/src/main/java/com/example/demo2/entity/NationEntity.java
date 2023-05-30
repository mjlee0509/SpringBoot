package com.example.demo2.entity;

import javax.persistence.*;

@Entity
@Table(name="nation_table")
public class NationEntity {

    // pk 지정
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 칼럼 추가
    @Column(length = 50, nullable = false, unique = true)
    private String nationName;

    @Column
    private int population;



}
