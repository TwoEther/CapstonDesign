/* Assignment: 3
Campus: Ashdod
Authors:
Eliran Naduyev 312089105
Maria Garber
*/

package com.example.capstonproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        recyclerView = findViewById(R.id.recyclerview);
        emptyView = findViewById(R.id.emptyView);

        List<Recipe> recipeList = getIntent().getParcelableArrayListExtra("KEY_RECIPE_LIST");
        for(int i=0; i<recipeList.size(); i++) {
            System.out.println(recipeList.get(i) +"");
        }

        if(recipeList.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        } else {
            emptyView.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);

            RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, recipeList);
            recyclerView.setAdapter(myAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAlpha(1);
        }
    }

//    private void getResults(String searchText) {
//        myrv = findViewById(R.id.recycleview_ingredients_search_result);
//        myrv.setLayoutManager(new GridLayoutManager(this, 2));
//        String URL = "https://openapi.foodsafetykorea.go.kr/api/a610db1f12954422b81a/COOKRCP01/json/1/999/RCP_NM="+searchText;
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
//                Request.Method.GET,
//                URL,
//                null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            COOKRCP01 = (JSONObject) response.get("COOKRCP01");
//                            recipeData = (JSONArray) COOKRCP01.get("row");
//                            for(int i=0; i<recipeData.length(); i++) {
//                                tmp = (JSONObject) recipeData.get(i);
//                                Log.i("the res is:", String.valueOf(tmp.get("RCP_NM")));
//                                String thum = "https"+((String)tmp.get("ATT_FILE_NO_MK")).substring(4);
//                                lstRecipe.add(new Recipe(Integer.toString(i), (String)tmp.get("RCP_NM"),(String)tmp.get("RCP_NM"), thum, Float.parseFloat((String)tmp.get("INFO_WGT")), (String)tmp.get("RCP_PAT2")));
//
//                            }
////                            testArr = response;
//
//                            progressBar.setVisibility(View.GONE);
//                            Collections.sort(lstRecipe, new Comparator<Recipe>() {
//                                @Override
//                                public int compare(Recipe o, Recipe t1) {
//                                    return o.getname().compareTo(t1.getname());
//                                }
//                            });
//                            RecyclerViewAdapterSearchResult myAdapter = new RecyclerViewAdapterSearchResult(getApplicationContext(), lstRecipe);
//                            myrv.setAdapter(myAdapter);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.i("the res is error:", error.toString());
//                    }
//                }
//        );
//        requestQueue.add(jsonObjectRequest);
//    }

//    private String getStringFromList(List<String> ingredientsList) {
//        StringBuilder result= new StringBuilder(ingredientsList.get(0));
//        for (int i=1;i < ingredientsList.size();i++)
//        {
//            result.append(", ").append(ingredientsList.get(i));
//        }
//        return result.toString();
//    }
}