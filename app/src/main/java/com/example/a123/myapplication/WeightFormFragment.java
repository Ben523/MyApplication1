package com.example.a123.myapplication;

import android.app.DatePickerDialog;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class WeightFormFragment extends Fragment {
    private ArrayList<Weight> weights = new ArrayList<>();
    private DBHelper mHelper;
    private String user_id;
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight_form,container,false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mHelper = new DBHelper(getActivity());
        SharedPreferences sp = getContext().getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
        user_id = sp.getString("userId", "");
        weights = mHelper.getWeightList(user_id);

        saveWeight();
        backBtn();
    }
    private void backBtn()
    {
        Button back = getView().findViewById(R.id.weight_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view,new WeightFragment())
                        .commit();
            }
        });
    }
    private void saveWeight()
    {
        Button addWeight = getView().findViewById(R.id.weight_save);
        addWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText date = getView().findViewById(R.id.weight_date1);
                EditText weight = getView().findViewById(R.id.weight_value);


                String dateTxt = date.getText().toString();
                String weightTxt = weight.getText().toString();
                int weight_value = Integer.parseInt(weightTxt);
                Weight last_record;
                if(!weights.isEmpty()) {
                    last_record = weights.get(weights.size() - 1);
                }
                else
                {
                    last_record = new Weight();
                }
                if(weights.isEmpty())
                {
                    Log.d("USER","EMPTY");
                    Log.d("USER",user_id);
                }
                else
                {
                    Log.d("USER","FULL");
                    Log.d("USER",user_id);
                }
                String status = "";
                if((weight_value > last_record.getWeight())&&(!weights.isEmpty()))
                {
                    status = "UP";
                }
                else if(weight_value < last_record.getWeight())
                {
                    status = "DOWN";
                }
                else
                {
                    status = "";
                }

                Weight weight1 = new Weight(dateTxt,weight_value,status);
                Log.d("USER",dateTxt);
                Log.d("USER",weightTxt);
                mHelper.addWeight(weight1,user_id);
                Toast.makeText(getActivity(),"save weight",Toast.LENGTH_SHORT).show();

            }
        });
    }
}
