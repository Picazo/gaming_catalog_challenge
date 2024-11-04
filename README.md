# Gaming Catalog Challenge

* Language: Kotlin
* MVVM + Clean Architecture
* Unit Testing: View Model
* Jetpack Compose

## App structure:

* Hilt was used as a dependency injector and is implemented with a separate module for better concern handling
* For networking Retrofit was used respectively
* To handle asynchronization, kotlin coroutines were used
* Finally, to carry out the unit tests, MockK was used

For the consumption of services, a Repository pattern was used that goes very well with Clean, as an extra, a SplashScreen was used using the Google library in which the use of a direct class is no longer necessary for its implementation.

For the database part, we used Room 
The patter Repository separates a DataSource to know if it takes it from the API or from the DB, this if it detects that there is nothing in the DB.

Inside the DB we used flow to guarantee the rapidity in which the user sees the change of data, as much when the game is eliminated, as when it updates it.

For the deletion it is given in a logical way adding a field isActive to be able to show the ones that are active, if it is deleted it stops to 0 and with this it continues in the DB but it is not shown.


## Demo


https://github.com/user-attachments/assets/fbb1475f-5970-4c60-80f1-5a2abe708c60


## BD

![GaminCatalogDB](https://github.com/user-attachments/assets/513b786e-151e-4773-9884-dde00a5149a0)


## Extras

It is added like small detail a slight animation to the image of the detail of the game to appreciate like “collapses”.


## Notes


Some points that could not be addressed due to time constraints are mentioned below:

* Adding a handler for internet connection is required.
* Error messages are required to be added in case of failure (both DB and service).
* Tests are required to be added to the UseCase


