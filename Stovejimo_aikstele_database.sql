CREATE TABLE auja9067.Tipas (
    Pavadinimas VARCHAR(100) NOT NULL,
    Tarifas     DECIMAL(10, 4) NOT NULL,
    PRIMARY KEY (Pavadinimas)
);

CREATE TABLE auja9067.Stovejimo_vieta (
    Nr          INTEGER NOT NULL,
    Tipas       VARCHAR(100) DEFAULT 'Standartinis' NOT NULL,
    PRIMARY KEY (Nr),
    CONSTRAINT ITipa FOREIGN KEY (Tipas) REFERENCES auja9067.Tipas ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE auja9067.Rezervacija (
    Nr              INTEGER NOT NULL 
                    GENERATED ALWAYS AS IDENTITY
                    (START WITH 1 INCREMENT BY 1) ,
    Kaina           DECIMAL(10, 5) NOT NULL CONSTRAINT MaxMinKaina
                                            CHECK (Kaina > 0 AND Kaina < 100000),
    Pradzia         TIMESTAMP NOT NULL,
    Pabaiga         TIMESTAMP NOT NULL CONSTRAINT VeliauUzPradzia
                                            CHECK (Pabaiga > Pradzia),
    Vieta           INTEGER  NOT NULL,
    PRIMARY KEY (Nr),
    CONSTRAINT IVieta FOREIGN KEY (Vieta) REFERENCES auja9067.Stovejimo_vieta ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE auja9067.Vairuotojas (
    Vardas         VARCHAR(100) NOT NULL,
    Pavarde        VARCHAR(100) NOT NULL,
    Telefonas      VARCHAR(20) NOT NULL,
    Pastas         VARCHAR(50) CONSTRAINT GalimasPastas
                                CHECK(Pastas LIKE '%_@%_.%_'),
    AK             CHAR(11)     NOT NULL CONSTRAINT ValidusAK
                                            CHECK(
                                                CASE
                                                WHEN
                                                    MOD(
                                                        CAST(SUBSTR(AK, 1, 1) AS INTEGER) * 1 +
                                                        CAST(SUBSTR(AK, 2, 1) AS INTEGER) * 2 +
                                                        CAST(SUBSTR(AK, 3, 1) AS INTEGER) * 3 +
                                                        CAST(SUBSTR(AK, 4, 1) AS INTEGER) * 4 +
                                                        CAST(SUBSTR(AK, 5, 1) AS INTEGER) * 5 +
                                                        CAST(SUBSTR(AK, 6, 1) AS INTEGER) * 6 +
                                                        CAST(SUBSTR(AK, 7, 1) AS INTEGER) * 7 +
                                                        CAST(SUBSTR(AK, 8, 1) AS INTEGER) * 8 +
                                                        CAST(SUBSTR(AK, 9, 1) AS INTEGER) * 9 +
                                                        CAST(SUBSTR(AK, 10, 1) AS INTEGER) * 1,
                                                        11
                                                    ) <> 10
                                                THEN 
                                                    MOD(
                                                        CAST(SUBSTR(AK, 1, 1) AS INTEGER) * 1 +
                                                        CAST(SUBSTR(AK, 2, 1) AS INTEGER) * 2 +
                                                        CAST(SUBSTR(AK, 3, 1) AS INTEGER) * 3 +
                                                        CAST(SUBSTR(AK, 4, 1) AS INTEGER) * 4 +
                                                        CAST(SUBSTR(AK, 5, 1) AS INTEGER) * 5 +
                                                        CAST(SUBSTR(AK, 6, 1) AS INTEGER) * 6 +
                                                        CAST(SUBSTR(AK, 7, 1) AS INTEGER) * 7 +
                                                        CAST(SUBSTR(AK, 8, 1) AS INTEGER) * 8 +
                                                        CAST(SUBSTR(AK, 9, 1) AS INTEGER) * 9 +
                                                        CAST(SUBSTR(AK, 10, 1) AS INTEGER) * 1,
                                                        11
                                                    ) 
                                                    = CAST(SUBSTR(AK, 11, 1) AS INTEGER)
                                                WHEN
                                                    MOD(
                                                        CAST(SUBSTR(AK, 1, 1) AS INTEGER) * 3 +
                                                        CAST(SUBSTR(AK, 2, 1) AS INTEGER) * 4 +
                                                        CAST(SUBSTR(AK, 3, 1) AS INTEGER) * 5 +
                                                        CAST(SUBSTR(AK, 4, 1) AS INTEGER) * 6 +
                                                        CAST(SUBSTR(AK, 5, 1) AS INTEGER) * 7 +
                                                        CAST(SUBSTR(AK, 6, 1) AS INTEGER) * 8 +
                                                        CAST(SUBSTR(AK, 7, 1) AS INTEGER) * 9 +
                                                        CAST(SUBSTR(AK, 8, 1) AS INTEGER) * 1 +
                                                        CAST(SUBSTR(AK, 9, 1) AS INTEGER) * 2 +
                                                        CAST(SUBSTR(AK, 10, 1) AS INTEGER) * 3,
                                                        11
                                                    ) <> 10
                                                THEN
                                                    MOD(
                                                        CAST(SUBSTR(AK, 1, 1) AS INTEGER) * 3 +
                                                        CAST(SUBSTR(AK, 2, 1) AS INTEGER) * 4 +
                                                        CAST(SUBSTR(AK, 3, 1) AS INTEGER) * 5 +
                                                        CAST(SUBSTR(AK, 4, 1) AS INTEGER) * 6 +
                                                        CAST(SUBSTR(AK, 5, 1) AS INTEGER) * 7 +
                                                        CAST(SUBSTR(AK, 6, 1) AS INTEGER) * 8 +
                                                        CAST(SUBSTR(AK, 7, 1) AS INTEGER) * 9 +
                                                        CAST(SUBSTR(AK, 8, 1) AS INTEGER) * 1 +
                                                        CAST(SUBSTR(AK, 9, 1) AS INTEGER) * 2 +
                                                        CAST(SUBSTR(AK, 10, 1) AS INTEGER) * 3,
                                                        11
                                                    ) 
                                                    = CAST(SUBSTR(AK, 11, 1) AS INTEGER)
                                                ELSE
                                                    CAST(SUBSTR(AK, 11, 1) AS INTEGER) = 0
                                                END
                                            ),
    Likutis        DECIMAL(15, 7) DEFAULT 0.00 NOT NULL,
    PRIMARY KEY (AK)
);

CREATE TABLE auja9067.Automobilis (
    Tipas          VARCHAR(100) NOT NULL,
    Numeris        VARCHAR(12)  NOT NULL,
    Vairuotojas    CHAR(11)     NOT NULL,
    PRIMARY KEY (Numeris),
    CONSTRAINT IVARUOTOJA FOREIGN KEY (Vairuotojas) REFERENCES auja9067.Vairuotojas ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE UNIQUE INDEX telefonu_indeksas
    ON Vairuotojas(Telefonas);

CREATE TABLE auja9067.Automobilio_rezervacija (
    Automobilis     VARCHAR(12) NOT NULL,
    Rezervacija     INTEGER NOT NULL,
    PRIMARY KEY (Rezervacija, Automobilis),
    CONSTRAINT IAutomob FOREIGN KEY (Automobilis) REFERENCES auja9067.Automobilis ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT IRezer FOREIGN KEY (Rezervacija) REFERENCES auja9067.Rezervacija ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE INDEX AutoIRezervazija_indeksas
    ON Automobilio_rezervacija(Automobilis);

CREATE MATERIALIZED VIEW Vairuotojo_rezervacijos AS
SELECT DISTINCT Vairuotojas.Vardas as Vardas,
                Vairuotojas.Pavarde as Pavarde,
                Vairuotojas.AK as AK,
                Rezervacija.Nr,
                Rezervacija.Kaina,
                Rezervacija.Vieta
FROM auja9067.Vairuotojas,
     auja9067.Rezervacija,
     auja9067.Automobilis,
     auja9067.Automobilio_rezervacija
WHERE Vairuotojas.AK = Automobilis.Vairuotojas
  AND Automobilio_rezervacija.Automobilis = Automobilis.Numeris
  AND Automobilio_rezervacija.Rezervacija = Rezervacija.Nr
WITH DATA;

CREATE VIEW Dabar_rezervave_auto AS
SELECT DISTINCT Automobilis.Numeris,
                Stovejimo_vieta.Nr
FROM auja9067.Automobilis,
     auja9067.Stovejimo_vieta,
     auja9067.Rezervacija,
     auja9067.Automobilio_rezervacija
WHERE Rezervacija.Vieta = Stovejimo_vieta.Nr
  AND Rezervacija.Nr = Automobilio_rezervacija.Rezervacija
  AND Automobilio_rezervacija.Automobilis = Automobilis.Numeris
  AND Now() BETWEEN Rezervacija.Pradzia AND Rezervacija.Pabaiga;

REFRESH MATERIALIZED VIEW Vairuotojo_rezervacijos WITH DATA;

CREATE FUNCTION LaikoKirtimasis()
RETURNS TRIGGER
AS $$
BEGIN
IF (SELECT COUNT(Rezervacija.Nr) FROM auja9067.Rezervacija
    WHERE Rezervacija.Vieta = NEW.Vieta
      AND (Rezervacija.Pabaiga > NEW.Pradzia
             AND Rezervacija.Pradzia < NEW.Pabaiga)
    ) > 0
THEN
    RAISE EXCEPTION 'Rezervacija bloga';
END IF;
RETURN NEW;
END;$$
LANGUAGE plpgsql;

CREATE TRIGGER RezervacijosNesikerta
BEFORE UPDATE OR INSERT ON Rezervacija
FOR EACH ROW
EXECUTE PROCEDURE LaikoKirtimasis();

CREATE FUNCTION LikucioTikrinimas()
RETURNS TRIGGER
AS $$
BEGIN
IF (SELECT SUM(Rezervacija.Kaina) FROM auja9067.Rezervacija
    WHERE (SELECT Automobilis.Vairuotojas FROM auja9067.Automobilis WHERE Automobilis.Numeris = NEW.Automobilis)
       = (SELECT DISTINCT Automobilis.Vairuotojas FROM auja9067.Automobilis, auja9067.Automobilio_rezervacija 
            WHERE Automobilis.Numeris = Automobilio_rezervacija.Automobilis 
              AND Automobilio_rezervacija.Rezervacija = Rezervacija.Nr)
    ) > (CASE
            WHEN 2 * (SELECT DISTINCT Vairuotojas.Likutis 
                FROM auja9067.Vairuotojas, auja9067.Automobilio_rezervacija, auja9067.Automobilis 
                WHERE Automobilis.Numeris = NEW.Automobilis
                  AND Automobilis.Vairuotojas = Vairuotojas.AK) >= 100 
            THEN 2 * (SELECT DISTINCT Vairuotojas.Likutis 
                FROM auja9067.Vairuotojas, auja9067.Automobilio_rezervacija, auja9067.Automobilis 
                WHERE Automobilis.Numeris = NEW.Automobilis
                  AND Automobilis.Vairuotojas = Vairuotojas.AK)
            ELSE 100
         END)
THEN
    RAISE EXCEPTION 'Likutis per mažas rezervacijai';
END IF;
RETURN NEW;
END;$$
LANGUAGE plpgsql;

CREATE TRIGGER NevirsitaLeistinaSuma
BEFORE UPDATE OR INSERT ON Automobilio_rezervacija
FOR EACH ROW
EXECUTE PROCEDURE LikucioTikrinimas();
