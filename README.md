# 요리 초보를 위한 요리 레시피 애플리 케이션 <공공데이터 레시피 API를 사용>
<p align="center"><img src="screenshots/chef_logo.png" heigth="250" width="250"/>Reference. https://github.com/MariaGarber/Home-Chef</p>

<h4>조리 식품의 레시피 DB : <a href="https://www.foodsafetykorea.go.kr/api/newDatasetDetail.do">링크</a></h4>


<h5>🧨 기술 스택
<ul>
    <li>Java</li>
    <li>Firebase</li>
</ul><hr>

<h5>🧨 역할 분담
<ul>
    <li>백엔드 2명</li>
    <li>프론트 엔드 2명</li>
</ul><hr>

<h5>🧨 구현 기능
<ul>
    <li>공공데이터 API를 이용한 레시피 데이터 구축</li>
    <li>Firebase를 통한 레시피 및 회원 데이터 저장</li>
    <li>자바 tts를 이용한 음성 출력</li>
</ul><hr>


<h5> ✨ 공공 데이터 API 처리 코드

    private void searchRecipe(String search) {
        // 레시피를 담을 리스트
        searchRecipe = new ArrayList<Recipe>();
        
        /*
            Request parameters
            ※ http://openapi.foodsafetykorea.go.kr/api/keyId/serviceId/dataType/startIdx/endIdx
                - keyId : 인증키 
                - serviceId	 : 서비스명	 
                - dataType : 요청파일 타입 (XML, Json중 JSON 으로 설정) 
                - startIdx : 요청시작위치
                - endIdx : 요청종료위치 
                - RCP_NM : 메뉴명 
                - RCP_PARTS_DTLS : 재료정보1 
                - RCP_PAT2 : 요리종류 

        */
        // 메뉴 정보를 검색
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
                    // 레시피 데이터 처리
                    COOKRCP01 = (JSONObject) response.get("COOKRCP01");
                    String total = (String) COOKRCP01.get("total_count");
    
                    if(total.equals("0")){
                      myrv.setAlpha(0);
                        emptyView.setVisibility(View.VISIBLE);
                    }

                    else{
                        recipeData = (JSONArray) COOKRCP01.get("row");
                        for(int i=0; i<recipeData.length(); i++) {
                           Log.i("the search res is:", String.valueOf(tmp.get("RCP_NM")));
                           String thum = "https"+((String)tmp.get("ATT_FILE_NO_MK")).substring(4);
                           searchRecipe.add(new Recipe((String)tmp.get("RCP_NM"),(String)tmp.get("RCP_NM"), thum, Integer.parseInt((String)tmp.get("INFO_WGT")), (String)tmp.get("RCP_PAT2")));
                        }


                        emptyView.setVisibility(View.GONE);
                        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getContext(), searchRecipe);
                        myrv.setAdapter(myAdapter);
                        myrv.setItemAnimator(new DefaultItemAnimator());
                        myrv.setAlpha(1);

                        progressBar.setVisibility(View.GONE);

                        if(searchRecipe.isEmpty()){
                            myrv.setAlpha(0);
                            emptyView.setVisibility(View.VISIBLE);
                        }
                        else{
                            emptyView.setVisibility(View.GONE);
                            RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getContext(), searchRecipe);
                            myrv.setAdapter(myAdapter);
                            myrv.setItemAnimator(new DefaultItemAnimator());
                            myrv.setAlpha(1);
                        }
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
<hr>
<h5>🧨 실행 이미지 <hr>
<p>홈 화면</p>
<img width="80%" src="https://user-images.githubusercontent.com/101616106/215674262-72b372fe-d2d8-4f43-bb1d-5492543c1098.png"/>
<img width="80%" src="https://user-images.githubusercontent.com/101616106/215674263-d615c497-3f9b-4935-9261-a286feebbea1.png"/>

<p>레시피 클릭시 화면</p>
<img width="80%" src="https://user-images.githubusercontent.com/101616106/215674265-a730c991-52c1-42e4-97d2-b0ae43db2279.PNG"/>

<p>즐겨찾기 화면</p>
<img width="80%" src="https://user-images.githubusercontent.com/101616106/215674251-de73ba0c-1915-4a64-afca-d3c311f78e1b.PNG"/>

<p>카테고리 화면</p>
<img width="80%" src="https://user-images.githubusercontent.com/101616106/215674257-81ac3198-eac0-4b78-8620-310d160980f7.PNG"/>
