REATE TABLE persoana (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255),
    nume VARCHAR(255)
);

CREATE TABLE sofer (
    id SERIAL PRIMARY KEY,
	username VARCHAR(255),
    nume VARCHAR(255),
    indicativ_masina VARCHAR(255)
);

CREATE TABLE comanda (
	id SERIAL PRIMARY KEY,
    persoana_id BIGINT,
    sofer_id BIGINT,
    data TIMESTAMP,
	locatie VARCHAR(255),
    FOREIGN KEY (persoana_id) REFERENCES persoana(id) ON DELETE CASCADE,
    FOREIGN KEY (sofer_id) REFERENCES sofer(id) ON DELETE CASCADE
);

INSERT INTO persoana (username, nume) VALUES
('user1', 'John Doe'),
('user2', 'Jane Smith'),
('user3', 'Alice Johnson');

INSERT INTO sofer (username, nume, indicativ_masina) VALUES
('driver1', 'Bob Brown', 'ABC123'),
('driver2', 'Eva White', 'XYZ789'),
('driver3', 'Charlie Green', 'DEF456');

INSERT INTO comanda (persoana_id, sofer_id, data,locatie) VALUES
(1, 1, '2024-01-18 16:00:00','Ploiesti'),
(2, 1, '2024-01-20 15:30:00','Pitesti');

DELETE FROM comanda;