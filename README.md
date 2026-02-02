## 🚀 Features Implemented

- 🔐 Short URL generation using **Base62 encoding**
- 🌍 Root-level redirection (`/{shortCode}`) with HTTP redirect
- ⏳ Optional **URL expiration**
- ✅ Input validation using DTOs
- ⚠️ Global exception handling
- 🧱 Clean layered architecture (Controller / Service / Repository)
- 🗄️ MySQL persistence using Spring Data JPA & Hibernate
- 🔁 Single-write, constraint-safe database design
- 📊 Click count analytics
- 🚦 Rate limiting
- ⚡ Redis caching for ultra-fast redirects 

---

## 🧠 Design Highlights

- 🔀 Public short URLs are **not versioned** (`/{code}`), APIs are versioned (`/api/v1`)
- 🧩 Short codes generated **before persistence** to respect DB constraints
- 🧹 Expired URLs are softly invalidated (no hard deletes)
- ⚡ Designed to be **cache-first** (Redis planned)
- 🎯 Focused on trade-offs and correctness over over-engineering

---

## 🛠 Tech Stack

- ☕ Java 17  
- 🌱 Spring Boot 3  
- 🌐 Spring Web  
- 🧬 Spring Data JPA  
- 🐬 MySQL  
- 🧪 Hibernate  
- 📦 Maven  

---

## 📡 API Endpoints

### ➕ Create Short URL

Request

```json
{
  "originalUrl": "https://www.google.com",
  "expiryMinutes": 10
}
```

Response

```json
{
  "shortUrl": "http://localhost:8080/abc123"
}
```

---

## 🔁 Redirect

GET /{shortCode}

Behavior

🚀 Redirects to original URL  
❌ Returns error if URL is expired or not found

---

## 🎯 Learning Focus

This project was built as a system design learning exercise, emphasizing:

🧠 Backend fundamentals  
🗃️ Data modeling & DB constraints  
🧩 Clean service-layer logic  
✅ Validation & error handling  
💥 Real-world failure scenarios  

---

## 🔗 Repository

👉 https://github.com/neeraj552/urlShortner
