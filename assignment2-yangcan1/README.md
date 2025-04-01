[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/XsvULgb_)
# Assignment 2
**Due by 11:59pm on Monday, 2/19/2024** <br/>
**Demo due by 11:59pm on Monday, 3/4/2024**

In this assignment, we'll continue working on the weather app we started in assignment 1.  This time, we'll hook the app up to an HTTP API to fetch forecast data over the internet, and we'll adapt the app to gracefully deal with transitions in the activity lifecycle by implementing the `ViewModel` architecture.  The parts of the assignment are outlined below.

## 1. Hook your app up to the OpenWeather API

This repository provides you with some starter code that displays hard-coded dummy forecast data in a `RecyclerView`-based list (i.e. a solution to assignment 1).  Your first task for this assignment is to use Retrofit to fetch forecast data from the OpenWeather API and to display that data in the `RecyclerView` instead of the dummy data.  You can find more info about the OpenWeather API here: https://openweathermap.org/api.  Here are some steps you can follow to get everything working for this part of the assignment:

  1. To be able to use the OpenWeather API, you'll first need to sign up for an OpenWeather API key here: http://openweathermap.org/appid.  Without this API key, you won't be able to make calls to the API.

  2. Set up a Retrofit service that allows you to fetch forecast data from the OpenWeather API.  For this assignment you'll use to query OpenWeather's [5-day forecast API](https://openweathermap.org/forecast5).  You should set up your Retrofit service to allow you to query [by city name](https://openweathermap.org/forecast5#name5).  This requires a city name to be added as a query string parameter into the URL representing the API request.  **For this assignment, you can just use a hard-coded city name (e.g. "Corvallis,OR,US").**  Don't forget to set your Retrofit service up to also include your API key as a query string parameter in the URL.  The URL should also be set up to request JSON data from the API.  You may also want to set other API query parameters in the URL, as well, such as `units`, which controls the units of temperature, etc. returned by the API.  The documentation for the 5-day forecast API describes all the API query parameters you can set in the URL.

      Once your Retrofit service is set up, use it to send an API request to fetch forecast data when the app is first launched.  Make sure you set your app up to do the following things when an API request is sent:
        * Display a loading indicator while data is being fetched and hide the progress bar when fetching is complete.
        * Display an appropriate error message if you were unable to successfully fetch data for some reason.  The error message should be represented as a string resource within your application code.
        * If data is successfully fetched, display it in the main activity's `RecyclerView`-based list (more on this in the next couple items).

  3. Use [Moshi](https://github.com/square/moshi/) to parse the JSON data returned by the OpenWeather API.  At a minimum, your app should extract (at a minimum) the following data for each entry in the returned JSON data:
      * The date/time.
      * The low temperature.
      * The high temperature.
      * The probability of precipitation.
      * The short textual description of the weather.

      The documentation for the 5-day forecast API [describes all the fields and the structure of the JSON response](https://openweathermap.org/forecast5#JSON).  Note that the structure of the JSON response is somewhat complex, and the fields containing the data listed above are at different levels and in different locations in the JSON response.  To be able to successfully parse out those fields, you'll likely need to set up a hierarchy of nested Kotlin model classes to use in conjunction with Moshi.  It may be helpful to implement a [custom Moshi type adapter](https://github.com/square/moshi/#custom-type-adapters) to give you a class that's a little easier to work with.

  4. Send your parsed forecast data into the `ForecastAdapter`, so it is displayed in the main activity's `RecyclerView`-based list.  Depending on how you set up your parsing, you may need to make modifications to the `RecyclerView` framework to correctly display the new data.  For example, you may need to modify the layout representing one item in the `RecyclerView` list.  Other changes may be needed as well.

  5. Trigger an API request to fetch forecast data from your main activity class's `onCreate()` method, so forecast data is fetched when the app starts.  Don't forget to set up the correct permissions to be able to make network calls from your app.

## 2. Implement the ViewModel architecture and update Retrofit to support Kotlin coroutines

After you finish the first part of the assignment, one thing you might notice is that when you do things like rotate your device when viewing the main activity, the activity will be recreated, and this will result in a new network call being made to fetch the same weather forecast data you were just looking at.  You can see this is happening because the loading indicator will be displayed, indicating that the network call to fetch forecast data from the OpenWeather API is being re-executed.  You can also use Android Studio's [network inspector](https://developer.android.com/studio/debug/network-profiler) to verify that multiple network calls have been made.

In this case, the app makes multiple network calls because the network call is initiated in the `onCreate()` function in the main activity class.  Your second task in this assignment is to fix this problem by moving the main activity's data management behind a `ViewModel` to make our activity better cope with lifecycle transitions.  Doing this will involve a few different sub-tasks:

  * Update the Retrofit service you implemented above to adapt your API query method so it's compatible for use with Kotlin coroutines.

  * Implement a Repository class to perform the data operations associated with communicating with the OpenWeather API.  This Repository class will use your Retrofit service to make API calls.  The Repository class should use elements of the Kotlin coroutines framework to make sure the API call is executed asynchronously.

  * Make your Repository class cache the data you fetched from the OpenWeather API and to return cached data when appropriate (e.g. if the cached data is for the same city and was fetched very recently) instead of making a new network call.

  * Implement a `ViewModel` class to serve as the intermediary between the Repository class and the UI.  This class should contain methods for triggering a new data fetching operation, and it should make the fetched forecast data available to the UI.

  * Adapt the UI in the main activity class to observe changes to the forecast data held within the `ViewModel` and to update the state of the UI as appropriate based on changes to that data.  In particular, this should be done in such a way that the loading indicator and error message you implemented above continue to function as before.

As a result of these changes, you should see your app fetch results from the OpenWeather API only one time through typical usage of the app, including through rotations of the phone.  You should use the Android Studio network inspector to verify that your app only makes one network call to fetch the data.

## CS 599 only: Use Hilt to perform dependency injection

This assignment's challenge for students in the grad section of the course is to implement more robust handling of some of the dependencies between the parts of our application.  Specifically, if you're in the grad section of the course, you have one additional requirement for this assignment, which is to use [Hilt](https://dagger.dev/hilt/) to perform dependency injection within your application.

[Dependency injection](https://developer.android.com/training/dependency-injection) is an architectural pattern that helps to increase code reusability and to make it easier to perform refactoring and testing.  At a high level, dependency injection works by supplying a class's dependencies (i.e. objects of other classes needed by the class) to it rather than making the class obtain its own dependencies.  Dependency injection can be as simple as passing a class's dependencies into its constructor as parameters.  However, more advanced approaches to dependency injection exist that are more scalable and easier to maintain.  Hilt is a dependency injection library specifically designed for use in Android apps, and it is the recommended way to perform dependency injection in Android.

For this assignment, you are working within a small application, so you will implement a very simple dependency injection setup.  Spefically, you should use Hilt to inject the following dependencies:
  * Inject an instance of your Retrofit service into your Repository class.
  * Inject an instance of your Repository class into your `ViewModel` class.
  * Inject an instance of your `ViewModel` class into your main activity class.

The following pieces of documentation may be helpful for this task:
  * [The official Hilt "quick start"](https://dagger.dev/hilt/quick-start) and other "core APIs" documentation.
  * ["Dependency injection with Hilt"](https://developer.android.com/training/dependency-injection/hilt-android) from the Android Developers site
  * ["Using Hilt in your Android app"](https://developer.android.com/codelabs/android-hilt), a code lab from the Android Developers site

## Submission

We'll be using GitHub Classroom for this assignment, and you will submit your assignment via GitHub.  Make sure your completed files are committed and pushed by the assignment's deadline to the main branch of the GitHub repo that was created for you by GitHub Classroom.  A good way to check whether your files are safely submitted is to look at the main branch your assignment repo on the github.com website (i.e. https://github.com/osu-cs492-599-w24/assignment-2-YourGitHubUsername/). If your changes show up there, you can consider your files submitted.

## Grading criteria

The base assignment is worth 100 total points, broken down as follows:

  * 50 points: Successfully fetches and displays data from the OpenWeather API in the main activity's `RecyclerView`-based list.
    * 15 points: Correctly defines a Retrofit service containing a method to query OpenWeather's 5-day forecast API.
    * 10 points: Correctly defines Moshi model classes and/or a custom Moshi type adapter to represent the JSON response from OpenWeather API.
    * 5 points: Correctly uses Moshi model classes/custom type adapter to parse JSON response from OpenWeather API.
    * 5 points: Correctly displays response from OpenWeather API in the main activity's `RecyclerView`.
    * 5 points: Correctly displays a loading indicator or an error message when appropriate.
      * Error message must be represented as a string resource in your application code.
    * 10 points: Correctly constructs an instance of the Retrofit service and uses it to query the OpenWeather API when the app is launched.

  * 50 points: Successfully implements the `ViewModel` architecture to store and manage weather forecast data.
    * 10 points: Correctly implements a Repository class to perform data fetching and store data.
    * 10 points: Correctly implements a `ViewModel` class to interface between UI and Repository.
    * 10 points: Correctly observes `ViewModel` data in UI and updates the UI state appropriately (including loading indicator and error message) as data changes.
    * 10 points: Correctly uses Kotlin coroutines to execute network calls in a background thread, following best practices as discussed in lecture.
    * 10 points: Successfully caches data in the Repository class and uses cached data when appropriate.
      * Android Studio network inspector should show only a single network request to fetch data, even across lifecycle transitions like a device rotation.

In addition, the assignment for the grad section of the course has the following additional grading criteria, making the grad section's version of the assignment worth 125 points total:

  * 25 points: Successfully uses Hilt to perform dependency injection within application.
    * Must use Hilt to inject Retrofit service into Repository class, Repository class into `ViewModel`, and `ViewModel` into main activity.
