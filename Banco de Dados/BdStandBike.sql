-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 30, 2022 at 06:04 PM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bdstandbike`
--
CREATE DATABASE IF NOT EXISTS `bdstandbike` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `bdstandbike`;

-- --------------------------------------------------------

--
-- Table structure for table `cliente`
--

CREATE TABLE `cliente` (
  `idCliente` int(11) NOT NULL,
  `nome` varchar(128) NOT NULL,
  `cpf` varchar(11) NOT NULL,
  `email` varchar(128) NOT NULL,
  `endereco` varchar(256) NOT NULL,
  `telefone` varchar(11) NOT NULL,
  `dataCadastro` date NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `cliente`
--

INSERT INTO `cliente` (`idCliente`, `nome`, `cpf`, `email`, `endereco`, `telefone`, `dataCadastro`, `status`) VALUES
(1, 'Fernanda Martins Cunha', '11111111111', 'fernanda@gmail.com', 'Planaltina - Quadra 02 Conjunto F, 354', '61999999999', '2022-11-15', 1),
(2, 'Danilo Cunha Ribeiro', '99988877755', 'danilo@gmail.com', 'Rua Queimados-RJ', '61999887744', '2022-11-20', 1),
(3, 'Felipe Almeida Silva', '44455566611', 'felipe@gmail.com', 'Rua Margem da Estrada de Ferro, 255', '61955885544', '2022-11-10', 1),
(4, 'Emily Sousa Dias', '33322211155', 'emily@hotmail.com', 'Rua José Ignácio, 964', '61988447711', '2022-11-19', 1),
(5, 'Joao pedro', '55599988811', 'joao@gmail.com', 'São vicente', '61788445566', '2022-11-26', 1);

-- --------------------------------------------------------

--
-- Table structure for table `menu`
--

CREATE TABLE `menu` (
  `idMenu` int(11) NOT NULL,
  `nome` varchar(128) NOT NULL,
  `link` varchar(256) NOT NULL,
  `exibir` int(11) NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `menu`
--

INSERT INTO `menu` (`idMenu`, `nome`, `link`, `exibir`, `status`) VALUES
(1, 'Home', 'index.jsp', 1, 1),
(2, 'Perfis', 'gerenciarPerfil?acao=listar', 1, 1),
(3, 'Menus', 'gerenciarMenu?acao=listar', 1, 1),
(4, 'Clientes', 'gerenciarCliente?acao=listar', 1, 1),
(5, 'Produtos', 'gerenciarProduto?acao=listar', 1, 1),
(6, 'Usuarios', 'gerenciarUsuario?acao=listar', 1, 1),
(7, 'Cadastrar Perfil', 'cadastrarPerfil.jsp', 0, 1),
(8, 'Alterar Perfil', 'gerenciarPerfil?acao=alterar', 0, 1),
(9, 'Deletar Perfil', 'gerenciarPerfil?acao=deletar', 0, 1),
(10, 'Listar Perfil', 'listarPerfis.jsp', 0, 1),
(11, 'Cadastrar Menu', 'cadastrarMenu.jsp', 0, 1),
(12, 'Listar Menu', 'listarMenus.jsp', 0, 1),
(13, 'Alterar Menu', 'gerenciarMenu?acao=alterar', 0, 1),
(14, 'Deletar Menu', 'gerenciarMenu?acao=deletar', 0, 1),
(15, 'Perfis Acesso', 'gerenciarPerfil.do?acao=gerenciar', 0, 1),
(16, 'Form Menu Perfil', 'formMenuPerfil.jsp', 0, 1),
(17, 'Desvincular Menu Perfil', 'gerenciarPerfil.do?acao=desvincular', 0, 1),
(18, 'Cadastrar Cliente', 'cadastrarCliente.jsp', 0, 1),
(19, 'Pagina Cliente', 'listarClientes.jsp', 0, 1),
(20, 'Alterar Cliente', 'gerenciarCliente?acao=alterar', 0, 1),
(21, 'Ativar Cliente', 'gerenciarCliente?acao=ativar', 0, 1),
(22, 'Desativar Cliente', 'gerenciarCliente?acao=desativar', 0, 1),
(25, 'Pagina Produto', 'listarProdutos.jsp', 0, 1),
(26, 'Alterar Produto', 'gerenciarProduto?acao=alterar', 0, 1),
(27, 'Ativar Produto', 'gerenciarProduto?acao=ativar', 0, 1),
(28, 'Desativar Produto', 'gerenciarProduto?acao=desativar', 0, 1),
(29, 'Pagina Usuario', 'listarUsuarios.jsp', 0, 1),
(30, 'Alterar Usuario', 'gerenciarUsuario?acao=alterar', 0, 1),
(31, 'Ativar Usuario', 'gerenciarUsuario?acao=ativar', 0, 1),
(32, 'Desativar Usuario', 'gerenciarUsuario?acao=desativar', 0, 1),
(33, 'Cadastrar Produto', 'cadastrarProduto.jsp', 0, 1),
(34, 'Cadastrar Usuario', 'cadastrarUsuario.jsp', 0, 1),
(35, 'Form venda', 'formVenda.jsp', 0, 1),
(36, 'Form Continuar Venda', 'formVenda.jsp?acao=continuar', 0, 1),
(37, 'Gerenciar Carrinho Add', 'gerenciarCarrinho?acao=add', 0, 1),
(38, 'Formulário finalizar venda', 'formFinalizarVenda.jsp', 0, 1),
(39, 'Gerar Relatório Cliente', 'gerenciarCliente?acao=report', 0, 1),
(40, 'Pagina Vendas', 'listarVendas.jsp', 0, 1),
(41, 'Vendas', 'gerenciarVenda?acao=listar', 1, 1),
(42, 'Ativar Perfil', 'gerenciarPerfil?acao=ativar', 0, 1),
(43, 'Desativar Perfil', 'gerenciarPerfil?acao=desativar', 0, 1),
(44, 'Ativar Menu', 'gerenciarMenu?acao=ativar', 0, 1),
(45, 'Desativar Menu', 'gerenciarMenu?acao=desativar', 0, 1),
(50, 'teste', 'teste', 0, 1);

-- --------------------------------------------------------

--
-- Table structure for table `menu_perfil`
--

CREATE TABLE `menu_perfil` (
  `idMenu` int(11) NOT NULL,
  `idPerfil` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `menu_perfil`
--

INSERT INTO `menu_perfil` (`idMenu`, `idPerfil`) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 1),
(3, 1),
(4, 1),
(4, 3),
(5, 1),
(5, 3),
(6, 1),
(7, 1),
(8, 1),
(9, 1),
(10, 1),
(11, 1),
(12, 1),
(13, 1),
(14, 1),
(15, 1),
(16, 1),
(17, 1),
(18, 1),
(19, 1),
(19, 3),
(20, 1),
(21, 1),
(22, 1),
(25, 1),
(25, 3),
(26, 1),
(27, 1),
(28, 1),
(29, 1),
(30, 1),
(31, 1),
(32, 1),
(33, 1),
(34, 1),
(35, 1),
(35, 3),
(36, 1),
(36, 3),
(37, 1),
(37, 3),
(38, 1),
(38, 3),
(39, 1),
(40, 1),
(40, 3),
(41, 1),
(41, 3),
(42, 1),
(43, 1),
(44, 1),
(45, 1);

-- --------------------------------------------------------

--
-- Table structure for table `perfil`
--

CREATE TABLE `perfil` (
  `idPerfil` int(11) NOT NULL,
  `nome` varchar(128) NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `perfil`
--

INSERT INTO `perfil` (`idPerfil`, `nome`, `status`) VALUES
(1, 'Administrador', 1),
(2, 'Gerente', 1),
(3, 'Vendedor', 1);

-- --------------------------------------------------------

--
-- Table structure for table `produto`
--

CREATE TABLE `produto` (
  `idProduto` int(11) NOT NULL,
  `nome` varchar(128) NOT NULL,
  `descricao` varchar(128) DEFAULT NULL,
  `preco` double NOT NULL,
  `nomeArquivo` varchar(256) DEFAULT NULL,
  `caminho` varchar(256) DEFAULT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `produto`
--

INSERT INTO `produto` (`idProduto`, `nome`, `descricao`, `preco`, `nomeArquivo`, `caminho`, `status`) VALUES
(1, 'VELOX - Caloi', 'Caloi Velox V Brake Aro 29', 1549.99, 'bicicleta_1.png', 'H:\\TCC\\Standbike\\web\\imagens_produto\\bicicleta_1.png', 1),
(2, 'Banco Selim MTB', 'Selim MTB Vazado  Shunfeng', 29.9, 'selim-mtb-vazado-shunfeng.jpg', 'H:\\TCC\\Standbike\\web\\imagens_produto\\selim-mtb-vazado-shunfeng.jpg', 1),
(3, 'Quadro KSW 29', 'Quadro 29 MTB Feminino Mwza - KSW', 475, 'quadro-29-mtb-feminino-mwza-ksw.jpg', 'H:\\TCC\\Standbike\\web\\imagens_produto\\quadro-29-mtb-feminino-mwza-ksw.jpg', 1),
(4, 'Bomba Ar - Elleven', 'Bomba Ar tripé, Grande - Elleven', 119, 'bomba-ar-oficina-grande-elleven.jpg', 'H:\\TCC\\Standbike\\web\\imagens_produto\\bomba-ar-oficina-grande-elleven.jpg', 1);

-- --------------------------------------------------------

--
-- Table structure for table `usuario`
--

CREATE TABLE `usuario` (
  `idUsuario` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `login` varchar(50) NOT NULL,
  `senha` varchar(256) NOT NULL,
  `status` int(11) NOT NULL,
  `idPerfil` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `usuario`
--

INSERT INTO `usuario` (`idUsuario`, `nome`, `login`, `senha`, `status`, `idPerfil`) VALUES
(1, 'Carlos', 'adm', 'adm', 1, 1),
(2, 'Leonardo Ribeiro Barbosa', 'vendedor', 'vendedor', 1, 3),
(3, 'Igor Dias Silva', 'gerente', 'gerente', 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `venda`
--

CREATE TABLE `venda` (
  `idVenda` int(11) NOT NULL,
  `dataVenda` date NOT NULL,
  `precoTotal` decimal(8,2) NOT NULL,
  `idCliente` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `venda`
--

INSERT INTO `venda` (`idVenda`, `dataVenda`, `precoTotal`, `idCliente`, `idUsuario`) VALUES
(1, '2022-11-20', '534.80', 2, 1),
(2, '2022-11-20', '119.00', 3, 1),
(3, '2022-11-20', '1549.99', 4, 1),
(4, '2022-11-21', '2292.89', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `venda_produto`
--

CREATE TABLE `venda_produto` (
  `idVenda` int(11) NOT NULL,
  `idProduto` int(11) NOT NULL,
  `quantidade` int(11) NOT NULL,
  `valor` decimal(8,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `venda_produto`
--

INSERT INTO `venda_produto` (`idVenda`, `idProduto`, `quantidade`, `valor`) VALUES
(1, 2, 2, '29.90'),
(1, 3, 1, '475.00'),
(2, 4, 1, '119.00'),
(3, 1, 1, '1549.99'),
(4, 1, 1, '1549.99'),
(4, 2, 1, '29.90'),
(4, 3, 1, '475.00'),
(4, 4, 2, '119.00');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`idCliente`);

--
-- Indexes for table `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`idMenu`);

