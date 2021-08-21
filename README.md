# AirQI

This Android application displays live air quality data for cities.

 

This project is built on MVVM Architecture and uses Web Sockets to get updated data in 30 seconds interval.

The CustomWebSocket class listens to the socket response, process the response and stores the data for cities in Room Database.

The Repository class gets the latest Air Quality Index from the database for various cities.

The displayed cities and their Air Quality Indexes are highlighted based on their air quality. 

The home page also shows, when was the value of AQI last updated for that particular city.

 

On clicking on a city, it will open a graph for the quality index (interval at 1 minute) for that selected city. 

To display graph, we are using AnyChart -Android library. 

(https://github.com/AnyChart/AnyChart-Android)

Using this library, we can click on the graph, and view the AQI level at different times.
