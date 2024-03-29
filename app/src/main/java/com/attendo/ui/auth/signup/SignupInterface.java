package com.attendo.ui.auth.signup;

public interface SignupInterface {

    interface View{
        void disableinput();
        void enableInput();
        void handleSignup();
        boolean isValidEmail();
        boolean isValidPassword();
        boolean isValidConfPassword();
        void onSignup();
        void onError(String message);
    }
    interface Presenter{
        void onDestroy();
        void toLogin(String email,String password,String confpassword);
    }

    interface Model {
        void doSignup(String email,String password,String confpassword);
    }

    interface Tasklistener{
        void OnSuccess();
        void OnError(String message);
    }

}
