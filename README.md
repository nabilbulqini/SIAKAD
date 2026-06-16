## 🚀 Key Features & Implementation Details

This application implements core academic workflows tailored to demonstrate high-quality object-oriented design and robust business logic without external database dependencies:

* **Role-Based Access Control (RBAC):** 
  Secured login mechanism separating system privileges. The `Admin` role is granted full operational access to master data management (Students, Lecturers, and Courses), while the `Operator` role is restricted to transaction processing and personal credential updates.
* **Many-to-Many Academic Association (KRS Modality):**
  A robust simulation of relational databases where the `KRS` object acts as an associative entity bridging `Mahasiswa`, `Dosen`, and `Matakuliah` objects simultaneously, showcasing clean object collaboration.
* **Automated Aggregate Grading System:**
  Implements structured business logic to process student performance. It calculates the final grades automatically based on realistic institutional weightings: **30% Midterm Exam (UTS)**, **20% Assignments (Tugas)**, **40% Attendance (Absensi)**, and **10% Final Exam (UAS)**, converting numerical metrics into standard alphabetical grades (A to E).
* **Real-time Institutional Validation:**
  Prevents academic data anomaly by strictly enforcing a maximum threshold of **24 SKS** per semester. Any attempt to breach this constraint automatically triggers an intuitive graphical error dialog using `JOptionPane`.
* **State Management & In-Memory Storage:**
  Utilizes fixed-size object arrays (capped at a maximum of 5 records per entity) managed via a centralized `MockDatabase` controller, ensuring persistent data availability across different GUI frames during runtime.
