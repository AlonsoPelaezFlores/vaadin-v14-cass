# Vaadin CASS

## Prerequisites

- [Docker Desktop](https://www.docker.com/products/docker-desktop/) (Windows/macOS) or Docker Engine + Docker Compose (Linux)
- Recommended version: Docker **29.2.1**
- **Minimum 4 GB of RAM allocated to Docker** (6 GB recommended)

> ⚠️ Insufficient RAM may cause the container to fail during the Vaadin build.

## Quick Start
```bash
# 1. Clone the repository
git clone https://github.com/AlonsoPelaezFlores/vaadin-v14-cass.git
cd vaadin-v14-cass

# 2. Build and start
docker-compose up --build
```

## Local Development (without Docker)

You can run only the database via Docker and the app directly from your IDE or terminal.

**1. Start the database**
```bash
  docker-compose up db_service
```

**2. Prepare frontend** *(only required on first clone or after a clean)*
```bash
  mvn vaadin:prepare-frontend
```

**3. Run the application**
```bash
  mvn spring-boot:run
```

Once the logs confirm Spring Boot has started, open:  
**http://localhost:8080**

## Tech Stack

| Component      | Version              |
|----------------|----------------------|
| Java (JDK)     | 11 (Eclipse Temurin) |
| Node.js        | 16.20.2              |
| Maven          | 3.8.8                |
| PostgreSQL     | 16 (Bookworm)        |
| Vaadin         | 14.10.1              |
| Spring Boot    | 2.6.6                |

## Database

To connect with an external client (DBeaver, pgAdmin, etc.) while the containers are running:

| Parameter | Value             |
|-----------|-------------------|
| Host      | `localhost`       |
| Port      | `5432`            |
| Database  | `vaadin_db`       |
| User      | `vaadin_user`     |
| Password  | `vaadin_password` |



## Tasks

This document tracks the tasks decided in our last meeting. Each task has its own branch following the `feature/task-name` convention.

---

### 1. Base Mock
**Branch:** `main`

Implements page mocks to achieve functional navigation with action buttons, forms and tables with their respective structure. They include a basic design that will serve as a base for the implementation of each feature

---

### 2. IBAN Validator and Global Validations
**Branch:** `feature/migrate-validations`

Implements form validations including IBAN verification and reusable field validators for use across the application.

---

### 3. Import and Export XML Files
**Branch:** `feature/xml-import-export`

Management XML file for import and export.

---

### 4. Error Export to Excel and PDF
**Branch:** `feature/error-export-excel-pdf`

Allow users to download error reports in both Excel and PDF formats.

---

### 5. Signature Declaration PDF Generation
**Branch:** `feature/signature-declaration-pdf`

Generate a PDF document containing the signature declaration based on form data.

---

### 6. Alfresco Dockerized - Upload and Download Documents
**Branch:** `feature/alfresco-dockerized`

Set up Alfresco in Docker and implement document upload and download functionality.
 
---
 
