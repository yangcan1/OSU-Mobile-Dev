# Assignment 3
**Due by 11:59pm on Monday, 3/4/2024** <br/>
**Demo due by 11:59pm on Monday, 3/18/2024**

In this assignment, we'll keep building on our weather app.  Your main goal for this assignment is to add new screens to your app, including a screen that the user will be able to use to set app preferences that affect how the app behaves.  The parts of the assignment are outlined below.

You are provided with starter code here that fetches weather forecast data from the OpenWeather API and displays that data in a `RecyclerView`-based list.  The starter code implements the `ViewModel` architecture.  Feel free to use this starter code as the basis upon which to write your code for assignment 3.  Alternatively, if you liked your own implementation from assignment 2, you can start with that instead of the provided starter code.

## 1. Convert the UI for the main activity into a fragment

The next couple tasks for this assignment will involve using the Jetpack Navigation component to add navigational features and new screens to the app.  In order to use the Navigation component, each screen in the app must be represented as a fragment, i.e. an instance of the `Fragment` class.  To set yourself up to be able to easily incorporate the app's one existing screen (implemented by the main activity) into the navigation setup you implement, start out here by converting that screen into a fragment called `FiveDayForecastFragment`.  We'll discuss in lecture a recipe for doing this.

Once the content from the original main activity is implemented as a fragment, make sure you can run the app and see that fragment by making the main activity host the content of the fragment.  Once you're done with this step, you should be able to run the app and see it behave just like it previously did.  The only difference will be the way the screen is implemented.

## 2. Implement a new screen to show the current forecast

