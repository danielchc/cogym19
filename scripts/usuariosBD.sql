CREATE USER centrodeportivo WITH PASSWORD 'U2uPiCAaFBicL7UfFbPD';
GRANT  ALL PRIVILEGES  ON DATABASE "CentroDeportivo" TO centrodeportivo;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO centrodeportivo;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO centrodeportivo;