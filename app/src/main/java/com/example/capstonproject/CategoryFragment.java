package com.example.capstonproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ToggleButton btn1, btn2, btn3, btn4, btn5;
    Button select_btn;

    private ArrayList<String> category = new ArrayList<>();

    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn1 = view.findViewById(R.id.button);
        btn2 = view.findViewById(R.id.button2);
        btn3 = view.findViewById(R.id.button3);
        btn4 = view.findViewById(R.id.button4);
        btn5 = view.findViewById(R.id.button5);
        select_btn = view.findViewById(R.id.select_btn);

        btn1.setOnClickListener(view1 -> {
            if(btn1.isChecked()) category.add("밥");
            else category.remove("밥");
        });

        btn2.setOnClickListener(view1 -> {
            if(btn2.isChecked()) category.add("반찬");
            else category.remove("반찬");
        });

        btn3.setOnClickListener(view1 -> {
            if(btn3.isChecked()) category.add("국&찌개");
            else category.remove("국&찌개");
        });

        btn4.setOnClickListener(view1 -> {
            if(btn4.isChecked()) category.add("일품");
            else category.remove("일품");
        });

        btn5.setOnClickListener(view1 -> {
            if(btn5.isChecked()) category.add("후식");
            else category.remove("후식");
        });

        select_btn.setOnClickListener(view1 -> {
            Toast.makeText(requireContext(), "카테고리가 저장되었습니다!", Toast.LENGTH_SHORT).show();
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            HomeFragment homeFragment = new HomeFragment();
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("category", category);
            homeFragment.setArguments(bundle);

            transaction.replace(R.id.fragment_container, homeFragment);
            transaction.commit();
        });
    }
}