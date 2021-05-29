# nagakawa-guarantee-be
project nagakawa-guarantee-be

Cài Lombok plugin

https://projectlombok.org/setup/intellij

Tạo DB local bằng PostgreSQL:

CREATE USER nagakawa_guarantee WITH PASSWORD 'Nagakawa1234a@';
CREATE DATABASE nagakawa_guarantee WITH OWNER =  nagakawa_guarantee ENCODING = 'UTF-8'	LC_COLLATE = 'en_US.utf8' LC_CTYPE = 'en_US.utf8' TABLESPACE = 'pg_default' CONNECTION LIMIT = -1;
GRANT ALL PRIVILEGES ON DATABASE nagakawa_guarantee TO nagakawa_guarantee;

## Quản lí Database Changelog

Khi có bất kì thay đổi cấu trúc trong Database.

Trước khi commit code mới lên, Chạy lệnh:

```
mvn liquibase:diff
```

Sẽ sinh ra file src/main/resources/db/changelog/yyyy_MM_dd_HH_mm.xml

Add file vừa tạo vào trong commit của bạn.

```
git add .
```

## Apply thay đổi DB

Khi pull code mới về, chạy lệnh dưới để đồng bộ Database

```
mvn liquibase:update
```

