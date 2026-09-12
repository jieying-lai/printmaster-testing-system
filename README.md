<div align="center">

  # 🖨️ PrintMaster Service & Automated Testing Suite

  **A robust printing service management system backed by comprehensive unit, integration, and parameterized testing suites.**

  [![Java](https://img.shields.io/badge/Java-8%2B-orange?style=flat-square&logo=openjdk&logoColor=white)](#)
  [![JUnit 4](https://img.shields.io/badge/JUnit-4-25A162?style=flat-square&logo=junit5&logoColor=white)](#)
  [![JUnitParams](https://img.shields.io/badge/JUnitParams-Parameterized-blue?style=flat-square)](#)
  [![Mockito](https://img.shields.io/badge/Mockito-Test%20Doubles-brightgreen?style=flat-square)](#)
  [![Coursework](https://img.shields.io/badge/Coursework-Software%20Testing-lightgrey?style=flat-square)](#)

</div>

---

## 📌 Project Overview

**PrintMaster** is a commercial printing management solution engineered to automate order intake, cost computation, customer records, and invoice generation. 

This repository implements the core business logic alongside an extensive automated test suite developed for the **UECS2354 Software Testing** coursework at **Universiti Tunku Abdul Rahman (UTAR)**. The project emphasizes formal test design methodologies, defensive input validation, and test double orchestration.

---

## 🧪 Testing Engineering & Methodologies

The core value of this repository lies in its systematic test design and execution coverage:

* **Black-Box Test Design**:
  * **Equivalence Partitioning (EP)**: Segmenting valid and invalid input classes across paper dimensions, page thresholds, and copy volumes.
  * **Boundary Value Analysis (BVA)**: Precision testing at system critical boundaries (e.g., minimum 1 page/copy, upper bound of 500 pages and 1,000 copies).
  * **Decision Table Testing**: Mapping combinational business rules for cumulative discounts (Student, Corporate, Order Subtotal > RM300, Loyalty > 20 orders).
* **Automated Test Automation**:
  * **Parameterized Testing**: Employed `JUnitParams` to feed multi-variable data tables and external text-file datasets directly into test runners.
  * **Test Doubles via Mockito**: Simulated external, uncoupled dependencies—specifically mocking `printerAvailability` services to verify fault recovery without operational hardware.
  * **Integration Testing**: Verified end-to-end transaction state flow across registration, cost calculation, and invoice output modules.

---

## ⚙️ Business Rules & Calculation Matrix

The system calculates total charges based on base rates, optional additions, and sequentially applied discounts:

$$\text{Base Charge} = \text{Base Rate} \times \text{Pages} \times \text{Copies}$$

* **Base Rate Matrix (per page)**:
  * **A4**: B&W (RM0.20 single / RM0.18 double), Colour (RM0.80 single / RM0.75 double)
  * **A3**: B&W (RM0.40 single / RM0.35 double), Colour (RM1.50 single / RM1.40 double)
  * **A5**: B&W (RM0.15 single / RM0.13 double), Colour (RM0.60 single / RM0.55 double)
* **Add-on Services**: Staple (RM2.00), Comb (RM5.00), Spiral (RM8.00), Lamination (RM1.50 per total page), Express Delivery (RM20.00).
* **Discounts (Applied Sequentially)**:
  * Student (10%) or Corporate (15%)
  * High-value subtotal > RM300 (+5%)
  * Loyalty program > 20 previous orders (+5%)

---
