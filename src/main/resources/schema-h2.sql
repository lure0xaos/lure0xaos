create table if not exists USERS
(
    USERNAME varchar_ignorecase(255) not null primary key,
    EMAIL    varchar_ignorecase(255) not null,
    ENABLED  boolean                 not null,
    PASSWORD varchar(255)            not null
);
create table if not exists AUTHORITIES
(
    ID        bigint default '(NEXT VALUE FOR SYSTEM_SEQUENCE_AUTHORITIES)' auto_increment not null primary key,
    AUTHORITY varchar_ignorecase(255)                                                      not null,
    USERNAME  varchar(255)                                                                 not null,
    UID       varchar(255),
    primary key (ID),
    constraint IX_AUTH_USERNAME unique (USERNAME, AUTHORITY),
    constraint FK_AUTHORITIES_USERS foreign key (USERNAME) references USERS
);
create table if not exists CREATIONS
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
create table if not exists QUOTES
(
    ID               bigint default '(NEXT VALUE FOR SYSTEM_SEQUENCE_QUOTES)' auto_increment primary key,
    AUTHOR           varchar(32),
    HEAD             varchar(255),
    PRIMARY_BODY     clob not null,
    PRIMARY_SOURCE   clob not null,
    SECONDARY_BODY   clob not null,
    SECONDARY_SOURCE clob not null,
    UID              varchar(255)
);
create table if not exists NEWS
(
    DATE   timestamp    not null primary key,
    BODY   clob         not null,
    HEAD   varchar(255),
    SOURCE clob         not null,
    UID    varchar(255),
    AUTHOR varchar(255) not null,
    constraint FK_NEWS_USERS foreign key (AUTHOR) references USERS
);
create table if not exists COMMENTS
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
create table if not exists VOTES
(
    USER_USERNAME  varchar_ignorecase(255) not null,
    CREATION_ALIAS varchar_ignorecase(255) not null,
    DATE           timestamp,
    RATE           integer,
    UID            varchar(255),
    primary key (USER_USERNAME, CREATION_ALIAS)
);

create table if not exists MESSAGES
(
    ID              bigint default '(NEXT VALUE FOR SYSTEM_SEQUENCE_MESSAGES)' auto_increment not null primary key,
    LOCALE          varchar(2),
    MESSAGE_KEY     varchar(255),
    MESSAGE_CONTENT varchar(255),
    primary key (id),
    constraint LANGUAGES_MESSAGES unique (MESSAGE_KEY, LOCALE)
);
