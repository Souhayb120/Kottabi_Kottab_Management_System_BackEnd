CREATE TABLE user_entity
(
    id             BIGINT      NOT NULL AUTO_INCREMENT,
    user_name      VARCHAR(255),
    nom            VARCHAR(255),
    prenom         VARCHAR(255),
    email         VARCHAR(255) unique,
    tel            VARCHAR(50),
    password       VARCHAR(255),
    role           INT,

    DTYPE          VARCHAR(31) NOT NULL,

    date_Naissance DATE,

    specialite     VARCHAR(255),
    description    VARCHAR(1000),

    PRIMARY KEY (id),

    CONSTRAINT uk_user_entity_username
        UNIQUE (user_name)
);



CREATE TABLE concour
(
    id            BIGINT NOT NULL AUTO_INCREMENT,
    nom           VARCHAR(255),
    description   VARCHAR(1000),
    date_creation DATE,
    niveau_hifz   INT,

    PRIMARY KEY (id)
);



CREATE TABLE progression
(
    id            BIGINT NOT NULL AUTO_INCREMENT,
    sourat        VARCHAR(255),
    verset_debut  INT    NOT NULL,
    verset_fin    INT    NOT NULL,

    eleve_id      BIGINT,

    enseignant_id BIGINT,

    PRIMARY KEY (id)
);



CREATE TABLE presence
(
    id       BIGINT NOT NULL AUTO_INCREMENT,
    date     DATE,
    statut   INT,

    eleve_id BIGINT,

    PRIMARY KEY (id)
);



CREATE TABLE participation
(
    id            BIGINT NOT NULL AUTO_INCREMENT,
    note DOUBLE,
    commentaire   VARCHAR(1000),
    classement    INT    NOT NULL,

    eleve_id      BIGINT,

    enseignant_id BIGINT,

    concour_id    BIGINT,

    PRIMARY KEY (id)
);