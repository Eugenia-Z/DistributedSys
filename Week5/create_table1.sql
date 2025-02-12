CREATE TABLE records (
    RecordID INT NOT NULL AUTO_INCREMENT,
    Student VARCHAR(255) NOT NULL,
    ProblemID INT,
    PassOrFail BOOLEAN,
    SubmitTime TIME,
    Runtime INT,
    PRIMARY KEY (RecordID)
);

INSERT INTO records (Student, ProblemID, PassOrFail, SubmitTime, Runtime) VALUES
('Fac', 111, TRUE, '11:00:00', 12),
('Lin', 222, TRUE, '12:00:00', 17),
('Ama', 222, FALSE, '11:00:00', 17),
('Goo', 333, FALSE, '12:00:00', 12);
