# UM VISA MATE - Project Directory Structure

## 📁 Complete Project Layout

```
UM VISA MATE/
│
├── 📄 README.md
├── 📄 APP_IS_RUNNING.md
├── 📄 IMPLEMENTATION_SUMMARY.md
├── 📄 DESIGN_CHECKLIST.md
├── 📄 PROJECT_STRUCTURE.md (this file)
│
├── 📄 build.gradle.kts                     # Root build configuration
├── 📄 settings.gradle.kts                  # Project settings
├── 📄 gradle.properties                    # Gradle properties
├── 📄 gradlew                              # Gradle wrapper (Unix)
├── 📄 gradlew.bat                          # Gradle wrapper (Windows)
├── 📄 local.properties                     # Local SDK path
│
├── 📂 gradle/
│   ├── 📂 wrapper/
│   │   ├── gradle-wrapper.jar
│   │   └── gradle-wrapper.properties
│   └── 📄 libs.versions.toml              # Dependency versions
│
└── 📂 app/
    │
    ├── 📄 build.gradle.kts                 # App module build config
    ├── 📄 proguard-rules.pro              # ProGuard rules
    │
    ├── 📂 src/
    │   │
    │   ├── 📂 main/
    │   │   │
    │   │   ├── 📄 AndroidManifest.xml     # App manifest
    │   │   │
    │   │   ├── 📂 java/com/um/visamate/
    │   │   │   │
    │   │   │   ├── 📂 ui/                 # UI Layer (Activities)
    │   │   │   │   │
    │   │   │   │   ├── 📂 login/
    │   │   │   │   │   └── 📄 LoginActivity.kt
    │   │   │   │   │
    │   │   │   │   ├── 📂 dashboard/
    │   │   │   │   │   └── 📄 DashboardActivity.kt
    │   │   │   │   │
    │   │   │   │   ├── 📂 documents/
    │   │   │   │   │   └── 📄 DocumentSubmissionActivity.kt
    │   │   │   │   │
    │   │   │   │   ├── 📂 payment/
    │   │   │   │   │   └── 📄 PaymentActivity.kt
    │   │   │   │   │
    │   │   │   │   ├── 📂 faculty/
    │   │   │   │   │   └── 📄 FacultyPortalActivity.kt
    │   │   │   │   │
    │   │   │   │   ├── 📂 officer/
    │   │   │   │   │   └── 📄 OfficerDashboardActivity.kt
    │   │   │   │   │
    │   │   │   │   ├── 📂 profile/
    │   │   │   │   │   └── 📄 ProfileActivity.kt
    │   │   │   │   │
    │   │   │   │   └── 📂 settings/
    │   │   │   │       └── 📄 SettingsActivity.kt
    │   │   │   │
    │   │   │   ├── 📂 data/               # Data Layer
    │   │   │   │   └── 📂 models/
    │   │   │   │       ├── 📄 User.kt
    │   │   │   │       ├── 📄 Role.kt
    │   │   │   │       ├── 📄 Submission.kt
    │   │   │   │       ├── 📄 SubmissionStatus.kt
    │   │   │   │       └── 📄 Attachment.kt
    │   │   │   │
    │   │   │   └── 📂 utils/              # Utilities
    │   │   │       ├── 📄 FakeDatabase.kt
    │   │   │       ├── 📄 RoleManager.kt
    │   │   │       └── 📄 MockNetworkClient.kt
    │   │   │
    │   │   └── 📂 res/                    # Resources
    │   │       │
    │   │       ├── 📂 layout/             # XML Layouts
    │   │       │   ├── 📄 activity_login.xml
    │   │       │   ├── 📄 activity_dashboard.xml
    │   │       │   ├── 📄 activity_document_submission.xml
    │   │       │   ├── 📄 activity_payment.xml
    │   │       │   ├── 📄 activity_faculty_portal.xml
    │   │       │   ├── 📄 activity_officer_dashboard.xml
    │   │       │   ├── 📄 activity_profile.xml
    │   │       │   ├── 📄 activity_settings.xml
    │   │       │   └── 📄 nav_header.xml
    │   │       │
    │   │       ├── 📂 values/             # Values Resources
    │   │       │   ├── 📄 colors.xml      # Color definitions
    │   │       │   ├── 📄 strings.xml     # String resources
    │   │       │   └── 📄 themes.xml      # App themes
    │   │       │
    │   │       ├── 📂 values-night/       # Dark theme
    │   │       │   └── 📄 themes.xml
    │   │       │
    │   │       ├── 📂 drawable/           # Drawable resources
    │   │       │   ├── 📄 ic_launcher_background.xml
    │   │       │   ├── 📄 ic_launcher_foreground.xml
    │   │       │   └── 📄 circle_emblem_bg.xml
    │   │       │
    │   │       ├── 📂 mipmap-anydpi-v26/  # Launcher icons
    │   │       │   ├── ic_launcher.xml
    │   │       │   └── ic_launcher_round.xml
    │   │       │
    │   │       ├── 📂 mipmap-hdpi/
    │   │       │   ├── ic_launcher.webp
    │   │       │   └── ic_launcher_round.webp
    │   │       │
    │   │       ├── 📂 mipmap-mdpi/
    │   │       │   ├── ic_launcher.webp
    │   │       │   └── ic_launcher_round.webp
    │   │       │
    │   │       ├── 📂 mipmap-xhdpi/
    │   │       │   ├── ic_launcher.webp
    │   │       │   └── ic_launcher_round.webp
    │   │       │
    │   │       ├── 📂 mipmap-xxhdpi/
    │   │       │   ├── ic_launcher.webp
    │   │       │   └── ic_launcher_round.webp
    │   │       │
    │   │       ├── 📂 mipmap-xxxhdpi/
    │   │       │   ├── ic_launcher.webp
    │   │       │   └── ic_launcher_round.webp
    │   │       │
    │   │       ├── 📂 menu/               # Menu resources
    │   │       │   └── 📄 drawer_menu.xml
    │   │       │
    │   │       └── 📂 xml/                # XML configs
    │   │           ├── 📄 backup_rules.xml
    │   │           └── 📄 data_extraction_rules.xml
    │   │
    │   ├── 📂 androidTest/                # Instrumented tests
    │   │   └── 📂 java/com/example/umvisamate/
    │   │       └── 📄 ExampleInstrumentedTest.kt
    │   │
    │   └── 📂 test/                       # Unit tests
    │       └── 📂 java/com/example/umvisamate/
    │           └── 📄 ExampleUnitTest.kt
    │
    └── 📂 build/                          # Build outputs (generated)
        ├── 📂 intermediates/
        ├── 📂 outputs/
        │   └── 📂 apk/
        │       └── 📂 debug/
        │           └── 📄 app-debug.apk  # Final APK
        └── 📂 tmp/
```

