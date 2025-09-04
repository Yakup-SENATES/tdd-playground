create table if not exists Post (
    id int not null,
    user_id int not null,
    title varchar(250) not null,
    body text not null,
    version int,
    primary key (id)

);