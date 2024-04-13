create table account (account_id bigint not null auto_increment, avatar_url varchar(255), nickname varchar(255), password varchar(255), username varchar(255), role enum ('MEMBER','ADMIN') not null, primary key (account_id)) engine=InnoDB;
create table chat_room (chat_user_cnt integer not null, has_password bit not null, chat_room_id bigint not null auto_increment, created_at datetime(6) not null, user_id bigint, password varchar(20), title varchar(255) not null, type enum ('PUBLIC','PRIVATE') not null, primary key (chat_room_id)) engine=InnoDB;
create table chat_user (latest_num integer, account_id bigint, chat_room_id bigint, chat_user_id bigint not null auto_increment, created_at datetime(6), id_pair varchar(255), primary key (chat_user_id)) engine=InnoDB;
create table chat_message (num integer, chat_message_id bigint not null auto_increment, chat_room_id bigint, created_at datetime(6) not null, user_id bigint, content varchar(200) not null, cr_id_num varchar(255), primary key (chat_message_id)) engine=InnoDB;
create table friend (friend_id bigint not null auto_increment, from_id bigint, to_id bigint, primary key (friend_id)) engine=InnoDB;

alter table account add constraint UK_gex1lmaqpg0ir5g1f5eftyaa1 unique (username);
alter table chat_room add constraint FK6p2aheutlf624xrong1hu25su foreign key (user_id) references account (account_id);
alter table chat_user add constraint UK_hlbamjafvvgbsg5037np5ts13 unique (id_pair);
alter table chat_user add constraint FK5nk4pk3f1r78r5tkgv62gppqm foreign key (account_id) references account (account_id);
alter table chat_user add constraint FK67fgnu7l9ghfnf79aj7p316rx foreign key (chat_room_id) references chat_room (chat_room_id);
alter table chat_message add constraint UK_1d0s880yip4mleceufp71qakg unique (cr_id_num);
alter table chat_message add constraint FKj52yap2xrm9u0721dct0tjor9 foreign key (chat_room_id) references chat_room (chat_room_id);
alter table chat_message add constraint FK2t5r4msd2f82ywssf3tdg945b foreign key (user_id) references account (account_id);
alter table friend add constraint FK1wy5tl5kc36jb82qwfvdddq0w foreign key (from_id) references account (account_id);
alter table friend add constraint FKaioua1mj287vadscqi9dkl822 foreign key (to_id) references account (account_id);

create index idx_username on account (username);
create index idx_chat_room_id_num_asc on chat_message (chat_room_id, num);
create index idx_chat_room_id_num_desc on chat_message (chat_room_id, num desc);
