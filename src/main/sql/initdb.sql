-- Table: public.book


CREATE TABLE IF NOT EXISTS public.book
(
    id bigint NOT NULL ,
    title text COLLATE pg_catalog."default",
    publisher_id bigint,
    CONSTRAINT book_pkey PRIMARY KEY (id),
    CONSTRAINT fk_book_publisher FOREIGN KEY (publisher_id)
        REFERENCES public.publisher (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

-- Table: public.author

CREATE TABLE IF NOT EXISTS public.author
(
    id bigint NOT NULL,
    name text COLLATE pg_catalog."default",
    CONSTRAINT author_pkey PRIMARY KEY (id)
);

-- Table: public.book_author


CREATE TABLE IF NOT EXISTS public.book_author
(
    book_id bigint NOT NULL,
    author_id bigint NOT NULL,
    CONSTRAINT book_author_pkey PRIMARY KEY (book_id, author_id),
    CONSTRAINT author_fkey FOREIGN KEY (author_id)
        REFERENCES public.author (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE NO ACTION,
    CONSTRAINT book_fkey FOREIGN KEY (book_id)
        REFERENCES public.book (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS public.publisher
(
    id bigint NOT NULL,
    name text COLLATE pg_catalog."default",
    address text COLLATE pg_catalog."default",
    CONSTRAINT publisher_pkey PRIMARY KEY (id)
);

INSERT INTO publisher (id,name,address) VALUES
    (1,'Publisher one','add1'),
    (2,'Publisher two','add2'),
	(3,'Publisher three','add3'),
	(4,'Publisher four','add4'),
	(5,'Publisher five','add5');

INSERT INTO book (id,title,publisher_id) VALUES
    	(1,'Book one',3),
    	(2,'Book two',2),
	(3,'Book three',3),
	(4,'Book four',2),
	(5,'Book five',1);

INSERT INTO author (id,name) VALUES
    (1,'Author one'),
    (2,'Author two'),
	(3,'Author three'),
	(4,'Author four'),
	(5,'Author five');

INSERT INTO public.book_author(book_id, author_id) VALUES
	(1, 3),
	(1, 4),
	(3, 4),
	(1, 5),
	(1, 2);
