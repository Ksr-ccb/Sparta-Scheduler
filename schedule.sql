USE scheduler;

CREATE TABLE `USER` (
                        `USER_ID` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '사용자 식별자',
                        `USER_NAME` VARCHAR(20) NULL,
                        `EMAIL` VARCHAR(30) NULL,
                        `UPDATE_DATE` DATE NULL
);

CREATE TABLE `SCHEDULE` (
                            `SCHEDULE_ID` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '스케줄 식별자',
                            `SCHEDULE_CONTENT` VARCHAR(200) NULL,
                            `PASSWORD` VARCHAR(40) NULL,
                            `CREATE_DATE` DATE NULL,
                            `UPDATE_DATE` DATE NULL,
                            `USER_ID` BIGINT NOT NULL,
                            CONSTRAINT FK_USER_SCHEDULE FOREIGN KEY (`USER_ID`) REFERENCES `USER`(`USER_ID`)
                                ON DELETE CASCADE
);


ALTER TABLE `USER`
    MODIFY COLUMN `USER_NAME` VARCHAR(20) NOT NULL,
    MODIFY COLUMN `EMAIL` VARCHAR(30) NOT NULL,
    MODIFY COLUMN `UPDATE_DATE` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE `schedule`
    MODIFY COLUMN `SCHEDULE_CONTENT` VARCHAR(200) NOT NULL,
    MODIFY COLUMN `PASSWORD` VARCHAR(40) NOT NULL,
    MODIFY COLUMN `CREATE_DATE` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    MODIFY COLUMN `UPDATE_DATE` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    MODIFY COLUMN `USER_ID` BIGINT NOT NULL;