import os
import json
import requests
from flask import Flask, request, jsonify

app = Flask(__name__)

# The OpenRouter API key is injected as an environment variable when the function is deployed.
OPENROUTER_API_KEY = os.getenv('OPENROUTER_API_KEY')
OPENROUTER_URL = 'https://openrouter.ai/api/v1/chat/completions'

@app.route('/generateQuiz', methods=['POST'])
def generate_quiz():
    payload = request.get_json(force=True)
    if not OPENROUTER_API_KEY:
        return jsonify({"error": "OPENROUTER_API_KEY not set on server"}), 500

    headers = {
        'Content-Type': 'application/json',
        'Authorization': f'Bearer {OPENROUTER_API_KEY}',
        'HTTP-Referer': 'https://quizhub.app',
        'X-Title': 'QuizHub AI'
    }

    try:
        resp = requests.post(OPENROUTER_URL, headers=headers, json=payload, timeout=45)
        return (json.dumps(resp.json()), resp.status_code, {'Content-Type': 'application/json'})
    except requests.exceptions.RequestException as e:
        return jsonify({"error": f"OpenRouter gateway error: {str(e)}"}), 502

@app.route('/generateRevisionQuiz', methods=['POST'])
def generate_revision_quiz():
    data = request.get_json(force=True)
    if not OPENROUTER_API_KEY:
        return jsonify({"error": "OPENROUTER_API_KEY not set on server"}), 500

    subject = data.get('subject', 'General')
    weak_topics = data.get('weakTopics', [])
    mistake_questions = data.get('mistakes', [])

    prompt = f"Generate a personalized revision multiple choice quiz for a student in {subject}. " \
             f"The student struggled with these topics: {', '.join(weak_topics)}. " \
             f"Based on their mistakes: {json.dumps(mistake_questions)}, create 5 new targeted practice questions. " \
             f"Return ONLY valid JSON matching: {{\"title\":\"Adaptive Revision\",\"questions\":[{{\"id\":\"r1\",\"text\":\"...\",\"options\":[\"A\",\"B\",\"C\",\"D\"],\"correctOptionIndex\":0,\"explanation\":\"...\"}}]}}"

    payload = {
        "model": "openrouter/free",
        "messages": [
            {"role": "system", "content": "You are QuizHub Adaptive Learning AI. Return ONLY JSON without markdown fences."},
            {"role": "user", "content": prompt}
        ]
    }

    headers = {
        'Content-Type': 'application/json',
        'Authorization': f'Bearer {OPENROUTER_API_KEY}'
    }

    try:
        resp = requests.post(OPENROUTER_URL, headers=headers, json=payload, timeout=45)
        return (json.dumps(resp.json()), resp.status_code, {'Content-Type': 'application/json'})
    except Exception as e:
        return jsonify({"error": str(e)}), 502

def cloud_function(request):
    with app.request_context(request.environ):
        return app.full_dispatch_request()
