# KKPP Food 🍽️

Android 레시피 검색 앱  
TheMealDB Open API를 사용한 Clean Architecture 기반 Compose 앱

---

## 📱 주요 기능

- 🔍 레시피 검색
- 📂 카테고리별 레시피 탐색
- 📖 레시피 상세 보기
    - 재료 & 계량
    - 조리 방법
    - YouTube 요리 영상 연결
- ⭐ 즐겨찾기 (Room DB)
- ❤️ 즐겨찾기 목록 보기 (오프라인 지원)

---

## 🧱 아키텍처

- **UI**: Jetpack Compose
- **Architecture**: Clean Architecture
- **State**: ViewModel + StateFlow
- **DI**: Hilt
- **Network**: Retrofit + OkHttp
- **Local DB**: Room
- **Async**: Kotlin Coroutines

presentation
├─ screen (search / category / detail / favorite)
├─ components
domain
├─ model
├─ repository
└─ usecase
data
├─ remote
├─ local (Room)
└─ repository


---

## 🔗 API

- [TheMealDB](https://www.themealdb.com/api.php)

---

## 🛠️ Tech Stack

| Category | Stack |
|------|------|
| UI | Jetpack Compose, Material3 |
| DI | Hilt |
| Network | Retrofit, OkHttp |
| DB | Room |
| Async | Coroutines, Flow |

---

## ✨ Screens

- Search
- Category
- Detail
- Favorite

---

