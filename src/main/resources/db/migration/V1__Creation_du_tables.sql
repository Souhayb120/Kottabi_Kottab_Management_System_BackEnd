-- =========================================================
-- USER ENTITY
-- =========================================================

CREATE TABLE user_entity (
                             id BIGINT NOT NULL AUTO_INCREMENT,
                             user_name VARCHAR(255),
                             nom VARCHAR(255),
                             prenom VARCHAR(255),
                             tel VARCHAR(50),
                             password VARCHAR(255),
                             role INT,

    -- JPA SINGLE_TABLE inheritance discriminator
                             DTYPE VARCHAR(31) NOT NULL,

    -- Fields specific to Eleve
                             date_lissance DATE,

    -- Fields specific to Enseignant
                             specialite VARCHAR(255),
                             description VARCHAR(1000),

                             PRIMARY KEY (id),

                             CONSTRAINT uk_user_entity_username
                                 UNIQUE (user_name)
);


-- =========================================================
-- CONCOUR
-- =========================================================

CREATE TABLE concour (
                         id BIGINT NOT NULL AUTO_INCREMENT,
                         nom VARCHAR(255),
                         description VARCHAR(1000),
                         date_creation DATE,
                         niveau_hifz INT,

                         PRIMARY KEY (id)
);


-- =========================================================
-- PROGRESSION
-- =========================================================

CREATE TABLE progression (
                             id BIGINT NOT NULL AUTO_INCREMENT,
                             sourat VARCHAR(255),
                             verset_debut INT NOT NULL,
                             verset_fin INT NOT NULL,

    -- Eleve -> Progression
                             eleve_id BIGINT,

    -- Enseignant -> Progression
                             enseignant_id BIGINT,

                             PRIMARY KEY (id)
);


-- =========================================================
-- PRESENCE
-- =========================================================

CREATE TABLE presence (
                          id BIGINT NOT NULL AUTO_INCREMENT,
                          date DATE,
                          statut INT,

    -- Eleve -> Presence
                          eleve_id BIGINT,

                          PRIMARY KEY (id)
);


-- =========================================================
-- PARTICIPATION
-- =========================================================

CREATE TABLE participation (
                               id BIGINT NOT NULL AUTO_INCREMENT,
                               note DOUBLE,
                               commentaire VARCHAR(1000),
                               classement INT NOT NULL,

    -- Eleve -> Participation
                               eleve_id BIGINT,

    -- Enseignant -> Participation
                               enseignant_id BIGINT,

    -- Concour -> Participation
                               concour_id BIGINT,

                               PRIMARY KEY (id)
);