package com.board.post.service;

import com.board.common.exception.member.ForbiddenException;
import com.board.common.jwt.JWTUtil;
import com.board.config.SecurityUtil;
import com.board.entity.Board;
import com.board.entity.Comments;
import com.board.entity.Member;
import com.board.member.repository.MemberRepository;
import com.board.post.repository.CommentsRepository;
import com.board.post.repository.PostRepository;
import com.board.post.request.CreateCommentsRequest;
import com.board.post.request.CreatePostRequest;
import com.board.post.request.PatchCommentsRequest;
import com.board.post.request.PatchPostRequest;
import com.board.post.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentsRepository commentsRepository;

    @Autowired
    public PostService(PostRepository postRepository, MemberRepository memberRepository, CommentsRepository commentsRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
        this.commentsRepository = commentsRepository;
    }

    // 게시글 작성
    @Transactional
    public CreatePostResponse createPost(CreatePostRequest createPostRequest){
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

    // 게시글 수정
    @Transactional
    public PatchPostResponse patchPost(Long boardId, PatchPostRequest patchPostRequest){
        String username = SecurityUtil.getCurrentUsername();

        Board board = postRepository.findByBoardId(boardId);

        Member member = memberRepository.findByUsername(username)
                .orElseThrow(ForbiddenException::new);

        // 작성자 확인
        if(!board.getMember().getMemberId().equals(member.getMemberId())){
            throw new ForbiddenException();
        }

        // JPA 는 Transactional 로 감싸진 메서드 안에서 객체가 변경시 트랜잭션이 종료되는 순간 UPDATE 쿼리를 실행함
        board.update(patchPostRequest.getTitle(), patchPostRequest.getContent());

        return new PatchPostResponse(board.getBoardId(), board.getTitle(), board.getContent(), board.getCreateDate());
    }

    // 게시글 삭제
    @Transactional(rollbackFor = Exception.class)
    public void deletePost(Long boardId){
        String username = SecurityUtil.getCurrentUsername();

        Board board = postRepository.findByBoardId(boardId);

        // 작성자 확인
        if(!board.getMember().getMemberId().equals(memberRepository.findByUsername(username).get().getMemberId())){
            throw new ForbiddenException();
        }

        postRepository.deleteByBoardId(boardId);
    }

    /* 댓글 기능 */

    // 댓글 생성
    @Transactional
    public CreateCommentsResponse createComments(Long boardId, CreateCommentsRequest createCommentsRequest){
        String username = SecurityUtil.getCurrentUsername();
        Member member = memberRepository.findByUsername(username).get();

        Comments comments = commentsRepository.save(createCommentsRequest.to(member, postRepository.findByBoardId(boardId)));
        CreateCommentsResponse createCommentsResponse = new CreateCommentsResponse(
                comments.getCommentsId(),
                comments.getContent(),
                comments.getCreateDate()
        );

        return createCommentsResponse;
    }

    // 댓글 수정
    @Transactional
    public PatchCommentsResponse patchComments(Long boardId, Long commentsId, PatchCommentsRequest patchCommentsRequest){
        String username = SecurityUtil.getCurrentUsername();

        Comments comments = commentsRepository.findByCommentsId(commentsId);

        // 작성자 확인
        if(!comments.getMember().getMemberId().equals(memberRepository.findByUsername(username).get().getMemberId())){
            throw new ForbiddenException();
        }

        // JPA 는 Transactional 로 감싸진 메서드 안에서 객체가 변경시 트랜잭션이 종료되는 순간 UPDATE 쿼리를 실행함
        comments.update(patchCommentsRequest.getContent());

        return new PatchCommentsResponse(comments.getCommentsId(), comments.getContent(), comments.getCreateDate());
    }

    // 댓글 삭제
    @Transactional(rollbackFor = Exception.class)
    public void deleteComments(Long commentsId){
        String username = SecurityUtil.getCurrentUsername();

        Comments comments = commentsRepository.findByCommentsId(commentsId);

        // 작성자 확인
        if(!comments.getMember().getMemberId().equals(memberRepository.findByUsername(username).get().getMemberId())){
            throw new ForbiddenException();
        }

        commentsRepository.deleteByCommentsId(commentsId);
    }
}


