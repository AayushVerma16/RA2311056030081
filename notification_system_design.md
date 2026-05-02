# 🔔 Notification System Design

## 📌 Overview

This system is designed to handle notifications for students in a scalable and efficient manner. It ensures reliable delivery, fast retrieval, and prioritization of important notifications.

---

# 🟢 Stage 1: API Design

### APIs:

#### 1. Get Notifications

```http
GET /notifications?userId=123
```

#### 2. Create Notification

```http
POST /notifications
```

#### 3. Mark as Read

```http
PATCH /notifications/{id}/read
```

---

### Example JSON:

```json
{
  "id": "123",
  "type": "Placement",
  "message": "Company hiring for SDE role",
  "isRead": false,
  "timestamp": "2026-04-25T10:30:00"
}
```

---

### Real-time Updates:

* WebSockets can be used for instant notifications
* Alternatively, Kafka or Pub/Sub can be used for event-driven updates

---

# 🟢 Stage 2: Database Design

### Table Structure:

```sql
CREATE TABLE notifications (
  id UUID PRIMARY KEY,
  studentId INT,
  type VARCHAR(50),
  message TEXT,
  isRead BOOLEAN,
  createdAt TIMESTAMP
);
```

---

### Challenges at Scale:

* Large number of records (millions of rows)
* Slow queries without indexing

---

### Solutions:

* Add indexing on frequently queried fields
* Use partitioning (by date or studentId)
* Archive old notifications

---

# 🟢 Stage 3: Query Optimization

### Problem Query:

```sql
SELECT * FROM notifications
WHERE studentID = 1042 AND isRead = false
ORDER BY createdAt DESC;
```

---

### Issues:

* Full table scan (slow)
* Sorting large data

---

### Optimized Query (with Index):

```sql
CREATE INDEX idx_notifications
ON notifications(studentID, isRead, createdAt DESC);
```

---

### Complexity:

* Before: O(n)
* After: O(log n)

---

# 🟢 Stage 4: Performance Improvements

### Techniques:

* Pagination (LIMIT & OFFSET)
* Redis caching for frequent queries
* Lazy loading (load more on scroll)
* Pre-fetching important notifications

---

# 🟢 Stage 5: Reliable Notification System

### Problem:

If notification sending fails (email/push), data may be inconsistent.

---

### Solution:

Use a queue-based system (Kafka / RabbitMQ)

### Flow:

1. Save notification in database
2. Push event to queue
3. Worker processes event
4. Send email/push notification

---

### Benefits:

* No data loss
* Retry mechanism
* Scalable system

---

# 🟢 Stage 6: Priority Inbox Algorithm

### Goal:

Show most important notifications at the top

---

### Priority Calculation:

Priority Score = Weight(type) + Recency

---

### Example Weights:

* Placement → 3
* Result → 2
* Event → 1

---

### Java Logic:

```java
int getScore(Notification n) {
    int weight = switch(n.type) {
        case "Placement" -> 3;
        case "Result" -> 2;
        default -> 1;
    };

    return weight * 100 + n.timestamp;
}
```

---

### Sorting:

```java
notifications.sort((a, b) -> getScore(b) - getScore(a));
```

---

### Output:

Return top 10 notifications

---

# 🚀 Conclusion

This system ensures:

* Efficient storage and retrieval
* Scalable architecture
* Reliable delivery
* Smart prioritization of notifications
