DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students` (
    `stuNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `stuName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `sex` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `majorNo` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `classNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `cur_grade` int NOT NULL,
    `enteryear` int NOT NULL,
    `birthday` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `nation` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `PoliticalStatus` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `SourcePlace` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    PRIMARY KEY (`stuNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;