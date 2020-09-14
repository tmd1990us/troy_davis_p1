create table ers_user_roles(
	id serial,
	role_name varchar(25) not null,
	
	constraint ers_user_roles_pk
	primary key (id)
);
INSERT INTO project_1.ers_user_roles
(role_name)
VALUES('ADMIN'),('FINANCE_MANAGER'),('EMPLOYEE');
INSERT INTO project_1.ers_user_roles
(role_name)
VALUES('DELETED');

-- +++--------------------------------------------+++ --
create table ers_reimbursement_types(
	id serial,
	role_name varchar(10) not null,
	
	constraint ers_reimbursement_types_pk
	primary key (id)
);
INSERT INTO project_1.ers_reimbursement_types
(role_name)
VALUES('LODGING'),('TRAVEL'),('FOOD'),('OTHER');

-- +++--------------------------------------------+++ --
create table ers_reimbursement_statuses(
	id serial,
	role_name varchar(10) not null,
	
	constraint ers_reimbursement_statuses_pk
	primary key (id)
);
INSERT INTO project_1.ers_reimbursement_statuses
(role_name)
VALUES('PENDING'),('APPROVED'),('DENIED'),('CLOSED');

-- +++--------------------------------------------+++ --
create table ers_users(
	id serial,
	username varchar(25) unique not null,
	password varchar(256) not null,
	first_name varchar(25) not null,
	last_name varchar(25) not null,
	email varchar(256) unique not null,
	user_role_id int not null,
	is_active boolean default true,
	
	
	constraint ers_user_id
	primary key (id),
	
	constraint ers_user_roles_fk
	foreign key (user_role_id)
	references ers_user_roles
);


-- +++--------------------------------------------+++ --
-- project_1.ers_reimbursements definition

-- Drop table

-- DROP TABLE project_1.ers_reimbursements;

create TABLE project_1.ers_reimbursements (
	id serial NOT NULL,
	amount numeric(6,2) NOT NULL,
	submitted timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	resolved timestamp NULL,
	description varchar(1000) NULL,
	reciept bytea,
	author_id int4 NOT NULL,
	resolver_id int4 NULL,
	reimbursement_status_id int4 NOT NULL,
	reimbursement_type_id int4 NOT NULL,
	CONSTRAINT ers_reimbursements_pk PRIMARY KEY (id)
);


-- project_1.ers_reimbursements foreign keys

ALTER TABLE project_1.ers_reimbursements 
ADD CONSTRAINT author_id_fk 
FOREIGN KEY (author_id) 
REFERENCES project_1.ers_users(id);

ALTER TABLE project_1.ers_reimbursements 
ADD CONSTRAINT reimbursement_status_id_fk 
FOREIGN KEY (reimbursement_status_id) 
REFERENCES project_1.ers_reimbursement_statuses(id);

ALTER TABLE project_1.ers_reimbursements 
ADD CONSTRAINT reimbursement_type_id_fk 
FOREIGN KEY (reimbursement_type_id) 
REFERENCES project_1.ers_reimbursement_types(id);

ALTER TABLE project_1.ers_reimbursements 
ADD CONSTRAINT resolver_id_fk 
FOREIGN KEY (resolver_id) 
REFERENCES project_1.ers_users(id);




INSERT INTO project_1.ers_users
(username, password, first_name, last_name, email, user_role_id)
VALUES('u6', crypt('password', gen_salt('bf', 10)), 'troy', 'davis', 'u6', 1);

select * from project_1.ers_users eu 
where password = project_1.crypt('password', password);

--https://www.meetspaceapp.com/2016/04/12/passwords-postgresql-pgcrypto.html

truncate ers_users ;

select * from ers_users eu ;

select * from ers_reimbursements er ;
--needed to be able to hash and unhash the passwords
CREATE EXTENSION pgcrypto;

SELECT id, amount, submitted, resolved, description, reciept, author_id, resolver_id, reimbursement_status_id, reimbursement_type_id
FROM project_1.ers_reimbursements;

