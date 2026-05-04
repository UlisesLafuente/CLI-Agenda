SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS notes;
DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS events;

CREATE TABLE IF NOT EXISTS events (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    eventDate DATETIME NOT NULL,
    recurrent TINYINT NOT NULL DEFAULT 0,
    annualRecurring TINYINT NOT NULL DEFAULT 0,
    recurrenceInterval INT NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS tasks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    body VARCHAR(250) NOT NULL,
    event_fk INT,
    completed TINYINT NOT NULL DEFAULT 0,
    FOREIGN KEY (event_fk) REFERENCES events(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS notes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    body VARCHAR(250) NOT NULL,
    task_fk INT,
    FOREIGN KEY (task_fk) REFERENCES tasks(id) ON DELETE SET NULL
);

SET FOREIGN_KEY_CHECKS = 1;

-- Sample data
INSERT INTO events (title, description, eventDate, recurrent) VALUES
('Reunión de equipo', 'descripcion', '2024-06-15 10:00:00', 0),
('Presentación proyecto', 'descripcion', '2024-06-20 14:00:00', 0),
('Entrega deadline', 'descripcion', '2024-06-30 23:59:00', 0);

INSERT INTO tasks (body, event_fk, completed) VALUES ('Tarea 1', 1, 0);
INSERT INTO tasks (body, event_fk, completed) VALUES ('Tarea 2', 1, 0);

INSERT INTO notes (body, task_fk) VALUES ('Preparar slides para la reunión', 1);
INSERT INTO notes (body, task_fk) VALUES ('Revisar presupuesto', 1);
INSERT INTO notes (body, task_fk) VALUES ('Practicar presentación', 2);