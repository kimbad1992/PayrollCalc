CREATE TABLE PERSON_INFO (
                             PERSON_ID SERIAL PRIMARY KEY,
                             AFFILIATION VARCHAR(50),
                             NAME VARCHAR(50),
                             RESIDENT_NUMBER VARCHAR(20) UNIQUE
);
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE WORK_INFO (
                           WORK_ID SERIAL PRIMARY KEY,
                           PERSON_ID INT REFERENCES PERSON_INFO(PERSON_ID),
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
                                PERSON_ID INT REFERENCES PERSON_INFO(PERSON_ID),
                                BANK VARCHAR(50),
                                ACCOUNT_NUMBER VARCHAR(30),
                                HOURLY_WAGE NUMERIC(10, 2),
                                SALARY NUMERIC(10, 2),
                                OT_HOURLY_WAGE NUMERIC(10, 2)
);
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE DOCUMENT_STATUS (
                                 DOC_ID SERIAL PRIMARY KEY,
                                 PERSON_ID INT REFERENCES PERSON_INFO(PERSON_ID),
                                 HEALTH_CHECK BOOLEAN,
                                 FAMILY_REGISTER BOOLEAN,
                                 BANKBOOK_COPY BOOLEAN,
                                 EMPLOYMENT_CONTRACT BOOLEAN
);
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE EDUCATION_AND_CAREER (
                                      EDU_ID SERIAL PRIMARY KEY,
                                      PERSON_ID INT REFERENCES PERSON_INFO(PERSON_ID),
                                      EDUCATION VARCHAR(20),
                                      CAREER_MONTHS NUMERIC(5)
);
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE ADMIN_USERS (
                             id SERIAL PRIMARY KEY,
                             username VARCHAR(50) UNIQUE NOT NULL,
                             password VARCHAR(100) NOT NULL,
                             enabled BOOLEAN DEFAULT TRUE
);
INSERT INTO public.admin_users (id, username, "password", enabled) VALUES(1, 'leepay', '1234', true);
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
                            level INT NOT NULL
);
INSERT INTO public.admin_menu
(page_seq, page_name, page_url, gnb_sort, gnb_name, role_id, parent_page_seq, "level")
VALUES(1, '사원', '/employee', 1, '사원', 1, NULL, 1);
INSERT INTO public.admin_menu
(page_seq, page_name, page_url, gnb_sort, gnb_name, role_id, parent_page_seq, "level")
VALUES(2, '사원 조회', '/employee/list', 1, '사원 조회', 1, 1, 2);
INSERT INTO public.admin_menu
(page_seq, page_name, page_url, gnb_sort, gnb_name, role_id, parent_page_seq, "level")
VALUES(3, '사원 등록', '/employee/register', 1, '사원 등록', 1, 1, 2);
INSERT INTO public.admin_menu
(page_seq, page_name, page_url, gnb_sort, gnb_name, role_id, parent_page_seq, "level")
VALUES(4, '사원 엑셀 일괄등록', '/employee/excel', 1, '사원 엑셀 일괄등록', 1, 1, 2);
------------------------------------------------------------------------------------------------------------------------
