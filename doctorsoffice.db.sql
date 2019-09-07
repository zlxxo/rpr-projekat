BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS `boss` (
	`username`	TEXT,
	`first_name`	TEXT,
	`last_name`	TEXT,
	`password`	TEXT,
	PRIMARY KEY(`username`)
);
INSERT INTO `boss` VALUES ('mramic2','Muhamed','Ramić','NekaSifra4');
COMMIT;