# Music Recommender System

Микросервисная система для персонализированных рекомендаций музыки на основе Spotify, построенная с использованием Spring Cloud и Kubernetes.

## Архитектура

Система состоит из следующих микросервисов:

| Сервис | Порт | Описание |
|--------|------|----------|
| **API Gateway** | 8080 | Единая точка входа, маршрутизация запросов, JWT аутентификация |
| **Discovery Server** | 8761 | Eureka сервер для обнаружения сервисов |
| **Auth Service** | 8081 | Аутентификация через Spotify OAuth2, управление JWT токенами |
| **User Service** | 8888 | Управление профилями пользователей |
| **Spotify Service** | 8800 | Взаимодействие с Spotify API, кэширование в Redis |
| **Recommendation Service** | 8808 | Алгоритмы рекомендаций на основе аудио-характеристик |

## Технологический стек

- **Java 21**
- **Spring Boot 3.x**
- **Spring Cloud** (Gateway, Netflix Eureka, LoadBalancer)
- **Spring Security** + OAuth2 Client
- **Spring Data JPA** + PostgreSQL
- **Redis** (кэширование)
- **JWT** (аутентификация)
- **Maven** (сборка)
- **Kubernetes** + **Docker** (развертывание)
- **Lombok**

## Предварительные требования

- JDK 21
- Docker и Kubernetes (minikube или кластер)
- Maven
- PostgreSQL (для локальной разработки)
- Redis (для локальной разработки)
- Аккаунт разработчика Spotify

## Настройка Spotify Developer Application

