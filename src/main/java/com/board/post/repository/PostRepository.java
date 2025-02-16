package com.board.post.repository;


import com.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Board, Long> {

    // 전체 게시글 조회
    List<Board> findAllByOrderByCreateDateDesc();

    // 게시글 상세 조회
    Board findByBoardId(Long boardId);

    // 게시글 삭제
    void deleteByBoardId(Long boardId);
}
