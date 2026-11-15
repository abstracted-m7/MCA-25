package com.example.quizmania.util;

import android.os.Handler;
import android.os.Looper;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Utility class that sends email silently via Gmail SMTP on a background thread.
 * Uses a dedicated Gmail App Password — does NOT open any email client.
 *
 * Setup: Go to https://myaccount.google.com/apppasswords and generate an App Password
 * for the sender Gmail account, then paste it as SENDER_APP_PASSWORD below.
 */
public class EmailSender {

    // ── Configure these once ──────────────────────────────────────────────────
    /** Gmail address that SENDS the feedback email (your account). */
    private static final String SENDER_EMAIL = "bishuya545rok@gmail.com";

    /**
     * Gmail App Password for the sender account.
     * Generate at: Google Account → Security → 2-Step Verification → App Passwords.
     * Replace the placeholder below with your 16-character App Password.
     */
    private static final String SENDER_APP_PASSWORD = "vogn wjsk cqvu eiby";

    /** The recipient email address. */
    private static final String RECIPIENT_EMAIL = "bishuya545rok@gmail.com";
    // ─────────────────────────────────────────────────────────────────────────

    /** Callback interface for send result. */
    public interface SendCallback {
        void onSuccess();
        void onFailure(String error);
    }

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    /**
     * Sends an email in the background thread.
     *
     * @param subject  Email subject line
     * @param body     Plain-text email body
     * @param callback Result callback delivered on the main thread
     */
    public static void send(String subject, String body, SendCallback callback) {
        executor.execute(() -> {
            try {
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.starttls.required", "true");

                Session session = Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(SENDER_EMAIL, SENDER_APP_PASSWORD);
                    }
                });

                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(SENDER_EMAIL, "QuizMania Feedback"));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(RECIPIENT_EMAIL));
                message.setSubject(subject);
                message.setText(body);

                Transport.send(message);

                mainHandler.post(() -> {
                    if (callback != null) callback.onSuccess();
                });

            } catch (Exception e) {
                String errorMsg = e.getMessage() != null ? e.getMessage() : "Unknown SMTP error";
                mainHandler.post(() -> {
                    if (callback != null) callback.onFailure(errorMsg);
                });
            }
        });
    }
}

