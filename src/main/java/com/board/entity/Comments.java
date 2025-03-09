package com.board.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.hibernate.annotations.Comment;
import java.time.LocalDateTime;



@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentsId;

    @Comment("내용")
    @Column(nullable = false)
    @Lob
    private String content;

    @Comment("댓글 등록 날짜")
    @Column(name = "create_date")
    @CreatedDate
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name="member_id", referencedColumnName = "member_id", foreignKey = @ForeignKey(name = "FK_COMMENTS_MEMBER"))
    private Member member;

    @ManyToOne
    @JoinColumn(name="board_id", referencedColumnName = "board_id", foreignKey = @ForeignKey(name = "FK_COMMENTS_BOARD"))
    private Board board;

    @Builder
    public Comments( String content, Member member, Board board){
        this.content = content;
        this.member = member;
        this.board = board;
    }

    // 업데이트 메서드 추가
    public void update( String content) {
        this.content = content;
    }
}