---

## 📋 File Count Summary

### Source Code (Kotlin)
- **Activities:** 8 files
  - LoginActivity.kt
  - DashboardActivity.kt
  - DocumentSubmissionActivity.kt
  - PaymentActivity.kt
  - FacultyPortalActivity.kt
  - OfficerDashboardActivity.kt
  - ProfileActivity.kt
  - SettingsActivity.kt

- **Data Models:** 5 files
  - User.kt
  - Role.kt
  - Submission.kt
  - SubmissionStatus.kt
  - Attachment.kt

- **Utilities:** 3 files
  - FakeDatabase.kt
  - RoleManager.kt
  - MockNetworkClient.kt

**Total Kotlin Files:** 16

### Layout Files (XML)
- activity_login.xml
- activity_dashboard.xml
- activity_document_submission.xml
- activity_payment.xml
- activity_faculty_portal.xml
- activity_officer_dashboard.xml
- activity_profile.xml
- activity_settings.xml
- nav_header.xml

**Total Layout Files:** 9

### Resource Files
- **Values:** colors.xml, strings.xml, themes.xml (2 versions)
- **Drawables:** 3 files
- **Mipmaps:** 10 files (launcher icons)
- **Menu:** drawer_menu.xml
- **XML Configs:** 2 files

**Total Resource Files:** ~20

---

## 🗂️ Key Directories Explained

### `/app/src/main/java/com/um/visamate/`
Main source code directory containing all Kotlin files.