--
-- Indexes for table `menu_perfil`
--
ALTER TABLE `menu_perfil`
  ADD PRIMARY KEY (`idMenu`,`idPerfil`),
  ADD KEY `fk_menu_has_perfil_perfil1_idx` (`idPerfil`),
  ADD KEY `fk_menu_has_perfil_menu1_idx` (`idMenu`);

--
-- Indexes for table `perfil`
--
ALTER TABLE `perfil`
  ADD PRIMARY KEY (`idPerfil`);

--
-- Indexes for table `produto`
--
ALTER TABLE `produto`
  ADD PRIMARY KEY (`idProduto`);

--
-- Indexes for table `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`idUsuario`),
  ADD KEY `fk_usuario_perfil1_idx` (`idPerfil`);

--
-- Indexes for table `venda`
--
ALTER TABLE `venda`
  ADD PRIMARY KEY (`idVenda`),
  ADD KEY `fk_venda_usuario1_idx` (`idUsuario`),
  ADD KEY `fk_venda_cliente1_idx` (`idCliente`);

--
-- Indexes for table `venda_produto`
--
ALTER TABLE `venda_produto`
  ADD PRIMARY KEY (`idVenda`,`idProduto`),
  ADD KEY `fk_venda_has_produto_produto1_idx` (`idProduto`),
  ADD KEY `fk_venda_has_produto_venda1_idx` (`idVenda`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cliente`
