
DROP DATABASE IF EXISTS dbprojeto;


CREATE DATABASE dbprojeto;
USE dbprojeto;


CREATE TABLE produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    custo_total DECIMAL(10,2) DEFAULT 0.00,
    quantidade_total INT DEFAULT 0
);


CREATE TABLE componente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    custo DECIMAL(10,2) NOT NULL
);


CREATE TABLE formula (
    id INT AUTO_INCREMENT PRIMARY KEY,
    produto_id INT NOT NULL,
    componente_id INT NOT NULL,
    quantidade INT NOT NULL,
    FOREIGN KEY (produto_id) REFERENCES produto(id) ON DELETE CASCADE,
    FOREIGN KEY (componente_id) REFERENCES componente(id) ON DELETE CASCADE
);


INSERT INTO produto (nome, custo_total, quantidade_total) VALUES
('Caneta', 6.00, 100),
('Lápis', 3.50, 200),
('Caderno', 15.00, 50);


INSERT INTO componente (nome, custo) VALUES
('Tinta', 2.00),
('Tubo', 2.00),
('Tampa', 2.00),
('Grafite', 1.50),
('Corpo do lápis', 2.00),
('Capa dura', 5.00),
('Folhas', 8.00);


INSERT INTO formula (produto_id, componente_id, quantidade) VALUES
((SELECT id FROM produto WHERE nome='Caneta'), (SELECT id FROM componente WHERE nome='Tinta'), 1),
((SELECT id FROM produto WHERE nome='Caneta'), (SELECT id FROM componente WHERE nome='Tubo'), 1),
((SELECT id FROM produto WHERE nome='Caneta'), (SELECT id FROM componente WHERE nome='Tampa'), 1);


INSERT INTO formula (produto_id, componente_id, quantidade) VALUES
((SELECT id FROM produto WHERE nome='Lápis'), (SELECT id FROM componente WHERE nome='Grafite'), 1),
((SELECT id FROM produto WHERE nome='Lápis'), (SELECT id FROM componente WHERE nome='Corpo do lápis'), 1);


INSERT INTO formula (produto_id, componente_id, quantidade) VALUES
((SELECT id FROM produto WHERE nome='Caderno'), (SELECT id FROM componente WHERE nome='Capa dura'), 1),
((SELECT id FROM produto WHERE nome='Caderno'), (SELECT id FROM componente WHERE nome='Folhas'), 1);


SELECT * FROM produto;
SELECT * FROM componente;
SELECT * FROM formula;





