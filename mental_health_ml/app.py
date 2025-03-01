# app.py

from flask import Flask, request, jsonify
import joblib
import pandas as pd
from flask_cors import CORS
import logging

# Configure logging
logging.basicConfig(level=logging.INFO)

app = Flask(__name__)
CORS(app)

# Load the trained multi-output model
clf = joblib.load('multi_output_model.pkl')

# Load the feature scaler
scaler = joblib.load('feature_scaler.pkl')

# Load the label encoders
label_encoders = joblib.load('label_encoders.pkl')

# Load the target encoders
target_encoders = joblib.load('target_encoders.pkl')

# Expected features
expected_features = [
    'anxiety_frequency', 'physical_symptoms', 'impact_on_life',
    'history_of_anxiety', 'sadness_frequency', 'interest_in_activities',
    'energy_levels', 'sleep_patterns', 'thoughts_of_self_harm',
    'support_system', 'feeling_overwhelmed', 'stress_management',
    'social_withdrawal', 'mood_swings', 'concentration_levels',
    'social_engagement', 'perceived_stress', 'feelings_of_isolation'
]

@app.route('/predict', methods=['POST'])
def predict():
    try:
        # Get the JSON data from the request
        data = request.get_json(force=True)
        logging.info(f"Received data: {data}")

        # Check for empty input
        if not data:
            return jsonify({'error': 'No input data provided'}), 400

        # Convert data to DataFrame
        input_df = pd.DataFrame([data])

        # Ensure all required features are present
        missing_features = [feat for feat in expected_features if feat not in input_df.columns]
        if missing_features:
            return jsonify({'error': f'Missing features: {missing_features}'}), 400

        # Encode input data using the label encoders
        for column in expected_features:
            le = label_encoders.get(column)
            if le:
                try:
                    input_df[column] = le.transform(input_df[column])
                except ValueError as ve:
                    return jsonify({'error': f'Invalid value for feature "{column}": {ve}'}), 400
            else:
                return jsonify({'error': f'Unexpected feature: {column}'}), 400

        # Scale the features
        input_scaled = scaler.transform(input_df)

        # Predict using the model
        predictions = clf.predict(input_scaled)

        # Decode the predicted classes
        result = {}
        for idx, target in enumerate(target_encoders.keys()):
            target_le = target_encoders[target]
            predicted_class = target_le.inverse_transform([predictions[0][idx]])[0]
            result[target] = predicted_class

        # Return the result
        return jsonify(result)

    except Exception as e:
        logging.error(f"Error in prediction: {e}")
        return jsonify({'error': str(e)}), 500

if __name__ == '__main__':
    app.run(host='127.0.0.1', port=5000)