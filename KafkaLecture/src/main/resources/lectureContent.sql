create table IF NOT EXISTS lectureContent
(
    id int NOT NULL AUTO_INCREMENT,
    contentName VARCHAR(255) NOT NULL,
    contentBody TEXT NOT NULL,
    lectureId int NOT NULL,
    contentType VARCHAR(255) NOT NULL,
    insertDt DATETIME DEFAULT CURRENT_TIMESTAMP,
    updateDt DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id)
);

INSERT INTO lectureContent
VALUES (1, 'test1', 'content1', 1, 'exam', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO lectureContent
VALUES (2, 'test2', 'content2', 2, 'lecture', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO lectureContent
VALUES (3, 'test2', 'content2', 3, 'lecture', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);