--
ALTER TABLE `cliente`
  MODIFY `idCliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `menu`
--
ALTER TABLE `menu`
  MODIFY `idMenu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT for table `menu_perfil`
--
ALTER TABLE `menu_perfil`
  MODIFY `idMenu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT for table `perfil`
--
ALTER TABLE `perfil`
  MODIFY `idPerfil` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `produto`
--
ALTER TABLE `produto`
  MODIFY `idProduto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `usuario`
--
ALTER TABLE `usuario`
  MODIFY `idUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `venda`
--
ALTER TABLE `venda`
  MODIFY `idVenda` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `menu_perfil`
--
ALTER TABLE `menu_perfil`
  ADD CONSTRAINT `fk_menu_has_perfil_menu1` FOREIGN KEY (`idMenu`) REFERENCES `menu` (`idMenu`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_menu_has_perfil_perfil1` FOREIGN KEY (`idPerfil`) REFERENCES `perfil` (`idPerfil`) ON UPDATE CASCADE;

--
-- Constraints for table `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `fk_usuario_perfil1` FOREIGN KEY (`idPerfil`) REFERENCES `perfil` (`idPerfil`) ON UPDATE CASCADE;

--
-- Constraints for table `venda`
--
ALTER TABLE `venda`
  ADD CONSTRAINT `fk_venda_cliente1` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`idCliente`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_venda_usuario1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`) ON UPDATE CASCADE;

--
-- Constraints for table `venda_produto`
--
ALTER TABLE `venda_produto`
  ADD CONSTRAINT `fk_venda_has_produto_produto1` FOREIGN KEY (`idProduto`) REFERENCES `produto` (`idProduto`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_venda_has_produto_venda1` FOREIGN KEY (`idVenda`) REFERENCES `venda` (`idVenda`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
