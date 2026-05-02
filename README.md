# Vehicle Maintenance Scheduler & Notification System

## Overview

This repository contains the implementation of a backend assignment consisting of two major components:

1. **Vehicle Maintenance Scheduler** – Optimizes task allocation across depots using a dynamic programming approach (0/1 Knapsack).
2. **Notification System Design** – A conceptual design for a scalable and reliable notification system.

The project demonstrates skills in API integration, authentication handling, algorithm design, and system architecture.

---

## Part 1: Vehicle Maintenance Scheduler

### Objective

To select an optimal subset of vehicle maintenance tasks for each depot such that:

* The total duration does not exceed the available mechanic hours.
* The total impact score is maximized.

---

### Key Features

* Secure API integration using Bearer token authentication
* JSON parsing using `org.json` library
* Implementation of **0/1 Knapsack Algorithm**
* Backtracking to retrieve selected tasks
* Constraint validation (duration ≤ capacity)

---

### Algorithm Details

* **Technique:** Dynamic Programming (0/1 Knapsack)
* **Time Complexity:** O(n × capacity)
* **Approach:**

  * Build DP table to compute maximum achievable impact
  * Backtrack to determine selected tasks

---

### Execution Flow

```text
Authentication → Fetch API Data → Parse JSON → Apply Knapsack → Generate Output
```

---

### Sample Output

```text
Depot 1
Capacity: 60
Selected Tasks:
...
Total Duration: 60
Max Impact: 133
```

---

### Output Screenshot

<img width="1920" height="1080" alt="Screenshot 2026-05-02 114609" src="https://github.com/user-attachments/assets/a9c9cfd8-caa1-44f1-83e1-997f3a46d2b8" />
<br>
<img width="1920" height="1080" alt="Screenshot 2026-05-02 114619" src="https://github.com/user-attachments/assets/5bdcd34a-0c42-4772-a7ac-39f242d7bca9" />
<br>
<img width="1920" height="1080" alt="Screenshot 2026-05-02 114627" src="https://github.com/user-attachments/assets/0be0d4f6-ccd3-4d27-9829-a85eda7220d3" />
<br>
<img width="1920" height="1080" alt="Screenshot 2026-05-02 114639" src="https://github.com/user-attachments/assets/59c40753-8184-4fec-971d-4594ca2c1364" />





---

## Part 2: Notification System Design

The detailed design is documented in:

```text
notification_system_design.md
```

---

### Key Concepts Covered

* REST API design for notifications
* Database schema with indexing strategies
* Query optimization techniques
* Performance improvements (caching, pagination)
* Reliable messaging using queue-based systems
* Priority-based notification ranking

---

## Technology Stack

* **Language:** Java
* **Networking:** Java HTTP Client
* **Data Handling:** JSON (org.json)
* **Algorithms:** Dynamic Programming
* **Documentation:** Markdown

---

## Project Structure

```text
vehicle-notification-assignment/
 ├── VehicleScheduler.java
 ├── lib/
 │    └── json-20240303.jar
 ├── vehicle_scheduler/
 │    └── output.png
 ├── notification_system_design.md
 ├── README.md
```

---

## Challenges and Solutions

| Challenge              | Solution                                  |
| ---------------------- | ----------------------------------------- |
| Secured API access     | Implemented Bearer token authentication   |
| JSON data handling     | Used structured parsing via org.json      |
| Optimal task selection | Applied Knapsack dynamic programming      |
| Data consistency       | Validated duration and impact correctness |

---

### Output Screenshot

<img width="1541" height="1032" alt="image" src="https://github.com/user-attachments/assets/7de984a7-2536-466e-b5f2-c2c5c1773b05" />






---


## Learning Outcomes

* Practical experience with authenticated APIs
* Implementation of dynamic programming in real-world problems
* Understanding of scalable backend system design
* Exposure to performance optimization techniques

---

## Author

**Aayush Verma**
