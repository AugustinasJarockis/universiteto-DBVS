INSERT INTO auja9067.Rezervacija (Kaina, Pradzia, Pabaiga, Vieta) VALUES(20, '2033-11-30 12:00:00', '2033-12-30 15:00:00', 2);
INSERT INTO auja9067.Automobilio_rezervacija VALUES('OPA 808', (SELECT Nr FROM Rezervacija WHERE (SELECT MAX(Rezervacija.Nr) from Rezervacija) = Rezervacija.Nr));
INSERT INTO auja9067.Rezervacija (Kaina, Pradzia, Pabaiga, Vieta) VALUES(20, '2033-11-30 14:00:00', '2033-12-30 16:00:00', 2);
INSERT INTO auja9067.Automobilio_rezervacija VALUES('OPA 808', (SELECT Nr FROM Rezervacija WHERE (SELECT MAX(Rezervacija.Nr) from Rezervacija) = Rezervacija.Nr));

INSERT INTO auja9067.Rezervacija (Kaina, Pradzia, Pabaiga, Vieta) VALUES(110, '2033-12-30 12:00:00', '2033-12-30 15:00:00', 2);
INSERT INTO auja9067.Automobilio_rezervacija VALUES('OPA 808', (SELECT Nr FROM Rezervacija WHERE (SELECT MAX(Rezervacija.Nr) from Rezervacija) = Rezervacija.Nr));

INSERT INTO auja9067.Rezervacija (Kaina, Pradzia, Pabaiga, Vieta) VALUES(1000, '2033-12-30 14:00:00', '2033-12-30 16:00:00', 2);
INSERT INTO auja9067.Automobilio_rezervacija VALUES('OPA 808', (SELECT Nr FROM Rezervacija WHERE (SELECT MAX(Rezervacija.Nr) from Rezervacija) = Rezervacija.Nr));