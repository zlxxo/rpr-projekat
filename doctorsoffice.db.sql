BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS `boss` (
	`username`	TEXT,
	`first_name` TEXT,
	`last_name`	TEXT,
	`password`	TEXT,
	PRIMARY KEY(`username`)
);
INSERT INTO `boss` VALUES ('mramic2','Muhamed','Ramić','NekaSifra4');
CREATE TABLE `department` (
	`id`	INTEGER,
	`name`	TEXT,
	PRIMARY KEY(`id`)
);
INSERT INTO `department` VALUES (1, 'opća medicina');
INSERT INTO `department` VALUES (2, 'oftamologija');
CREATE TABLE IF NOT EXISTS `doctor` (
	`username`	TEXT,
	`first_name` TEXT,
	`last_name`	TEXT,
	`password`	TEXT,
	`licence_number` TEXT UNIQUE,
	`department`	INTEGER,
	PRIMARY KEY(`username`),
	FOREIGN KEY(`department`) REFERENCES `department`(`id`)
);
INSERT INTO `doctor` VALUES ('silic1','Selma','Ilić','Mojasifra6','123456', 1);
INSERT INTO `doctor` VALUES ('hsadic2','Hana','Sadić','Sifrica6','123457', 2);
CREATE TABLE `patient` (
	`pin`	TEXT,
	`first_name`	TEXT,
	`last_name`	TEXT,
	`medical_history`	INTEGER UNIQUE,
	`general_practitioner`	TEXT,
    PRIMARY KEY(`pin`),
    FOREIGN KEY(`general_practitioner`) REFERENCES `doctor`(`username`)
);
INSERT INTO `patient` VALUES('1605998167723', 'Sara', 'Hasić', 1, "silic1");
CREATE TABLE `allergy` (
	`patient`	INTEGER,
	`name`	TEXT
);
INSERT INTO `allergy` VALUES(1, "kikiriki");
INSERT INTO `allergy` VALUES(1, "sunce");
CREATE TABLE `disease` (
	`patient`	INTEGER,
	`name`	TEXT
);
INSERT INTO `disease` VALUES(1, "dijabetes tip 1");
CREATE TABLE `checkup` (
	`patient`	INTEGER,
	`doctor`	TEXT,
	`date`	TEXT,
	`time`	TEXT,
	`diagnosis`	TEXT
);
COMMIT;