CREATE TABLE document (
        status tinyint check (status between 0 and 2),
        id bigint not null auto_increment,
        size bigint,
        user_id bigint,
        hash varchar(255),
        name varchar(255),
        storage_path varchar(255),
        primary key (id)
    )
