-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 06-Maio-2026 às 01:43
-- Versão do servidor: 10.4.32-MariaDB
-- versão do PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `ravania`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `clientes`
--

CREATE TABLE `clientes` (
                            `id_cliente` int(11) NOT NULL,
                            `nome_cliente` varchar(50) DEFAULT NULL,
                            `data_nascimento` date DEFAULT NULL,
                            `cpf_cliente` varchar(14) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `clientes`
--

INSERT INTO `clientes` (`id_cliente`, `nome_cliente`, `data_nascimento`, `cpf_cliente`) VALUES
    (8, 'Teste Fulano 1', '1999-12-31', '478.562.147-85');

-- --------------------------------------------------------

--
-- Estrutura da tabela `transacoes`
--

CREATE TABLE `transacoes` (
                              `id_transacao` int(11) NOT NULL,
                              `data_transacao` date DEFAULT NULL,
                              `cvv` int(11) DEFAULT NULL,
                              `status_transacao` varchar(20) DEFAULT NULL,
                              `valor_transacao` double DEFAULT NULL,
                              `numero_cartao` int(11) DEFAULT NULL,
                              `cliente_transacao` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `transacoes`
--

INSERT INTO `transacoes` (`id_transacao`, `data_transacao`, `cvv`, `status_transacao`, `valor_transacao`, `numero_cartao`, `cliente_transacao`) VALUES
    (3, '2026-05-05', 147, 'Ok', 45844.84, 74851245, NULL);

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `clientes`
--
ALTER TABLE `clientes`
    ADD PRIMARY KEY (`id_cliente`);

--
-- Índices para tabela `transacoes`
--
ALTER TABLE `transacoes`
    ADD PRIMARY KEY (`id_transacao`),
    ADD KEY `FK_transacoes_2` (`cliente_transacao`);

--
-- AUTO_INCREMENT de tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `clientes`
--
ALTER TABLE `clientes`
    MODIFY `id_cliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de tabela `transacoes`
--
ALTER TABLE `transacoes`
    MODIFY `id_transacao` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restrições para despejos de tabelas
--

--
-- Limitadores para a tabela `transacoes`
--
ALTER TABLE `transacoes`
    ADD CONSTRAINT `FK_transacoes_2` FOREIGN KEY (`cliente_transacao`) REFERENCES `clientes` (`id_cliente`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;