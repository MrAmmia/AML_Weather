# AML_Weather

This is a simple API request app but with a little twist.....Query calls are perfected and POJOs are created to cater for JSON response

The response is then cached into memory to save data, request time and other device resources

The general operation is thus

SQLite database is queried. if data exists, display data, else...

...else make a network request to fetch live data

The network response is then stored to the SQLite database from where we will call, whenever we want to display it to the user

A connectivity manager can be used to monitor network state and indicate if the app should make a new network request

![Screenshot_20221118-005400](https://user-images.githubusercontent.com/61557175/202588530-efcc2221-5464-4964-984b-c1a23cb5ec32.png)


![second](https://user-images.githubusercontent.com/61557175/202588536-73a93b52-6e36-471b-bc04-ec09ad3908bf.png)


![third](https://user-images.githubusercontent.com/61557175/202588542-4cc06b4e-e623-4eff-8767-131930e0331b.png)
