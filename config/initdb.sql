DROP TABLE IF EXISTS contact;
DROP TABLE IF EXISTS resume;

CREATE TABLE IF NOT EXISTS resume
(
    uuid      character(36) NOT NULL,
    full_name text          NOT NULL,
    CONSTRAINT resume_pk PRIMARY KEY (uuid)
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS resume
    OWNER to resumeapp;

CREATE TABLE IF NOT EXISTS contact
(
    id          integer NOT NULL GENERATED ALWAYS AS IDENTITY,
    type        text    NOT NULL,
    value       text    NOT NULL,
    resume_uuid character(36),
    CONSTRAINT contact_pk PRIMARY KEY (id),
    CONSTRAINT contact_resume_uuid_fk FOREIGN KEY (resume_uuid)
        REFERENCES resume (uuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS contact
    OWNER to resumeapp;

CREATE UNIQUE INDEX IF NOT EXISTS contact_uuid_type_index
    ON contact USING btree
        (resume_uuid ASC NULLS LAST, id ASC NULLS LAST)
    TABLESPACE pg_default;





