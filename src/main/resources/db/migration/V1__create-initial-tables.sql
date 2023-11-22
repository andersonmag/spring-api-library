CREATE TABLE CATEGORIA
(
    id IDENTITY NOT NULL PRIMARY KEY,
    nome VARCHAR(200)        NOT NULL,
    link VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE LIVRO
(
    id IDENTITY NOT NULL PRIMARY KEY,
    titulo           VARCHAR(200)        NOT NULL,
    autor            VARCHAR             NOT NULL,
    editora          VARCHAR             NOT NULL,
    preco            NUMERIC             NOT NULL,
    preco_anterior   NUMERIC,
    descricao        TEXT                NOT NULL,
    idioma           VARCHAR(100)        NOT NULL,
    data_publicacao  DATE                NOT NULL,
    imagem_url       VARCHAR             NOT NULL,
    categoria_id     BIGINT              NOT NULL,
    link             VARCHAR(100) UNIQUE NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP,
    FOREIGN KEY(categoria_id) REFERENCES CATEGORIA(id)
);

CREATE TABLE USUARIO
(
    id IDENTITY NOT NULL PRIMARY KEY,
    nome   VARCHAR(200)        NOT NULL,
    email  VARCHAR(100) UNIQUE NOT NULL,
    senha  VARCHAR             NOT NULL,
    status BOOLEAN DEFAULT TRUE
);

CREATE TABLE ROLE
(
    id IDENTITY NOT NULL PRIMARY KEY,
    nome VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE USUARIO_ROLES
(
    usuario_id BIGINT NOT NULL,
    role_id    BIGINT NOT NULL,
    FOREIGN KEY(usuario_id) REFERENCES USUARIO(id),
    FOREIGN KEY(role_id) REFERENCES ROLE(id),
    CONSTRAINT pk_usuario_roles PRIMARY KEY (usuario_id, role_id)
);