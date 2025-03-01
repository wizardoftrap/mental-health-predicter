# create_dataset.py

import pandas as pd
import numpy as np

# Number of samples
n_samples = 10000

# Define possible values and corresponding scores for each feature
frequency_scale = ['Never', 'Rarely', 'Sometimes', 'Often', 'Always']
frequency_scores = {'Never': 0, 'Rarely': 1, 'Sometimes': 2, 'Often': 3, 'Always': 4}

impact_scale = ['Low', 'Moderate', 'High']
impact_scores = {'Low': 0, 'Moderate': 1, 'High': 2}

yes_no = ['No', 'Yes']
yes_no_scores = {'No': 0, 'Yes': 2}

support_scale = ['Strong', 'Moderate', 'Weak']
support_scores = {'Strong': 0, 'Moderate': 1, 'Weak': 2}

sleep_scale = ['Good', 'Moderate', 'Poor']
sleep_scores = {'Good': 0, 'Moderate': 1, 'Poor': 2}

# Initialize a list to collect data samples
data_samples = []

for _ in range(n_samples):
    sample = {}

    # Generate random responses for each question
    sample['anxiety_frequency'] = np.random.choice(frequency_scale)
    sample['physical_symptoms'] = np.random.choice(frequency_scale)
    sample['impact_on_life'] = np.random.choice(impact_scale)
    sample['history_of_anxiety'] = np.random.choice(yes_no)
    sample['sadness_frequency'] = np.random.choice(frequency_scale)
    sample['interest_in_activities'] = np.random.choice(impact_scale)
    sample['energy_levels'] = np.random.choice(impact_scale)
    sample['sleep_patterns'] = np.random.choice(sleep_scale)
    sample['thoughts_of_self_harm'] = np.random.choice(yes_no)
    sample['support_system'] = np.random.choice(support_scale)
    sample['feeling_overwhelmed'] = np.random.choice(frequency_scale)
    sample['stress_management'] = np.random.choice(impact_scale)
    sample['social_withdrawal'] = np.random.choice(frequency_scale)
    sample['mood_swings'] = np.random.choice(frequency_scale)
    sample['concentration_levels'] = np.random.choice(impact_scale)
    sample['social_engagement'] = np.random.choice(impact_scale)
    sample['perceived_stress'] = np.random.choice(impact_scale)
    sample['feelings_of_isolation'] = np.random.choice(frequency_scale)

    # Calculate scores for each category
    # Anxiety Score
    anxiety_score = 0
    anxiety_score += frequency_scores[sample['anxiety_frequency']]
    anxiety_score += frequency_scores[sample['physical_symptoms']]
    anxiety_score += yes_no_scores[sample['history_of_anxiety']]
    anxiety_score += frequency_scores[sample['feeling_overwhelmed']]
    anxiety_score += frequency_scores[sample['social_withdrawal']]
    anxiety_score += frequency_scores[sample['mood_swings']]

    # Assign anxiety level
    if anxiety_score <= 6:
        anxiety_level = 'Low'
    elif anxiety_score <= 12:
        anxiety_level = 'Moderate'
    else:
        anxiety_level = 'High'
    sample['anxiety_level'] = anxiety_level

    # Depression Score
    depression_score = 0
    depression_score += frequency_scores[sample['sadness_frequency']]
    depression_score += impact_scores[sample['interest_in_activities']]
    depression_score += impact_scores[sample['energy_levels']]
    depression_score += sleep_scores[sample['sleep_patterns']]
    depression_score += yes_no_scores[sample['thoughts_of_self_harm']] * 2  # Higher weight
    depression_score += frequency_scores[sample['feelings_of_isolation']]

    # Assign depression level
    if depression_score <= 6:
        depression_level = 'Low'
    elif depression_score <= 12:
        depression_level = 'Moderate'
    else:
        depression_level = 'High'
    sample['depression_level'] = depression_level

    # Stress Score
    stress_score = 0
    stress_score += frequency_scores[sample['feeling_overwhelmed']]
    stress_score += impact_scores[sample['stress_management']] * -1  # Reverse scoring
    stress_score += frequency_scores[sample['social_withdrawal']]
    stress_score += impact_scores[sample['perceived_stress']]

    # Assign stress level
    if stress_score <= 4:
        stress_level = 'Low'
    elif stress_score <= 8:
        stress_level = 'Moderate'
    else:
        stress_level = 'High'
    sample['stress_level'] = stress_level

    # General Mental Health Score (Combining all scores)
    total_score = anxiety_score + depression_score + stress_score
    if total_score <= 16:
        mental_health_score = 'Low'
    elif total_score <= 32:
        mental_health_score = 'Moderate'
    else:
        mental_health_score = 'High'
    sample['general_mental_health_score'] = mental_health_score

    # Add the sample to the dataset
    data_samples.append(sample)

# Create a DataFrame from the list of samples
df = pd.DataFrame(data_samples)

# Shuffle the DataFrame
df = df.sample(frac=1).reset_index(drop=True)

# Save the dataset to a CSV file
df.to_csv('mental_health_dataset.csv', index=False)

print("Synthetic dataset with separate labels created and saved as 'mental_health_dataset.csv'.")