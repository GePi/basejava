CREATE USER resumeapp PASSWORD 'IowJFIfFwYalsz0B5HDn';
CREATE DATABASE resumedb OWNER resumeapp ENCODING 'UTF8';

\c resumedb;

DROP TABLE IF EXISTS resumedb.public.contact;
DROP TABLE IF EXISTS resumedb.public.section;
DROP TABLE IF EXISTS resumedb.public.resume;

CREATE TABLE IF NOT EXISTS resumedb.public.resume
(
    uuid      character(36) NOT NULL,
    full_name text          NOT NULL,
    CONSTRAINT resume_pk PRIMARY KEY (uuid)
)
    TABLESPACE pg_default;

CREATE TABLE IF NOT EXISTS resumedb.public.contact
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
    ON resumedb.public.contact USING btree
        (resume_uuid ASC NULLS LAST, id ASC NULLS LAST)
    TABLESPACE pg_default;

CREATE TABLE IF NOT EXISTS resumedb.public.section
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
    ON resumedb.public.section USING btree
        (resume_uuid ASC NULLS LAST, id ASC NULLS LAST)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS resumedb.public.resume
    OWNER to resumeapp;
ALTER TABLE IF EXISTS resumedb.public.contact
    OWNER to resumeapp;
ALTER TABLE IF EXISTS resumedb.public.section
    OWNER to resumeapp;
