


FROM  postgres:13.0
ENV POSTGRES_USER sa
ENV POSTGRES_PASSWORD 1234567
ENV POSTGRES_DB municipality_db
COPY migrations/V1__Initial_create.sql /docker-entrypoint-initdb.d/


