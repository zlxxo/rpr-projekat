BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS `boss` (
	`username`	TEXT,
	`first_name` TEXT,
	`last_name`	TEXT,
	`password`	TEXT,
	PRIMARY KEY(`username`)
);
INSERT INTO `boss` VALUES ('mramic2','Muhamed','Ramić','NekaSifra4');
CREATE TABLE IF NOT EXISTS `doctor` (
	`username`	TEXT,
	`first_name` TEXT,
	`last_name`	TEXT,
	`password`	TEXT,
	`licence_number` TEXT,
	PRIMARY KEY(`username`)
);
INSERT INTO `doctor` VALUES ('silic1','Selma','Ilić','Mojasifra6','123456');
COMMIT;