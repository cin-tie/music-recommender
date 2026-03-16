CREATE DATABASE auth_db;
CREATE DATABASE user_db;
CREATE DATABASE rec_db;

CREATE USER auth_user WITH ENCRYPTED PASSWORD 'auth_pass';
CREATE USER user_user WITH ENCRYPTED PASSWORD 'user_pass';
CREATE USER rec_user WITH ENCRYPTED PASSWORD 'rec_pass';

GRANT ALL PRIVILEGES ON DATABASE auth_db TO auth_user;
GRANT ALL PRIVILEGES ON DATABASE user_db TO user_user;
GRANT ALL PRIVILEGES ON DATABASE rec_db TO rec_user;

\c auth_db
GRANT USAGE, CREATE ON SCHEMA public TO auth_user;
ALTER SCHEMA public OWNER TO auth_user;

\c user_db
GRANT USAGE, CREATE ON SCHEMA public TO user_user;
ALTER SCHEMA public OWNER TO user_user;

\c rec_db
GRANT USAGE, CREATE ON SCHEMA public TO rec_user;
ALTER SCHEMA public OWNER TO rec_user;