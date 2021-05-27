# nagakawa-guarantee-be
project nagakawa-guarantee-be

Cai Lombok plugin

https://projectlombok.org/setup/intellij

Tao DB local:

CREATE DATABASE nagakawa_guarantee CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'nagakawa_guarantee'@'%' IDENTIFIED BY 'CVT1234a@';
GRANT ALL PRIVILEGES ON nagakawa_guarantee.* TO 'nagakawa_guarantee'@'%';

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

