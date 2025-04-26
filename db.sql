show databases;

drop database rapid_db;

use rapid_db;

show tables;

select * from rapid_db.rapid_clients;
select * from rapid_db.rapid_users;
select * from rapid_db.rapid_roles ;
select * from rapid_db.user_roles ;
select * from rapid_db.rapid_permissions rp;
select * from rapid_db.rapid_role_permissions rrp ;


SET FOREIGN_KEY_CHECKS = 0;
truncate rapid_db.rapid_permissions;
SET FOREIGN_KEY_CHECKS = 1;
