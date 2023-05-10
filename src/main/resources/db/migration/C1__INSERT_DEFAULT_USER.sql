-- create default user
INSERT INTO bts_user(status, created_date, firstname, lastname, middle_name,birt_date,phone_number, password, role_enum, username)
VALUES
      ('CREATED', now(), 'super_admin', 'super_admin', 'super_admin','1999-05-10 12:44:37.000000','+998993189918','$2a$10$IL.LZehsMxdMOvMoZPusiu8xq/IYNGf22SUbGcJgMXhYdoMXhOHTy','SUPER_ADMIN', 'super_admin'),
      ('CREATED', now(), 'admin', 'admin', 'admin','2000-10-18 12:44:37.000000','+998993189918','$2a$10$BiGuQ.vTRtZIvhjI0pNtA.RP2UnDDp/9dum69701HHT2AZ/Psol72','ADMIN', 'admin')