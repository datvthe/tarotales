# Tarotales Diagrams

Diagrams cho chức năng **Daily Tarot Card Draw** (Rút bài Tarot hàng ngày).

## Files

1. **usecase_daily_tarot.puml** - Use Case Diagram
2. **erd_tarot.puml** - Entity Relationship Diagram (ERD)
3. **sequence_daily_tarot.puml** - Sequence Diagram

## Cách xem diagrams

### Online
Sử dụng [PlantUML Online Editor](http://www.plantuml.com/plantuml/uml/):
1. Copy nội dung file `.puml`
2. Paste vào editor
3. Xem kết quả

### VS Code
1. Cài extension: **PlantUML** by jebbs
2. Mở file `.puml`
3. Press `Alt+D` để xem preview

### Command line
```bash
# Cài PlantUML
# Windows: choco install plantuml
# Mac: brew install plantuml

# Generate PNG
plantuml usecase_daily_tarot.puml
plantuml erd_tarot.puml
plantuml sequence_daily_tarot.puml
```

## Mô tả chức năng Daily Tarot Card

### Use Case
- User có thể xem và rút bài Tarot mỗi ngày
- Mỗi ngày chỉ được rút 1 lần
- Hệ thống tự động reset vào 6h sáng hàng ngày

### ERD
Database gồm:
- **TarotCard**: Thông tin các lá bài Tarot
- **Element, Planet, Zodiac**: Các yếu tố liên quan
- **SharedPreferences**: Lưu trạng thái đã rút bài chưa

### Sequence
Flow khi user rút bài:
1. Kiểm tra đã rút bài hôm nay chưa (SharedPreferences)
2. Nếu chưa → Query random card từ database
3. Lưu cardId và isOpenToday = true
4. Hiển thị animation lật bài
5. Hệ thống tự động reset vào 6h sáng

## Các chức năng khác trong app

- **Chat**: Chat với AI (Google Gemini) về Tarot
- **Learn**: Học về các lá bài, Element, Planet, Zodiac
- **Topic**: Xem lịch sử các chủ đề đã xem
- **Monthly**: Rút bài Tarot theo tháng
