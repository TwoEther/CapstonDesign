/* Assignment: 3
Campus: Ashdod
Authors:
Eliran Naduyev 312089105
Maria Garber
*/

package com.example.capstonproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.tts.TextToSpeech;
import android.content.Intent;
import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kakao.sdk.newtoneapi.SpeechRecognizerManager;
import com.kakao.sdk.newtoneapi.TextToSpeechClient;
import com.kakao.sdk.newtoneapi.TextToSpeechListener;
import com.kakao.sdk.newtoneapi.TextToSpeechManager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class Recipe_Activity extends AppCompatActivity implements App{

    private TextView title, calories_in, servings, kind, instructions;
    private ImageView img;
    private DatabaseReference mRootRef, mref;
    private FirebaseAuth mAuth;
    private JSONObject cookrcp01;
    private JSONArray recipeData;
    private List<Ingredient> ingredientsLst = new ArrayList<Ingredient>();
    private RecyclerView myrv;
    private FloatingActionButton fab, button;
    private boolean like = false;
    private TextToSpeech tts;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_);
        final Intent intent = getIntent();
        final String recipeId = Objects.requireNonNull(intent.getExtras()).getString("seq");
        mAuth = FirebaseAuth.getInstance();
        final String uid = mAuth.getCurrentUser().getUid();
//        mRootRef.child(uid).push().setValue(recipeId);
        mRootRef = FirebaseDatabase.getInstance().getReference().child(uid).child(recipeId);
        img = findViewById(R.id.recipe_img);
        title = findViewById(R.id.recipe_title);
        calories_in = findViewById(R.id.recipe_calories_in);
        servings = findViewById(R.id.recipe_servings);
//        kind = findViewById(R.id.recipe_kind);
        instructions = findViewById(R.id.recipe_instructions);
        fab = findViewById(R.id.floatingActionButton);
        getRecipeData(recipeId);
        mRootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("mRootRef", String.valueOf(dataSnapshot));
                if (dataSnapshot.getValue() != null) {
                    fab.setImageResource(R.drawable.ic_favorite_black_24dp);
                    like = true;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //스크랩
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like = !like;
                mRootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (like) {
                            fab.setImageResource(R.drawable.ic_favorite_black_24dp);
                            Map favorites = new HashMap();
                            favorites.put("seq", intent.getExtras().getString("seq"));
                            favorites.put("img", intent.getExtras().getString("img"));
                            favorites.put("title", intent.getExtras().getString("title"));
                            mRootRef.setValue(favorites);
                        } else {
                            try {
                                fab.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                                mRootRef.setValue(null);
                            } catch (Exception e) {
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
        myrv = findViewById(R.id.recipe_ingredients_rv);
        myrv.setLayoutManager(new GridLayoutManager(this, 2));

//        Button button = findViewById(R.id.floatingActionButton3);         # button 타입이 아니라 floatingActionButton 타입입니다

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {tts.setLanguage(Locale.KOREAN);}
        });
        button = findViewById(R.id.floatingActionButton3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.setPitch(1.0f);         // 음성 톤을 1.0배 올려준다.
                tts.setSpeechRate(1.1f);    // 읽는 속도 1.5배

                String sp = instructions.getText().toString();
                StringBuilder speech = new StringBuilder();
                String[] sArr = sp.split("<br><br>");
                for (String s : sArr) {
                    speech.append(s);
                }
                tts.speak(speech.toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TTS 객체가 남아있다면 실행을 중지하고 메모리에서 제거한다.
        if(tts != null){
            tts.stop();
            tts.shutdown();
            tts = null;
        }
    }



    //API이용 데이터 가져오기
    private void getRecipeData(final String recipeID) {
//        String URL = " https://api.spoonacular.com/recipes/" + recipeId + "/information?apiKey=c957b6816ba048139fbc25a67d2cff33";
        String URL = "https://openapi.foodsafetykorea.go.kr/api/a610db1f12954422b81a/COOKRCP01/json/1/"+App.MAX_SIZE;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            cookrcp01 = (JSONObject) response.get("COOKRCP01");
                            recipeData = (JSONArray) cookrcp01.get("row");
                            JSONObject tmp;
                            JSONArray tmpArr;
                            int idx = 0;
                            StringBuilder sb= new StringBuilder();

                            for(int i=0;i<20000;i++){
                                tmp = (JSONObject)recipeData.get(i);
                                if(((String)tmp.get("RCP_SEQ")).equals(recipeID)) {
                                    idx = i;
                                    break;
                                }

                            }
                            tmp = (JSONObject)recipeData.get(idx);
                            String thum = "https"+((String)tmp.get("ATT_FILE_NO_MK")).substring(4);     // http 포맷을 https 포맷으로 변경
                            try {
                                Picasso.get().load(thum).into(img);
                            }
                            catch (Exception e){
                                img.setImageResource(R.drawable.nopicture);
                            }
                            title.setText((String) tmp.get("RCP_NM"));
                            calories_in.setText((String)tmp.get("INFO_ENG"));
                            servings.setText((String)tmp.get("RCP_PAT2"));
//                            kind.setText((String) tmp.get("RCP_PAT2"));

                            StringBuilder sp = new StringBuilder();

                            for(int i=1; i<21; i++) {           // manual1 ~ 20 까지 가져옴
                                StringBuilder box = new StringBuilder();
                                StringBuilder manual = new StringBuilder("MANUAL");
                                if(i<10) manual.append("0"+i);
                                else manual.append(i);

                                if(tmp.get(manual.toString()).equals("")) {
                                    continue;
                                }
                                else {
                                    String str = (String) tmp.get(manual.toString());
                                    String[] strArr = str.split("\\.");
                                    sp.append(i+"."+" "+strArr[1] +"<br><br>");
                                }
                            }

                            instructions.setText(Html.fromHtml(sp.toString()));

////                            try{
//////                                if(tmp.get("instructions").equals("")){
//////                                    throw new Exception("No Instructions");
//////                                }
////                                if{
////
////                                }
////
////                                else
////                                    instructions.setText(Html.fromHtml((String) tmp.get("instructions")));
////                            }
//                            catch(Exception e){
//                                String msg= "Unfortunately, the recipe you were looking for not found, to view the original recipe click on the link below:" + "<a href="+tmp.get("spoonacularSourceUrl")+">"+tmp.get("spoonacularSourceUrl")+"</a>";
//                                instructions.setMovementMethod(LinkMovementMethod.getInstance());
//                                instructions.setText(Html.fromHtml(msg));
//                            }

                            StringBuilder result = new StringBuilder();
                            String str = (String) tmp.get("RCP_PARTS_DTLS");
                            String[] strArr = str.split("\n|,");

                            for(int j = 1; j <strArr.length; j++){
                                if(strArr[j] == null) continue;
                                result.append("★ "+ strArr[j].trim()+"\n");
                            }

                            ingredientsLst.add(new Ingredient(result.toString()));

                            RecyclerViewAdapterRecipeIngredient myAdapter = new RecyclerViewAdapterRecipeIngredient(getApplicationContext(), ingredientsLst);
                            myrv.setAdapter(myAdapter);
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
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

}