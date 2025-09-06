CREATE DATABASE MCA_DBMS;
USE MCA_DBMS;


/* Q1: Create the relation PATIENT and insert the data */
CREATE TABLE PATIENT(
	P_ID VARCHAR(5) NOT NULL,
    P_NAME VARCHAR(20),
    P_DOB DATE,
    P_ALIMENT VARCHAR(10),
    P_BILL INT,
    P_CONTACT VARCHAR(11),
    P_ADDR VARCHAR(10)
);

/* INSERT THE VALUES - DATE FORMAT: YYYY-MM-DD */
INSERT INTO PATIENT VALUES
('P001','H. Das','1965-07-13','HEART',23000,'Kolkata','Patna'),
('P002','P. Sen','1981-07-31','LUNG',18000,'Kolkata','Kolkata'),
('P003','M. Ray','1963-12-12','HEART',19950,'Patna','Odisha'),
('P004','Md. Ali','1989-11-01','ENT',21000,'Jharkhand','Jharkhand'),
('P005','D. Clerk','2000-09-23','HEART',15000,'Kolkata','Kolkata'),
('P005','K. Reddy','1998-02-17','LUNG',24500,'Siliguri','Assam'),
('P006','T. Ghosh','1995-05-19','HEART',19900,'Kolkata','Kolkata'),
('P007','M. Sekh','1976-04-23','ENT',12000,'Patna','Bihar'),
('P008','L. Paul','1987-11-22','LUNG',23000,'Siliguri','Assam'),
('P009','G. Giri','1992-08-30','HEART',27000,'Durgapur','Durgapur'),
('P010','N. Koley','2002-05-25','ENT',18000,'Asansol','Asansol');


/* 
	Q2: Find all existing infromation in the table 
*/
SELECT *
FROM PATIENT;

/*
	Q3: Find all the patients who can avail senior citizen discount.
	[ Hint: Patent with >= 60 Years of age are considered senior citizens.]
*/
SELECT P_ID, P_NAME ,P_DOB, P_ALIMENT
FROM PATIENT
WHERE TIMESTAMPDIFF(YEAR, P_DOB, CURDATE()) >= 60;

/*
	Q4: Find all the patients who have heart aliment and are from kolkata.
*/
SELECT P_ID, P_NAME
FROM PATIENT
WHERE P_ALIMENT = 'HEART'
  AND P_ADDR = 'Kolkata';
  
/*
	Q5: Find id, name, bill of all the patients who have a bill of more than RS 20000/- and are from outside kolkata.
*/
SELECT P_ID, P_NAME, P_BILL
FROM PATIENT
WHERE P_BILL > 20000
  AND P_ADDR != 'Kolkata';

/*
	Q6: Find all the patients who were born between 1980 and 1990
*/
SELECT P_ID, P_NAME, P_DOB
FROM PATIENT
WHERE P_DOB BETWEEN '1980-01-01' AND '1990-01-01';

/*
	Q7: Find all the patients who have a contact person in kolkata
*/
SELECT P_ID, P_NAME, P_CONTACT
FROM PATIENT
WHERE P_CONTACT = 'Kolkata';
