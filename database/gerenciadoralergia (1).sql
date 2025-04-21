-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 21, 2025 at 04:31 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gerenciadoralergia`
--

-- --------------------------------------------------------

--
-- Table structure for table `componente`
--

CREATE TABLE `componente` (
  `componente_id` int(11) NOT NULL,
  `nome` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `componente`
--

INSERT INTO `componente` (`componente_id`, `nome`) VALUES
(3, 'BHT'),
(1, 'Formaldeído'),
(2, 'Parabeno');

-- --------------------------------------------------------

--
-- Table structure for table `componente_equivalente`
--

CREATE TABLE `componente_equivalente` (
  `componente_01_id` int(11) NOT NULL,
  `componente_02_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `produto`
--

CREATE TABLE `produto` (
  `produto_id` int(11) NOT NULL,
  `nome` varchar(120) NOT NULL,
  `descricao` varchar(250) NOT NULL,
  `foto` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `produto`
--

INSERT INTO `produto` (`produto_id`, `nome`, `descricao`, `foto`) VALUES
(2, 'shampoo', 'very good', NULL),
(3, 'Sabonete Neutro', 'sabonete bom', 'uploads/foto_1745186962584.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `produto_componente`
--

CREATE TABLE `produto_componente` (
  `produto_id` int(11) NOT NULL,
  `componente_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `produto_componente`
--

INSERT INTO `produto_componente` (`produto_id`, `componente_id`) VALUES
(3, 1),
(3, 3);

-- --------------------------------------------------------

--
-- Table structure for table `usuario`
--

CREATE TABLE `usuario` (
  `usuario_id` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `tipo_usuario` enum('ADMIN','CLIENTE') NOT NULL DEFAULT 'CLIENTE'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `usuario`
--

INSERT INTO `usuario` (`usuario_id`, `nome`, `email`, `senha`, `tipo_usuario`) VALUES
(4, 'leticia2', 'let2@gmail.com', '1223', 'CLIENTE'),
(6, 'raquel3', 'raquel4@gmail.com', '123', 'CLIENTE'),
(7, 'leticia3', 'let3@gmail.com', '1223', 'CLIENTE'),
(8, 'Samuel Silva', 'samuel@gmail.com', '123', 'CLIENTE');

-- --------------------------------------------------------

--
-- Table structure for table `usuario_componente`
--

CREATE TABLE `usuario_componente` (
  `usuario_id` int(11) NOT NULL,
  `componente_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `usuario_componente`
--

INSERT INTO `usuario_componente` (`usuario_id`, `componente_id`) VALUES
(8, 1),
(8, 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `componente`
--
ALTER TABLE `componente`
  ADD PRIMARY KEY (`componente_id`),
  ADD UNIQUE KEY `nome` (`nome`);

--
-- Indexes for table `componente_equivalente`
--
ALTER TABLE `componente_equivalente`
  ADD PRIMARY KEY (`componente_01_id`,`componente_02_id`),
  ADD KEY `componente_equivalente_fk_componente_01` (`componente_01_id`),
  ADD KEY `componente_equivalente_fk_componente_02` (`componente_02_id`);

--
-- Indexes for table `produto`
--
ALTER TABLE `produto`
  ADD PRIMARY KEY (`produto_id`),
  ADD UNIQUE KEY `nome` (`nome`);

--
-- Indexes for table `produto_componente`
--
ALTER TABLE `produto_componente`
  ADD PRIMARY KEY (`produto_id`,`componente_id`),
  ADD KEY `produto_componente_fk_componente` (`componente_id`);

--
-- Indexes for table `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`usuario_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `usuario_componente`
--
ALTER TABLE `usuario_componente`
  ADD PRIMARY KEY (`usuario_id`,`componente_id`),
  ADD KEY `usuario_componente_fk_componente` (`componente_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `componente`
--
ALTER TABLE `componente`
  MODIFY `componente_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `produto`
--
ALTER TABLE `produto`
  MODIFY `produto_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `usuario`
--
ALTER TABLE `usuario`
  MODIFY `usuario_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `componente_equivalente`
--
ALTER TABLE `componente_equivalente`
  ADD CONSTRAINT `componente_equivalente_fk_componente_01` FOREIGN KEY (`componente_01_id`) REFERENCES `componente` (`componente_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `componente_equivalente_fk_componente_02` FOREIGN KEY (`componente_02_id`) REFERENCES `componente` (`componente_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `produto_componente`
--
ALTER TABLE `produto_componente`
  ADD CONSTRAINT `produto_componente_fk_componente` FOREIGN KEY (`componente_id`) REFERENCES `componente` (`componente_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `produto_componente_fk_produto` FOREIGN KEY (`produto_id`) REFERENCES `produto` (`produto_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `usuario_componente`
--
ALTER TABLE `usuario_componente`
  ADD CONSTRAINT `fk_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`usuario_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `usuario_componente_fk_componente` FOREIGN KEY (`componente_id`) REFERENCES `componente` (`componente_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
