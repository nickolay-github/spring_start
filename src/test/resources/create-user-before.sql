delete from user_role;
delete from usr;

insert into usr(id, username, password, active) values
(1, 'dru', '$2a$08$fNUHI3FnO3cbT6VAcClJOOsIq93f2101ud2RAKiZFAh7Y2h.oFRzC', true),
(2, 'nico', '$2a$08$8DTqibkBq25PORxhHDtDHueE3.yIrdKG/gzAmIrgH0vrw5FpD9/qi', true);

insert into user_role(user_id, roles) values
(1, 'ADMIN'), (1, 'USER'),
(2, 'USER');