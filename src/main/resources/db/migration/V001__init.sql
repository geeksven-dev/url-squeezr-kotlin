create table squeeze
(
    id        bigint auto_increment primary key,
    slug      varchar2(6) unique,
    short_url varchar2(250) unique,
    long_url  varchar2(2048) not null,
    created   timestamp      not null
);

create table visits
(
    id      bigint auto_increment,
    squeeze bigint,
    created timestamp not null
);

alter table visits
    add foreign key (squeeze) references squeeze (id);