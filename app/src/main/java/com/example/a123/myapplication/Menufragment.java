package com.example.a123.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Menufragment extends Fragment {
    DatabaseHelper db;
    ArrayList<String> menu = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = new DatabaseHelper(this.getContext());

        menu.add("BMI");
        menu.add("Weight");
        menu.add("sleep");
        menu.add("Post");
        menu.add("Sign OUT");

        final ArrayAdapter<String> menuAdapter = new ArrayAdapter<>(
                getActivity(),android.R.layout.simple_list_item_1,menu
        );

        ListView menuList = getView().findViewById(R.id.menu_list);
        menuList.setAdapter(menuAdapter);

        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("MENU","Select "+menu.get(i));
                switch (menu.get(i))
                {
                    case "BMI":getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view,new BMIFragment())
                            .commit();
                        Log.d("USER","GO TO BMI");
                        menu.clear();

                        break;
                    case "Weight":getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view,new WeightFragment())
                            .commit();
                        Log.d("USER","GO TO Weight");
                        break;
                    case "sleep":getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view,new SleepFragment())
                            .commit();
                        Log.d("USER","GO TO Weight");
                        break;
                    case "Post":getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view,new PostFragment())
                            .commit();
                        Log.d("USER","GO TO Weight");
                        break;

                    case "Sign OUT":
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view,new LoginFragment())
                                .commit();
                        break;
                    default:break;

                }


            }
        });
    }
}
