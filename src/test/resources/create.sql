CREATE domain IF NOT EXISTS json AS other;
CREATE TABLE players (
    id integer PRIMARY KEY,
    userid integer,
    name text,
    inventory json,
    "position" point,
    stats json,
);