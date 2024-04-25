CREATE TABLE IF NOT EXISTS public.clients
(
    shared_key character varying(255) COLLATE pg_catalog."default" NOT NULL,
    bussines_id character varying(255) COLLATE pg_catalog."default",
    date_added date,
    email character varying(255) COLLATE pg_catalog."default",
    phone bigint NOT NULL,
    CONSTRAINT clients_pkey PRIMARY KEY (shared_key),
    CONSTRAINT uk_7fvtquc16ltuuakean6y7xhpp UNIQUE (bussines_id),
    CONSTRAINT uk_srv16ica2c1csub334bxjjb59 UNIQUE (email)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.clients
    OWNER to postgres;