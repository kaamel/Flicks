# Project 1 - *Flicks*

**Name of your app** shows the latest movies currently playing in theaters. The app utilizes the Movie Database API to display images and basic information about these movies to the user.

Time spent: **15** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **scroll through current movies** from the Movie Database API
* [x] Layout is optimized with the [ViewHolder](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView#improving-performance-with-the-viewholder-pattern) pattern.
* [x] For each movie displayed, user can see the following details:
  * [x] Title, Poster Image, Overview (Portrait mode)
  * [x] Title, Backdrop Image, Overview (Landscape mode)

The following **optional** features are implemented:

* [x] Display a nice default [placeholder graphic](http://guides.codepath.com/android/Displaying-Images-with-the-Picasso-Library#configuring-picasso) for each image during loading.

The following **bonus** features are implemented:

* [x] Allow user to view details of the movie including ratings and popularity within a separate activity or dialog fragment.
* [x] When viewing a popular movie (i.e. a movie voted for more than 5 stars) the video should show the full backdrop image as the layout.  Uses [Heterogenous ListViews](http://guides.codepath.com/android/Implementing-a-Heterogenous-ListView) or [Heterogenous RecyclerView](http://guides.codepath.com/android/Heterogenous-Layouts-inside-RecyclerView) to show different layouts.
* [x] Allow video trailers to be played in full-screen using the YouTubePlayerView.
    * [x] Overlay a play icon for videos that can be played.
    * [x] More popular movies should start a separate activity that plays the video immediately.
    * [x] Less popular videos rely on the detail page should show ratings and a YouTube preview.
* [x] Apply the popular [Butterknife annotation library](http://guides.codepath.com/android/Reducing-View-Boilerplate-with-Butterknife) to reduce boilerplate code.
* [x] Apply rounded corners for the poster or background images using [Picasso transformations](https://guides.codepath.com/android/Displaying-Images-with-the-Picasso-Library#other-transformations)
* [x] Replaced android-async-http network client with the popular [OkHttp](http://guides.codepath.com/android/Using-OkHttp) networking libraries.

The following **additional** features are implemented:

* [x] List anything else that you can get done to improve the app functionality!

- A progress bar is shown while the movies list are downloaded
- If no trailers are available a Toast message inidicates it
- When showing a video, it checks the screen resolution and finds the best matched trailer
where more than one videos is available.
- The list of currently showing movies are maintained and are not downloaded again for the life of the application
- The list of trailers/videos for any movie is downloaded ondemand, but then not downloaded again
for the life of the application
- The connection and decoding of the movies are through an abstract class that can be
extended for other service providers.
- Movie ratings are normalized to a 0-5 range and starts are displayed based 0.5 steps.
For the purpose of this assignment a movie is considered popular if the normalized rating
is 3.6 or higher. This is done to assure some movies are popular!

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://i.imgur.com/MKJHsE4.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android

## License

    Copyright [yyyy] [name of copyright owner]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
