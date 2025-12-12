# BankGUI
A command-line banking system built in Java that simulates core banking functionality, including account creation, deposits, withdrawals, transfers, and transaction history. The system features secure PIN-based authentication.

This project demonstrates object-oriented design, basic security practices, and clean separation of concerns in a Java application.

**Features:** \
• Create Checking and Savings accounts\
• Secure PIN login system (4–6 digits, hashed + salted) \
• Deposit, withdraw, and transfer funds between accounts \
• View account balance and full transaction history \
• Change PIN after authentication \
• Input validation and error handling (no overdrafts, no invalid amounts) \
• Clean OOP architecture with extensible account types 

**Tech Stack:** \
Language: Java 

**Concepts:** 
• Object-Oriented Programming (inheritance, abstraction) \
• Secure credential handling (SHA-256 + salt) \
• Command-line interface (CLI) design \
• Data modeling and validation 

**How to run:** 
```
javac *.java
java Main
```

**Why this project?** \
This project was built to practice core Java fundamentals, secure authentication concepts, and real-world system design patterns commonly found in financial software. It can be easily extended with features such as persistent storage, interest calculations, overdraft protection, or a REST API.
