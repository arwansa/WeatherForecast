# WeatherForecast Android App
**WeatherForecast** is an Android application that provides weather forecasting information. This application is built using the **Clean Architecture** principles combined with **MVVM (Model-View-ViewModel)** pattern to ensure scalability, maintainability, and testability.

### Libraries Used
- **core-ktx**: Provides Kotlin extensions for the Android framework and several Kotlin-specific libraries.
- **appcompat**: Provides backward-compatible versions of Android framework APIs.
- **material**: Material Design components for Android.
- **constraintlayout**: A modern Android layout that allows building complex layouts with a flat view hierarchy.
- **androidx.lifecycle, activity, fragment**: These libraries help to build robust, testable, and maintainable apps.
- **Kotlin Coroutines**: For handling background tasks, threading, and async operations.
- **Room**: A SQLite object mapping library. Used for local data storage.
- **Hilt**: Dependency injection library for Android. Simplifies DI in Android apps.
- **Retrofit**: A type-safe HTTP client for Android. Used for network requests.
- **Glide**: An image loading and caching library.
-

### How to Run
- Clone this repository.
- Open the project in Android Studio.
- Create a config.properties file in the root directory of the project.
  Add the following lines to the config.properties:
    ```
    base_url="https://api.openweathermap.org/"
    api_key="YOUR API KEY"
    ```
- Replace **YOUR API KEY** with your actual OpenWeatherMap API key.
- Build and run the application on an emulator or physical device.



