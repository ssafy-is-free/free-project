package com.ssafy.backend.domain.entity;

import com.ssafy.backend.domain.algorithm.dto.response.BojLanguageResultDTO;
import com.ssafy.backend.domain.entity.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    public static BaekjoonLanguage createBaekjoonLanguage(Long languageId, BojLanguageResultDTO bojLanguageResultDTO, Baekjoon baekjoon){
        return BaekjoonLanguage.builder()
                .languageId(languageId)
                .passPercentage(bojLanguageResultDTO.getPassPercentage())
                .passCount(bojLanguageResultDTO.getPassCount())
                .baekjoon(baekjoon)
                .build();

    }

    public void updateBaekjoonLanguage(BojLanguageResultDTO bojLanguageResultDTO) {
        this.passPercentage = bojLanguageResultDTO.getPassPercentage();
        this.passCount = bojLanguageResultDTO.getPassCount();
    }
}
