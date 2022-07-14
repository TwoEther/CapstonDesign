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
            setIngredientOnClick("누룽지");
        });

        RootView.findViewById(R.id.button2).setOnClickListener(v -> {
            setIngredientOnClick("밀가루");
        });

        RootView.findViewById(R.id.button3).setOnClickListener(v -> {
            setIngredientOnClick("쌀");
        });

        RootView.findViewById(R.id.button4).setOnClickListener(v -> {
            setIngredientOnClick("달걀");
        });

        RootView.findViewById(R.id.button5).setOnClickListener(v -> {
            setIngredientOnClick("요거트");
        });

        RootView.findViewById(R.id.button6).setOnClickListener(v -> {
            setIngredientOnClick("두부");
        });

        RootView.findViewById(R.id.button7).setOnClickListener(v -> {
            setIngredientOnClick("당근");
        });

        RootView.findViewById(R.id.button8).setOnClickListener(v -> {
            setIngredientOnClick("숙주");
        });

        RootView.findViewById(R.id.button9).setOnClickListener(v -> {
            setIngredientOnClick("애호박");
        });

        RootView.findViewById(R.id.button10).setOnClickListener(v -> {
            setIngredientOnClick("양파");
        });

        return RootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    //private void initializeIngredients() {
//        lstIngredient.add(new Ingredient("고기", "beef-cubes-raw.png"));
//        lstIngredient.add(new Ingredient("생선", "fish-fillet.jpg"));
//        lstIngredient.add(new Ingredient("닭", "chicken-breasts.png"));
//        lstIngredient.add(new Ingredient("참치", "canned-tuna.png"));
//        lstIngredient.add(new Ingredient("밀가루", "flour.png"));
//        lstIngredient.add(new Ingredient("밥", "uncooked-white-rice.png"));
//        lstIngredient.add(new Ingredient("파스타", "fusilli.jpg"));
//        lstIngredient.add(new Ingredient("치즈", "cheddar-cheese.png"));
//        lstIngredient.add(new Ingredient("버터", "butter.png"));
//        lstIngredient.add(new Ingredient("빵", "white-bread.jpg"));
//        lstIngredient.add(new Ingredient("양파", "brown-onion.png"));
//        lstIngredient.add(new Ingredient("갈릭", "garlic.jpg"));
//        lstIngredient.add(new Ingredient("우유", "milk.png"));
//        lstIngredient.add(new Ingredient("계란", "egg.png"));
//        lstIngredient.add(new Ingredient("기름", "vegetable-oil.jpg"));
//        lstIngredient.add(new Ingredient("요거트", "plain-yogurt.jpg"));
//        lstIngredient.add(new Ingredient("소금", "salt.jpg"));
//        lstIngredient.add(new Ingredient("설탕", "sugar-in-bowl.png"));
//        lstIngredient.add(new Ingredient("후추", "pepper.jpg"));
//        lstIngredient.add(new Ingredient("물", "water.jpg"));
//        lstIngredient.add(new Ingredient("파슬리", "parsley.jpg"));
//        lstIngredient.add(new Ingredient("바질", "basil.jpg"));
//        lstIngredient.add(new Ingredient("초콜릿", "milk-chocolate.jpg"));
//        lstIngredient.add(new Ingredient("견과류", "hazelnuts.png"));
//        lstIngredient.add(new Ingredient("토마토", "tomato.png"));
//        lstIngredient.add(new Ingredient("오이", "cucumber.jpg"));
//        lstIngredient.add(new Ingredient("피망", "red-bell-pepper.jpg"));
//        lstIngredient.add(new Ingredient("버섯", "portabello-mushrooms.jpg"));
//        lstIngredient.add(new Ingredient("레몬", "lemon.jpg"));
//        lstIngredient.add(new Ingredient("오렌지", "orange.jpg"));
//        lstIngredient.add(new Ingredient("바나나", "bananas.jpg"));
//        lstIngredient.add(new Ingredient("와인", "red-wine.jpg"));
//        lstIngredient.add(new Ingredient("사과", "apple.jpg"));
//        lstIngredient.add(new Ingredient("블루베리", "berries-mixed.jpg"));
//        lstIngredient.add(new Ingredient("비스킷", "buttermilk-biscuits.jpg"));
//        lstIngredient.add(new Ingredient("파인애플", "pineapple.jpg"));

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
                recipeSet.clear(); //새로운 검색에 대해서 시작하는 부분, 저장된 레시피 관련 내용 초기화
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
                                if(recipeSet.contains(recipeId)) break; //다른 재료에서 이미 포함된 레시피면 더이상 추가하지 않음 (중복 처리)

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