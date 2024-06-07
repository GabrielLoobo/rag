CREATE TABLE user (
        query_count integer default 0,
        status integer default 0,
        id bigint not null auto_increment,
        last_queried_at datetime(6),
        social_auth_id varchar(255),
        name varchar(255),
        username varchar(255),
        primary key (id)
    )
