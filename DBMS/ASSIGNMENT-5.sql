USE BOOKS_MANAGEMENT_SYSTEM;

CREATE TABLE BOOKS_COPY (
    BOOK_NO INT(5) NOT NULL PRIMARY KEY,   -- Unique Book No
    TITLE VARCHAR(30) NOT NULL,            -- Name of the Book
    COST INT(10)                           -- Cost of Books
);

CREATE TABLE EMPLOYEE (
    ENO INT(10) NOT NULL PRIMARY KEY,      -- Unique Eno
    NAME VARCHAR(20) NOT NULL,             -- Name of the employee
    DEPARTMENT VARCHAR(20),                -- Department of employee
    SALARY INT(10)                         -- Salary of employee
);

-- 4) Insert sample data into BOOKS_COPY
INSERT INTO BOOKS_COPY VALUES
(1, 'Book A', 410),
(2, 'Book B', 300),
(3, 'Book C', 500),
(4, 'Book D', 460);

-- 5) Insert sample data into EMPLOYEE
INSERT INTO EMPLOYEE VALUES
(1, 'Arnab Das', 'IT', 50000),
(2, 'Suman Guha', 'HR', 45000),
(3, 'Nisha Mukherjee', 'Finance', 60000);

-- 6) Quick verification queries
SELECT * FROM BOOKS_COPY;
SELECT * FROM EMPLOYEE;
