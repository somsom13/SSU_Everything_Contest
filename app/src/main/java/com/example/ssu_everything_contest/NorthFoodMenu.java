package com.example.ssu_everything_contest;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class NorthFoodMenu extends Fragment {
    ArrayList<FoodData> foods;
    Context ct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView=(ViewGroup) inflater.inflate(R.layout.north_food_menu,container,false);

        ct=container.getContext();

        this.InitializeFoodData();

        ListView listView = (ListView)rootView.findViewById(R.id.northFoodListView);
        final FoodDataAdapter foodAdapter=new FoodDataAdapter(ct,foods);

        listView.setAdapter(foodAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                ((NorthFoodMain)getActivity()).replaceFragment(NorthFoodGame.newInstance());
            }
        });
        return rootView;

    }
    public void InitializeFoodData()
    {
        foods=new ArrayList<FoodData>();
        foods.add(new FoodData(R.drawable.food_1,"강냉이국수","강냉이로 만든 국수로 김칫물에 볶은 양배추, 고구마 잎줄기, 버섯, 풋고추, 미역 줄기 등을 고명으로 올린 음식"));
        foods.add(new FoodData(R.drawable.food_2,"농마국수","황해도에서 유래된 음식으로 농마는 녹말을 뜻한다. 감자, 고구마, 옥수수, 콩 따위의 녹말로 만든 국수이다. 물냉면으로, 면발이 매우 쫄깃하다."));
        foods.add(new FoodData(R.drawable.food_3,"언감자국수","언감자 전분으로 만든 면을 콩국에 말아먹는 국수 요리이다. 겨울철 꽁꽁 얼어 시커멓게 언 감자를 먹을 수 없었는데, 이를 강판에 갈아 녹말을 우려내고 틀에서 뽑아내어 국수로 만들어 먹게 되었다."));
        foods.add(new FoodData(R.drawable.food_4,"평양랭면","동치미를 섞은 고깃국물 냉 메밀 국수이다. 옥류관의 평양랭면이 유명하다."));
        foods.add(new FoodData(R.drawable.food_5,"두부밥","두부를 삼각형으로 자른 뒤 튀기듯 구운 두부 한 가운데에 칼집을 내어서 그 속에 밥을 채운 뒤 위에 양념장을 올려서 먹는 요리이다."));
        foods.add(new FoodData(R.drawable.food_6,"평양온반","흰쌀밥 위에 닭고기국을 부은 국밥이다."));
        foods.add(new FoodData(R.drawable.food_7,"인조고기밥","두부콩에서 기름을 뽑은 대두박으로 만든 2차 가공식품이다. 고기를 씹는 맛과 영양가 측면에서도 고기못지 않다는 뜻으로 인조고기라고 부른다."));
        foods.add(new FoodData(R.drawable.food_8,"해주비빔밥","모양이 아름다워 해주교반이라고 부르기도 했던 황해도 해주비빔밥은 예로부터 곡창지대로 알려진 황해도의 쌀로 만든 음식으로, 돼지비계 기름에 볶은 밥을 소금으로 간 한 뒤에 해주 수양산 고사리와 황해도 특산 김을 비롯해 갖가지 재료를 넣고 닭고기와 돼지고기를 기본 고명으로 얹어 양념간장으로 비벼먹는 요리이다."));
        foods.add(new FoodData(R.drawable.food_9,"고기순대","평양의 향토음식으로, 소창에 돼지고기와 찹쌀, 소 선지 및 각종 채소를 넣어 만든다. 특이한 것은 좁쌀이 들어간다는 것이다."));
        foods.add(new FoodData(R.drawable.food_10,"아바이순대","함경도 지방의 향토음식으로, 평양과 달리 대창으로 만든다. 함경도민이 강원도 속초시, 고성군 등지에 정착하여 명맥을 잇고 있어, 남한에서도 맛볼 수 있다."));
        foods.add(new FoodData(R.drawable.food_11,"토끼곰","토끼곰은 토끼고기 안에 밤, 대추, 검은콩, 황기 등을 넣고 삶아 먹는 요리이다."));
        foods.add(new FoodData(R.drawable.food_12,"식해","식해는 토막난 생선에 고춧가루, 무, 소금, 밥, 엿기름을 섞어 발효시킨 저장식품이다."));
        foods.add(new FoodData(R.drawable.food_13,"어복쟁반","암소의 어복살이나 살코기를 삶은 다음 얇은 편으로 썰어 양념을 올려놓은 평양의 고기반찬"));
    }
}
