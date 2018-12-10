package com.example.a123.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterFragment extends Fragment {
    DatabaseHelper db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = new DatabaseHelper(this.getContext());
        initRegisterBtn(savedInstanceState);
    }
    private void initRegisterBtn(final Bundle savedInstanceState) {
        Button registerBtn = getView().findViewById(R.id.register_register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userID = getView().findViewById(R.id.register_user_id);
                EditText ageText = getView().findViewById(R.id.register_re_password);
                EditText passText = getView().findViewById(R.id.register_password);
                String username = userID.getText().toString();
                String age = ageText.getText().toString();
                String password = passText.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Log.d("USER", "FIELD NAME IS EMPTY");
                    Toast.makeText(getActivity(), "FIELD NAME IS EMPTY", Toast.LENGTH_SHORT).show();
                } else if (username.equals("admin")) {
                    Log.d("USER", "USER ALREADY EXIST");
                    Toast.makeText(getActivity(), "USER ALREADY EXIST", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("USER", "GO TO BMI");
                    if (validate(username, password)) {


                        //Check in the database is there any user associated with  this email
                        if (!db.isEmailExists(username)) {

                            //Email does not exist now add new user to database
                            db.addUser(new User(0, username, username, password));
                            Toast.makeText(getActivity(), "User created successful", Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.main_view,new LoginFragment())
                                        .commit();


                        } else {

                            //Email exists with email input provided so show error user already exist
                            Toast.makeText(getActivity(), "User aleready exit", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }



    public boolean validate(String username,String password) {
        boolean valid = false;



        //Handling validation for UserName field
//        if (username.isEmpty()) {
//            valid = false;
//            Toast.makeText(getActivity(), "Please enter valid username", Toast.LENGTH_SHORT).show();
//        } else {
//            if (username.length() > 5) {
//                valid = true;
//
//            } else {
//                valid = false;
//                Toast.makeText(getActivity(), "username is too short", Toast.LENGTH_SHORT).show();
//            }
//        }

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            valid = false;
            Toast.makeText(getActivity(), "Please enter valid email", Toast.LENGTH_SHORT).show();
        } else {
            valid = true;
        }

        //Handling validation for Password field
        if (password.isEmpty()) {
            valid = false;
            Toast.makeText(getActivity(), "Please enter valid password", Toast.LENGTH_SHORT).show();
        } else {
            if (password.length() > 5) {
                valid = true;

            } else {
                valid = false;
                Toast.makeText(getActivity(), "password is too short", Toast.LENGTH_SHORT).show();
            }
        }


        return valid;
    }
}