1. Перейдите на [Spotify Developer Dashboard](https://developer.spotify.com/dashboard)
2. Создайте новое приложение
3. Добавьте redirect URI: `http://127.0.0.1:8081/login/oauth2/code/spotify`
4. Скопируйте Client ID и Client Secret

## Локальный запуск

### 1. Клонирование репозитория

```bash
git clone https://github.com/yourusername/music-recommender.git
cd music-recommender
```

### 2. Настройка переменных окружения

Создайте файл `.env` в корне проекта:

```env
# Spotify OAuth
SPOTIFY_CLIENT_ID=your_spotify_client_id
SPOTIFY_CLIENT_SECRET=your_spotify_client_secret

# JWT Secrets
JWT_USER_SECRET=your_user_jwt_secret_base64
JWT_SERVICE_SECRET=your_service_jwt_secret_base64
JWT_SECRET=your_api_gateway_jwt_secret_base64

# PostgreSQL
POSTGRES_DB=postgres
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
```

### 3. Сборка проекта

```bash
mvn clean package
```

### 4. Запуск через Docker Compose (опционально)

```bash
docker-compose up -d
```

### 5. Запуск микросервисов

**Порядок запуска важен!**

1. Discovery Server:
   ```
   cd discovery-server
   mvn spring-boot:run
   ```

2. Остальные сервисы (в любом порядке):
   ```
   cd auth-service
   mvn spring-boot:run -Dspring-boot.run.profiles=dev
   ```

   ```
   cd user-service
   mvn spring-boot:run
   ```

   ```
   cd spotify-service
   mvn spring-boot:run
   ```

   ```
   cd recommendation-service
   mvn spring-boot:run
   ```

   ```
   cd api-gateway
   mvn spring-boot:run
   ```

## Развертывание в Kubernetes

### 1. Сборка Docker образов

```bash
# Для каждого сервиса
docker build -t music-recommender/api-gateway:latest ./api-gateway
docker build -t music-recommender/discovery-server:latest ./discovery-server
docker build -t music-recommender/auth-service:latest ./auth-service
docker build -t music-recommender/user-service:latest ./user-service
docker build -t music-recommender/spotify-service:latest ./spotify-service
docker build -t music-recommender/recommendation-service:latest ./recommendation-service
docker build -t music-recommender/postgresql:latest ./postgresql
docker build -t music-recommender/redis:latest ./redis
```

### 2. Создание secrets

Создайте Kubernetes secrets на основе файлов в директории `k8s/`:

```bash
kubectl apply -f k8s/secret.yaml
kubectl apply -f postgresql/postgres-secret.yaml
```

### 3. Развертывание инфраструктуры

```bash
# PostgreSQL
kubectl apply -f postgresql/postgres-deployment.yaml
kubectl apply -f postgresql/postgres-service.yaml

# Redis
kubectl apply -f redis/redis-deployment.yaml
kubectl apply -f redis/redis-service.yaml
```

### 4. Развертывание микросервисов

```bash
# Discovery Server (запустить первым)
kubectl apply -f discovery-server/k8s/

# Остальные сервисы
kubectl apply -f auth-service/k8s/
kubectl apply -f user-service/k8s/
kubectl apply -f spotify-service/k8s/
kubectl apply -f recommendation-service/k8s/
kubectl apply -f api-gateway/k8s/
```

### 5. Проверка статуса

```bash
kubectl get pods
kubectl get services
```

## API Endpoints

### Auth Service (`/auth`)

| Метод | Endpoint | Описание |
|-------|----------|----------|
| GET | `/auth/login` | Перенаправление на Spotify OAuth |
| POST | `/auth/validate` | Проверка JWT токена |

### User Service (`/api/user`)

| Метод | Endpoint | Описание |
|-------|----------|----------|
| GET | `/api/user/profile` | Получение профиля пользователя |
| PUT | `/api/user/profile` | Обновление профиля |

### Spotify Service (`/api/spotify`)

| Метод | Endpoint | Описание |
|-------|----------|----------|
| GET | `/api/spotify/recent` | Недавно прослушанные треки |
| GET | `/api/spotify/top/tracks` | Топ треков пользователя |
| GET | `/api/spotify/top/artists` | Топ исполнителей |
| GET | `/api/spotify/saved/tracks` | Сохраненные треки |

### Recommendation Service (`/api/recommendations`)

| Метод | Endpoint | Описание |
|-------|----------|----------|
| GET | `/api/recommendations/top3` | Топ-3 рекомендации |

## Безопасность

- **JWT токены**: для пользовательской аутентификации
- **Service-to-service JWT**: для внутренней коммуникации
- **OAuth2**: интеграция с Spotify
- **Разделение секретов**: разные ключи для разных типов токенов

## Кэширование

Redis используется для кэширования:
- Аудио-характеристики треков (24 часа)
- User vectors (10 минут)
- Ответы Spotify API (1-2 минуты)

## Базы данных

PostgreSQL с отдельными базами для каждого сервиса:
- `auth_db` - данные аутентификации
- `user_db` - профили пользователей

## Взаимодействие сервисов

1. Пользователь авторизуется через Auth Service (Spotify OAuth)
2. Auth Service генерирует JWT и синхронизирует данные с User Service
3. API Gateway проверяет JWT и маршрутизирует запросы
4. Spotify Service получает данные через Spotify API, кэширует в Redis
5. Recommendation Service вычисляет рекомендации на основе аудио-характеристик

## Алгоритм рекомендаций

1. Сбор недавно прослушанных треков пользователя
2. Получение аудио-характеристик треков (Spotify API)
3. Вычисление среднего вектора пользователя
4. Нормализация вектора
5. Получение рекомендаций от Spotify
6. Вычисление косинусного сходства между вектором пользователя и треками
7. Возврат топ-3 наиболее похожих треков

## Структура проекта

```
music-recommender/
├── api-gateway/                 # API Gateway сервис
│   ├── src/
│   └── k8s/                     # Kubernetes манифесты
├── discovery-server/            # Eureka сервер
├── auth-service/                # Сервис аутентификации
├── user-service/                # Сервис пользователей
├── spotify-service/             # Сервис Spotify API
├── recommendation-service/       # Сервис рекомендаций
├── postgresql/                   # PostgreSQL конфигурация
│   ├── init/                     # Инициализация БД
│   └── k8s/                      # Kubernetes манифесты
├── redis/                         # Redis конфигурация
│   └── k8s/                      # Kubernetes манифесты
├── k8s/                           # Общие Kubernetes манифесты
├── pom.xml                        # Родительский POM
└── README.md
```

## Тестирование

### Получение JWT токена

```bash
# После OAuth2 авторизации вы получите JWT токен в ответе
curl -X GET http://localhost:8080/auth/login
```

### Запрос к защищенному endpoint

```bash
curl -X GET http://localhost:8080/api/spotify/recent \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Получение рекомендаций

```bash
curl -X GET http://localhost:8080/api/recommendations/top3 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## Устранение неполадок

### Проблемы с Eureka
- Убедитесь, что discovery-server запущен первым
- Проверьте логи: `kubectl logs discovery-server`

### Проблемы с JWT
- Проверьте, что все секреты корректно закодированы в Base64
- Убедитесь, что secrets созданы в Kubernetes

### Проблемы с PostgreSQL
- Проверьте init скрипты в `postgresql/init/`
- Убедитесь, что базы данных созданы: `auth_db`, `user_db`


## Вклад в проект

1. Fork репозитория
2. Создайте ветку для фичи (`git checkout -b feature/amazing-feature`)
3. Commit изменений (`git commit -m 'Add amazing feature'`)
4. Push в ветку (`git push origin feature/amazing-feature`)
5. Откройте Pull Request

