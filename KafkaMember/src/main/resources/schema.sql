DROP TABLE IF EXISTS member; -- member 테이블이 존재할 경우 DROP

CREATE TABLE IF NOT EXISTS member ( -- member 테이블이 없을 경우 테이블 생성
    memberId INT(20) AUTO_INCREMENT PRIMARY KEY NOT NULL COMMENT '회원 고유번호',
    memberName VARCHAR(50) NOT NULL COMMENT '회원 이름',
    memberPassword VARCHAR(255) NOT NULL COMMENT '회원 비밀번호',
    memberType VARCHAR(255) COMMENT '회원 유형',
    memberPhone VARCHAR(50) COMMENT '회원 연락처',
    insertDt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    updateDt DATETIME ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일'
);

