# UM VISA MATE: Functional Requirements

## Module 1: Authentication & Status Hub (The Gateway)
**Objective:** To provide a secure entry point and a clear, personalized, real-time status tracker for the student.

| F.R. No. | Feature Name | Description | User Interface / Screen |
| :--- | :--- | :--- | :--- |
| 1.1 | User Authentication | Secure login (mock) that authenticates the student (Ahmed) and the Faculty/Visa Unit Officer (separate roles). | Login Screen |
| 1.2 | Student Profile Display | Displays essential, non-editable student information (Name, Passport Number, Faculty) fetched from a local data source. | Dashboard Header |
| 1.3 | Visa Status Tracker | A prominent card displaying the application status (e.g., Pre-Application, Waiting for Faculty, Waiting for Payment, Submitted, Approved). | Main Dashboard |
| 1.4 | Renewal Deadline Alert | Logic to display a high-priority banner or push notification when the visa expiry is 3 months away or less. | Main Dashboard |

## Module 2: Digital Document Submission (The Digital Collection Point)
**Objective:** To replace the physical trips and paperwork by providing secure portals for both Ahmed and the Faculty to upload required documents.

| F.R. No. | Feature Name | Description | User Interface / Screen |
| :--- | :--- | :--- | :--- |
| 2.1 | Faculty Document Request | A button that, when pressed by Ahmed, updates the application status and sends a trigger to the Faculty Officer's portal. | Document Submission Screen (Student) |
| 2.2 | Document Checklist (Compliance) | A dynamic list of all required documents (based on the renewal form) that updates status in real-time as files are uploaded. | Document Submission Screen (Student) |
| 2.3 | Student File Upload | Functionality to upload files (e.g., passport photo, scanned passport pages) using the device's file picker or camera. | Document Submission Screen (Student) |
| 2.4 | Faculty Officer Upload Portal | A secured view accessible only to the Faculty role, allowing them to upload the Confirmation Letter and Latest Result Transcript to Ahmed's file. | Faculty Officer Portal |
| 2.5 | Document Guidelines (Advisory) | Information icons next to each required document (F.R. 2.2) that show clear specifications (e.g., photo size, scanning tips) from the renewal form. | Document Submission Screen (Student) |

## Module 3: Integrated Financial & Final Submission
**Objective:** To handle the fee payment digitally and enforce the final submission only after all compliance requirements are met.

| F.R. No. | Feature Name | Description | User Interface / Screen |
| :--- | :--- | :--- | :--- |
| 3.1 | Fee Calculation Display | Displays the correct, total renewal fee (RM) specific to Ahmed’s nationality, as listed in the renewal form's fee table. | Payment Screen |
| 3.2 | Payment Simulation | A screen that simulates a successful payment transaction via a button click, generating a timestamped "Paid" status. | Payment Screen |
| 3.3 | Digital Receipt Generation | Generates a digital record/mock receipt confirming the payment and attaches it to the application file. | Payment Confirmation Screen |
| 3.4 | Final Submission Logic | The "Submit to UM Visa Unit" button is disabled until F.R. 2.2 (Checklist Complete) and F.R. 3.2 (Payment Complete) conditions are both true. | Main Dashboard / Submission Screen |

## Module 4: UM Visa Unit & Communication (The Processor & Feedback Loop)
**Objective:** To provide the tools necessary for the Visa Unit to process applications and ensure transparent, timely communication back to the student.

| F.R. No. | Feature Name | Description | User Interface / Screen |
| :--- | :--- | :--- | :--- |
| 4.1 | Visa Officer Review Dashboard | A dedicated list view (for the Officer role) showing all applications requiring action (e.g., Submitted, Action Required). | Visa Officer Portal |
| 4.2 | Document Review & Action | Allows the Officer to view all submitted documents and change the application status with two options: Approve or Reject/Request Resubmission. | Visa Officer Application Detail View |
| 4.3 | Automated Notification | Critical feature: Triggers a push notification and updates the Status Tracker (F.R. 1.3) immediately when the Visa Officer changes the status. | Student Device / Dashboard |
| 4.4 | In-App Communication Channel | A simple ticketing system or contact form allowing Ahmed to send direct, tracked queries to the UM Visa Unit. | Help/Contact Screen (Student) |
