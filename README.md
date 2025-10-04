# 🛒 Noon.com Automation Testing Framework  

A **Selenium + TestNG** automation framework to validate critical e-commerce functionalities on [Noon.com](https://www.noon.com/egypt-en/).  
The framework uses **Page Object Model (POM)** and **Data-Driven Testing (DDT)** with JSON, Excel, and CSV.  

---

## 📌 Features
- Page Object Model (POM) structure  
- TestNG integration for execution & assertions  
- Selenium WebDriver for UI automation  
- Data-Driven Testing (DDT) via:
  - **DataProvider**
  - **Excel (Apache POI)**
  - **JSON (Jackson)**
  - **CSV (Apache Commons CSV)**
- Allure reporting with screenshots on failure  

---

## 📂 Project Structure
```
Noon/
│── src/main/java/
│   ├── pages/        # Page Object classes
│   └── utilities/    # Managers (CSV, Excel, JSON)
│
│── src/test/java/
│   ├── BaseTest.java
│   ├── FirstScenario.java
│   ├── SecondScenario.java
│   ├── ThirdScenario.java
│   └── FourthScenario.java
│
│── src/main/resources/
│   ├── second_scenario.json
│   ├── third_scenario.xlsx
│   └── fourth_scenario.csv
│
└── pom.xml
```

---

## 🧪 Test Scenarios

| # | Scenario | Data Source |
|---|----------|-------------|
| **1** | Price filter validation across all pages | TestNG DataProvider |
| **2** | Add specific products to cart & verify (Headphones) | JSON |
| **3** | Add products from 4 brands (Samsung, Braun, Toshiba, Nilco) & checkout | Excel |
| **4** | Invalid search returns "No results found" | CSV |

---

## ⚙️ Setup & Run

### 1️⃣ Clone the Repo
```bash
git clone https://github.com/<your-username>/Noon.git
cd Noon
```

### 2️⃣ Install Dependencies
```bash
mvn clean install
```

### 3️⃣ Run All Tests
```bash
mvn test
```

### 4️⃣ Run Specific Scenario
```bash
mvn -Dtest=FirstScenario test
mvn -Dtest=SecondScenario test
mvn -Dtest=ThirdScenario test
mvn -Dtest=FourthScenario test
```

---

## 📊 Reports (Allure)

### Generate Report with Maven
```bash
mvn allure:serve
```

### Or Using CLI
Install Allure CLI:  
```bash
winget install -e QAmetaSoftware.Allure
# or: choco install allure-commandline
# or: scoop install allure
```

Run report:
```bash
allure serve target/allure-results
```

📸 Screenshots on failure are saved in `target/screenshots/` and embedded in Allure report.

---

## 🛠 Dependencies
```xml
<dependencies>
    <dependency>
        <groupId>org.seleniumhq.selenium</groupId>
        <artifactId>selenium-java</artifactId>
        <version>4.21.0</version>
    </dependency>
    <dependency>
        <groupId>org.testng</groupId>
        <artifactId>testng</artifactId>
        <version>7.10.0</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>io.qameta.allure</groupId>
        <artifactId>allure-testng</artifactId>
        <version>2.29.0</version>
    </dependency>
    <dependency>
        <groupId>org.apache.poi</groupId>
        <artifactId>poi-ooxml</artifactId>
        <version>5.3.0</version>
    </dependency>
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-csv</artifactId>
        <version>1.11.0</version>
    </dependency>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.17.2</version>
    </dependency>
</dependencies>
```

---

## 👨‍💻 Author
**Khaled Abbas**  
Software Test Engineer | Automation QA | ISTQB CTFL  
🔗 [LinkedIn](https://www.linkedin.com/in/khaled-abbas-qc/)

