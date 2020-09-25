package com.attendo.ui.auth.signup;

public class signupPresenter implements signupinterface.Presenter, signupinterface.Tasklistener {

    private signupinterface.View view;
    private signupinterface.Model model;

    public signupPresenter(signupinterface.View view) {
        this.view = view;
        model = new signupmodel(this);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void toLogin(String email, String password,  String institution) {
        if(view != null){
            view.disableinput();
        }
        model.doSignup(email,password,institution);
    }

    @Override
    public void OnSuccess() {
        if(view!=null){
            view.enableInput();
            view.onSignup();
        }
    }

    @Override
    public void OnError(String message) {
        if(view!=null){
            view.enableInput();
            view.onError(message);
        }
    }
}
