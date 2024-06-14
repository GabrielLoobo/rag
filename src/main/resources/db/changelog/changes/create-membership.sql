create table membership_plan (
    id bigint not null auto_increment,
    daily_prompt_limit integer,
    document_upload_limit integer,
    primary key (id)
    )

