# Final Project — Training Bootcamp Springboot

Aplikasi Backend sederhana terkait dengan product smartphone yang memuat autentikasi JWT, CRUD produk, impor produk dari DummyJSON, dan komunikasi antar service melalui Eureka, Gateway, Feign, Redis, serta Kafka.

## 1. Arsitektur dan port

| Service | Port | Tanggung jawab |
|---|---:|---|
| Eureka Server | 8761 | Registry service |
| API Gateway | 8080 | Pintu masuk, validasi JWT, routing dengan `lb://` (load balancer) |
| Auth Service | 8081 | Register, login, dan membuat JWT |
| Product Service | 8082 | CRUD produk, validasi dan inti bisnis, Redis, dan event Kafka |
| Scheduler Service | 8083 | Mengimpor smartphones dari DummyJSON dan menerima event Kafka |

Auth Service dan Product Service memakai PostgreSQL. Scheduler Service tidak mengakses database produk; data impor dikirim ke Product Service melalui Feign.

## 2. Prasyarat

- Java 25
- Docker dan Docker Compose
- PostgreSQL yang tersedia di `localhost:5433`, dengan database `personaldata`
- IntelliJ IDEA 

## 3. Konfigurasi environment

Aplikasi memiliki nilai default untuk penggunaan lokal. Kamu dapat menggantinya dengan environment variable berikut:

| Variable | Default lokal | Digunakan oleh |
|---|---|---|
| `DB_USERNAME` | `personaldata` | Auth Service, Product Service |
| `DB_PASSWORD` | `personaldata` | Auth Service, Product Service |
| `JWT_SECRET` | `local-dev-auth-secret-change-this-32chars` | Auth Service, API Gateway |
| `JWT_EXPIRATION` | `360000` ms | Auth Service |
| `REDIS_HOST` | `localhost` | Product Service |
| `REDIS_PORT` | `6379` | Product Service |
| `KAFKA_BOOTSTRAP_SERVERS` | `localhost:9092` | Product Service, Scheduler Service |
| `DUMMYJSON_BASE_URL` | `https://dummyjson.com` | Product Service, Scheduler Service |


## 4. API Endpoint
List API:
| Method | Endpoint | Auth | Kegunaan |
|---|---|---|---|
| POST | `/auth/register` | Publik | Registrasi user |
| POST | `/auth/login` | Publik | Login dan memperoleh JWT |
| POST | `/product/add` | JWT | Membuat produk |
| GET | `/product/{id}` | JWT | Melihat detail produk |
| GET | `/product?huruf=a` | JWT | Melihat produk dengan filter nama |
| PUT | `/product/{id}` | JWT | Mengubah produk |
| DELETE | `/product/{id}` | JWT | Menghapus produk |
| POST | `/scheduler/import` | JWT | melakukan insert produk yang di trigger dari scheduler |
