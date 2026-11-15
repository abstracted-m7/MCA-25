package com.example.quizmania.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.quizmania.repository.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

public class AuthViewModel extends AndroidViewModel {
    private AuthRepository authRepository;
    private MutableLiveData<FirebaseUser> userLiveData;
    private MutableLiveData<Boolean> loginSuccessLiveData = new MutableLiveData<>();

    public AuthViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository();
        userLiveData = authRepository.getCurrentUser();
    }

    public void login(String email, String password) {
        authRepository.login(email, password, loginSuccessLiveData);
    }

    public void register(String name, String email, String password, String role) {
        authRepository.register(name, email, password, role, loginSuccessLiveData);
    }

    public LiveData<Boolean> getLoginSuccess() {
        return loginSuccessLiveData;
    }

    public LiveData<FirebaseUser> getUser() {
        return userLiveData;
    }
}
