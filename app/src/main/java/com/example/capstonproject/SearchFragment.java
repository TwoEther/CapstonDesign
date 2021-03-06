/* Assignment: 3
Campus: Ashdod
Authors:
Eliran Naduyev 312089105
Maria Garber
*/

package com.example.capstonproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SearchFragment extends Fragment {

    private ArrayList<Recipe> recipeList = new ArrayList<>();
    private JSONArray recipeData;
    private JSONObject cookrcp01;

    private RecyclerView myrv;

    private Set<String> recipeSet = new HashSet<>();
    private Set<String> keywordSet = new HashSet<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //initializeIngredients();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View RootView = inflater.inflate(R.layout.fragment_search, container, false);
        Toolbar mToolbarContact = RootView.findViewById(R.id.toolbar_search);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(mToolbarContact);

        /*
        myrv = RootView.findViewById(R.id.recycleview_ingredients);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        RecyclerViewAdapterIngredient myAdapter = new RecyclerViewAdapterIngredient(getContext(), lstIngredient);
        myrv.setAdapter(myAdapter);
         */

        RootView.findViewById(R.id.ingredients_search).setOnClickListener(v -> {
            searchRecipe();
        });

        RootView.findViewById(R.id.button1).setOnClickListener(v -> {
            setIngredientOnClick("?????????");
        });

        RootView.findViewById(R.id.button2).setOnClickListener(v -> {
            setIngredientOnClick("?????????");
        });

        RootView.findViewById(R.id.button3).setOnClickListener(v -> {
            setIngredientOnClick("???");
        });

        RootView.findViewById(R.id.button4).setOnClickListener(v -> {
            setIngredientOnClick("??????");
        });

        RootView.findViewById(R.id.button5).setOnClickListener(v -> {
            setIngredientOnClick("?????????");
        });

        RootView.findViewById(R.id.button6).setOnClickListener(v -> {
            setIngredientOnClick("??????");
        });

        RootView.findViewById(R.id.button7).setOnClickListener(v -> {
            setIngredientOnClick("??????");
        });

        RootView.findViewById(R.id.button8).setOnClickListener(v -> {
            setIngredientOnClick("??????");
        });

        RootView.findViewById(R.id.button9).setOnClickListener(v -> {
            setIngredientOnClick("?????????");
        });

        RootView.findViewById(R.id.button10).setOnClickListener(v -> {
            setIngredientOnClick("??????");
        });

        return RootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    //private void initializeIngredients() {
//        lstIngredient.add(new Ingredient("??????", "beef-cubes-raw.png"));
//        lstIngredient.add(new Ingredient("??????", "fish-fillet.jpg"));
//        lstIngredient.add(new Ingredient("???", "chicken-breasts.png"));
//        lstIngredient.add(new Ingredient("??????", "canned-tuna.png"));
//        lstIngredient.add(new Ingredient("?????????", "flour.png"));
//        lstIngredient.add(new Ingredient("???", "uncooked-white-rice.png"));
//        lstIngredient.add(new Ingredient("?????????", "fusilli.jpg"));
//        lstIngredient.add(new Ingredient("??????", "cheddar-cheese.png"));
//        lstIngredient.add(new Ingredient("??????", "butter.png"));
//        lstIngredient.add(new Ingredient("???", "white-bread.jpg"));
//        lstIngredient.add(new Ingredient("??????", "brown-onion.png"));
//        lstIngredient.add(new Ingredient("??????", "garlic.jpg"));
//        lstIngredient.add(new Ingredient("??????", "milk.png"));
//        lstIngredient.add(new Ingredient("??????", "egg.png"));
//        lstIngredient.add(new Ingredient("??????", "vegetable-oil.jpg"));
//        lstIngredient.add(new Ingredient("?????????", "plain-yogurt.jpg"));
//        lstIngredient.add(new Ingredient("??????", "salt.jpg"));
//        lstIngredient.add(new Ingredient("??????", "sugar-in-bowl.png"));
//        lstIngredient.add(new Ingredient("??????", "pepper.jpg"));
//        lstIngredient.add(new Ingredient("???", "water.jpg"));
//        lstIngredient.add(new Ingredient("?????????", "parsley.jpg"));
//        lstIngredient.add(new Ingredient("??????", "basil.jpg"));
//        lstIngredient.add(new Ingredient("?????????", "milk-chocolate.jpg"));
//        lstIngredient.add(new Ingredient("?????????", "hazelnuts.png"));
//        lstIngredient.add(new Ingredient("?????????", "tomato.png"));
//        lstIngredient.add(new Ingredient("??????", "cucumber.jpg"));
//        lstIngredient.add(new Ingredient("??????", "red-bell-pepper.jpg"));
//        lstIngredient.add(new Ingredient("??????", "portabello-mushrooms.jpg"));
//        lstIngredient.add(new Ingredient("??????", "lemon.jpg"));
//        lstIngredient.add(new Ingredient("?????????", "orange.jpg"));
//        lstIngredient.add(new Ingredient("?????????", "bananas.jpg"));
//        lstIngredient.add(new Ingredient("??????", "red-wine.jpg"));
//        lstIngredient.add(new Ingredient("??????", "apple.jpg"));
//        lstIngredient.add(new Ingredient("????????????", "berries-mixed.jpg"));
//        lstIngredient.add(new Ingredient("?????????", "buttermilk-biscuits.jpg"));
//        lstIngredient.add(new Ingredient("????????????", "pineapple.jpg"));

    //  }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    private void searchRecipe() {
//        String URL="https://api.spoonacular.com/recipes/search?query=" + search + "&number=30&instructionsRequired=true&apiKey=c957b6816ba048139fbc25a67d2cff33";
        String URL = " https://openapi.foodsafetykorea.go.kr/api/a610db1f12954422b81a/COOKRCP01/json/1/100";

        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, response -> {
            try {
                recipeSet.clear(); //????????? ????????? ????????? ???????????? ??????, ????????? ????????? ?????? ?????? ?????????
                recipeList.clear();

                cookrcp01 = (JSONObject) response.get("COOKRCP01");
                recipeData = (JSONArray) cookrcp01.get("row");

                System.out.println(recipeData.length());
                for (int i = 0; i < recipeData.length(); i++) {
                    JSONObject jsonObject = recipeData.getJSONObject(i);
                    String[] rcpPartsArray = ((String) jsonObject.get("RCP_PARTS_DTLS")).split("\n|,");
                    String recipeId = (String) jsonObject.get("RCP_SEQ");

                    for (int j = 0; j < rcpPartsArray.length; j++) {
                        System.out.print(rcpPartsArray[j] + ", ");

                        String[] rcpPartsStringArray = rcpPartsArray[j].split(" ");
                        for (int k = 0; k < rcpPartsStringArray.length; k++) {
                            if (keywordSet.contains(rcpPartsStringArray[k])) {
                                if(recipeSet.contains(recipeId)) break; //?????? ???????????? ?????? ????????? ???????????? ????????? ???????????? ?????? (?????? ??????)

                                recipeSet.add(recipeId);
                                String thumbnail = "https" + ((String) jsonObject.get("ATT_FILE_NO_MK")).substring(4);
                                recipeList.add(new Recipe((String) jsonObject.get("RCP_SEQ"), (String) jsonObject.get("RCP_NM"), (String) jsonObject.get("RCP_NM"), thumbnail, Float.parseFloat((String) jsonObject.get("INFO_ENG")), (String) jsonObject.get("RCP_PAT2")));
                                break;
                            }
                        }
                    }

                    System.out.println();
                }
                System.out.println(recipeList.size());

                moveSearchResultsActivity();
//                        for (int i = 0; i < recipeData.length(); i++) {
//                            JSONObject jsonObject = recipeData.getJSONObject(i);
//                            Log.i("the search res is:", String.valueOf(jsonObject.get("RCP_NM")));
//                            String thumbnail = "https" + ((String) jsonObject.get("ATT_FILE_NO_MK")).substring(4);
//                            recipeList.add(new Recipe((String) jsonObject.get("RCP_SEQ"), (String) jsonObject.get("RCP_NM"), (String) jsonObject.get("RCP_NM"), thumbnail, Float.parseFloat((String) jsonObject.get("INFO_ENG")), (String) jsonObject.get("RCP_PAT2")));
//                        }

//
//                            tmp = (JSONObject)recipeData.get(idx);
//
//                            String total = (String) cookrcp01.get("total_count");

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
//                            for (int i = 0; i < recipeData.length(); i++) {
//                                tmp = (JSONObject) recipeData.get(i);
//                                Log.i("the search res is:", String.valueOf(tmp.get("RCP_NM")));
//                                String thum = "https" + ((String) tmp.get("ATT_FILE_NO_MK")).substring(4);
//                                searchRecipe.add(new Recipe((String) tmp.get("RCP_SEQ"), (String) tmp.get("RCP_NM"), (String) tmp.get("RCP_NM"), thum, Float.parseFloat((String) tmp.get("INFO_ENG")), (String) tmp.get("RCP_PAT2")));
//                            }
//
//                            emptyView.setVisibility(View.GONE);
//                            RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getContext(), searchRecipe);
//                            myrv.setAdapter(myAdapter);
//                            myrv.setItemAnimator(new DefaultItemAnimator());
//                            myrv.setAlpha(1);
//
//                            progressBar.setVisibility(View.GONE);

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
        },
                error -> Log.i("the res is error:", error.toString())
        );

        requestQueue.add(jsonObjectRequest);
    }

    private void setIngredientOnClick(String keyword) {
        if(keywordSet.contains(keyword)) keywordSet.remove(keyword);
        else keywordSet.add(keyword);
    }

    private void moveSearchResultsActivity() {
        Intent searchResultsIntent = new Intent(getActivity(), SearchResultsActivity.class);
        searchResultsIntent.putParcelableArrayListExtra("KEY_RECIPE_LIST", recipeList);
        startActivity(searchResultsIntent);
    }
}