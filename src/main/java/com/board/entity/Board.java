package com.board.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    @Comment("제목")
    @Column(nullable = false)
    private String title;

    @Comment("내용")
    @Column(nullable = false)
    @Lob
    private String content;

    @Comment("게시판 생성 날짜")
    @Column(name = "create_date")
    @CreatedDate
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name="member_id", referencedColumnName = "member_id", foreignKey = @ForeignKey(name = "FK_BOARD_MEMBER"))
    private Member member;

    @Builder
    public Board(String title, String content, Member member){
        this.title = title;
        this.content = content;
        this.member = member;
    }

    // 업데이트 메서드 추가
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
