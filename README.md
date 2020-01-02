# Shutter-Stock-Client
Master/Details Android app consumes the [ShutterStock][1] specially the search api to retrieve images 
using the query submitted by the user

### Clone the Repository

As usual, you get started by cloning the project to your local machine:<br>
replace YOUR-USERNAME with your account username
```
$ git clone https://github.com/YOUR-USERNAME/Shutter-Stock-Client.git
```

<h4>Main features:</h4>

* Images search 
* endless scroll 
* Image Viewer 

<h4>ScreenShots:</h4>

<img src="https://github.com/AhmedGarhy/Shutter-Stock-Client/blob/master/screenshots/Screenshot_1578002631.png" width="200">&nbsp;&nbsp;&nbsp;
<img src="https://github.com/AhmedGarhy/Shutter-Stock-Client/blob/master/screenshots/Screenshot_1578002636.png" width="200">&nbsp;&nbsp;&nbsp;



<h4>Languages & Libraries:</h4>

* Kotlin
* Retrofit
* Picasso
* Dagger2
* RxJava
* ReactiveStreams 
* LiveData
* ViewModel
* Motion layout
* Mock Web Server
* 

<h4>Technical Choices</h4>

<h4>Architecture Level:</h4>

* I applied [MVVM][3] architecture with using the [LiveData][2] and [ViewModel][4] from [Android Jetpack][5] which is recommeded by Google,
 to get the app layer less coupling and high cohesion. Using view models to store and manage UI-related data in a lifecycle conscious way

<img src="https://developer.android.com/topic/libraries/architecture/images/final-architecture.png" width="500">&nbsp;&nbsp;&nbsp;


<h4>Implementation Level:</h4>

* Retrofit -> to make a REST API calls in a reliable way.
* Picasso -> to manage the loading of the images from urls.
* Dagger2 -> for dependency injectio
* Rxjava & ReactiveStreams -> to handle the asynchronous tasks efficiently, and reactive streams to adapt 
the Rx Observables with the LiveData
* Mock Web Server -> to mock web API calls in unit testing





<h4>Todo:</h4>

* implement disk cache using database , to cache the most search result with responsible validity cache policy.
* secure the CLIENNT_API_KEY and SECRET_KEY.
* add more error handling, as i just handled the most common errors from the HttpExceptions [500, 400, 401]
* refactor the error handling in the BaseFragment, it could be more reliable.
* add more test [unit - ui testing] cases to incrase the code coverage.
* style the app, use values files [dimensions - constants - styles - strings] in the app instead of hardcoded values.
* localize tha app.
* enable proguard rules.
* apply filter touches when obscured 
* use the navigation library from Jetpack 
* [Add a Network Security Configuration file][6]
* lower the min sdk to support more users.



[1]:https://www.shutterstock.com
[2]:https://developer.android.com/reference/androidx/lifecycle/LiveData?hl=en
[3]:https://developer.android.com/jetpack/docs/guide
[4]:https://developer.android.com/topic/libraries/architecture/viewmodel
[5]:https://developer.android.com/jetpack
[6]:https://developer.android.com/training/articles/security-config
