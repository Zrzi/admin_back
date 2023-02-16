DROP TABLE IF EXISTS `teacheres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teacheres` (
    `empNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `empName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `sex` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `birthday` date NOT NULL,
    `phone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    `IDNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    `marriage` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    `orgType` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    `staffType` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    `title` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    `degree` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    `title_level` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    `teachtype` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    `school` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    `enterdate` date DEFAULT NULL,
    `teachdate` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    `rank` int DEFAULT NULL,
    `tecposition` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    `memo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    `schoolid` int DEFAULT '30',
    PRIMARY KEY (`empNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;