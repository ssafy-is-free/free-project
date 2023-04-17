package com.ssafy.backend.entity;

import com.ssafy.backend.entity.common.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@DynamicUpdate
@NoArgsConstructor
@Table(name = "job_history")
public class JobHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "d_day", nullable = false)
    private LocalDate dDay;

    @Column(name = "d_day_name", nullable = false)
    private String dDayName;

    @Column(name = "memo")
    private String memo;

    @Column(name = "status_id", nullable = false)
    private long statusId;

    @Column(name = "job_objective", nullable = false)
    private String jobObjective;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_posting_id")
    private JobPosting jobPosting;

}
