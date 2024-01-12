# Talkies

This movie application, crafted with Jetpack Compose, boasts a clean architecture that enhances its robustness and maintainability. Seamlessly integrating modern technologies like Paging3 and Room DB, the app efficiently manages data. It follows the MVVM (Model-View-ViewModel) pattern, providing a structured and scalable codebase.

## Techs Used 💻
- 100% [Kotlin](https://kotlinlang.org/) based
- [Jetpack Compose](https://developer.android.com/jetpack/compose) - modern toolkit for building native Android UI.
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
- [Flow](https://developer.android.com/kotlin/flow)
- [Dagger-Hilt](https://dagger.dev/hilt/) - Standard library to incorporate Dagger dependency injection into an Android application.
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that is lifecycle aware (didn't destroyed on UI changes).
- [Android Architecture Components](https://developer.android.com/topic/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - MVVM Architecture (Declarative View - ViewModel - Model)
  - Repository pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging network data.
- [Room Database](https://developer.android.com/training/data-storage/room) - save data in a local database
- [Paging3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - load and display pages of data from a larger dataset from local storage or over a network
- [WorkManager](https://developer.android.com/reference/androidx/work/WorkManager) - schedules work and execute sometime after its Constraints are met


## Build and Run Instructions

1. Clone the repository to your local machine using the following command:
```XML

git clone git@github.com:Ashish45y/Talkies.git
```
2. Open the project in Android Studio.

3. Add the API KEY in project local.properties file
```XML

API KEY = YOUR API KEY

```
4. Build the project 

5. Run the app on an Android emulator or a physical device by selecting the target device and clicking on the **Run**