#### `/ui/` - User Interface Layer
Contains all Activities organized by feature:
- **login/** - Authentication screen
- **dashboard/** - Student main screen
- **documents/** - Document upload screen
- **payment/** - Fee payment screen
- **faculty/** - Faculty officer portal
- **officer/** - Visa unit dashboard
- **profile/** - User profile screen
- **settings/** - App settings screen

#### `/data/models/` - Data Layer
Contains data models and entities:
- **User.kt** - User account model
- **Role.kt** - User role enum (Student, Faculty, Officer)
- **Submission.kt** - Document submission model
- **SubmissionStatus.kt** - Submission status enum
- **Attachment.kt** - File attachment model

#### `/utils/` - Utility Classes
Helper and utility classes:
- **FakeDatabase.kt** - Mock local database
- **RoleManager.kt** - Role-based access control
- **MockNetworkClient.kt** - Mock network operations

### `/app/src/main/res/` - Resources
All app resources (layouts, images, strings, colors).

#### `/layout/` - UI Layouts
XML layout files for each screen matching the design mockups.

#### `/values/` - App Values
- **colors.xml** - Color palette (#163269, #4A7FBA, etc.)
- **strings.xml** - All text strings (localization ready)
- **themes.xml** - Material Design 3 theme configuration

#### `/drawable/` - Vector Graphics
Drawable resources including icons and backgrounds.

#### `/mipmap-*/` - App Icons
Launcher icons in various resolutions (hdpi, mdpi, xhdpi, xxhdpi, xxxhdpi).

---

## 📦 Build Outputs

### `/app/build/outputs/apk/debug/`
Contains the compiled APK file:
- **app-debug.apk** - Installable Android application (ready to run)

---

## 🔧 Configuration Files

### Root Level
- **build.gradle.kts** - Project-level build configuration
- **settings.gradle.kts** - Project settings and module configuration
- **gradle.properties** - Gradle build properties
- **local.properties** - Local SDK path (not version controlled)

### App Level
- **app/build.gradle.kts** - App module build configuration
  - Dependencies (Material Design, Kotlin Coroutines, etc.)
  - Android SDK versions
  - Build types (debug, release)

### Manifest
- **AndroidManifest.xml** - App manifest
  - Package name: `com.um.visamate`
  - Main launcher: LoginActivity
  - All activities declared
  - Permissions (if any)

---

## 📱 Screens to Code Mapping

| Screen Name | Activity File | Layout File |
|-------------|--------------|-------------|
| Login Screen | LoginActivity.kt | activity_login.xml |
| Student Dashboard | DashboardActivity.kt | activity_dashboard.xml |
| Document Submission | DocumentSubmissionActivity.kt | activity_document_submission.xml |
| Fee Payment | PaymentActivity.kt | activity_payment.xml |
| Faculty Portal | FacultyPortalActivity.kt | activity_faculty_portal.xml |
| Visa Unit Dashboard | OfficerDashboardActivity.kt | activity_officer_dashboard.xml |
| Profile | ProfileActivity.kt | activity_profile.xml |
| Settings | SettingsActivity.kt | activity_settings.xml |

---

## 🎨 Design Resources

### Color Palette (colors.xml)
```xml
<!-- Primary Colors -->
#163269 - primary_dark_blue (UM Blue)
#4A7FBA - secondary_steel_blue
#DEB406 - accent_gold

<!-- Status Colors -->
#4CAF50 - green_success
#FF9800 - orange_warning
#F44336 - red_urgent

<!-- Background -->
#ECEFF1 - colorBackground
#FFFFFF - colorSurface
```

### Typography
- Titles: 18-22sp, Bold
- Body: 14-16sp, Regular
- Labels: 11-12sp, Regular

### Spacing
- Screen padding: 16dp
- Element margins: 8-12dp
- Card radius: 8-12dp

---

## 🚀 Quick Navigation

### To modify UI:
```
app/src/main/res/layout/
```

### To modify colors/strings:
```
app/src/main/res/values/
```

### To modify business logic:
```
app/src/main/java/com/um/visamate/
```

### To modify app configuration:
```
app/build.gradle.kts
AndroidManifest.xml
```

---

## 📊 Project Statistics

- **Total Files:** ~60 files
- **Lines of Code:** ~2,000+ lines
- **Kotlin Files:** 16
- **Layout Files:** 9
- **Screens:** 8
- **Activities:** 8
- **Data Models:** 5
- **Utilities:** 3

---

## 🔍 Important Notes

### Version Control
The following should be in `.gitignore`:
- `/build/` - Build outputs
- `/app/build/` - App build outputs
- `local.properties` - Local SDK path
- `*.apk` - APK files (unless releasing)
- `.gradle/` - Gradle cache

### Package Structure
- **Package Name:** `com.um.visamate`
- **Application ID:** `com.um.visamate`

### Minimum Requirements
- **Min SDK:** 24 (Android 7.0)
- **Target SDK:** 34 (Android 14)
- **Compile SDK:** 34

---

## 📧 Team Setup Instructions

### 1. Clone/Copy the project
```bash
cd "/path/to/your/workspace"
# Copy the entire "UM VISA MATE" folder
```

### 2. Open in Android Studio
- File → Open → Select "UM VISA MATE" folder
- Wait for Gradle sync to complete

### 3. Update local.properties
Android Studio will create this automatically, or create manually:
```properties
sdk.dir=/Users/YOUR_USERNAME/Library/Android/sdk
```

### 4. Build the project
```bash
./gradlew clean build
```

### 5. Run the app
- Click Run button (green play icon)
- Select device/emulator
- App launches automatically

---

## ✅ Ready to Share!

This project structure is complete and ready for your team to:
- Clone and build
- Understand the codebase
- Make modifications
- Add new features
- Deploy to devices

**All screens are functional and match the design mockups exactly!**

