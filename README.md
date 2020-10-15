<img src="https://raw.githubusercontent.com/davidmarinangeli/SatisBeer/master/githubmedia/search_screenshot.png" width="400"/>  <img src="https://raw.githubusercontent.com/davidmarinangeli/SatisBeer/master/githubmedia/sheet_screenshot.png" width="400"/>


# Satisbeer 🍻
Satisbeer is an Android app in **Kotlin** that I developed for the tech test in Satispay.

## What does it do? 🤔
The app fetches Beers from [Punk API](https://punkapi.com/documentation/v2) and displays them through a **pagedList** that gets 25 items per call. The app supports names searching, and the item can be clicked through the "More Info" button and this inflates a Bottom Sheet with more info about that specific beer.

## Run it 🏃
Simply clone the project and start the app! For the task purposes, the light theme is disabled, but if your device has Android 10 (API level 29) or higher, you can put a comment
on line `29` in `HomeActivity.kt` to disable force dark mode and to make the app respect the system theme.

## Software Design Pattern 🏢
I used the MVVM pattern in order to make the code more readable, scalable and easily testable; furthermore, I used several modules to separate the scopes of the app itself.

## Modules 📦📦
There are four main modules, each one that fulfills a specific purpose:
1. *app* - main one
2. *network* - in the lib directory, it handles the network setup and, thanks to Dagger, provides the dependencies that are used in the whole project
3. *resources* - contains all the common resources used throughout the project ( themes / colors / strings / drawables / components ... )
4. *home* - in the feature folders, contains everything about the Home Feature, from the API interface, to the repository, to the View Model and UI
 
I decided to split the project in more modules because of 1. shorter Build Time 2. Separation of the Scopes 3. Cleaner code and better architecture. 
The separation has been done feature-based so the app can scale easily feature by feature ( Home, Details, Purchase a Beer, who knows! 😄... ) without any feature affecting
the others in a sensible way. Plus, it's easier to build an Instant App if we're starting from here!

<img src="https://github.com/davidmarinangeli/SatisBeer/blob/master/githubmedia/app_architecture.jpg?raw=true" width="500"/>

## UI 🎨
I tried to use the best pratices for what concerns the UI: The theme is the widely suggested `Theme.MaterialComponents.DayNight.NoActionBar`, that helps the developer creating both a 🌞 Light and 🌑 Dark Theme with the correct
palettes. All the Views and components are coming from the `MaterialComponents` library and the texts are set at a theme's level thanks to the override of the `textAppearance` from
the newest `TextAppearance.MdcTypographyStyles`.

## Testing 🏋
I developed some tests ( both unit and UI ) in order to be sure that everything worked as expected. The UI ones deal with the basic "<i>open app > open detail</i>" flow and with the "<i>open app > search beer > open detail</i>" one.
The unit tests, instead, check the repository retrieval and search feature's correct behaviour in many scenarios.

## Libraries 📚
I used some of the most used and common libraries such as:
 1. Android Jetpack ( ViewModels, LiveData )
 2. Dagger
 3. Retrofit
 4. Kotlin Coroutines
 5. Moshi
 6. Paging
 7. Android Material
 
## Improvements 📶

There are some improvements that I would like to make:
 - Network Errors / Empty list handling - with more time I would like to show the user a message when the retrieved list is empty or there's no connection
 - UI polishing - it could be cool to improve the Searchbar feature ( with some UI components such as the "x", an history of searches ... )
