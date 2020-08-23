package com.example.attendo.ui.auth.login;

public class loginPresenter implements logininterface.Presenter, logininterface.Tasklistener{

    private logininterface.View view;
    private logininterface.Model model;


    public loginPresenter(logininterface.View view) {
        this.view = view;
        model = new loginmodel(this);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void toLogin(String email, String password) {
        if(view != null){
            view.disableinput();
        }
        model.doLogin(email,password);
    }

    @Override
    public void OnSuccess() {
        if(view!=null){
            view.enableInput();
            view.onLogin();
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
