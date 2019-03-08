package comp3350.melodia.presentation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import comp3350.melodia.R;

public class LoginFragment extends Fragment implements View.OnClickListener{

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        Button register = view.findViewById(R.id.register);
        register.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        Fragment registerFragment = RegisterFragment.newInstance();
        openFragment(registerFragment);
        }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
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