-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Oct 18, 2019 at 03:05 PM
-- Server version: 5.7.19
-- PHP Version: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `eightproj`
--

-- --------------------------------------------------------

--
-- Table structure for table `categorias`
--

DROP TABLE IF EXISTS `categorias`;
CREATE TABLE IF NOT EXISTS `categorias` (
  `codCategoria` int(11) NOT NULL,
  `nomeCategoria` varchar(50) NOT NULL,
  PRIMARY KEY (`codCategoria`),
  UNIQUE KEY `codCategoria` (`codCategoria`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `categorias`
--

INSERT INTO `categorias` (`codCategoria`, `nomeCategoria`) VALUES
(1, 'Moletom'),
(123456, 'Camiseta'),
(156451, 'Teste');

-- --------------------------------------------------------

--
-- Table structure for table `compras`
--

DROP TABLE IF EXISTS `compras`;
CREATE TABLE IF NOT EXISTS `compras` (
  `numCompra` int(11) NOT NULL,
  `cpfCliente` int(13) NOT NULL,
  `valorCompra` float NOT NULL,
  `freteCompra` float NOT NULL,
  `dataCompra` date NOT NULL,
  `formaPag` varchar(30) NOT NULL,
  `modoPag` varchar(30) NOT NULL,
  PRIMARY KEY (`numCompra`),
  KEY `cpfCliente` (`cpfCliente`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `produtos`
--

DROP TABLE IF EXISTS `produtos`;
CREATE TABLE IF NOT EXISTS `produtos` (
  `codProduto` varchar(20) NOT NULL,
  `nomeProduto` varchar(50) NOT NULL,
  `descProduto` varchar(800) DEFAULT NULL,
  `codCategoria` int(11) NOT NULL,
  `nomeCategoria` varchar(50) NOT NULL,
  `precoProduto` int(11) NOT NULL,
  `estoqueProduto` int(11) NOT NULL,
  `classificacaoProduto` int(11) NOT NULL,
  PRIMARY KEY (`codProduto`),
  UNIQUE KEY `codProduto` (`codProduto`),
  KEY `fkCodCategoria` (`codCategoria`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `produtos`
--

INSERT INTO `produtos` (`codProduto`, `nomeProduto`, `descProduto`, `codCategoria`, `nomeCategoria`, `precoProduto`, `estoqueProduto`, `classificacaoProduto`) VALUES
('cod1', 'DarkForest', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In scelerisque diam nulla, sed hendrerit ipsum laoreet vel. Fusce sodales, dui in auctor euismod, purus tortor egestas dolor, ut pharetra orci augue non orci. Vivamus urna enim, vulputate in tincidunt nec, malesuada sed augue. Vivamus id neque eget turpis consectetur sodales. Etiam rutrum elit non libero interdum pellentesque. Etiam id ultricies odio. Sed porttitor congue felis eu commodo. Pellentesque sit amet ex mauris.\r\n\r\nNulla interdum ante eu dignissim vestibulum. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Integer pulvinar semper molestie. Vivamus gravida magna at magna elementum, et efficitur augue feugiat. Praesent sit amet augue tellus. Fusce aliquam sodales risus vel commodo cras.', 123456, 'Camisetas', 999, 10, 3),
('cod2', 'Painted', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In scelerisque diam nulla, sed hendrerit ipsum laoreet vel. Fusce sodales, dui in auctor euismod, purus tortor egestas dolor, ut pharetra orci augue non orci. Vivamus urna enim, vulputate in tincidunt nec, malesuada sed augue. Vivamus id neque eget turpis consectetur sodales. Etiam rutrum elit non libero interdum pellentesque. Etiam id ultricies odio. Sed porttitor congue felis eu commodo. Pellentesque sit amet ex mauris.  Nulla interdum ante eu dignissim vestibulum. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Integer pulvinar semper molestie. Vivamus gravida magna at magna elementum, et efficitur augue feugiat. Praesent sit amet augue tellus. Fusce aliquam sodales risus vel commodo cras.', 123456, 'Camisetas', 85, 10, 1),
('cod3', 'Dust', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In scelerisque diam nulla, sed hendrerit ipsum laoreet vel. Fusce sodales, dui in auctor euismod, purus tortor egestas dolor, ut pharetra orci augue non orci. Vivamus urna enim, vulputate in tincidunt nec, malesuada sed augue. Vivamus id neque eget turpis consectetur sodales. Etiam rutrum elit non libero interdum pellentesque. Etiam id ultricies odio. Sed porttitor congue felis eu commodo. Pellentesque sit amet ex mauris.  Nulla interdum ante eu dignissim vestibulum. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Integer pulvinar semper molestie. Vivamus gravida magna at magna elementum, et efficitur augue feugiat. Praesent sit amet augue tellus. Fusce aliquam sodales risus vel commodo cras.', 123456, 'Camisetas', 90, 10, 1),
('cod4', 'Sea', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In scelerisque diam nulla, sed hendrerit ipsum laoreet vel. Fusce sodales, dui in auctor euismod, purus tortor egestas dolor, ut pharetra orci augue non orci. Vivamus urna enim, vulputate in tincidunt nec, malesuada sed augue. Vivamus id neque eget turpis consectetur sodales. Etiam rutrum elit non libero interdum pellentesque. Etiam id ultricies odio. Sed porttitor congue felis eu commodo. Pellentesque sit amet ex mauris.  Nulla interdum ante eu dignissim vestibulum. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Integer pulvinar semper molestie. Vivamus gravida magna at magna elementum, et efficitur augue feugiat. Praesent sit amet augue tellus. Fusce aliquam sodales risus vel commodo cras.', 123456, 'Camisetas', 100, 10, 1),
('cod5', 'Island', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In scelerisque diam nulla, sed hendrerit ipsum laoreet vel. Fusce sodales, dui in auctor euismod, purus tortor egestas dolor, ut pharetra orci augue non orci. Vivamus urna enim, vulputate in tincidunt nec, malesuada sed augue. Vivamus id neque eget turpis consectetur sodales. Etiam rutrum elit non libero interdum pellentesque. Etiam id ultricies odio. Sed porttitor congue felis eu commodo. Pellentesque sit amet ex mauris.  Nulla interdum ante eu dignissim vestibulum. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Integer pulvinar semper molestie. Vivamus gravida magna at magna elementum, et efficitur augue feugiat. Praesent sit amet augue tellus. Fusce aliquam sodales risus vel commodo cras.', 123456, 'Camisetas', 165, 10, 1);

-- --------------------------------------------------------

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE IF NOT EXISTS `usuarios` (
  `cpf` varchar(13) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `privilegio` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`cpf`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `usuarios`
--

INSERT INTO `usuarios` (`cpf`, `nome`, `email`, `senha`, `privilegio`) VALUES
('12345678911', 'Davi de Oliveira Massini', 'adm@email.com', '123456', 3);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `produtos`
--
ALTER TABLE `produtos`
  ADD CONSTRAINT `fkCodCategoria` FOREIGN KEY (`codCategoria`) REFERENCES `categorias` (`codCategoria`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
