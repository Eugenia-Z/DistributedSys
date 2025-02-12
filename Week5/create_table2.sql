CREATE TABLE records (
    Student VARCHAR(255) NOT NULL,
    ProblemID INT NOT NULL,
    PassOrFail BOOLEAN,
    SubmitTime TIME NOT NULL,
    Runtime INT,
    PRIMARY KEY (Student, ProblemID, SubmitTime)
);