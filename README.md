# AML Weather

AML Weather is a weather forecasting app that uses the **Visual Crossing API** to fetch and display weather data. It retrieves the user’s location to provide detailed weather forecasts, including:

- **Hourly weather forecast for a day**
- **Weekly weather forecast**
- **Weather forecasts for 10 popular cities**

## Features

- **User Location**: Automatically gets the user's current location to provide accurate local weather forecasts.
- **Hourly Forecast**: Displays detailed hourly weather conditions for the day.
- **Weekly Forecast**: Provides weather forecasts for the upcoming week.
- **Popular Cities**: Displays weather forecasts for 10 popular cities around the world.
- **Visual Crossing API**: Utilizes the Visual Crossing API to retrieve up-to-date weather data.

## Screenshots
![Screenshot_20221118-005400](https://user-images.githubusercontent.com/61557175/202588530-efcc2221-5464-4964-984b-c1a23cb5ec32.png)


![second](https://user-images.githubusercontent.com/61557175/202588536-73a93b52-6e36-471b-bc04-ec09ad3908bf.png)


![third](https://user-images.githubusercontent.com/61557175/202588542-4cc06b4e-e623-4eff-8767-131930e0331b.png)


## Tech Stack

- **Programming Language**: Kotlin
- **API**: Visual Crossing Weather API
- **Location Services**: Fused Location Provider
- **UI**: Android XML layouts and views
- **Architecture**: MVVM (Model-View-ViewModel)

## Project Structure

- **Location Handling**: Retrieves the user's current GPS location to display local weather information.
- **API Integration**: Makes network calls to the Visual Crossing API to fetch weather data based on the user’s location and pre-set cities.
- **UI Design**: Uses Android XML layouts to display weather information in an intuitive and user-friendly way.
- **Forecasts**:
  - Hourly weather forecast for the current day.
  - Weekly weather forecast.
  - Weather forecasts for 10 popular cities.

## Setup Instructions

1. **Clone the repository**:
   ```bash
   git clone https://github.com/MrAmmia/AML_Weather.git
   cd AML_Weather
2. **Build the project**: Open the project in Android Studio, sync the Gradle dependencies, and ensure you have an emulator or physical device set up.
3. **API Key Setup**:
   - Register on [Visual Crossing](https://www.visualcrossing.com/) to get an API key.
   - Add your API key in the local.properties file or BuildConfig
4. **Run the App**: Build and run the app on an Android emulator or physical device.


## How It Works

- **User Location**: The app uses the device’s location services to determine the user’s current location and provide location-based weather information.
- **Visual Crossing API**: Weather data is fetched from the Visual Crossing API for the user’s location and pre-selected cities.
- **Weather Forecast**s:
  - **Hourly Forecast**: Displays the weather forecast for the current day in hourly intervals.
  - **Weekly Forecast**: Provides a 7-day forecast for the user's location.
  - **Popular Cities**: Shows weather forecasts for 10 predefined popular cities.


## Project Highlights

- **Location Services**: Accurate location-based weather forecasts.
- **API Integration**: Seamless integration with the Visual Crossing Weather API to provide real-time weather data.
- **Forecasts**: Offers a comprehensive view of weather conditions on an hourly and weekly basis, as well as weather in popular cities around the globe.
- **Dependency Injection**
- **Caching With Room**: To allow access to already-gotten weather forecasts without additional network calls

## Contributing

Contributions are welcome! Feel free to fork the repository, open issues, and submit pull requests.
