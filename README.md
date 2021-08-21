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

![Home_AirQI](https://user-images.githubusercontent.com/43383108/130334650-08fbbb4a-8d85-4da5-bec6-7df9a55900a5.jpg)

![Graph_AirQI](https://user-images.githubusercontent.com/43383108/130334657-76d452ed-cae3-4cd1-9887-9388fec09a90.jpg)

![Info_AirQI](https://user-images.githubusercontent.com/43383108/130334662-c6bcc061-6903-4c59-be5f-520c0be6f478.jpg)





