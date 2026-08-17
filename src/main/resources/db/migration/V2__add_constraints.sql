

ALTER TABLE presence
    ADD CONSTRAINT fk_presence_eleve
        FOREIGN KEY (eleve_id)
            REFERENCES user_entity(id)
            ON DELETE CASCADE;




ALTER TABLE progression
    ADD CONSTRAINT fk_progression_eleve
        FOREIGN KEY (eleve_id)
            REFERENCES user_entity(id)
            ON DELETE CASCADE;



ALTER TABLE progression
    ADD CONSTRAINT fk_progression_enseignant
        FOREIGN KEY (enseignant_id)
            REFERENCES user_entity(id)
            ON DELETE CASCADE;



ALTER TABLE participation
    ADD CONSTRAINT fk_participation_eleve
        FOREIGN KEY (eleve_id)
            REFERENCES user_entity(id)
            ON DELETE CASCADE;




ALTER TABLE participation
    ADD CONSTRAINT fk_participation_enseignant
        FOREIGN KEY (enseignant_id)
            REFERENCES user_entity(id)
            ON DELETE CASCADE;




ALTER TABLE participation
    ADD CONSTRAINT fk_participation_concour
        FOREIGN KEY (concour_id)
            REFERENCES concour(id)
            ON DELETE CASCADE;