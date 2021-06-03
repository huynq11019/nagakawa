# nagakawa-guarantee-be
project nagakawa-guarantee-be
## Lombok
Cài Lombok plugin cho eclips và IntelliJ

https://www.baeldung.com/lombok-ide
## Cài đặt postgre trên docker (nhớ thay password)
docker run -p 5432:5432 --name postgres:latest -e POSTGRES_PASSWORD=123456a@ -d postgres

## Cài đặt pgadmin4 trên docker để quản lý và truy vấn postgre
docker pull dpage/pgadmin4

docker run -p 80:80 -e 'PGADMIN_DEFAULT_EMAIL=admin@evotek.vn' -e 'PGADMIN_DEFAULT_PASSWORD=123456a@' -d dpage/pgadmin4

## Kết nối postgre bằng pgadmin4 (có thể sử dụng Navicat để truy vấn postgre)
Truy cập đường dẫn http://localhost để vào trang login pgadmin4

username: admin@evotek.vn / password: 123456a@ (user/pass cấu hình lúc chạy lệnh run docker bên trên)

Sau khi đăng nhập thành công click vào Add New Server và nhập các thông tin sau:

Name: --tùy chọn--

hostname/address: host.docker.internal

port: 5432

account root: postgres

pass root : 123456a@ (Password lúc tạo container trên docker)

Sau khi tạo thành công chạy sql script sau:
## PostgreSQL database
Tạo DB local bằng PostgreSQL:

CREATE USER nagakawa_guarantee WITH PASSWORD 'Nagakawa1234a@';

CREATE DATABASE nagakawa_guarantee WITH OWNER =  nagakawa_guarantee ENCODING = 'UTF-8'	LC_COLLATE = 'en_US.utf8' LC_CTYPE = 'en_US.utf8' TABLESPACE = 'pg_default' CONNECTION LIMIT = -1;

GRANT ALL PRIVILEGES ON DATABASE nagakawa_guarantee TO nagakawa_guarantee;
## Cài redis trên docker
docker pull redis:alpine

docker run -p 6379:6379 --name redis -d redis:alpine redis-server --requirepass 12345678aA@
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
## Spring fox
Trên môi trường development truy cập địa chỉ http://localhost:8080/swagger-ui/ để truy xuất thông tin của tất cả các api bao gồm url, method, request và response body.
