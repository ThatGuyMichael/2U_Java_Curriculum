CREATE DATABASE IF NOT EXISTS transaction;
DROP TABLE IF EXISTS transaction.transactions;
CREATE TABLE transaction.transactions (
  `id` int NOT NULL AUTO_INCREMENT,
  `sender` varchar(100) NOT NULL,
  `recipient` varchar(100) NOT NULL,
  `transaction_value` varchar(50) NOT NULL,
  `soft_delete` boolean NOT NULL default false,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8