Setup Instructions – MediTrack Project
1. Overview

This document explains how to set up the Java development environment required to run the MediTrack Hospital Management System project.

The project is built using Java (JDK 17 or higher) and runs through the command line or IntelliJ IDEA.

2. System Requirements
Requirement	Version
Operating System	Windows / MacOS / Linux
Java Development Kit (JDK)	JDK 17 or higher
IDE (Optional)	IntelliJ IDEA Community Edition
Version Control	Git
Build Tool	Command Line (javac/java)
3. Install Java (JDK)
Step 1: Download JDK

Download the latest JDK from:

https://www.oracle.com/java/technologies/javase-downloads.html

or

https://adoptium.net/

Recommended Version:

JDK 17 or above
Step 2: Install JDK

Run the installer and follow the setup instructions.

Example installation path (Windows):

C:\Program Files\Java\jdk-17
4. Configure JAVA_HOME
Windows Setup

Open Environment Variables

Click New System Variable

Variable Name

JAVA_HOME

Variable Value

C:\Program Files\Java\jdk-17
Add Java to PATH

Edit the Path variable and add:

%JAVA_HOME%\bin
5. Verify Java Installation

Open Command Prompt and run:

java -version

Example output:

java version "17.0.9"
Java(TM) SE Runtime Environment
Java HotSpot(TM) 64-Bit Server VM

Check Java compiler:

javac -version

Output example:

javac 17.0.9
6. Install IntelliJ IDEA (Recommended)

Download IntelliJ Community Edition:

https://www.jetbrains.com/idea/download/

Steps:

Install IntelliJ IDEA

Open IntelliJ

Select New Project

Choose Java

Select installed JDK

7. Clone the MediTrack Repository

Use Git to clone the project:

git clone https://github.com/<your-username>/MediTrack.git

Navigate into project directory:

cd MediTrack
8. Project Folder Structure

Expected project structure:

MediTrack
│
├── docs
│   ├── Setup_Instructions.md
│   └── JVM_Report.md
│
├── src
│   └── com
│       └── airtribe
│           └── meditrack
│               ├── entity
│               ├── service
│               ├── util
│               ├── exception
│               ├── constants
│               ├── interface
│               └── test
│
└── Main.java
9. Compile the Project

Navigate to the src folder.

Compile the project:

javac com/airtribe/meditrack/Main.java

This will compile all dependent classes.

10. Run the Application

Run the application using:

java com.airtribe.meditrack.Main
11. Console Menu (Example)

When the program runs, the console menu will appear:

===== MediTrack Hospital Management System =====

1. Add Patient
2. Add Doctor
3. Create Appointment
4. View Appointments
5. Cancel Appointment
6. Generate Bill
7. Search Patient
8. Exit

Enter your choice:
12. Load Data (Optional)

If the application supports persisted data loading:

java com.airtribe.meditrack.Main --loadData

This loads saved CSV or serialized data.

13. Git Workflow

Basic Git workflow for development:

Clone repository

git clone <repo-url>

Create branch

git checkout -b feature-name

Commit changes

git add .
git commit -m "Added patient service"

Push changes

git push origin feature-name
14. Screenshots Required for Submission

The following screenshots should be included in this document:

Java installation confirmation (java -version)

IntelliJ project setup

Project folder structure

Application running in terminal

15. Author

Name: Jayanth P
Project: MediTrack – Java Hospital Management System
