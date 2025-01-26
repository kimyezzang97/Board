```mermaid
erDiagram
    member {
        int member_idx PK "AUTO INCREMENT"
        string email UK "이메일"
        string password "비밀번호"
        string nickname UK "닉네임"
    }
    board ||--o{ member : is
    board {
        int board_idx PK "AUTO INCREMENT"
        int member_idx FK "member 테이블과 연결된 외래 키"
        string title "게시판 제목"
        date reg_date "게시판 등록 날짜"
        clob content "게시판 내용"
    }
    comment ||--o{ board : is
    comment ||--o{ member : is
    comment {
        int comment_idx PK  "AUTO INCREMENT"
        int board_idx FK "board 테이블과 연결된 외래 키"
        int member_idx FK "member 테이블과 연결된 외래 키"
        clob content "댓글 내용"
        date reg_date "댓글 등록 날짜"
    }
