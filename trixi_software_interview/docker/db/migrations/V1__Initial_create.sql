



CREATE TABLE municipality(
   code INTEGER PRIMARY KEY,
   name VARCHAR(60) UNIQUE NOT NULL -- Nejdelší název obce ve světě má 58 pismen => Llanfairpwllgwyngyllgogerychwyrndrobwllllantysiliogogogoch
);

CREATE TABLE municipality_part(
  code INTEGER PRIMARY KEY,
  municipality_code INTEGER,
  name VARCHAR(100) UNIQUE NOT NULL,
  CONSTRAINT fk_municipality_code
    FOREIGN KEY(municipality_code)
        REFERENCES municipality(code)
        ON DELETE CASCADE
  );


