CREATE TABLE IF NOT EXISTS catalogs(
    id serial primary key,
    name varchar(255),
    parent_id integer references catalogs(id)
);

CREATE TABLE IF NOT EXISTS file(
    id serial primary key,
    name varchar(255),
    size integer,
    parent_id integer references catalogs(id)
);
