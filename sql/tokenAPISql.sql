-- 토큰관리 테이블 정보
create table tokendbslave.token_manager
(
    token_name   varchar(255) not null,
    user_id      varchar(255) not null,
    access_count int          null,
    token        varchar(255) null,
    primary key (token_name, user_id)
);

-- master/slave 토큰테이블 조회
select *
from tokendbmaster.token_manager
union all
select *
from tokendbslave.token_manager ;

-- master/slave 토큰테이블 insert marge
INSERT INTO tokendbslave.token_manager
SELECT *
FROM tokendbmaster.token_manager;

-- master/slave 토큰테이블 update marge
update tokendbslave.token_manager as slave
    , tokendbmaster.token_manager as master
set slave.access_count = master.access_count, slave.token = master.token
where slave.token_name = master.token_name
  and slave.user_id = master.user_id;