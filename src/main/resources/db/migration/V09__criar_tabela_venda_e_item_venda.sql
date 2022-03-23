BEGIN;

CREATE SEQUENCE venda_seq START 1 MINVALUE 1 INCREMENT 1;
CREATE TABLE venda(
  id BIGINT PRIMARY KEY DEFAULT nextval('venda_seq'),
  data_criacao TIMESTAMP NOT NULL,
  valor_frete NUMERIC(10,2),
  valor_desconto NUMERIC(10,2),
  valor_total NUMERIC(10,2) NOT NULL,
  status VARCHAR(30) NOT NULL,
  observacao VARCHAR(200),
  data_entrega TIMESTAMP,
  id_cliente BIGINT NOT NULL,
  id_usuario BIGINT NOT NULL,
  CONSTRAINT fk_venda_clienteid FOREIGN KEY(id_cliente) REFERENCES cliente(id),
  CONSTRAINT fk_venda_usuarioid FOREIGN KEY(id_usuario) REFERENCES usuario(id)
);

CREATE SEQUENCE item_venda_seq START 1 MINVALUE 1 INCREMENT 1;
CREATE TABLE item_venda(
  id BIGINT PRIMARY KEY DEFAULT nextval('item_venda_seq'),
  quantidade INTEGER NOT NULL,
  valor_unitario NUMERIC(10,2) NOT NULL,
  id_cerveja BIGINT NOT NULL,
  id_venda BIGINT NOT NULL,
  CONSTRAINT fk_itemvenda_cervejaid FOREIGN KEY (id_cerveja) REFERENCES cerveja(id),
  CONSTRAINT fk_itemvenda_vendaid FOREIGN KEY (id_venda) REFERENCES venda(id)
);

COMMIT;