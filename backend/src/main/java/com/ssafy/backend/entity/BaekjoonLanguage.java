package com.ssafy.backend.entity;

import com.ssafy.backend.entity.common.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@DynamicUpdate
@NoArgsConstructor
@Table(name = "baekjoon_languages")
public class BaekjoonLanguage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "language_id", nullable = false)
    private long languageId;

    @Column(name = "pass_percentage", nullable = false)
    private String passPercentage;

    @Column(name = "pass_count", nullable = false)
    private int passCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "baekjoon_id", nullable = false)
    private Baekjoon baekjoon;
}
