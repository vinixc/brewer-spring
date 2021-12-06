CREATE SEQUENCE estilo_seq START  1 INCREMENT 1 MINVALUE 1;
CREATE TABLE estilo(
    id bigint primary key default nextval('estilo_seq'),
    nome varchar(50) not null
);

CREATE SEQUENCE cerveja_seq START  1 INCREMENT 1 MINVALUE 1;
CREATE TABLE cerveja(
  id bigint primary key default nextval('cerveja_seq'),
  sku varchar(50) not null,
  nome varchar(80) not null,
  descricao text not null,
  valor numeric(10,2) not null,
  teor_alcoolico numeric(10,2) not null,
  comissao numeric(10,2) not null,
  sabor varchar(50) not null,
  origem varchar(50) not null,
  id_estilo bigint not null,
  constraint fk_estilo_cerveja foreign key (id_estilo) references estilo(id)
);


INSERT INTO estilo (nome) VALUES ('Amber Lager');
INSERT INTO estilo (nome) VALUES ('Dark Lager');
INSERT INTO estilo (nome) VALUES ('Pale Lager');
INSERT INTO estilo (nome) VALUES ('Pilsner');