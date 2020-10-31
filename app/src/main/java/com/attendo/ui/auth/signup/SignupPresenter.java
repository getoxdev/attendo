package com.attendo.ui.auth.signup;

public class SignupPresenter implements SignupInterface.Presenter, SignupInterface.Tasklistener {

    private SignupInterface.View view;
    private SignupInterface.Model model;

    public SignupPresenter(SignupInterface.View view) {
        this.view = view;
        model = new SignupModel(this);
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
