/* Assignment: 3
Campus: Ashdod
Authors:
Eliran Naduyev 312089105
Maria Garber
*/

package com.example.capstonproject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private List<Recipe> lstRecipe = new ArrayList<>();
    private List<Recipe> searchRecipe;
    private JSONArray testArr;
    private JSONObject COOKRCP01, tmp;
    private JSONArray recipeData;
    private ImageButton searchBtn;
    private Button breakfastBtn, lunchBtn, dinnerBtn;
    private TextView searchTv, emptyView;
    private RecyclerView myrv;
    private ProgressBar progressBar;
    List<Recipe> newRecipe = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View RootView = inflater.inflate(R.layout.fragment_home, container, false);
        Toolbar mToolbarContact = RootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(mToolbarContact);
        progressBar = RootView.findViewById(R.id.progressbar2);
        emptyView = RootView.findViewById(R.id.empty_view2);
        myrv = RootView.findViewById(R.id.recyclerview);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        Bundle bundle = getArguments();
        getRandomRecipes(bundle);
        searchTv = RootView.findViewById(R.id.home_search_et);
        searchBtn = RootView.findViewById(R.id.home_search_btn);
        //breakfastBtn= RootView.findViewById(R.id.home_breakfast_filter);
        //lunchBtn= RootView.findViewById(R.id.home_lunch_filter);
        //dinnerBtn= RootView.findViewById(R.id.home_dinner_filter);
        //breakfastBtn.setOnClickListener(this);
        //lunchBtn.setOnClickListener(this);
        //dinnerBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        searchTv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!v.getText().toString().equals("")) {
                        emptyView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        myrv.setAlpha(0);
                        searchRecipe(v.getText().toString());
                    } else
                        Toast.makeText(getContext(), "Type something...", Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return RootView;
    }

    private void searchRecipe(String search) {
        searchRecipe = new ArrayList<Recipe>();
//        String URL="https://api.spoonacular.com/recipes/search?query=" + search + "&number=30&instructionsRequired=true&apiKey=c957b6816ba048139fbc25a67d2cff33";
        String URL = "https://openapi.foodsafetykorea.go.kr/api/a610db1f12954422b81a/COOKRCP01/json/1/99/RCP_NM=" + search;
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
//                              testArr = (JSONArray) response.get("results");
                            COOKRCP01 = (JSONObject) response.get("COOKRCP01");
                            String total = (String) COOKRCP01.get("total_count");

//                            if(total.equals("0")){
//                                myrv.setAlpha(0);
//                                emptyView.setVisibility(View.VISIBLE);
//                            }
//
//                            else{
//                                recipeData = (JSONArray) COOKRCP01.get("row");
//                                for(int i=0; i<recipeData.length(); i++) {
//                                    Log.i("the search res is:", String.valueOf(tmp.get("RCP_NM")));
//                                    String thum = "https"+((String)tmp.get("ATT_FILE_NO_MK")).substring(4);
//                                    searchRecipe.add(new Recipe((String)tmp.get("RCP_NM"),(String)tmp.get("RCP_NM"), thum, Integer.parseInt((String)tmp.get("INFO_WGT")), (String)tmp.get("RCP_PAT2")));
//                                }

                            recipeData = (JSONArray) COOKRCP01.get("row");
                            for (int i = 0; i < recipeData.length(); i++) {
                                tmp = (JSONObject) recipeData.get(i);
                                Log.i("the search res is:", String.valueOf(tmp.get("RCP_NM")));
                                String thum = "https" + ((String) tmp.get("ATT_FILE_NO_MK")).substring(4);
                                searchRecipe.add(new Recipe((String) tmp.get("RCP_SEQ"), (String) tmp.get("RCP_NM"), (String) tmp.get("RCP_NM"), thum, Float.parseFloat((String) tmp.get("INFO_ENG")), (String) tmp.get("RCP_PAT2")));
                            }



                            emptyView.setVisibility(View.GONE);
                            RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getContext(), searchRecipe);
                            myrv.setAdapter(myAdapter);
                            myrv.setItemAnimator(new DefaultItemAnimator());
                            myrv.setAlpha(1);

                            progressBar.setVisibility(View.GONE);

//                            if(searchRecipe.isEmpty()){
//                                myrv.setAlpha(0);
//                                emptyView.setVisibility(View.VISIBLE);
//                            }
//                            else{
//                                emptyView.setVisibility(View.GONE);
//                                RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getContext(), searchRecipe);
//                                myrv.setAdapter(myAdapter);
//                                myrv.setItemAnimator(new DefaultItemAnimator());
//                                myrv.setAlpha(1);
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("the res is error:", error.toString());
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    private void getRandomRecipes(Bundle bundle) {
//        Random rnd = new Random();                // 추후에 수정 동작확인 완료
//        Integer rndnum = rnd.nextInt(970) + 1;
        String URL = " https://openapi.foodsafetykorea.go.kr/api/a610db1f12954422b81a/COOKRCP01/json/1/100";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            COOKRCP01 = (JSONObject) response.get("COOKRCP01");
                            recipeData = (JSONArray) COOKRCP01.get("row");
                            for (int i = 0; i < recipeData.length(); i++) {
                                tmp = (JSONObject) recipeData.get(i);
                                Log.i("the res is:", String.valueOf(tmp.get("RCP_NM")));
                                String thum = "https" + ((String) tmp.get("ATT_FILE_NO_MK")).substring(4);
//                                lstRecipe.add(new Recipe((String)tmp.get("RCP_SEQ"),(String)tmp.get("RCP_NM"), (String) tmp.get("ATT_FILE_NO_MK"), Integer.parseInt((String)tmp.get("INFO_ENG")), (String)tmp.get("RCP_PAT2")));
                                lstRecipe.add(new Recipe((String) tmp.get("RCP_SEQ"), tmp.optString("RCP_NM"), tmp.optString("RCP_NM"), thum, Float.parseFloat((String) tmp.get("INFO_ENG")), tmp.optString("RCP_PAT2")));
                            }

                            if(bundle != null){
                                ArrayList<String> category = bundle.getStringArrayList("category");
                                for(int i = 0; i < category.size(); i++){
                                    for(int j = 0; j < lstRecipe.size(); j++){
                                        Log.d("Bundle recipe", lstRecipe.get(j).getkind() + " " + category.get(i));
                                        if(Objects.equals(lstRecipe.get(j).getkind(), category.get(i))){
                                            newRecipe.add(lstRecipe.get(j));
                                        }
                                    }
                                }
                                RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getContext(), newRecipe);
                                myrv.setAdapter(myAdapter);
                            } else {
                                RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getContext(), lstRecipe);
                                myrv.setAdapter(myAdapter);
                            }

                            progressBar.setVisibility(View.GONE);
                            myrv.setItemAnimator(new DefaultItemAnimator());


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("the res is error:", error.toString());
                        progressBar.setVisibility(View.GONE);
                        myrv.setAlpha(0);
                        emptyView.setVisibility(View.VISIBLE);
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onClick(View v) {
        if (v == breakfastBtn) {
            searchRecipe("breakfast");
        } else if (v == lunchBtn) {
            searchRecipe("lunch");
        } else if (v == dinnerBtn) {
            searchRecipe("dinner");
        } else if (v == searchBtn) {
            try {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            } catch (Exception e) {
            }
            if (!searchTv.getText().toString().toString().equals("")) {
                progressBar.setVisibility(View.VISIBLE);
                myrv.setAlpha(0);
                searchRecipe(searchTv.getText().toString());
            } else
                Toast.makeText(getContext(), "Type something...", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }


}
