CREATE TABLE colaborador (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    matricula VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL
);

INSERT INTO colaborador (nome,matricula,senha) VALUES ('Aluno Fiap','123456789', 'fiap123');