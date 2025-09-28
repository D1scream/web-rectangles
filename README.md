# Web Rectangles

Игра "Квадраты" - консольная игра для двух игроков, где цель состоит в том, чтобы первым создать квадрат из своих фишек на доске. Квадрат может быть любого размера и в любой ориентации.

## Требования

- Java 8+
- Maven 3.6+
- Python 3.x (для веб-приложения)

## Сборка проекта

```bash
mvn clean compile
```

## Запуск

### Консольная игра
```bash
mvn exec:java "-Dexec.mainClass=com.example.Main"
```

### Web-сервис (API)
```bash
mvn exec:java "-Dexec.mainClass=com.example.WebMain"
```

### Веб-приложение
```bash
# В папке web-app
python server.py
```

Веб-приложение будет доступно по адресу: http://localhost:3000

## Команды игры

- `exit` - выход из программы
- `help` - показать справку по командам
- `game N, U1, U2` - начать новую игру
  - N - размер доски (целое число > 2)
  - U1 - первый игрок в формате "ТИП ЦВЕТ" (например: "user w" или "comp b")
  - U2 - второй игрок в формате "ТИП ЦВЕТ"
  - Типы игроков: "user" (человек), "comp" (компьютер)
  - Цвета: "w" (белый), "b" (черный)
- `move X, Y` - сделать ход (X, Y - координаты клетки)
- `status` - показать текущее состояние игры

## Web API

Проект включает два компонента:

### 1. REST API (Java)
Web-сервис предоставляет REST API для вычисления ходов:

#### Эндпоинты

- **POST /api/move** - Вычислить следующий ход

### Формат запроса

```json
{
  "size": 5,
  "data": "       b   w w   b       ",
  "nextPlayerColor": "b"
}
```

**Ответ:**
```json
{
  "move": {
    "x": 2,
    "y": 1,
    "color": "b"
  }
}
```

#### Пример использования

```bash
# Получить следующий ход
curl -X POST http://localhost:8080/api/move \
  -H "Content-Type: application/json" \
  -d '{
    "size": 5,
    "data": "       b   w w   b       ",
    "nextPlayerColor": "b"
  }'
```

### 2. Веб-приложение (Python + HTML/CSS/JS)
Интерактивный веб-интерфейс для игры против компьютера.

#### Технологии
- HTML5, CSS3, JavaScript (ES6+)
- Python HTTP Server

## Запуск тестов

```bash
mvn test
```
