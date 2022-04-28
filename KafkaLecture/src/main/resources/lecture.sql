create table IF NOT EXISTS lecture
(
    lectureId INT NOT NULL AUTO_INCREMENT,
    lectureName VARCHAR(255),
    memberId INT,
    memberName VARCHAR(255),
    lectureShowYn BOOLEAN(10) DEFAULT FALSE,
    lectureTotalScore BIGINT,
    insertDt DATETIME DEFAULT CURRENT_TIMESTAMP,
    updateDt DATETIME DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (lectureId)

    );

create table IF NOT EXISTS lectureInfo
(
    lectureInfoId INT NOT NULL AUTO_INCREMENT,
    lectureId INT,
    memberId INT,
    testScore INTEGER DEFAULT 0,
    lectureState VARCHAR(255),
    lectureScore  INTEGER DEFAULT 0,
    insertDt DATETIME DEFAULT CURRENT_TIMESTAMP,
    updateDt DATETIME DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (lectureInfoId)
    );

INSERT INTO lecture VALUES (1, 'Event Driven Architecture', 1, '홍강사', false, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO lecture VALUES (2, 'Domain Driven Architecture', 1, '홍강사', false, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO lecture VALUES (3, 'Micro Service Architecture', 1, '홍강사', false, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO lecture VALUES (4, 'Kafka', 2, 'teacherName', false, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO lecture VALUES (5, 'Backend 심화과정', 3, 'teacherName', false, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO lectureInfo VALUES (0, 1, 0, 0, '수강중', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO lectureInfo VALUES (1, 1, 1, 0, '수강중', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO lectureInfo VALUES (2, 1, 2, 0, '수강중', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO lectureInfo VALUES (3, 1, 3, 0, '수강중', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

