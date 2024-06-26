DROP TABLE IF EXISTS ADMIN_MENU CASCADE;
DROP TABLE IF EXISTS ADMIN_USER_ROLES CASCADE;
DROP TABLE IF EXISTS ADMIN_ROLES CASCADE;
DROP TABLE IF EXISTS ADMIN_USERS CASCADE;
DROP TABLE IF EXISTS EDUCATION_AND_CAREER CASCADE;
DROP TABLE IF EXISTS DOCUMENT_STATUS CASCADE;
DROP TABLE IF EXISTS FINANCIAL_INFO CASCADE;
DROP TABLE IF EXISTS WORK_INFO CASCADE;
DROP TABLE IF EXISTS PERSON_INFO CASCADE;
DROP TABLE IF EXISTS COMMON_CODE CASCADE;
DROP TABLE IF EXISTS GROUP_CODE CASCADE;
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE PERSON_INFO (
                             PERSON_ID SERIAL PRIMARY KEY,
                             AFFILIATION VARCHAR(50),
                             NAME VARCHAR(50),
                             RESIDENT_NUMBER VARCHAR(20) UNIQUE
);
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE WORK_INFO (
                           WORK_ID SERIAL PRIMARY KEY,
                           PERSON_ID INT UNIQUE REFERENCES PERSON_INFO(PERSON_ID),
                           RATE VARCHAR(5),
                           WORK_LOCATION VARCHAR(50),
                           POSITION VARCHAR(20),
                           PHONE VARCHAR(15),
                           EMERGENCY_CONTACT VARCHAR(20),
                           JOIN_DATE DATE
);
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE FINANCIAL_INFO (
                                FINANCIAL_ID SERIAL PRIMARY KEY,
                                PERSON_ID INT UNIQUE REFERENCES PERSON_INFO(PERSON_ID),
                                BANK VARCHAR(50),
                                ACCOUNT_NUMBER VARCHAR(30),
                                HOURLY_WAGE NUMERIC(10, 2),
                                SALARY NUMERIC(10, 2),
                                OT_HOURLY_WAGE NUMERIC(10, 2)
);
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE DOCUMENT_STATUS (
                                 DOC_ID SERIAL PRIMARY KEY,
                                 PERSON_ID INT UNIQUE REFERENCES PERSON_INFO(PERSON_ID),
                                 HEALTH_CHECK BOOLEAN,
                                 FAMILY_REGISTER BOOLEAN,
                                 BANKBOOK_COPY BOOLEAN,
                                 EMPLOYMENT_CONTRACT BOOLEAN
);
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE EDUCATION_AND_CAREER (
                                      EDU_ID SERIAL PRIMARY KEY,
                                      PERSON_ID INT UNIQUE REFERENCES PERSON_INFO(PERSON_ID),
                                      EDUCATION VARCHAR(20),
                                      CAREER_MONTHS NUMERIC(5)
);
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE ADMIN_USERS (
                             id SERIAL PRIMARY KEY,
                             username VARCHAR(50) UNIQUE NOT NULL,
                             password VARCHAR(100) NOT NULL,
                             enabled BOOLEAN DEFAULT TRUE,
                             email VARCHAR(255) UNIQUE,
                             oauth_provider VARCHAR(50),
                             oauth_provider_id VARCHAR(100),
                             oauth_profile_picture VARCHAR(255),
                             two_factor_enabled BOOLEAN DEFAULT FALSE,
                             two_factor_secret VARCHAR(100)
);

