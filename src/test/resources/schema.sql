-- Schema unificado para CLI Agenda (app y tests)

DROP TABLE IF EXISTS notes;
DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS events;

SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE IF NOT EXISTS events (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100),
    description VARCHAR(250),
    eventDate DATETIME,
    recurrent TINYINT,
    annualRecurring TINYINT,
    recurrenceInterval INT
);

CREATE TABLE IF NOT EXISTS tasks (
    id INT PRIMARY KEY AUTO_INCREMENT,
    body VARCHAR(100),
    event_fk INT,
    completed TINYINT DEFAULT 0,
    FOREIGN KEY (event_fk) REFERENCES events(id)
);

CREATE TABLE IF NOT EXISTS notes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    body VARCHAR(250),
    task_fk INT,
    FOREIGN KEY (task_fk) REFERENCES tasks(id)
);

SET FOREIGN_KEY_CHECKS = 1;