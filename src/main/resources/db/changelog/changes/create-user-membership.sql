alter table user add column membership_plan_id bigint not null

alter table user add constraint FKivs28ld50n3eed22hv1vkgrk foreign key (membership_plan_id) references membership_plan (id)
