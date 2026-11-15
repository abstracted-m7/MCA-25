# QuizHub – AI-Powered Adaptive Learning and Quiz Management Platform

QuizHub is a complete, production-quality native Android application built using Java and the Android SDK, powered by Firebase Firestore and OpenRouter AI.

It features two distinct user panels:
1. **Student Panel**: Subject-wise quizzes, OpenRouter AI quiz generator, Mistake Notebook, Progress analytics, and an **Adaptive Learning Engine**.
2. **Teacher Panel**: Curriculum & subject management, question bank authoring, quiz creation, roll-number based student tracking, and class-wide analytics.

---

## 🌟 Core Differentiator: 5-Attempt Adaptive Learning System

1. **Wrong Answer Tracking**: Whenever a student submits a quiz, QuizHub logs every incorrect answer event into the Firestore `wrongAnswers` collection with a snapshot of the question, selected answer, correct answer, and explanation.
2. **Subject-Specific 5-Attempt Counter**: In `learningProgress`, QuizHub maintains an independent counter per student per subject (e.g., Java, Python, DBMS).
3. **Automated Personalized Revision Generation**: Upon completing the 5th attempt in a subject, QuizHub automatically:
   - Queries the incorrect answers from those 5 attempts
   - Deduplicates question IDs to ensure no duplicate practice items
   - Analyzes weak topics and frequently missed concepts
   - Generates and schedules a personalized revision quiz batch (`status: READY`)
   - Alerts the student on their dashboard with a prominent revision banner
4. **Mastery Tracking**: Questions transition through mastery tiers:
   - **Weak**: 0 correct answers on revision
   - **Practicing**: 1 correct answer on revision
   - **Mastered**: 2 correct answers on revision
5. **Before/After Improvement Metrics**: Revision quizzes measure and report the student's performance growth (+X percentage points).

---

## 🚀 Application Architecture & Package Structure

```
com.example.quizmania/
├── model/
│   ├── User.java                # Student / Teacher user profile with rollNumber
│   ├── Subject.java             # Subject curriculum models
│   ├── Topic.java               # Topics mapped to subjects
│   ├── Question.java            # 4-option MCQ with explanations
│   ├── Quiz.java                # Quiz models with duration & difficulty
│   ├── QuizAttempt.java         # Student attempt records
│   ├── WrongAnswer.java         # Historical mistake snapshots & mastery status
│   ├── LearningProgress.java    # Subject-specific 5-attempt progress tracking
│   ├── RevisionQuiz.java        # Personalized adaptive revision batch
│   └── RevisionAttempt.java     # Revision performance & improvement stats
├── student/
│   ├── AttemptQuizActivity.java      # Timer-based MCQ quiz engine with auto-submit
│   ├── QuizResultActivity.java       # Comprehensive score breakdown & 5-attempt alerts
│   ├── ReviewAnswersActivity.java    # Full answer keys & explanations
│   ├── SubjectBrowseActivity.java    # Subject & topic-wise quiz catalog
│   ├── MistakeNotebookActivity.java  # Weak, Practicing, Mastered question book
│   ├── RevisionDashboardActivity.java# 5-attempt step tracker & ready revisions
│   ├── RevisionQuizActivity.java     # Personalized revision quiz attempt
│   └── StudentProgressActivity.java  # Subject bar charts & weakness analysis
├── teacher/
│   ├── ManageSubjectActivity.java    # Add & organize subjects and curriculum topics
│   ├── AddQuestionActivity.java      # Add questions to central Question Bank
│   ├── CreateQuizActivity.java       # Create & publish subject-wise quizzes
│   ├── StudentTrackingActivity.java  # Search students by roll number (e.g. BWU/MCA/25/040)
│   └── TeacherAnalyticsActivity.java # Class average benchmarks & frequent errors
├── view/
│   ├── auth/AuthActivity.java        # Student & Teacher authentication
│   ├── dashboard/StudentDashboardActivity.java # Student main hub
│   ├── dashboard/AdminDashboardActivity.java   # Teacher main hub
│   ├── launch/LaunchActivity.java    # Welcome portal chooser
│   └── quiz/AiQuizActivity.java      # OpenRouter AI quiz generation
├── repository/
│   ├── AuthRepository.java
│   ├── SubjectRepository.java
│   ├── QuestionRepository.java
│   ├── QuizRepository.java
│   ├── AdaptiveLearningRepository.java
│   └── StudentRepository.java
├── services/
│   ├── QuizEvaluationService.java
│   ├── AdaptiveLearningService.java
│   └── ProgressCalculationService.java
├── ui/
│   └── SubjectBarChartView.java      # Dynamic custom Canvas bar chart for subjects
└── network/
    ├── RetrofitClient.java
    └── OpenRouterService.java
```

---

## ⚙️ Configuration & Setup

### 1. Firebase Integration
- `google-services.json` is located in `/app/google-services.json` (Project ID: `quizmania-25861`).
- Firebase Authentication (Email/Password) is enabled.
- Cloud Firestore collections used:
  - `users`
  - `subjects`
  - `topics`
  - `questions`
  - `quizzes`
  - `quizAttempts`
  - `wrongAnswers`
  - `learningProgress`
  - `revisionQuizzes`
  - `revisionAttempts`
- Security rules are defined in `firestore.rules`.

### 2. OpenRouter AI Configuration
- In `local.properties`:
  ```properties
  OPENROUTER_API_KEY=sk-or-v1-...
  ```
- In `functions/main.py`: A Python Cloud Function gateway provides server-side proxying to OpenRouter for `/generateQuiz` and `/generateRevisionQuiz`.

---

## 📱 Build & Run Instructions

To compile the application:
```bash
./gradlew.bat assembleDebug
```
The resulting debug APK will be generated at:
`app/build/outputs/apk/debug/app-debug.apk`
