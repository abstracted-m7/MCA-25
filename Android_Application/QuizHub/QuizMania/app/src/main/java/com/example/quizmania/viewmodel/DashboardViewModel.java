package com.example.quizmania.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quizmania.model.Quiz;
import com.example.quizmania.repository.AuthRepository;
import com.example.quizmania.repository.QuizRepository;

import java.util.List;

public class DashboardViewModel extends ViewModel {
    private QuizRepository quizRepository;
    private AuthRepository authRepository;
    private MutableLiveData<List<Quiz>> quizzesLiveData;

    public DashboardViewModel() {
        quizRepository = new QuizRepository();
        authRepository = new AuthRepository();
        quizzesLiveData = new MutableLiveData<>();
        fetchQuizzes();
    }

    public void fetchQuizzes() {
        quizRepository.fetchQuizzes(quizzesLiveData);
    }

    public LiveData<List<Quiz>> getQuizzes() {
        return quizzesLiveData;
    }
    
    public void logout() {
        authRepository.logout();
    }
}
