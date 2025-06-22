INSERT INTO auja9067.Tipas VALUES('Standartinis', 3.25);
INSERT INTO auja9067.Tipas VALUES('Elektromobilio', 5);
INSERT INTO auja9067.Tipas VALUES('Šeimos', 4);

INSERT INTO auja9067.Stovejimo_vieta VALUES(1, 'Standartinis');
INSERT INTO auja9067.Stovejimo_vieta VALUES(2, 'Standartinis');
INSERT INTO auja9067.Stovejimo_vieta VALUES(3, 'Standartinis');
INSERT INTO auja9067.Stovejimo_vieta VALUES(4, 'Standartinis');
INSERT INTO auja9067.Stovejimo_vieta VALUES(5, 'Standartinis');
INSERT INTO auja9067.Stovejimo_vieta VALUES(6, 'Standartinis');
INSERT INTO auja9067.Stovejimo_vieta VALUES(7, 'Standartinis');
INSERT INTO auja9067.Stovejimo_vieta VALUES(8, 'Standartinis');
INSERT INTO auja9067.Stovejimo_vieta VALUES(9, 'Standartinis');
INSERT INTO auja9067.Stovejimo_vieta VALUES(301, 'Elektromobilio');
INSERT INTO auja9067.Stovejimo_vieta VALUES(302, 'Elektromobilio');
INSERT INTO auja9067.Stovejimo_vieta VALUES(303, 'Elektromobilio');
INSERT INTO auja9067.Stovejimo_vieta VALUES(901, 'Šeimos');
INSERT INTO auja9067.Stovejimo_vieta VALUES(902, 'Šeimos');
INSERT INTO auja9067.Stovejimo_vieta VALUES(903, 'Šeimos');

INSERT INTO auja9067.Vairuotojas VALUES('Petras', 'Petraitis', 866819648, 'Jonava@Kaunas.lt', '34567891246');
INSERT INTO auja9067.Vairuotojas VALUES('Jonas', 'Jonaitis', 867869648, 'ManoElPastas@pastas.lt', '54567891248');

INSERT INTO auja9067.Automobilis VALUES('Lengvasis', 'OPA 808', '54567891248');
INSERT INTO auja9067.Automobilis VALUES('Lengvasis', 'PAS 810', '54567891248');
INSERT INTO auja9067.Automobilis VALUES('Elektromobilis', 'KAS 723', '54567891248');

INSERT INTO auja9067.Rezervacija (Kaina, Pradzia, Pabaiga, Vieta) VALUES(50, '2023-11-30 12:00:00', '2023-12-30 11:00:00', 6);
INSERT INTO auja9067.Automobilio_rezervacija VALUES('OPA 808', (SELECT Nr FROM Rezervacija WHERE (SELECT MAX(Rezervacija.Nr) from Rezervacija) = Rezervacija.Nr));
INSERT INTO auja9067.Rezervacija (Kaina, Pradzia, Pabaiga, Vieta) VALUES(50, '2025-11-30 12:00:00', '2026-12-30 11:00:00', 6);
INSERT INTO auja9067.Automobilio_rezervacija VALUES('OPA 808', (SELECT Nr FROM Rezervacija WHERE (SELECT MAX(Rezervacija.Nr) from Rezervacija) = Rezervacija.Nr));
INSERT INTO auja9067.Rezervacija (Kaina, Pradzia, Pabaiga, Vieta) VALUES(50, '2025-11-30 12:00:00', '2026-12-30 11:00:00', 5);
INSERT INTO auja9067.Automobilio_rezervacija VALUES('OPA 808', (SELECT Nr FROM Rezervacija WHERE (SELECT MAX(Rezervacija.Nr) from Rezervacija) = Rezervacija.Nr));
