
# Taxi Application

Taxi Application is a Java-based and Domain-Driven software that allows communication between drivers and users by requesting rides from a certain location.




## Demo

At first, a Log-In screen will pop up.

The person who wants to log in the application has to input a valid username. Otherwise, the software won't do anything.

![logingif](https://github.com/Paula-H/taxi-application/assets/118481907/d700c0e6-657e-44f9-872f-30f16d5178d9)

When an user attempts to search a ride, all logged drivers will get a notification in the 'Active Rides' panel(a list with all the rides that have yet to be accepted by any driver).

![searchdrivergif](https://github.com/Paula-H/taxi-application/assets/118481907/011c4378-43e7-47e3-93b7-aabd104da64f)

If any driver accepts a certain's user ride request, the said individual will get a notification in their respective screen that will allow them to accept or decline the driver.

![acceptrequestgif](https://github.com/Paula-H/taxi-application/assets/118481907/6662dcb6-73e0-4774-9ae3-91e778133ff8)


## Languages, tools, frameworks

- developed using [Intellij IDEA](https://www.jetbrains.com/idea/), [PostgreSQL](https://www.postgresql.org/) and [Gluon Scene Builder](https://gluonhq.com/products/scene-builder/)
- automation tool : [Gradle](https://gradle.org/)
- languages used : Java, SQL, CSS

## Design Patterns
- Creational Design Patterns :
    - Singleton : providing global access to only one instance of the Service class
    - Factory Method : creating instances of each Repository without specifying the exact class that will be created
- Behavioral Design Patters :
    - Observer Method : when the Service class(observable) modifies its state, all the windows(observers) will be notified in a certain way, depending on the resulting action
