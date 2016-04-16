/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50709
Source Host           : localhost:3306
Source Database       : dotamatch

Target Server Type    : MYSQL
Target Server Version : 50709
File Encoding         : 65001

Date: 2015-11-19 10:57:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `193_194`
-- ----------------------------
DROP TABLE IF EXISTS `193_194`;
CREATE TABLE `193_194` (
`RecordID`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`MatchID`  int(11) NULL DEFAULT NULL ,
`PlayerID`  int(11) NULL DEFAULT NULL ,
`Duration`  int(11) NULL DEFAULT NULL ,
`Area`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`FBTime`  int(11) NULL DEFAULT NULL ,
`Skill`  int(11) NULL DEFAULT NULL ,
`GameMode`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`HeroName`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`HeroLevel`  int(11) NULL DEFAULT NULL ,
`Kill`  int(11) NULL DEFAULT NULL ,
`Death`  int(11) NULL DEFAULT NULL ,
`Assist`  int(11) NULL DEFAULT NULL ,
`KDA`  double NULL DEFAULT NULL ,
`BattlePercentage`  double NULL DEFAULT NULL ,
`DamagePercentage`  double NULL DEFAULT NULL ,
`Lasthit`  int(11) NULL DEFAULT NULL ,
`Denied`  int(11) NULL DEFAULT NULL ,
`GPM`  int(11) NULL DEFAULT NULL ,
`XPM`  int(11) NULL DEFAULT NULL ,
`TDamage`  int(11) NULL DEFAULT NULL ,
`Heal`  int(11) NULL DEFAULT NULL ,
`Item0`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`Item1`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`Item2`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`Item3`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`Item4`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`Item5`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`Lasthit/min`  double NULL DEFAULT NULL ,
`Denied/min`  double NULL DEFAULT NULL ,
`Damage/min`  double NULL DEFAULT NULL ,
`TDamage/min`  double NULL DEFAULT NULL ,
`Heal/min`  double NULL DEFAULT NULL ,
`WinOrLose`  int(11) NULL DEFAULT NULL ,
`IsStar`  int(11) NULL DEFAULT NULL ,
`IsMVP`  int(11) NULL DEFAULT NULL ,
PRIMARY KEY (`RecordID`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of 193_194
-- ----------------------------
BEGIN;
COMMIT;
