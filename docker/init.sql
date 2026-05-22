-- ============================================================
-- SCRIPT DE CRIAÇÃO DO BANCO DE DADOS - "DE GRÃO EM GRÃO"
-- ============================================================

CREATE DATABASE IF NOT EXISTS degrao_em_grao
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE degrao_em_grao;

-- ============================================================
-- TABELA 1: ITENS
-- Guarda o cadastro geral dos produtos e sua unidade de medida base
-- ============================================================
CREATE TABLE IF NOT EXISTS Itens (
                                     gtin            VARCHAR(20)  NOT NULL,
    nome_produto    VARCHAR(255) NOT NULL,
    categoria       VARCHAR(100),
    unidade_medida  VARCHAR(50),

    PRIMARY KEY (gtin)
    );

-- ============================================================
-- TABELA 2: PESSOAS
-- Armazena usuários (doadores, receptores, voluntários) e seus limites
-- ============================================================
CREATE TABLE IF NOT EXISTS pessoas (
                                       id_pessoa       INT          NOT NULL AUTO_INCREMENT,
                                       nome            VARCHAR(255) NOT NULL,
    email           VARCHAR(255),
    telefone        VARCHAR(50),
    documento_login VARCHAR(20) UNIQUE,
    senha           VARCHAR(255),
    tipo_pessoa     ENUM('PF', 'PJ') NOT NULL,
    papel           SET('DOADOR', 'RECEPTOR', 'VOLUNTARIO'),
    nivel_acesso    ENUM('ADMIN', 'OTHER') DEFAULT 'OTHER',
    data_cadastro   DATETIME     DEFAULT CURRENT_TIMESTAMP,

    -- Configurações de atendimento e limites do receptor
    recorrencia            ENUM('MENSAL', 'QUINZENAL', 'EVENTUAL'),
    limite_mensal_valor    DECIMAL(10,2),
    limite_mensal_unidade  ENUM('KG', 'UNIDADE', 'PACOTE', 'LITRO'),
    prazo_atendimento      DATE,
    ultima_entrega         DATE,

    PRIMARY KEY (id_pessoa)
    );

-- ============================================================
-- TABELA 3: ESTOQUE
-- Controla a quantidade atual de itens disponíveis por lote e validade
-- ============================================================
CREATE TABLE IF NOT EXISTS Estoque (
                                       id_estoque      INT          NOT NULL AUTO_INCREMENT,
                                       gtin            VARCHAR(20)  NOT NULL,
    numero_lote     VARCHAR(50),
    quantidade_atual DECIMAL(10,2) NOT NULL DEFAULT 0,
    data_validade   DATE,
    data_entrada    DATETIME     DEFAULT CURRENT_TIMESTAMP,
    localizacao     VARCHAR(100),
    status          ENUM('DISPONIVEL', 'VENCIDO', 'DESTINADO') DEFAULT 'DISPONIVEL',

    PRIMARY KEY (id_estoque),
    FOREIGN KEY (gtin) REFERENCES Itens(gtin) ON DELETE RESTRICT ON UPDATE CASCADE
    );

-- ============================================================
-- TABELA 4: MOVIMENTAÇÕES
-- Guarda os movimentos de entrada e saída dos itens do estoque
-- ============================================================
CREATE TABLE IF NOT EXISTS Movimentacoes (
                                             id_movimentacao       INT     NOT NULL AUTO_INCREMENT,
                                             id_pessoa             INT     NOT NULL,
                                             id_estoque            INT     NOT NULL,
                                             tipo_movimentacao     ENUM('ENTRADA', 'SAIDA') NOT NULL,
    quantidade_movimentada DECIMAL(10,2) NOT NULL,
    data_hora_movimentacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    observacao            TEXT,

    PRIMARY KEY (id_movimentacao),
    FOREIGN KEY (id_pessoa) REFERENCES pessoas(id_pessoa) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (id_estoque) REFERENCES Estoque(id_estoque) ON DELETE RESTRICT ON UPDATE CASCADE
    );


-- ============================================================
-- REGISTROS DE TESTE (MOCK DATA)
-- ============================================================

-- Inserção de itens
INSERT INTO itens (gtin, nome_produto, categoria, unidade_medida) VALUES
                                                                      ('7891000315507', 'Arroz Longo Fino 5kg', 'Grãos', 'kg'),
                                                                      ('7891910000197', 'Feijão Preto 1kg', 'Grãos', 'kg'),
                                                                      ('7896005800027', 'Óleo de Girassol 900ml', 'Óleos', 'unidade');

-- Inserção de usuários (Administrador, Doador e Receptores com diferentes unidades de limite)
INSERT INTO pessoas (nome, email, documento_login, senha, tipo_pessoa, papel, nivel_acesso) VALUES
    ('Admin do Sistema', 'admin@ong.com', '00000000000', 'admin123', 'PF', 'VOLUNTARIO', 'ADMIN');

INSERT INTO pessoas (nome, email, telefone, documento_login, senha, tipo_pessoa, papel, recorrencia) VALUES
    ('Carlos Souza', 'carlos@email.com', '11988887777', '11122233344', 'carlos123', 'PF', 'DOADOR', 'MENSAL');

INSERT INTO pessoas (nome, email, telefone, documento_login, senha, tipo_pessoa, papel, limite_mensal_valor, limite_mensal_unidade) VALUES
    ('Maria das Dores', 'maria@email.com', '11977776666', '22233344455', 'maria123', 'PF', 'RECEPTOR', 20.00, 'KG');

INSERT INTO pessoas (nome, email, telefone, documento_login, senha, tipo_pessoa, papel, limite_mensal_valor, limite_mensal_unidade) VALUES
    ('Asilo São José', 'contato@asilo.org', '11966665555', '12345678000199', 'asilo123', 'PJ', 'RECEPTOR', 10.00, 'UNIDADE');

-- Inserção de lotes no estoque
INSERT INTO estoque (gtin, numero_lote, quantidade_atual, data_validade, localizacao, status) VALUES
                                                                                                  ('7891000315507', 'LOT-ARR-01', 100.00, '2026-12-31', 'Armário A', 'DISPONIVEL'),
                                                                                                  ('7891910000197', 'LOT-FEI-02', 45.00, '2026-08-15', 'Armário A', 'DISPONIVEL');

-- Registro de movimentação inicial
INSERT INTO movimentacoes (id_pessoa, id_estoque, tipo_movimentacao, quantidade_movimentada, observacao) VALUES
    (2, 1, 'ENTRADA', 20.00, 'Doação recebida de Carlos Souza.');
