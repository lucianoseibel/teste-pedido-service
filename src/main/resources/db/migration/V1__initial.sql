USE pedidosdb;

CREATE TABLE clientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

CREATE TABLE pedidos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_controle VARCHAR(50) NOT NULL UNIQUE,
    data_cadastro DATE,
    nome_produto VARCHAR(255) NOT NULL,
    valor DECIMAL(10, 2) NOT NULL,
    quantidade INT NOT NULL DEFAULT 1,
    total DECIMAL(10, 2) AS (quantidade * valor) STORED,
    cliente_id BIGINT,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);


insert into clientes (nome) values ('Cliente 1');
insert into clientes (nome) values ('Cliente 2');
insert into clientes (nome) values ('Cliente 3');
insert into clientes (nome) values ('Cliente 4');
insert into clientes (nome) values ('Cliente 5');
insert into clientes (nome) values ('Cliente 6');
insert into clientes (nome) values ('Cliente 7');
insert into clientes (nome) values ('Cliente 8');
insert into clientes (nome) values ('Cliente 9');
insert into clientes (nome) values ('Cliente 10');