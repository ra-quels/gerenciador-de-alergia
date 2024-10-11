-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 07/10/2024 às 04:44
-- Versão do servidor: 10.4.32-MariaDB
-- Versão do PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `gerenciadoralergia`
--
CREATE DATABASE gerenciadoralergia;
use gerenciadoralergia;
-- --------------------------------------------------------

--
-- Estrutura para tabela `componente`
--

CREATE TABLE `componente` (
  `componente_id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(150) NOT NULL UNIQUE,
  PRIMARY KEY(`componente_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `componente_equivalente`
--

CREATE TABLE `componente_equivalente` (
  `componente_01_id` int(11) NOT NULL,
  `componente_02_id` int(11) NOT NULL,
  PRIMARY KEY (`componente_01_id`,`componente_02_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `produto`
--

CREATE TABLE `produto` (
  `produto_id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(120) NOT NULL UNIQUE,
  `descricao` varchar(250) NOT NULL,
  PRIMARY KEY(`produto_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `produto_componente`
--

CREATE TABLE `produto_componente` (
  `produto_id` int(11) NOT NULL,
  `componente_id` int(11) NOT NULL,
  PRIMARY KEY (`produto_id`,`componente_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `usuario`
--

CREATE TABLE `usuario` (
  `usuario_id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL UNIQUE,
  `senha` varchar(255) NOT NULL,
  `tipo_usuario` enum('ADMIN','CLIENTE') NOT NULL DEFAULT 'CLIENTE',
  PRIMARY KEY(`usuario_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `usuario`
--

INSERT INTO `usuario` (`usuario_id`, `nome`, `email`, `senha`, `tipo_usuario`) VALUES
(1, 'raquel', 'raquel@gmail.com', '123', 'CLIENTE'),
(2, 'isabel', 'isabel@gmail.com', '123', 'CLIENTE');

-- --------------------------------------------------------

--
-- Estrutura para tabela `usuario_componente`
--

CREATE TABLE `usuario_componente` (
  `usuario_id` int(11) NOT NULL,
  `componente_id` int(11) NOT NULL,
  PRIMARY KEY (`usuario_id`,`componente_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


--
-- Índices de tabela `componente_equivalente`
--
ALTER TABLE `componente_equivalente`
  ADD KEY `componente_equivalente_fk_componente_01` (`componente_01_id`);

ALTER TABLE `componente_equivalente`
  ADD KEY `componente_equivalente_fk_componente_02` (`componente_02_id`);

-- Índices de tabela `produto_componente`
--
ALTER TABLE `produto_componente`
  ADD KEY `produto_componente_fk_componente` (`componente_id`);
--
-- Índices de tabela `usuario_componente`
--
ALTER TABLE `usuario_componente`
  ADD KEY `usuario_componente_fk_componente` (`componente_id`);

--
-- Restrições para tabelas `componente_equivalente`
--
ALTER TABLE `componente_equivalente`
  ADD CONSTRAINT `componente_equivalente_fk_componente_01` FOREIGN KEY (`componente_01_id`) REFERENCES `componente` (`componente_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `componente_equivalente_fk_componente_02` FOREIGN KEY (`componente_02_id`) REFERENCES `componente` (`componente_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Restrições para tabelas `produto_componente`
--
ALTER TABLE `produto_componente`
  ADD CONSTRAINT `produto_componente_fk_componente` FOREIGN KEY (`componente_id`) REFERENCES `componente` (`componente_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `produto_componente_fk_produto` FOREIGN KEY (`produto_id`) REFERENCES `produto` (`produto_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Restrições para tabelas `usuario_componente`
--
ALTER TABLE `usuario_componente`
  ADD CONSTRAINT `fk_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`usuario_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `usuario_componente_fk_componente` FOREIGN KEY (`componente_id`) REFERENCES `componente` (`componente_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

