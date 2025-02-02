package com.board.post.service;

import com.board.common.jwt.JWTUtil;
import com.board.config.SecurityUtil;
import com.board.entity.Board;
import com.board.entity.Member;
import com.board.member.repository.MemberRepository;
import com.board.post.repository.PostRepository;
import com.board.post.request.CreatePostRequest;
import com.board.post.response.CreatePostResponse;
import com.board.post.response.GetPostListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final JWTUtil jwtUtil;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public PostService(JWTUtil jwtUtil, PostRepository postRepository, MemberRepository memberRepository) {
        this.jwtUtil = jwtUtil;
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    // 게시글 작성
    @Transactional
    public CreatePostResponse createPost(CreatePostRequest createPostRequest){ //
        String username = SecurityUtil.getCurrentUsername();
        Optional<Member> member = memberRepository.findByUsername(username);

        Board board = postRepository.save(createPostRequest.to(member.get()));
        CreatePostResponse createPostResponse = new CreatePostResponse(
                board.getBoardId(),
                board.getTitle(),
                board.getContent(),
                board.getCreateDate()
        );

        return createPostResponse;

    }

    // 전체 게시글 리스트 조회
    @Transactional(readOnly = true)
    public List<GetPostListResponse> getPostList(){
        List<Board> list = postRepository.findAllByOrderByCreateDateDesc();

        return list.stream()
                .map(GetPostListResponse::new)
                .collect(Collectors.toList());
    }

    // 상세 게시글 조회
    @Transactional(readOnly = true)
    public GetPostListResponse getPost(Long boardId){
        Board board = postRepository.findByBoardId(boardId);

        GetPostListResponse getPostListResponse = new GetPostListResponse(board.getBoardId(),
                board.getMember().getUsername(),
                board.getTitle(),
                board.getContent(),
                board.getCreateDate());

        return getPostListResponse;
    }
}
/**
 * private Long boardId;
 *
 *     private String username;
 *
 *     private String title;
 *
 *     private String content;
 *
 *     private LocalDateTime createDate;
 */
