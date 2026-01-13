# UM VISA MATE: Non-Functional Requirements (N.F.R.)

While Functional Requirements (F.R.) define what the app must do, Non-Functional Requirements (N.F.R.) define how well the app must perform those functions.

## 1. Usability and Accessibility (Crucial for SDG 10)
These requirements ensure the app is easy to use for all international students, regardless of their native language, technical skills, or physical impairments.

| N.F.R. No. | Requirement Type | Description | Measurement & Metric |
| :--- | :--- | :--- | :--- |
| 1.1 | Accessibility (WCAG AA) | The application's UI elements (text, buttons, icons) must meet a minimum WCAG AA contrast ratio (e.g., 4.5:1 for normal text). This includes using the specified UM High Contrast color palette. | Metric: All key text and UI components pass a color contrast checker test. |
| 1.2 | Intuitiveness | 90% of first-time users (students) must be able to successfully locate the "Start Renewal" button and the Visa Status Tracker within 10 seconds of logging in. | Metric: Completion time (T) on a usability test (T ≤ 10 seconds). |
| 1.3 | Multilingual Support (Simulated) | All static text labels and guidelines must be coded to support simple language switch toggle that successfully loads alternative string resources. | Metric: Presence of a language switch toggle at that successfully loads alternative string resources. |
| 1.4 | Form Clarity & Error Prevention | Form fields must provide in-line hints and validation. An error message must clearly explain what is wrong and how to fix it, preventing incomplete submissions. | Metric: Zero applications can be submitted to the Visa Unit with a missing required document. |

## 2. Performance and Efficiency
These requirements ensure the app is fast and reliable, especially when handling document uploads.

| N.F.R. No. | Requirement Type | Description | Measurement & Metric |
| :--- | :--- | :--- | :--- |
| 2.1 | Application Load Time | The main Dashboard (F.R. 1.3) must load and display the student's current status and profile information in under 3 seconds upon successful login. | Metric: Average Load Time (T) ≤ 3.0 seconds. |
| 2.2 | Document Upload Speed | The time taken to upload a standard 1MB PDF document must be completed (or begin processing) in under 5 seconds on a standard 4G connection (simulated). | Metric: Time to receive server acknowledgement (T) ≤ 5.0 seconds. |
| 2.3 | Response Time (Visa Officer) | The Visa Officer Dashboard (F.R. 4.1) must update the list of pending applications instantly (within 1 second) after a student submits their request. | Metric: Data update latency (L) ≤ 1.0 second. |

## 3. Security and Data Integrity
These requirements are critical for handling sensitive documents (passports, financial records, academic transcripts).

| N.F.R. No. | Requirement Type | Description | Measurement & Metric |
| :--- | :--- | :--- | :--- |
| 3.1 | Data Encryption | All sensitive data transmitted between the app (client) and the server (simulated database) must be secured using HTTPS (SSL/TLS). | Metric: All communication uses a secure protocol (e.g., https:// endpoint). |
| 3.2 | Role-Based Access Control (RBAC) | The app must strictly enforce separate access roles: Ahmed (Student), Faculty Officer, and Visa Unit Officer. Ahmed must never be able to access the Faculty Upload Portal or the Officer Review Dashboard. | Metric: Successful login using a Student role fails to render Officer-specific screens. |
| 3.3 | Input Sanitization | All user inputs (e.g., in the profile or communication channel) must be sanitized to prevent malicious code injection (e.g., XSS attacks). | Metric: Attempting to submit HTML/script tags as input results in a cleaned or rejected submission. |

## 4. Maintainability and Extensibility
These requirements pertain to the long-term health and growth of the application by your team and the university.

| N.F.R. No. | Requirement Type | Description | Measurement & Metric |
| :--- | :--- | :--- | :--- |
| 4.1 | Code Standard | The source code must adhere to clean coding practices (e.g., Kotlin Style Guide, clear naming conventions, comprehensive comments) to allow new developers to understand the project quickly. | Metric: All team members confirm that the code for another member's module can be understood in under 2 hours. |
| 4.2 | Modular Architecture | The application must maintain clear separation of concerns, ensuring that changes in one module do not break the final submission logic (Module 3). | Metric: The four modules are implemented as distinct classes/packages with well-defined interfaces. |
| 4.3 | Platform Compatibility | The mobile application must be tested to ensure full functionality on multiple Android OS versions (e.g., Android 7.0/API 24 up to the latest version). | Metric: Successful testing on a minimum of two distinct Android API levels. |
