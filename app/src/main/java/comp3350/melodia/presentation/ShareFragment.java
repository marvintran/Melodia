package comp3350.melodia.presentation;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import comp3350.melodia.R;

public class ShareFragment extends Fragment implements View.OnClickListener {


    private OnRegisterClickedListener registerListener;
    private OnLoginClickedListener loginListener;

    public interface OnRegisterClickedListener {
        public void onRegisterClicked();
    }
    public interface OnLoginClickedListener {
        void onLoginClicked();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            registerListener = (OnRegisterClickedListener) context;
            loginListener = (OnLoginClickedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() + " must implement OnArticleSelectedListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_share, container, false);
        Button login = view.findViewById(R.id.login);
        Button register = view.findViewById(R.id.register);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                onLoginClicked();
                break;
            case R.id.register:
                onRegisterClicked();
                break;
        }
    }

    public void onRegisterClicked() {
        registerListener.onRegisterClicked();
    }

    public void onLoginClicked(){
        loginListener.onLoginClicked();
    }

    public static ShareFragment newInstance() {
        ShareFragment fragment = new ShareFragment();
        return fragment;
    }


    private void openFragment(Fragment fragment) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction().hide(this);
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}