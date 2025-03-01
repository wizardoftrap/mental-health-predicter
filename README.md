##Mental Health Prediction System

This project predicts mental health conditions using a machine learning model trained with the Random Forest algorithm. The model is deployed using a Flask API, while a Spring Boot API handles user interactions, stores data, and sends predictions via email.

#Tech Stack

Machine Learning Model: Random Forest
Backend APIs: Flask (ML Model), Spring Boot (User Interaction & Email)
Database: (Specify if used)
Deployment: Local or Cloud


#How to Run the Project
1. Prepare the Dataset
Create or load the dataset required for training.
2. Train the Model
Train the model using the Random Forest algorithm.
Save the trained model for later use.
3. Start the Flask API
Run the Flask application to deploy the ML model.
Ensure the API is accessible for predictions.
4. Start the Spring Boot Application
Run the Spring Boot API to handle user requests.
5. Make a Prediction Request

Send a request via the Spring Boot API, which forwards it to the Flask API.
The Flask API processes the data and returns the prediction.
The result is sent to the user via email.

#Endpoints
Flask API – Handles ML model inference.
Spring Boot API – Manages user requests, stores data, and emails results.

License
This project is open-source. Feel free to modify and enhance it.
