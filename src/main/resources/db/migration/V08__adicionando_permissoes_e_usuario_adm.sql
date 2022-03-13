BEGIN;

INSERT INTO usuario (nome, email, senha, data_nascimento) VALUES ('admin','admin@brewer.com.br','$2a$10$/bTuBmNlUmMYhmaWgC2Y1OOI5NtAG40PAds8HWXZJWRUJsEV7UsnO', '2000-01-02');

INSERT INTO permissao(nome) VALUES ('CADASTRAR_CIDADE');
INSERT INTO permissao(nome) VALUES ('CADASTRAR_USUARIO');

INSERT INTO grupo_permissao values (1,1);
INSERT INTO grupo_permissao values (1,2);

INSERT INTO usuario_grupo VALUES (1,1);

COMMIT;
