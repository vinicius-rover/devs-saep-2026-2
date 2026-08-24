
CREATE DATABASE IF NOT EXISTS sistema_produtos;
USE sistema_produtos;


SET FOREIGN_KEY_CHECKS = 0;


DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(150) NOT NULL,
  `email` varchar(255) NOT NULL,
  `senha` varchar(255) DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  `data_criacao` datetime DEFAULT NULL,
  `data_atualizacao` datetime DEFAULT NULL,
  `excluido_em` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `usuarios` VALUES 
(1,'Administrador Sistema','admin@sistema.com','$2y$10$exemploHashAdmin123','ativo',NULL,NULL,NULL),
(2,'Maria Silva','maria.silva@email.com','$2y$10$exemploHashMaria456','ativo',NULL,NULL,NULL),
(3,'Carlos Oliveira','carlos.oliveira@email.com','$2y$10$exemploHashCarlos012','bloqueado',NULL,NULL,NULL);


DROP TABLE IF EXISTS `categorias`;
CREATE TABLE `categorias` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `descricao` text DEFAULT NULL,
  `ativo` tinyint(1) NOT NULL,
  `data_criacao` datetime DEFAULT NULL,
  `data_atualizacao` datetime DEFAULT NULL,
  `excluido_em` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `categorias` VALUES 
(1,'Eletrônicos','Produtos eletrônicos e acessórios',1,NULL,NULL,NULL),
(2,'Roupas','Vestuário masculino e feminino',1,NULL,NULL,NULL),
(3,'Alimentos','Produtos alimentícios em geral',1,NULL,NULL,NULL);


DROP TABLE IF EXISTS `produtos`;
CREATE TABLE `produtos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(200) NOT NULL,
  `preco` decimal(10,0) NOT NULL,
  `estoque` int(11) NOT NULL,
  `categoria_id` int(11) DEFAULT NULL,
  `descricao` text DEFAULT NULL,
  `ativo` tinyint(1) NOT NULL,
  `usuario_id` int(11) DEFAULT NULL,
  `data_criacao` datetime DEFAULT NULL,
  `data_atualizacao` datetime DEFAULT NULL,
  `excluido_em` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nome` (`nome`),
  KEY `categoria_id` (`categoria_id`),
  KEY `usuario_id` (`usuario_id`),
  CONSTRAINT `produtos_ibfk_1` FOREIGN KEY (`categoria_id`) REFERENCES `categorias` (`id`),
  CONSTRAINT `produtos_ibfk_2` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `produtos` VALUES 
(7,'Smartphone XYZ Pro',1900,25,1,'Smartphone com 128GB e câmera tripla',1,1,NULL,NULL,NULL),
(8,'Camiseta Básica Algodão',50,150,2,'Camiseta 100% algodão, várias cores',1,2,NULL,NULL,NULL),
(9,'Arroz Tipo 1 5kg',25,200,3,'Arroz branco tipo 1 pacote 5kg',1,1,NULL,NULL,NULL);


DROP TABLE IF EXISTS `movimentacao`;
CREATE TABLE `movimentacao` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipo_movimentacao` varchar(50) NOT NULL,
  `usuario_id` int(11) NOT NULL,
  `produto_id` int(11) NOT NULL,
  `data_criacao` datetime DEFAULT NULL,
  `excluido_em` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `usuario_id` (`usuario_id`),
  KEY `produto_id` (`produto_id`),
  CONSTRAINT `movimentacao_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`),
  CONSTRAINT `movimentacao_ibfk_2` FOREIGN KEY (`produto_id`) REFERENCES `produtos` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `movimentacao` VALUES 
(13,'entrada',1,7,NULL,NULL),
(14,'saida',2,8,NULL,NULL),
(15,'ajuste',1,9,NULL,NULL);


SET FOREIGN_KEY_CHECKS = 1;
