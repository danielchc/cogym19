create sequence secc;
create table test(
	id varchar(100) default concat('mono',nextval('secc')) not null primary key,
	a integer
);
insert into test (a) values (12);
insert into test (a) values (12);
insert into test (a) values (12);
insert into test (a) values (12);
insert into test (a) values (12);
insert into test (a) values (12);
select * from test;