package com.example.a123.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginFragment extends Fragment {
    DatabaseHelper db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = new DatabaseHelper(this.getContext());
        initLoginBtn(savedInstanceState);
        initRegisterBtn(savedInstanceState);
    }

    private void initLoginBtn(final Bundle savedInstanceState)
    {
        Button loginBtn = getView().findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userID = getView().findViewById(R.id.login_user_id);
                EditText passwordText = getView().findViewById(R.id.login_password);
                String username = userID.getText().toString();
                String password = passwordText.getText().toString();

                if(username.isEmpty()||password.isEmpty()){
                    Toast.makeText(getActivity(),
                            "USER OR PASSWORD IS EMPTY",
                            Toast.LENGTH_SHORT).show();
                    Log.d("USER","USER OR PASSWORD IS EMPTY");
                }else if(username.equals("admin") && password.equals("admin")){
                    Toast.makeText(getActivity(),
                            "“INVALID USER OR PASSWORD",
                            Toast.LENGTH_SHORT).show();
                    Log.d("USER","INVALID USER OR PASSWORD");
                }else{
                    Log.d("USER","GO TO BMI");
                    Log.d("USER",username);
                    User currentUser = db.Authenticate(new User(0, null, username, password));
                    if (currentUser != null) {
                        Toast.makeText(getActivity(), "Successfully Logged in!", Toast.LENGTH_SHORT).show();
                        int x = 100;

                        SharedPreferences sp = getContext().getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("My_Value", username);
                        editor.commit();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view,new Menufragment())
                                .commit();
                    } else {

                        //User Logged in Failed
                        Toast.makeText(getActivity(), "Failed to  Logged in!", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });
    }

    private void initRegisterBtn(final Bundle savedInstanceState)
    {
        TextView registerBtn = getView().findViewById(R.id.login_register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("USER","GO TO REGISTER");
                if(savedInstanceState == null){
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view,new RegisterFragment())
                            .commit();
                }

            }
        });
    }
}
