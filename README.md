# Web Rectangles

Игра "Квадраты" - консольная игра для двух игроков, где цель состоит в том, чтобы первым создать квадрат из своих фишек на доске. Квадрат может быть любого размера и в любой ориентации.

## Сборка проекта

```bash
mvn clean compile
```

## Запуск игры

###  Консольная игра
```bash
mvn exec:java "-Dexec.mainClass=com.example.Main"
```

##  Web-сервис
```bash
mvn exec:java "-Dexec.mainClass=com.example.WebMain"
```

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

Web-сервис предоставляет REST API для вычисления ходов:

### Эндпоинты

- **POST /api/move** - Вычислить следующий ход
- **POST /api/getStatus** - Проверить статус игры

### Формат запроса

```json
{
  "size": 5,
  "data": "       b   w w   b       ",
  "nextPlayerColor": "b"
}
```

### Формат ответа для хода

```json
{
  "success": true,
  "message": "Move calculated successfully",
  "move": {
    "x": 2,
    "y": 1,
    "color": "b"
  }
}
```

### Формат ответа для статуса

```json
{
  "success": true,
  "message": "Status retrieved successfully",
  "status": "running",
  "winner": "",
  "gameOver": false
}
```

### Пример использования

```bash
# Получить следующий ход
curl -X POST http://localhost:8080/api/move \
  -H "Content-Type: application/json" \
  -d '{
    "size": 5,
    "data": "       b   w w   b       ",
    "nextPlayerColor": "b"
  }'

# Проверить статус игры
curl -X POST http://localhost:8080/api/getStatus \
  -H "Content-Type: application/json" \
  -d '{
    "size": 5,
    "data": "       b   w w   b       ",
    "nextPlayerColor": "b"
  }'
```

## Запуск тестов

```bash
mvn test
```
