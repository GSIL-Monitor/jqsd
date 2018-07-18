
CREATE TABLE t_admin (
  id varchar(128) primary key  NOT NULL ,
  userName varchar(32) NOT NULL DEFAULT '' ,
  password varchar(32) NOT NULL DEFAULT '' ,
  createTime datetime DEFAULT NULL ,
  updateTime datetime DEFAULT NULL ,
  realName varchar(32) DEFAULT NULL ,
  phone varchar(32) DEFAULT NULL ,
  email varchar(50) DEFAULT NULL ,
  status varchar(2) NOT NULL DEFAULT 'y' ,
  lastLoginTime datetime DEFAULT NULL ,
  lastLoginIp varchar(32) DEFAULT NULL ,
  loginTime datetime DEFAULT NULL ,
  loginIp varchar(32) DEFAULT NULL ,
  roleId varchar(64) NOT NULL DEFAULT '0',
  constraint uq_acc UNIQUE(userName)
);

CREATE TABLE t_admin_menu (
aId varchar(64) NOT NULL,
mId int NOT NULL,
PRIMARY KEY (aId,mId)
) ;


CREATE TABLE t_db_config (
id int identity(1,1) primary key NOT NULL ,
name varchar(255) DEFAULT NULL,
dbType varchar(255) DEFAULT NULL,
dbName varchar(255) DEFAULT NULL,
dbUrl varchar(255) DEFAULT NULL,
url varchar(1024) DEFAULT NULL,
port varchar(20) DEFAULT NULL,
userName varchar(255) DEFAULT NULL,
pwd varchar(1024) DEFAULT NULL,
createTime datetime DEFAULT NULL,
remark varchar(255) DEFAULT NULL,
constraint uq_name UNIQUE(name)
);


CREATE TABLE t_job_log (
id int identity(1,1) primary key NOT NULL,
jobId int DEFAULT NULL,
jobName varchar(255) DEFAULT NULL,
msg varchar(1024) DEFAULT NULL,
createTime datetime DEFAULT NULL,
status varchar(20) DEFAULT NULL,
) ;

CREATE TABLE t_module (
id int identity(1,1) primary key NOT NULL,
pId int DEFAULT NULL ,
name varchar(64) NOT NULL DEFAULT '' ,
icon varchar(60) DEFAULT NULL,
priority int NOT NULL DEFAULT '0' ,
sn varchar(32) NOT NULL DEFAULT '' ,
url varchar(255) NOT NULL DEFAULT '' ,
is_use int DEFAULT NULL ,
level int DEFAULT NULL ,
description varchar(255) DEFAULT NULL,
) ;

CREATE TABLE t_role (
id int identity(1,1) primary key NOT NULL,
roleName varchar(32) NOT NULL DEFAULT '0' ,
mId varchar(1024) DEFAULT '0' ,
description varchar(64) DEFAULT NULL,
) ;


CREATE TABLE t_schedule_job (
id int identity(1,1) primary key NOT NULL,
createTime datetime DEFAULT NULL,
name varchar(255) DEFAULT NULL,
status int DEFAULT '0',
cron varchar(255) NOT NULL,
description varchar(255) DEFAULT NULL,
jobClass varchar(255) DEFAULT NULL,
dbName varchar(60) DEFAULT NULL,
param varchar(255) DEFAULT NULL,
min varchar(20) DEFAULT NULL,
hour varchar(20) DEFAULT NULL,
day varchar(20) DEFAULT NULL,
mon varchar(20) DEFAULT NULL,
week varchar(20) DEFAULT NULL,
updateTime datetime DEFAULT CURRENT_TIMESTAMP,
constraint uq_job_name UNIQUE(name)
) ;
