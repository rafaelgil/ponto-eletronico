CREATE TABLE registro_ponto (
    id SERIAL PRIMARY KEY,
    colaborador_id INTEGER NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    hora TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    CONSTRAINT fk_colaborador
        FOREIGN KEY (colaborador_id)
        REFERENCES colaborador(id)
        ON DELETE CASCADE
);