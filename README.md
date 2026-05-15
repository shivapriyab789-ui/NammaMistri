# Namma-Mistri (NAMMA-MISTRI)

**Namma-Mistri** is a digital service-booking platform designed to connect users with skilled local workers such as electricians, plumbers, carpenters, painters, mechanics, and other service professionals.

## 📌 Problem Statement

In many urban and semi-urban areas, people struggle to quickly find reliable workers for household repairs and maintenance tasks. Most users depend on local references or contacts, which can be time-consuming and unreliable. Skilled workers also face difficulties in getting regular job opportunities because of limited digital visibility.

This project solves the problem by providing a **Smart Local Service Platform** where users can easily discover nearby workers, view ratings and experience, book services, and communicate directly through a simple mobile application.

---

# 🚀 Features

## For Customers

* **Service Categories Dashboard**: Browse different worker categories such as Electrician, Plumber, Carpenter, Painter, Mechanic, etc.
* **Worker Profile View**: View worker details including experience, ratings, availability, and contact information.
* **Quick Service Booking**: Book services easily with preferred date and time.
* **Location-Based Search**: Find nearby workers quickly using location services.
* **Ratings & Reviews**: Provide feedback and ratings after service completion.
* **Booking History**: Track ongoing and completed bookings.

## For Workers

* **Worker Registration**: Skilled workers can create profiles and register their services.
* **Availability Management**: Update working availability status.
* **Booking Notifications**: Receive customer booking requests instantly.
* **Digital Visibility**: Increase reach and connect with more customers.

---

# 🛠 Tech Stack

* **Language**: Kotlin
* **Architecture**: MVVM (Model-View-ViewModel)
* **Database**: Firebase Realtime Database / Room Database
* **Authentication**: Firebase Authentication
* **Navigation**: Jetpack Navigation Component
* **UI Components**: Material Design 3, View Binding
* **Image Loading**: Coil / Glide
* **Asynchronous Operations**: Kotlin Coroutines & StateFlow

---

# 📸 Screenshots (Placeholders)

|                          Home Screen                          |                            Worker Profile                           |                            Booking Screen                           |
| :-----------------------------------------------------------: | :-----------------------------------------------------------------: | :-----------------------------------------------------------------: |
| ![Home](https://via.placeholder.com/200x400?text=Home+Screen) | ![Profile](https://via.placeholder.com/200x400?text=Worker+Profile) | ![Booking](https://via.placeholder.com/200x400?text=Booking+Screen) |

---

# 📦 Installation & Setup

## Prerequisites

* **Android Studio** (Ladybug or newer)
* **JDK 17**
* **Android SDK 24+**

---

## Steps to Setup

### 1. Download or Clone the Repository

```bash id="g4d7px"
git clone https://github.com/shivapriyab789-ui/NammaMistri.git
```

### 2. Open the Project

Open Android Studio → **File** → **Open** → Select the `NammaMistri` folder.

### 3. Sync Gradle

Android Studio will automatically ask to sync Gradle files. Click **Sync Now** and wait for completion.

### 4. Run the Project

* Connect your Android device or start an emulator.
* Click the **Run** (▶) button in Android Studio.

Or use terminal:

```bash id="7wx7vw"
./gradlew installDebug
```

---

# 🔐 Default Test Credentials

## Customer Login

* **Phone numaber**: `4657424257`
* **Password**: `user123`

## Worker Login

* **Phone numaber**: `4657424258`
* **Password**: `worker123`

---

# 📁 Folder Structure

```text id="krph5w"
NAMMA-MISTRI/
├── app/
│   ├── src/main/java/com/nammamistri/
│   │   ├── data/           # Database, Models, Repositories
│   │   ├── ui/             # Activities, Fragments, ViewModels
│   │   ├── auth/           # Login & Registration
│   │   └── util/           # Helper Classes & Utilities
│   ├── src/main/res/       # Layouts, Drawables, Strings
│   └── build.gradle.kts    # Module-level Gradle
├── build.gradle.kts        # Project-level Gradle
└── README.md               # Documentation
```

---

# 🛠 Future Improvements

* **Live Worker Tracking**: Real-time worker location tracking.
* **In-App Chat**: Messaging between customer and worker.
* **Online Payments**: UPI and payment gateway integration.
* **Multi-Language Support**: Kannada, Hindi, Tamil, and Telugu support.
* **Push Notifications**: Booking updates and alerts.
* **AI Recommendations**: Smart worker suggestions based on ratings and location.

---

# 📄 License

This project is licensed under the MIT License.
