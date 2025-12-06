ALTER TABLE users
DROP COLUMN username;

ALTER TABLE users
ADD COLUMN nip varchar(18) unique;