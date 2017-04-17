DROP database if exists MapDB;

CREATE DATABASE MapDB;

CREATE TABLE MapDB.playtennis(
outlook varchar(10),
temperature float(5,2),
humidity varchar(10),
wind varchar(10),
play varchar(10)
);
insert into MapDB.playtennis values('sunny',30.3,'high','weak','no');
insert into MapDB.playtennis values('sunny',30.3,'high','strong','no');
insert into MapDB.playtennis values('overcast',30.0,'high','weak','yes');
insert into MapDB.playtennis values('rain',13.0,'high','weak','yes');
insert into MapDB.playtennis values('rain',0.0,'normal','weak','yes');
insert into MapDB.playtennis values('rain',0.0,'normal','strong','no');
insert into MapDB.playtennis values('overcast',0.1,'normal','strong','yes');
insert into MapDB.playtennis values('sunny',13.0,'high','weak','no');
insert into MapDB.playtennis values('sunny',0.1,'normal','weak','yes');
insert into MapDB.playtennis values('rain',12.0,'normal','weak','yes');
insert into MapDB.playtennis values('sunny',12.5,'normal','strong','yes');
insert into MapDB.playtennis values('overcast',12.5,'high','strong','yes');
insert into MapDB.playtennis values('overcast',29.21,'normal','weak','yes');
insert into MapDB.playtennis values('rain',12.5,'high','strong','no');

CREATE TABLE MapDB.example(
x float(5,2),
y float(5,2)
);

insert into MapDB.example values(0.9,1);
insert into MapDB.example values(0.9,1.2);
insert into MapDB.example values(1.3,2);
insert into MapDB.example values(1.2,3.7);
insert into MapDB.example values(1.9,1);
insert into MapDB.example values(2,2.2);
insert into MapDB.example values(1.9,3.1);
insert into MapDB.example values(2.9,1);
insert into MapDB.example values(2.9,2.7);
insert into MapDB.example values(11,5);
insert into MapDB.example values(11,6);
insert into MapDB.example values(11.5,5.4);
insert into MapDB.example values(12,6.2);
insert into MapDB.example values(12,7);
insert into MapDB.example values(12.2,5.9);
insert into MapDB.example values(12.5,6.2);
insert into MapDB.example values(13,5.3);

GRANT CREATE, SELECT, INSERT, DELETE ON MapDB.* TO MapUser@localhost IDENTIFIED BY 'map';
