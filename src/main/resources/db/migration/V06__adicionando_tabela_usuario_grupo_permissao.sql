BEGIN;

CREATE SEQUENCE usuario_seq START 1 MINVALUE 1 INCREMENT 1;
CREATE TABLE usuario(
  id bigint primary key default nextval('usuario_seq'),
  nome varchar(50) not null,
  email varchar(80) not null,
  senha varchar not null,
  ativo boolean default true,
  data_nascimento date
);

CREATE SEQUENCE grupo_seq START 1 MINVALUE 1 INCREMENT 1;
CREATE TABLE grupo(
    id bigint primary key default nextval('grupo_seq'),
    nome varchar(50) not null
);

CREATE SEQUENCE permissao_seq START 1 MINVALUE 1 INCREMENT 1;
CREATE TABLE permissao(
    id bigint primary key default nextval('permissao_seq'),
    nome varchar(50) not null
);

CREATE TABLE usuario_grupo(
    usuario_id bigint not null,
    grupo_id bigint not null,
    constraint pk_usuario_grupo primary key (usuario_id,grupo_id),
    constraint fk_usuario_grupo_x_usuarioid foreign key (usuario_id) references usuario(id),
    constraint fk_usuario_grupo_x_grupoid foreign key (grupo_id) references grupo(id)
);

CREATE TABLE grupo_permissao(
  grupo_id bigint not null,
  permissao_id bigint not null,
  constraint pk_grupo_permissao primary key (grupo_id,permissao_id),
  constraint fk_grupo_permissao_x_grupoid foreign key (grupo_id) references grupo(id),
  constraint fk_grupo_permissao_x_permissaoid foreign key (permissao_id) references permissao(id)
);

COMMIT;