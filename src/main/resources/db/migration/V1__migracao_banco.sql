

-- Table: categoria_post

-- DROP TABLE categoria_post;

CREATE TABLE IF NOT EXISTS categoria_post
(
    cat_codigo SERIAL PRIMARY KEY,
    cat_ativo boolean,
    cat_nome character varying(255) COLLATE pg_catalog."default"
   
);


 -- Table: usuario

-- DROP TABLE usuario;

CREATE TABLE IF NOT EXISTS usuario
(
    usu_codigo SERIAL PRIMARY KEY,
    usu_datacadastro date,
    usu_email character varying(255) COLLATE pg_catalog."default",
    usu_nome character varying(255) COLLATE pg_catalog."default",
    usu_senha character varying(255) COLLATE pg_catalog."default"
   
);


-- Table: post

-- DROP TABLE post;

CREATE TABLE IF NOT EXISTS post
(
    pos_codigo SERIAL PRIMARY KEY,
    pos_datacadastro timestamp without time zone,
    pos_tipo_postagem integer,
    pos_titulo character varying(500) COLLATE pg_catalog."default" NOT NULL,
    pos_visualizacoes integer,
    cat_codigo bigint,
    usu_codigo_post bigint NOT NULL,
    pos_descricao text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT fk1hviqln35348gxp3efhrx7tlg FOREIGN KEY (usu_codigo_post)
        REFERENCES usuario (usu_codigo) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fknpagv2klww1dsp53i77s4oeay FOREIGN KEY (cat_codigo)
        REFERENCES categoria_post (cat_codigo) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


-- Table: imagens_post

-- DROP TABLE imagens_post;

CREATE TABLE IF NOT EXISTS imagens_post
(
    ipo_codigo SERIAL PRIMARY KEY,
    ipo_imagem_contenttype character varying(255) COLLATE pg_catalog."default",
    ipo_nome_imagem character varying(255) COLLATE pg_catalog."default",
    pos_codigo bigint NOT NULL,
    CONSTRAINT fk9g3whs3pypif3h2vhxa4lda4t FOREIGN KEY (pos_codigo)
        REFERENCES post (pos_codigo) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

    
    
    
 -- Table: link

-- DROP TABLE link;

CREATE TABLE IF NOT EXISTS link
(
    lin_codigo SERIAL PRIMARY KEY,
    lin_descricao character varying(255) COLLATE pg_catalog."default" NOT NULL,
    pos_codigo bigint NOT NULL,
    CONSTRAINT fk5xhfi4pmhxudl37chr79x55ir FOREIGN KEY (pos_codigo)
        REFERENCES post (pos_codigo) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


 -- Table: curtida

-- DROP TABLE curtida;

CREATE TABLE IF NOT EXISTS curtida
(
    cur_codigo SERIAL PRIMARY KEY,
    cur_datacadastro timestamp without time zone,
    pos_codigo bigint,
    usu_codigo_curtida bigint NOT NULL,
    CONSTRAINT fk241r1lv0leadyumm10ar0uqjq FOREIGN KEY (pos_codigo)
        REFERENCES post (pos_codigo) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkelqxue1v341h9ntrfrg5fjy49 FOREIGN KEY (usu_codigo_curtida)
        REFERENCES usuario (usu_codigo) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
  
 -- Table: comentario

-- DROP TABLE comentario;

CREATE TABLE IF NOT EXISTS comentario
(
    com_codigo SERIAL PRIMARY KEY,
    com_datacadastro timestamp without time zone,
    cat_descricao character varying(5000) COLLATE pg_catalog."default",
    pos_codigo bigint,
    usu_codigo_comentario bigint NOT NULL,
    CONSTRAINT fkgfg28509ud0jivpayhu8rmtk4 FOREIGN KEY (usu_codigo_comentario)
        REFERENCES usuario (usu_codigo) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkmnm7ledfowx9ibvvrh1fojhxl FOREIGN KEY (pos_codigo)
        REFERENCES post (pos_codigo) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

    
    
INSERT INTO categoria_post(cat_ativo, cat_nome) VALUES ( TRUE, 'PHP');
INSERT INTO categoria_post(cat_ativo, cat_nome) VALUES ( TRUE, 'JAVA');
INSERT INTO categoria_post(cat_ativo, cat_nome) VALUES ( TRUE, 'SPRING');
INSERT INTO categoria_post(cat_ativo, cat_nome) VALUES ( TRUE, 'SQLSERVER');
    
  