INSERT INTO public.admin_users (id, username, "password", enabled) VALUES(1, 'leepay', '$2a$12$.JM/B5OymjeJ0aDbkBxBm.hlkJ6HAr9P76.FTZLj/5vxABbKzKSMy', true);
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE ADMIN_ROLES (
                             id SERIAL PRIMARY KEY,
                             name VARCHAR(50) UNIQUE NOT NULL
);
INSERT INTO public.admin_roles (id, "name") VALUES(1, 'ROLE_admin');
INSERT INTO public.admin_roles (id, "name") VALUES(2, 'ROLE_user');
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE ADMIN_USER_ROLES (
                                  user_id INT REFERENCES ADMIN_USERS(id),
                                  role_id INT REFERENCES ADMIN_ROLES(id),
                                  PRIMARY KEY(user_id, role_id)
);
INSERT INTO public.admin_user_roles (user_id, role_id) VALUES(1, 1);
INSERT INTO public.admin_user_roles (user_id, role_id) VALUES(1, 2);
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE ADMIN_MENU (
                            page_seq SERIAL PRIMARY KEY,
                            page_name VARCHAR(100) NOT NULL,
                            page_url VARCHAR(200) NOT NULL,
                            gnb_sort INT NOT NULL,
                            gnb_name VARCHAR(100) NOT NULL,
                            role_id INT REFERENCES ADMIN_ROLES(id),
                            parent_page_seq INT REFERENCES ADMIN_MENU(page_seq),
                            level INT NOT NULL,
                            icon_class VARCHAR(100)
);
INSERT INTO public.admin_menu (page_seq, page_name, page_url, gnb_sort, gnb_name, role_id, parent_page_seq, level, icon_class) VALUES (2, '', '/employee/list', 1, '사원 조회', 1, 1, 2, null);
INSERT INTO public.admin_menu (page_seq, page_name, page_url, gnb_sort, gnb_name, role_id, parent_page_seq, level, icon_class) VALUES (3, '', '/employee/register', 1, '사원 등록', 1, 1, 2, null);
INSERT INTO public.admin_menu (page_seq, page_name, page_url, gnb_sort, gnb_name, role_id, parent_page_seq, level, icon_class) VALUES (4, '', '/employee/excel', 1, '사원 엑셀 일괄등록', 1, 1, 2, null);
INSERT INTO public.admin_menu (page_seq, page_name, page_url, gnb_sort, gnb_name, role_id, parent_page_seq, level, icon_class) VALUES (1, '사원 관리', '/employee', 1, '사원', 1, null, 1, 'fa fa-address-book');
INSERT INTO public.admin_menu (page_seq, page_name, page_url, gnb_sort, gnb_name, role_id, parent_page_seq, level, icon_class) VALUES (5, '급여 관리', '/payroll', 2, '급여', 1, null, 1, 'fa fa-credit-card');
INSERT INTO public.admin_menu (page_seq, page_name, page_url, gnb_sort, gnb_name, role_id, parent_page_seq, level, icon_class) VALUES (6, '시스템 설정', '/system', 3, '시스템', 1, null, 1, 'fa fa-cog');
INSERT INTO public.admin_menu (page_seq, page_name, page_url, gnb_sort, gnb_name, role_id, parent_page_seq, level, icon_class) VALUES (7, '', '/system/commonCode', 3, '공통코드 관리', 1, 6, 2, null);
INSERT INTO public.admin_menu (page_seq, page_name, page_url, gnb_sort, gnb_name, role_id, parent_page_seq, level, icon_class) VALUES (8, '', '/system/administration', 3, '운영자 관리', 1, 6, 2, null);
INSERT INTO public.admin_menu (page_seq, page_name, page_url, gnb_sort, gnb_name, role_id, parent_page_seq, level, icon_class) VALUES (9, '', '/system/menu', 3, '메뉴 관리', 1, 6, 2, null);
INSERT INTO public.admin_menu (page_seq, page_name, page_url, gnb_sort, gnb_name, role_id, parent_page_seq, level, icon_class) VALUES (10, '놀이터', '/playground', 4, '놀이터', 1, null, 1, 'fa fa-rocket');
INSERT INTO public.admin_menu (page_seq, page_name, page_url, gnb_sort, gnb_name, role_id, parent_page_seq, level, icon_class) VALUES (11, ' ', '/playground/map', 4, '카카오맵 API', 1, 10, 2, null);
INSERT INTO public.admin_menu (page_seq, page_name, page_url, gnb_sort, gnb_name, role_id, parent_page_seq, level, icon_class) VALUES (12, ' ', '/playground/mail', 4, '메일 발송', 1, 10, 2, null);
INSERT INTO public.admin_menu (page_seq, page_name, page_url, gnb_sort, gnb_name, role_id, parent_page_seq, level, icon_class) VALUES (13, ' ', '/playground/chat', 5, '실시간 채팅', 1, 10, 2, null);
INSERT INTO public.admin_menu (page_seq, page_name, page_url, gnb_sort, gnb_name, role_id, parent_page_seq, level, icon_class) VALUES (14, ' ', '/playground/lostark', 6, '로스트아크 API', 1, 10, 2, null);
INSERT INTO public.admin_menu (page_seq, page_name, page_url, gnb_sort, gnb_name, role_id, parent_page_seq, level, icon_class) VALUES (15, ' ', '/playground/llm', 6, '로컬 AI', 1, 10, 2, null);
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE GROUP_CODE (
                            GROUP_CODE_SEQ SERIAL PRIMARY KEY,
                            GROUP_CODE_ID VARCHAR(255) UNIQUE NOT NULL,
                            GROUP_CODE_NAME VARCHAR(255) NOT NULL,
                            GROUP_CODE_DESC TEXT,
                            CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

------------------------------------------------------------------------------------------------------------------------
CREATE TABLE COMMON_CODE (
                            CODE_ID SERIAL PRIMARY KEY,
                            GROUP_CODE_ID VARCHAR(255) REFERENCES GROUP_CODE(GROUP_CODE_ID),
                            CODE_NAME VARCHAR(255) NOT NULL,
                            CODE_VALUE VARCHAR(255) NOT NULL,
                            CODE_DESC TEXT,
                            SORT_ORDER INT,
                            USE_YN BOOLEAN DEFAULT TRUE,
                            CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE PLAYGROUND_DATA (
                                 key VARCHAR(255),
                                 data JSONB
);
INSERT INTO public.playground_data (key, data) VALUES ('raidData', '[{"difficulty": {"노말": {"gate": {"1": {"gold": 500, "minLevel": 1415}, "2": {"gold": 700, "minLevel": 1415}}}, "하드": {"gate": {"1": {"gold": 700, "minLevel": 1445}, "2": {"gold": 1100, "minLevel": 1445}}}}, "dungeonName": "발탄"}, {"difficulty": {"노말": {"gate": {"1": {"gold": 600, "minLevel": 1430}, "2": {"gold": 1000, "minLevel": 1430}}}, "하드": {"gate": {"1": {"gold": 900, "minLevel": 1460}, "2": {"gold": 1500, "minLevel": 1460}}}}, "dungeonName": "비아키스"}, {"difficulty": {"노말": {"gate": {"1": {"gold": 600, "minLevel": 1475}, "2": {"gold": 900, "minLevel": 1475}, "3": {"gold": 1500, "minLevel": 1475}}}, "하드": {}}, "dungeonName": "쿠크세이튼"}, {"difficulty": {"노말": {"gate": {"1": {"gold": 1500, "minLevel": 1490}, "2": {"gold": 1500, "minLevel": 1490}, "3": {"gold": 1500, "minLevel": 1500}, "4": {"gold": 2500, "minLevel": 1520}}}, "하드": {"gate": {"1": {"gold": 2000, "minLevel": 1540}, "2": {"gold": 2000, "minLevel": 1540}, "3": {"gold": 2000, "minLevel": 1550}, "4": {"gold": 3000, "minLevel": 1560}}}}, "dungeonName": "아브렐슈드"}, {"difficulty": {"노말": {"gate": {"1": {"gold": 1500, "minLevel": 1580}, "2": {"gold": 2000, "minLevel": 1580}, "3": {"gold": 4000, "minLevel": 1580}}}, "하드": {"gate": {"1": {"gold": 1750, "minLevel": 1600}, "2": {"gold": 2500, "minLevel": 1600}, "3": {"gold": 5750, "minLevel": 1600}}}}, "dungeonName": "일리아칸"}, {"difficulty": {"노말": {"gate": {"1": {"gold": 3500, "minLevel": 1610}, "2": {"gold": 4000, "minLevel": 1610}, "3": {"gold": 5500, "minLevel": 1610}}}, "하드": {"gate": {"1": {"gold": 5000, "minLevel": 1630}, "2": {"gold": 6000, "minLevel": 1630}, "3": {"gold": 9000, "minLevel": 1630}, "4": {"gold": 21000, "minLevel": 1630}}}}, "dungeonName": "카멘"}, {"difficulty": {"노말": {"gate": {"1": {"gold": 1000, "minLevel": 1540}, "2": {"gold": 1500, "minLevel": 1540}, "3": {"gold": 2000, "minLevel": 1540}}}, "하드": {"gate": {"1": {"gold": 1500, "minLevel": 1580}, "2": {"gold": 2000, "minLevel": 1580}, "3": {"gold": 3000, "minLevel": 1580}}}}, "dungeonName": "카양겔"}, {"difficulty": {"노말": {"gate": {"1": {"gold": 1500, "minLevel": 1600}, "2": {"gold": 1750, "minLevel": 1600}, "3": {"gold": 2500, "minLevel": 1600}, "4": {"gold": 3250, "minLevel": 1600}}}, "하드": {"gate": {"1": {"gold": 2000, "minLevel": 1620}, "2": {"gold": 2500, "minLevel": 1620}, "3": {"gold": 4000, "minLevel": 1620}, "4": {"gold": 6000, "minLevel": 1620}}}}, "dungeonName": "상아탑"}]');


