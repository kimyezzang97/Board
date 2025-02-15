```mermaid
erDiagram
    member {
        int member_id PK "AUTO INCREMENT"
        string usernmae UK "아이디"
        string password "비밀번호"
    }
    board ||--o{ member : is
    board {
        int board_id PK "AUTO INCREMENT"
        int member_id FK "member 테이블과 연결된 외래 키"
        string title "게시판 제목"
        date reg_date "게시판 등록 날짜"
        clob content "게시판 내용"
    }
    comment ||--o{ board : is
    comment ||--o{ member : is
    comment {
        int comment_id PK  "AUTO INCREMENT"
        int board_id FK "board 테이블과 연결된 외래 키"
        int member_id FK "member 테이블과 연결된 외래 키"
        clob content "댓글 내용"
        date reg_date "댓글 등록 날짜"
    }
