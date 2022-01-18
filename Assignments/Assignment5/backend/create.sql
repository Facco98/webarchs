-- auto-generated definition
create table ACCOMMODATION
(
    DTYPE                  CHARACTER not null,
    ID                     INTEGER   not null
        primary key,
    NAME                   CHARACTER,
    PRICE                  DOUBLE PRECISION,
    HALF_BOARD_EXTRA_PRICE DOUBLE PRECISION,
    NUMBER_OF_STARS        INTEGER,
    TOTAL_NUMBER_OF_ROOMS  INTEGER,
    FINAL_CLEANING_PRICE   DOUBLE PRECISION,
    MAX_NUMBER_OF_PEOPLE   INTEGER
);

-- auto-generated definition
create table RESERVATION
(
    ID               INTEGER not null
        primary key,
    ACCOMMODATION_ID INTEGER,
    END_DATE         TIMESTAMP,
    START_DATE       TIMESTAMP,
    USER_NAME        CHARACTER,
    USER_SURNAME     CHARACTER,
    PRICE            DOUBLE PRECISION,
    constraint FKII3QURFAETF4OBR2Y3RC6MUJ1
        foreign key (ACCOMMODATION_ID) references ACCOMMODATION
);

-- auto-generated definition
create sequence HIBERNATE_SEQUENCE;

