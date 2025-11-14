Fee Reminder System - Standalone GUI

A Java Swing application to manage student fee reminders.  
It generates detailed fee receipts, saves them as text files, and shows desktop notifications for pending fees.



Features
- Enter student details: Name, Roll No, Father’s Name, Phone, Year of Passing.
- Select Stream and optional facilities (Hostel, Bus).
- Calculates total fee automatically.
- Generates and displays fee receipt.
- Saves receipts in `receipts/` folder.
- Desktop notifications for fee reminders.
- Print receipt functionality.
- Reset form to add new students.


Project Structure
Fee-Payment-Reminder-App/
│
├── src/
│ └── FeeReminderSystem.java
├── assets/
│ └── screenshots/
│ ├── gui.png
│ ├── receipt.png
│ └── notification.png
├── receipts/ # Auto-generated receipts
├── README.md
├── LICENSE
└── .gitignore


Installation & Running

1. Clone the repository
git clone <https://github.com/Keekee0207/FeeReminderSystem>
cd Fee-Payment-Reminder-App/src

2.Compile
javac FeeReminderSystem.java

3.Run
java FeeReminderSystem

Usage

Fill all student details.
Select stream and optional facilities.
Click Generate Receipt → receipt displayed and saved.
Click Print Receipt to print.
Desktop notification will appear with total fee.
Click Reset for new entries.

Sample Output
Receipt Example

Fee Receipt:
Student Name: John Doe
Father's Name: Mark Doe
Roll Number: 101
Phone Number: 9876543210
Year of Passing: 2025
Stream: Engineering
Base Fee: ₹60000
Hostel Fee: ₹10000
Bus Fee: ₹5000
Total Fee to be Paid: ₹75000

Notification:
"Fee Reminder: ₹75000 due for John Doe"






