-- MySQL Script generated by MySQL Workbench
-- Thu Nov 10 09:13:37 2022
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema bdstandbike
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema bdstandbike
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `bdstandbike` DEFAULT CHARACTER SET utf8 ;
USE `bdstandbike` ;

-- -----------------------------------------------------
-- Table `bdstandbike`.`cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdstandbike`.`cliente` (
  `idCliente` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(128) NOT NULL,
  `cpf` VARCHAR(11) NOT NULL,
  `email` VARCHAR(128) NOT NULL,
  `endereco` VARCHAR(256) NOT NULL,
  `telefone` VARCHAR(11) NOT NULL,
  `dataCadastro` DATE NOT NULL,
  `status` INT NOT NULL,
  PRIMARY KEY (`idCliente`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bdstandbike`.`perfil`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdstandbike`.`perfil` (
  `idPerfil` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(128) NOT NULL,
  `dataCadastro` DATE NOT NULL,
  `status` INT NOT NULL,
  PRIMARY KEY (`idPerfil`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bdstandbike`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdstandbike`.`usuario` (
  `idUsuario` INT NOT NULL AUTO_INCREMENT,
  `idPerfil` INT NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `login` VARCHAR(50) NOT NULL,
  `senha` VARCHAR(128) NOT NULL,
  `status` INT NOT NULL,
  PRIMARY KEY (`idUsuario`),
  INDEX `fk_usuario_perfil1_idx` (`idPerfil` ASC) ,
  CONSTRAINT `fk_usuario_perfil1`
    FOREIGN KEY (`idPerfil`)
    REFERENCES `bdstandbike`.`perfil` (`idPerfil`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bdstandbike`.`venda`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdstandbike`.`venda` (
  `idVenda` INT NOT NULL AUTO_INCREMENT,
  `carrinho_idCarrinho` INT NOT NULL,
  `status` INT NOT NULL,
  `dataVenda` DATE NOT NULL,
  `precoTotal` VARCHAR(45) NOT NULL,
  `idUsuario` INT NOT NULL,
  `idCliente` INT NOT NULL,
  PRIMARY KEY (`idVenda`),
  INDEX `fk_venda_usuario1_idx` (`idUsuario` ASC) ,
  INDEX `fk_venda_cliente1_idx` (`idCliente` ASC) ,
  CONSTRAINT `fk_venda_usuario1`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `bdstandbike`.`usuario` (`idUsuario`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_venda_cliente1`
    FOREIGN KEY (`idCliente`)
    REFERENCES `bdstandbike`.`cliente` (`idCliente`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bdstandbike`.`produto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdstandbike`.`produto` (
  `idProduto` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(128) NOT NULL,
  `descricao` VARCHAR(128) NULL,
  `estoque` INT NOT NULL,
  `preco` DOUBLE NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idProduto`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bdstandbike`.`menu`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdstandbike`.`menu` (
  `idMenu` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(128) NOT NULL,
  `link` VARCHAR(256) NOT NULL,
  `icone` VARCHAR(256) NULL,
  `status` INT NOT NULL,
  `exibir` INT NOT NULL,
  PRIMARY KEY (`idMenu`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bdstandbike`.`menu_perfil`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdstandbike`.`menu_perfil` (
  `idMenu` INT NOT NULL AUTO_INCREMENT,
  `idPerfil` INT NOT NULL,
  `status` INT NOT NULL,
  PRIMARY KEY (`idMenu`, `idPerfil`),
  INDEX `fk_menu_has_perfil_perfil1_idx` (`idPerfil` ASC) ,
  INDEX `fk_menu_has_perfil_menu1_idx` (`idMenu` ASC) ,
  CONSTRAINT `fk_menu_has_perfil_menu1`
    FOREIGN KEY (`idMenu`)
    REFERENCES `bdstandbike`.`menu` (`idMenu`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_menu_has_perfil_perfil1`
    FOREIGN KEY (`idPerfil`)
    REFERENCES `bdstandbike`.`perfil` (`idPerfil`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bdstandbike`.`venda_produto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdstandbike`.`venda_produto` (
  `idVenda` INT NOT NULL,
  `idProduto` INT NOT NULL,
  `quantidade` INT NOT NULL,
  `valor` DOUBLE NULL,
  PRIMARY KEY (`idVenda`, `idProduto`),
  INDEX `fk_venda_has_produto_produto1_idx` (`idProduto` ASC) ,
  INDEX `fk_venda_has_produto_venda1_idx` (`idVenda` ASC) ,
  CONSTRAINT `fk_venda_has_produto_venda1`
    FOREIGN KEY (`idVenda`)
    REFERENCES `bdstandbike`.`venda` (`idVenda`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_venda_has_produto_produto1`
    FOREIGN KEY (`idProduto`)
    REFERENCES `bdstandbike`.`produto` (`idProduto`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
