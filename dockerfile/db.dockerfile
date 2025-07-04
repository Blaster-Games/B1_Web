FROM mariadb:lts
COPY blaster_backup.sql /docker-entrypoint-initdb.d/
