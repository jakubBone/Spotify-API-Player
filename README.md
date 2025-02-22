# 🎵 Spotify API Player

[![Watch the video](src/main/resources/logo.png)](https://youtu.be/cqTQBMT-nBQ)

Welcome to the Spotify API Player! This application leverages the Spotify Web API to search for
music albums and  play them in your web browser. Designed with a focus on simplicity and usability, 
the app integrates secure authentication and efficient API calls to enhance your music discovery experience.


## 🎯 Features

- **Spotify Web API Integration**: Communicates directly with Spotify's Web API for album search and playback

- **OAuth 2.0 Authentication**: Secure login process through Spotify's authorization flow

- **Album Search**: Quickly search for albums by name

- **Web Playback**: Open albums in the Spotify Web Player directly from the application

- **Configuration-Driven Setup**: Easily manage API credentials and endpoints via a `config.properties` file



## 🚀 Technologies & Libraries Used

- **Java 21**: Core programming language for client-server logic

- **OkHttp**: HTTP client for interacting with REST APIs

- **GSon**: JSON parser for processing API responses

- **Gradle**: Build automation and dependency management


## 📂 Project Structure

```
src
├── com.jakub.bone.core          # Main application logic
├── com.jakub.bone.service       # Spotify API services (Auth, Search)
├── com.jakub.bone.server        # Local server for handling OAuth callbacks
├── com.jakub.bone.utills        # Utility classes for configuration and constants
└── resources                    # Configuration file
``` 


## 🚀 Getting Started

Follow these steps to set up and run the project:

### Prerequisites

Before you begin, ensure you have the following tools installed:
- **Java Development Kit (JDK)** 21 or higher
- **Gradle** for dependency management

### Setup Instructions

1. **Clone the Repository**  
   Download the project files to your local machine:
   ```bash
   git clone https://github.com/jakubBone/Spotify-API-Player.git
   cd Spotify-API-Player

2. **Configure the Application**  
   Update the `config.properties` file with your Spotify Developer credentials: 
   ```java
   client.ID=your_client_id
   client.secret=your_client_secret
   redirect=http://localhost:8080

3. **Build the Project**   
   Use Gradle to build the project:
   ```bash
   ./gradlew build

6. **Run the Application**  
   Launch the Spotify API Player::
   ```bash
   java -cp build/classes/java/main com.jakub.bone.core.SpotifyPlayer

7. **Search and Play**  
   - Enter an album name
   - Log in via the Spotify web interface
   - Enjoy your selected album in the Spotify Web Player

## 📧 Contact

If you have any questions, feedback, or suggestions, feel free to reach out to me:

- **Email**: [jakub.bone1990@gmail.com](mailto:jakub.bone1990@gmail,com)
- **Blog**: [javamPokaze.pl](https://javampokaze.pl)  
- **LinkedIn**: [Jakub Bone](https://www.linkedin.com/in/jakub-bone)  

Let's connect and discuss this project further! 🚀
