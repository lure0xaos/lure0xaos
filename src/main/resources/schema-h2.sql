create sequence HIBERNATE_SEQUENCE;
create table SPRING_SESSION
(
    PRIMARY_ID            char(36) not null,
    SESSION_ID            char(36) not null,
    CREATION_TIME         bigint   not null,
    LAST_ACCESS_TIME      bigint   not null,
    MAX_INACTIVE_INTERVAL int      not null,
    EXPIRY_TIME           bigint   not null,
    PRINCIPAL_NAME        varchar(100),
    constraint SPRING_SESSION_PK primary key (PRIMARY_ID)
);

create unique index SPRING_SESSION_IX1 on SPRING_SESSION (SESSION_ID);
create index SPRING_SESSION_IX2 on SPRING_SESSION (EXPIRY_TIME);
create index SPRING_SESSION_IX3 on SPRING_SESSION (PRINCIPAL_NAME);

create table SPRING_SESSION_ATTRIBUTES
(
    SESSION_PRIMARY_ID char(36)      not null,
    ATTRIBUTE_NAME     varchar(200)  not null,
    ATTRIBUTE_BYTES    longvarbinary not null,
    constraint SPRING_SESSION_ATTRIBUTES_PK primary key (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
    constraint SPRING_SESSION_ATTRIBUTES_FK foreign key (SESSION_PRIMARY_ID) references SPRING_SESSION (PRIMARY_ID) on delete cascade
);

create table USERS
(
    USERNAME varchar_ignorecase(255) not null primary key,
    EMAIL    varchar_ignorecase(255) not null,
    ENABLED  boolean                 not null,
    PASSWORD varchar(255)            not null
);
create table AUTHORITIES
(
    ID        varchar(255) default '(NEXT VALUE FOR SYSTEM_SEQUENCE_AUTHORITIES)' auto_increment not null primary key,
    AUTHORITY varchar_ignorecase(255)                                                            not null,
    USERNAME  varchar(255)                                                                       not null,
    UID       varchar(255),
    primary key (ID),
    constraint IX_AUTH_USERNAME unique (USERNAME, AUTHORITY),
    constraint FK_AUTHORITIES_USERS foreign key (USERNAME) references USERS
);
create table CREATIONS
(
    ALIAS  varchar_ignorecase(32) not null primary key,
    BODY   clob                   not null,
    DATE   timestamp              not null,
    GENRE  varchar(32)            not null,
    HEAD   varchar(255)           not null,
    SOURCE clob                   not null,
    UID    varchar(255),
    AUTHOR varchar(255)           not null,
    constraint FK_CREATIONS_USERS foreign key (AUTHOR) references USERS
);
create table QUOTES
(
    ID               varchar(255) default '(NEXT VALUE FOR SYSTEM_SEQUENCE_QUOTES)' auto_increment primary key,
    AUTHOR           varchar(32),
    HEAD             varchar(255),
    PRIMARY_BODY     clob not null,
    PRIMARY_SOURCE   clob not null,
    SECONDARY_BODY   clob not null,
    SECONDARY_SOURCE clob not null,
    UID              varchar(255)
);
create table NEWS
(
    DATE   timestamp    not null primary key,
    BODY   clob         not null,
    HEAD   varchar(255),
    SOURCE clob         not null,
    UID    varchar(255),
    AUTHOR varchar(255) not null,
    constraint FK_NEWS_USERS foreign key (AUTHOR) references USERS
);
create table COMMENTS
(
    ID             bigint                 not null primary key,
    BODY           clob,
    DATE           timestamp              not null,
    FLAGS          varchar(8)             not null,
    HEAD           varchar(255),
    SOURCE         clob                   not null,
    UID            varchar(255),
    AUTHOR         varchar(255)           not null,
    CREATION_ALIAS varchar_ignorecase(32) not null,
    PID            bigint,
    constraint FK_COMMENTS_COMMENTS foreign key (PID) references COMMENTS,
    constraint FK_COMMENTS_CREATIONS foreign key (CREATION_ALIAS) references CREATIONS,
    constraint FK_COMMENTS_USERS foreign key (AUTHOR) references USERS
);
create table VOTES
(
    USER_USERNAME  varchar_ignorecase(255) not null,
    CREATION_ALIAS varchar_ignorecase(255) not null,
    DATE           timestamp,
    RATE           integer,
    UID            varchar(255),
    primary key (USER_USERNAME, CREATION_ALIAS)
);

create table MESSAGES
(
    ID              bigint default '(NEXT VALUE FOR SYSTEM_SEQUENCE_MESSAGES)' auto_increment not null primary key,
    LOCALE          varchar(2),
    MESSAGE_KEY     varchar(255),
    MESSAGE_CONTENT varchar(255),
    primary key (id),
    constraint LANGUAGES_MESSAGES unique (MESSAGE_KEY, LOCALE)
);

