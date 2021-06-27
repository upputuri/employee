DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS departments;
DROP TABLE IF EXISTS projects;
DROP TABLE IF EXISTS employee_projects;


CREATE TABLE employees (
  id VARCHAR(9),
  first_name VARCHAR(9) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE departments (
  id VARCHAR(9) PRIMARY KEY,
  name VARCHAR(30) NOT NULL
);

CREATE TABLE projects (
  id VARCHAR(9) NOT NULL,
  dept_id VARCHAR(9) NOT NULL,
  name VARCHAR(30) NOT NULL,
  PRIMARY KEY (id, dept_id)
);

CREATE TABLE employee_projects (
  emp_id VARCHAR(9) NOT NULL,
  dept_id VARCHAR(9) NOT NULL,
  proj_id VARCHAR(9) NOT NULL,
  PRIMARY KEY (emp_id, dept_id, proj_id),
  FOREIGN KEY (emp_id) references employees(id),
  FOREIGN KEY (proj_id, dept_id) references projects(id, dept_id)
);

CREATE INDEX ON employee_projects(proj_id);



INSERT INTO employees (id, first_name) VALUES
  ('E10001', 'Anand'),
  ('E10002', 'Bhargav'),
  ('E10003', 'Chetana'),
  ('E10004', 'Dharani'),
  ('E10005', 'Eeshwar');

INSERT INTO departments (id, name) VALUES
  ('D101', 'Sales'),
  ('D102', 'Operations'),
  ('D103', 'IT');

INSERT INTO projects (id, dept_id, name) VALUES
  ('P1001', 'D101', 'p1001_of_D101'),
  ('P1001', 'D102', 'p1001_of_D102'),
  ('P1002', 'D102', 'p1002_of_D102'),
  ('P1001', 'D103', 'p1001_of_D103');

INSERT INTO employee_projects (emp_id, dept_id, proj_id) VALUES
  ('E10001', 'D101', 'P1001'),
  ('E10002', 'D101', 'P1001'),
  ('E10001', 'D102', 'P1001'),
  ('E10002', 'D102', 'P1002'),
  ('E10003', 'D102', 'P1002'),
  ('E10004', 'D102', 'P1002'),
  ('E10005', 'D103', 'P1001');

-- INSERT INTO billionaires (first_name, last_name, career) VALUES
--   ('Aliko', 'Dangote', 'Billionaire Industrialist'),
--   ('Bill', 'Gates', 'Billionaire Tech Entrepreneur'),
--   ('Folrunsho', 'Alakija', 'Billionaire Oil Magnate');