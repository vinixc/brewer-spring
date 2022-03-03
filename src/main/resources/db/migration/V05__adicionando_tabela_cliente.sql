BEGIN;

CREATE SEQUENCE cliente_seq START 1 MINVALUE 1 INCREMENT 1;

CREATE TABLE cliente(
    id bigint primary key default nextval('cliente_seq'),
    nome varchar(80) not null,
    tipo_pessoa VARCHAR(15) not null,
    cpf_cnpj VARCHAR(30),
    telefone VARCHAR(20),
    email VARCHAR(50) not null,
    logradouro varchar(50),
    numero varchar(15),
    complemento varchar(20),
    cep varchar(15),
    cidade_id bigint,
    constraint fk_cliente_cidadeid FOREIGN KEY (cidade_id) REFERENCES cidade(id)
);

COMMIT;