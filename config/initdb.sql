DROP TABLE IF EXISTS contact;
DROP TABLE IF EXISTS section;
DROP TABLE IF EXISTS resume;

CREATE TABLE IF NOT EXISTS resume
(
    uuid      character(36) NOT NULL,
    full_name text          NOT NULL,
    CONSTRAINT resume_pk PRIMARY KEY (uuid)
)
    TABLESPACE pg_default;

CREATE TABLE IF NOT EXISTS contact
(
    id          integer NOT NULL GENERATED ALWAYS AS IDENTITY,
    type        text    NOT NULL,
    value       text    NOT NULL,
    resume_uuid character(36),
    CONSTRAINT contact_pk PRIMARY KEY (id),
    CONSTRAINT resume_uuid_contact_type UNIQUE (resume_uuid, type),
    CONSTRAINT contact_resume_uuid_fk FOREIGN KEY (resume_uuid)
        REFERENCES resume (uuid) MATCH FULL
        ON UPDATE NO ACTION
        ON DELETE CASCADE
)
    TABLESPACE pg_default;

CREATE UNIQUE INDEX IF NOT EXISTS contact_resume_index
    ON contact USING btree
        (resume_uuid ASC NULLS LAST, id ASC NULLS LAST)
    TABLESPACE pg_default;

CREATE TABLE IF NOT EXISTS section
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    type text  NOT NULL,
    value text NOT NULL,
    resume_uuid character(36) NOT NULL,
    CONSTRAINT section_pk PRIMARY KEY (id),
    CONSTRAINT resume_uuid_section_type UNIQUE (resume_uuid, type),
    CONSTRAINT section_resume_fk FOREIGN KEY (resume_uuid)
        REFERENCES resume (uuid) MATCH FULL
        ON UPDATE NO ACTION
        ON DELETE CASCADE
)
    TABLESPACE pg_default;

CREATE UNIQUE INDEX IF NOT EXISTS section_resume_index
    ON section USING btree
        (resume_uuid ASC NULLS LAST, id ASC NULLS LAST)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS resume
    OWNER to resumeapp;
ALTER TABLE IF EXISTS contact
    OWNER to resumeapp;
ALTER TABLE IF EXISTS section
    OWNER to resumeapp;
