CREATE TABLE public.students (
	id SERIAL PRIMARY KEY,
    --id character varying(15) NOT NULL,
    nom character varying(255),
    prenom character varying(255),
	mail character varying(120),
    formation character varying(120)
);

CREATE TABLE public.clients (
	cle varchar(255) NOT NULL,
	datedeb timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	datefin timestamp NOT NULL
);

ALTER TABLE public.clients ADD CONSTRAINT clients_pk PRIMARY KEY (cle);

INSERT INTO public.clients (cle, datedeb, datefin) VALUES('CAFEBABE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + interval '30' day);

