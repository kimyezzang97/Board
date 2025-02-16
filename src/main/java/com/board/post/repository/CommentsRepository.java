package com.board.post.repository;

import com.board.entity.Board;
import com.board.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
    Comments findByCommentsId(Long commentsId);

    void deleteByCommentsId(Long commentsId);
}
