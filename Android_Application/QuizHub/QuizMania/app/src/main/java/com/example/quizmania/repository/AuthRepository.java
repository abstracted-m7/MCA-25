package com.example.quizmania.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.quizmania.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class AuthRepository {
    private static final String TAG = "AuthRepository";
    private final FirebaseAuth firebaseAuth;
    private final FirebaseFirestore db;

    public interface AuthCallback {
        void onSuccess(User user);
        void onFailure(String errorMessage);
    }

    public AuthRepository() {
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public MutableLiveData<FirebaseUser> getCurrentUser() {
        MutableLiveData<FirebaseUser> userLiveData = new MutableLiveData<>();
        userLiveData.setValue(firebaseAuth.getCurrentUser());
        return userLiveData;
    }

    public void login(String email, String password, MutableLiveData<Boolean> successLiveData) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful() && task.getException() != null) {
                        Log.e(TAG, "Login failed", task.getException());
                    }
                    successLiveData.setValue(task.isSuccessful());
                });
    }

    public void loginWithCallback(String email, String password, AuthCallback callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser firebaseUser = authResult.getUser();
                    if (firebaseUser != null) {
                        fetchUserProfile(firebaseUser.getUid(), callback);
                    } else {
                        callback.onFailure("User record not found");
                    }
                })
                .addOnFailureListener(e -> callback.onFailure(e.getMessage() != null ? e.getMessage() : "Login failed"));
    }

    public void register(String name, String email, String password, String role, MutableLiveData<Boolean> successLiveData) {
        register(name, email, password, "BWU/MCA/25/001", role, new AuthCallback() {
            @Override
            public void onSuccess(User user) {
                successLiveData.setValue(true);
            }

            @Override
            public void onFailure(String errorMessage) {
                successLiveData.setValue(false);
            }
        });
    }

    public void register(String name, String email, String password, String rollNumber, String role, AuthCallback callback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser firebaseUser = authResult.getUser();
                    if (firebaseUser != null) {
                        String uid = firebaseUser.getUid();
                        User user = new User(uid, name, email, rollNumber, role, System.currentTimeMillis());
                        db.collection("users").document(uid)
                                .set(user)
                                .addOnSuccessListener(aVoid -> callback.onSuccess(user))
                                .addOnFailureListener(e -> {
                                    Log.e(TAG, "Firestore write failed", e);
                                    callback.onFailure("Failed to create user profile: " + e.getMessage());
                                });
                    } else {
                        callback.onFailure("User creation returned empty user");
                    }
                })
                .addOnFailureListener(e -> callback.onFailure(e.getMessage() != null ? e.getMessage() : "Registration failed"));
    }

    public void fetchUserProfile(String uid, AuthCallback callback) {
        db.collection("users").document(uid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        User user = documentSnapshot.toObject(User.class);
                        if (user != null) {
                            if (user.getId() == null) user.setId(uid);
                            callback.onSuccess(user);
                        } else {
                            callback.onFailure("Failed to parse user profile");
                        }
                    } else {
                        // Create default user if document doesn't exist yet
                        FirebaseUser current = firebaseAuth.getCurrentUser();
                        String email = current != null ? current.getEmail() : "";
                        User user = new User(uid, "Student", email, "BWU/MCA/25/001", "STUDENT", System.currentTimeMillis());
                        db.collection("users").document(uid).set(user);
                        callback.onSuccess(user);
                    }
                })
                .addOnFailureListener(e -> callback.onFailure("Error fetching profile: " + e.getMessage()));
    }

    public void logout() {
        firebaseAuth.signOut();
    }
}
