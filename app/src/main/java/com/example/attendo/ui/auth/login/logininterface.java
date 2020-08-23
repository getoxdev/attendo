package com.example.attendo.ui.auth.login;

public interface logininterface {

    interface View{
        void disableinput();
        void enableInput();
        void handleLogin();
        boolean isValidEmail();
        boolean isValidPassword();
        void onLogin();
        void onError(String message);
    }

    interface Presenter{
        void onDestroy();
        void toLogin(String email,String password);
    }

    interface Model {
        void doLogin(String email,String password);
    }

    interface Tasklistener{
        void OnSuccess();
        void OnError(String message);
    }

}
