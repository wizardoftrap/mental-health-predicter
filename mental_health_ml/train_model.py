# train_model.py

import pandas as pd
from sklearn.preprocessing import LabelEncoder, StandardScaler
from sklearn.model_selection import train_test_split
from sklearn.multioutput import MultiOutputClassifier
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import accuracy_score, classification_report
import joblib

# Load the dataset
df = pd.read_csv('mental_health_dataset.csv')

# Features
feature_cols = [
    'anxiety_frequency', 'physical_symptoms', 'impact_on_life',
    'history_of_anxiety', 'sadness_frequency', 'interest_in_activities',
    'energy_levels', 'sleep_patterns', 'thoughts_of_self_harm',
    'support_system', 'feeling_overwhelmed', 'stress_management',
    'social_withdrawal', 'mood_swings', 'concentration_levels',
    'social_engagement', 'perceived_stress', 'feelings_of_isolation'
]

# Targets
target_cols = [
    'anxiety_level', 'depression_level', 'stress_level', 'general_mental_health_score'
]

X = df[feature_cols].copy()
y = df[target_cols].copy()

# Encode categorical variables
label_encoders = {}
for column in X.columns:
    le = LabelEncoder()
    X[column] = le.fit_transform(X[column])
    label_encoders[column] = le

# Encode target variables
target_encoders = {}
for column in y.columns:
    le = LabelEncoder()
    y[column] = le.fit_transform(y[column])
    target_encoders[column] = le

# Save the encoders
joblib.dump(label_encoders, 'label_encoders.pkl')
joblib.dump(target_encoders, 'target_encoders.pkl')

# Feature scaling
scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)
joblib.dump(scaler, 'feature_scaler.pkl')

# Split the dataset
X_train, X_test, y_train, y_test = train_test_split(
    X_scaled, y, test_size=0.2, random_state=42
)

# Create the multi-output classifier
base_estimator = RandomForestClassifier(random_state=42, class_weight='balanced')
clf = MultiOutputClassifier(base_estimator)

# Train the model
clf.fit(X_train, y_train)

# Save the trained model
joblib.dump(clf, 'multi_output_model.pkl')

print("Multi-output model trained and saved successfully.")

# Evaluate the model
y_pred = clf.predict(X_test)

# Evaluate accuracy for each target
for idx, column in enumerate(y.columns):
    print(f"\nEvaluating {column}:")
    accuracy = accuracy_score(y_test.iloc[:, idx], y_pred[:, idx])
    print(f"Accuracy: {accuracy:.2f}")
    print("Classification Report:")
    print(classification_report(
        y_test.iloc[:, idx], y_pred[:, idx],
        target_names=target_encoders[column].classes_
    ))