Your next task for the assignment will involve implementing a fragment to represent a new screen that displays the current weather forecast.  This new screen will eventually serve as the start destination for your app, i.e. the screen the user will initially land in when they launch the app.  Here are some requirements you'll need to make sure your new screen satisfies:

  * Your new fragment should fetch data from a different method in the OpenWeather API.  Specifically, you should fetch data to power the new screen from OpenWeather's [current weather data API](https://openweathermap.org/current).  In many ways, this API method behaves like the five-day forecast API method you used in assignment 2.  For example, you can [query it by city name](https://openweathermap.org/current#name), just like with the five-day forecast API, and it [returns a JSON response](https://openweathermap.org/current#example_JSON) that is structured almost exactly like an entry in the `list` field from the five-day forecast API's JSON response (the one main difference being the current weather API's response does not include a `pop` field).

    To implement the API call to fetch data from this new API, you'll need to add a new corresponding method to the app's existing Retrofit service.  You'll also need to implement a `ViewModel` to allow you to connect the UI with the data you fetch.  This will also involve implementing a new Repository class to perform data loading functionality.  To parse the JSON response, you should be able to use the existing `ForecastPeriod` class in conjunction with Moshi.  Note that you may need to make very minor changes to the setup of the `ForecastPeriod` and some of the other JSON-parsing classes (specifically `OpenWeatherForecastPeriodJsonAdapter` and `OpenWeatherListJson`) to account for the fact that the `pop` field doesn't exist in the JSON response from OpenWeather's current weather API.

  * You will need to implement a new layout file to define the appearence of your new screen.  It's up to you to design this new layout file.  You can take inspiration from the layout for the cards used to display the five-day forecast, but remember, this layout will represent an entire screen, so you'll have more real estate to work with than you would within an individual card.  You should try to create a layout that makes good use of that real estate.  I encourage you to challenge yourself to implement a layout that's as visually appealing as you can make it.

If you'd like to see what your new screen looks like and test it out before moving on to the next task, you can modify the main activity to host the new fragment instead of the five-day forecast fragment.

## 3. Add navigation using the Jetpack Navigation component

Once you have two screens implemented in separate fragments, your next goal is to allow the user to navigate between them.  You'll do this by incorporating the Jetpack Navigation component into the app.  Remember, there are three main parts of the Jetpack Navigation component: the navigation graph, the navigation host, and the navigation controller.  You'll have to add all three of these elements to the app.

Here are some requirements you'll have to meet for this part of the assignment:

  * The new "current weather" screen you implemented in the task above should serve as the start destination for your app.

  * There should be some mechanism present in the "current weather" screen that the user can use to navigate to the "five-day forecast" screen.  This can be, for example, a button within the layout of the "current weather" screen or an action icon in the app bar that's displayed in the "current weather" screen.

  * Now that your app contains multiple activities, it should display an app par at the top of every screen.  This app bar should reflect information about the current screen, and it should correctly provide "up" navigation.

## 4. Allow the user to share the current weather

Next, add an action to the app bar for the "current weather" screen that allows the user to share a textual representation of the current weather.  Your action should launch the Android Sharesheet to allow the user to select an app with which to share the weather text.

## 5. Add a new screen to allow the user to set preferences

Finally, use a `PreferenceFragment` to implement a new settings screen that allows the user to set some basic preferences that influence the way the app behaves.  The user should be able to navigate to this setings screen via app bar actions in *both* the "current weather" screen and the "five-day forecast" screen.

You should include the following preferences:

  * **Forecast units** - The user should be allowed to select between "Imperial", "Metric", and "Kelvin" temperature units.

  * **Forecast location** - The user should be allowed to enter an arbitrary location for which to fetch a forecast.  This should be implemented as a single text-entry preference.  You can specify any default location you'd like (e.g. "Corvallis,OR,US").

The currently-set value should be set as the summary for both of these preferences.

The settings of these preferences should affect the URL used to query the OpenWeather API.  The app should be hooked up so that any change to the preferences results in the OpenWeather API being re-queried and the newly-fetched forecast data being displayed.  Importantly, make sure the UI reflects these settings (e.g. by displaying the correct temperature units, "F", "C", or "K", according to the value of the units preference).

## CS 599 only: Implement deep links into the application

This assignment's challenge for students in the grad section of the course is to implement more robust handling of some of the dependencies between the parts of our application.  Specifically, if you're in the grad section of the course, you have one additional requirement for this assignment, which is to implement [deep links](https://developer.android.com/guide/navigation/design/deep-link) that allow the user to jump directly into your app from another app when they click on a link of the correct format.

Specifically, set yor app up with deep links that handle OpenWeather API URLs.  Specifically, your app should handle two different forms of URL:

  * **`https://api.openweathermap.org/data/2.5/weather?q={city name}`** - When the user clicks a link or enters a URL of this form, it should deep link into your app's "current weather" screen, which should display the current weather for the location specified in `{city name}`.

  * **`https://api.openweathermap.org/data/2.5/forecast?q={city name}`** - When the user clicks a link or enters a URL of this form, it should deep link into your app's "five-day forecast" screen, which should display the five-day forecast for the location specified in `{city name}`.

In both cases, make sure you update the current value of the "forecast location" preference to reflect the specified `{city name}`.  You can do this programmatically with a [`SharedPreferences.Editor`](https://developer.android.com/reference/kotlin/android/content/SharedPreferences.Editor).

## Submission

We'll be using GitHub Classroom for this assignment, and you will submit your assignment via GitHub.  Make sure your completed files are committed and pushed by the assignment's deadline to the main branch of the GitHub repo that was created for you by GitHub Classroom.  A good way to check whether your files are safely submitted is to look at the main branch your assignment repo on the github.com website (i.e. https://github.com/osu-cs492-599-w24/assignment-3-YourGitHubUsername/). If your changes show up there, you can consider your files submitted.

## Grading criteria

The base assignment is worth 100 total points, broken down as follows:

  * 10 points: The "five-day forecast" activity is successfully converted into a fragment.

  * 25 points: A new "current weather" fragment is successfully implemented, as described above.
    * 5 points: The new fragment successfully uses Retrofit to fetch data from OpenWeather's current weather API and parses the JSON response
    * 10 points: The new fragment successfully displays the fetched data in a visually appealing layout
    * 10 points: The new fragment successfully implements the `ViewModel` architecture to manage data

  * 30 points: The app uses the Jetpack Navigation component to enable navigation between screens.
    * 10 points: A navigation graph is correctly defined representing the relationships between the app's destinations.
      * The "current weather" destination must be designated as the start destination.
    * 10 points: Navigation activities are successfully included to allow the user to navigate between destinations.
    * 10 points: All destinations contain a top app bar that reflects information about the current screen and provides "up" navigation when relevant.

  * 10 points: The user can successfully use the Android Sharesheet to share a textual representation of the "current weather" data.
    * Sharing should be triggered via an app bar action in the "current weather" screen.

  * 25 points: A settings screen is successfully added to the app, as described above.
    * 10 points: A settings screen is successfully implemented using a `PreferenceFragment` that allows the user to set temperature units and city name as preferences.
    * 5 points: The user can successfully navigate to the settings screen from both the "current weather" screen and the "five-day" forecast screen using app bar actions.
    * 10 points: The values the temperature units and city name settings values set by the user are used to update the weather forecast information displayed to the user in both the "current weather" screen and the "five-day forecast" screen.

In addition, the assignment for the grad section of the course has the following additional grading criteria, making the grad section's version of the assignment worth 125 points total:

  * 25 points: Implements deep links into the app using the Jetpack Navigation component, as described above.
    * 12.5 points: URLs of the form `https://api.openweathermap.org/data/2.5/weather?q={city name}` are correctly handled by the app's "current weather" screen.
    * 12.5 points: URLs of the form `https://api.openweathermap.org/data/2.5/forecast?q={city name}` are correctly handled by the app's "five-day forecast" screen.
