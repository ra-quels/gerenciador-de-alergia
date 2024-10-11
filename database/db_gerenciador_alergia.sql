-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 11/10/2024 às 02:35
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

-- --------------------------------------------------------

--
-- Estrutura para tabela `componente`
--

CREATE TABLE `componente` (
  `componente_id` int(11) NOT NULL,
  `nome` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `componente`
--

INSERT INTO `componente` (`componente_id`, `nome`) VALUES
(1, 'Formaldeído');

-- --------------------------------------------------------

--
-- Estrutura para tabela `componente_equivalente`
--

CREATE TABLE `componente_equivalente` (
  `componente_01_id` int(11) NOT NULL,
  `componente_02_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `produto`
--

CREATE TABLE `produto` (
  `produto_id` int(11) NOT NULL,
  `nome` varchar(120) NOT NULL,
  `descricao` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `produto`
--

INSERT INTO `produto` (`produto_id`, `nome`, `descricao`) VALUES
(2, 'shampoo', 'very good');

-- --------------------------------------------------------

--
-- Estrutura para tabela `produto_componente`
--

CREATE TABLE `produto_componente` (
  `produto_id` int(11) NOT NULL,
  `componente_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `usuario`
--

CREATE TABLE `usuario` (
  `usuario_id` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `tipo_usuario` enum('ADMIN','CLIENTE') NOT NULL DEFAULT 'CLIENTE'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `usuario`
--

INSERT INTO `usuario` (`usuario_id`, `nome`, `email`, `senha`, `tipo_usuario`) VALUES
(4, 'leticia2', 'let2@gmail.com', '1223', 'CLIENTE'),
(6, 'raquel3', 'raquel4@gmail.com', '123', 'CLIENTE'),
(7, 'leticia3', 'let3@gmail.com', '1223', 'CLIENTE');

-- --------------------------------------------------------

--
-- Estrutura para tabela `usuario_componente`
--

CREATE TABLE `usuario_componente` (
  `usuario_id` int(11) NOT NULL,
  `componente_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `componente`
--
ALTER TABLE `componente`
  ADD PRIMARY KEY (`componente_id`),
  ADD UNIQUE KEY `nome` (`nome`);

--
-- Índices de tabela `componente_equivalente`
--
ALTER TABLE `componente_equivalente`
  ADD PRIMARY KEY (`componente_01_id`,`componente_02_id`),
  ADD KEY `componente_equivalente_fk_componente_01` (`componente_01_id`),
  ADD KEY `componente_equivalente_fk_componente_02` (`componente_02_id`);

--
-- Índices de tabela `produto`
--
ALTER TABLE `produto`
  ADD PRIMARY KEY (`produto_id`),
  ADD UNIQUE KEY `nome` (`nome`);

--
-- Índices de tabela `produto_componente`
--
ALTER TABLE `produto_componente`
  ADD PRIMARY KEY (`produto_id`,`componente_id`),
  ADD KEY `produto_componente_fk_componente` (`componente_id`);

--
-- Índices de tabela `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`usuario_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Índices de tabela `usuario_componente`
--
ALTER TABLE `usuario_componente`
  ADD PRIMARY KEY (`usuario_id`,`componente_id`),
  ADD KEY `usuario_componente_fk_componente` (`componente_id`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `componente`
--
ALTER TABLE `componente`
  MODIFY `componente_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de tabela `produto`
--
ALTER TABLE `produto`
  MODIFY `produto_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `usuario`
--
ALTER TABLE `usuario`
  MODIFY `usuario_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Restrições para tabelas despejadas
--

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

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
