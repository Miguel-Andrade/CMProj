package com.kingoftheill.app1.presentation.ui.util;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kingoftheill.app1.R;
import com.kingoftheill.app1.domain2.PlayerItem;
import com.kingoftheill.app1.domain2.Receita;

import java.util.List;


public class CraftFragment extends Fragment {

    private List<Receita> Receitas;

    //RECYCLER VIEW
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;



    private Boolean ing1, ing2, ing3, ing4, ing5;
    //private ArrayList<Integer> antidote;
    private ImageButton b1, b2, b3, b4, b5;
    private TextView rName;
    private ProgressBar pb;
    private int currentProgress;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_craft,container,false);


        /*b1 = (ImageButton)view.findViewById(R.id.imageButton);
        b2 = (ImageButton)view.findViewById(R.id.imageButton2);
        b3 = (ImageButton)view.findViewById(R.id.imageButton3);
        b4 = (ImageButton)view.findViewById(R.id.imageButton4);
        b5 = (ImageButton)view.findViewById(R.id.imageButton5);
        rName = (TextView) view.findViewById(R.id.receitaName);


        List<Integer> ings = new ArrayList<>(5);
        ings.add(1);
        ings.add(2);
        ings.add(3);
        ings.add(4);
        ings.add(5);
        List<Boolean> ingsUsed = new ArrayList<>(5);
        ingsUsed.add(true);
        ingsUsed.add(true);
        ingsUsed.add(true);
        ingsUsed.add(true);
        ingsUsed.add(true);
        Receita receita1 = new Receita (1, "BubonicCure", ings, 6, 0, ingsUsed);

        // Ingredientes
        ing1 = ingsUsed.get(0);
        ing2 = ingsUsed.get(1);
        ing3 = ingsUsed.get(2);
        ing4 = ingsUsed.get(3);
        ing5 = ingsUsed.get(4);

        //Progresso atual para completar o antidoto
        currentProgress = receita1.getProgresso();
        pb = (ProgressBar)view.findViewById(R.id.vprogressbar);
        pb.setProgress(currentProgress);


        Receitas = new ArrayList<>();
        for (int i =0; i<=3; i++) {
            Receitas.add(new Receita(i, "BubonicCure",ings,6, 0, ingsUsed));
        }
        Receitas.add(new Receita(3, "InfluenzaCure",ings,6, 0, ingsUsed));
        Receitas.add(new Receita(4, "", null, 0, 0, null));

        //RECYCLER VIEW
        mRecyclerView = (RecyclerView) view.findViewById(R.id.receitas_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        ReceitasAdapter.RecyclerViewClickListener listener = (view1, rec) -> {
            String receitaName = rec.getName();
            rName.setText(receitaName);
//            for(Item i : items){
//                if(i.getId()== pItemId){
//                    itemName.setText("ITEM: " + i.getName());
//                    itemStat.setText("STAT: "+ i.getStat());
//                    itemValue.setText("VALUE: " + String.valueOf(i.getValue()));
//                    itemDescription.setText("DESCRIPTON: " + i.getDescription());
//                    itemImage.setBackgroundResource(getContext().getResources().getIdentifier("bubonic_plague_doc_icon_3", "drawable", getContext().getPackageName()));
//                }
//            }


            Toast.makeText(getContext(), "Position " + receitaName, Toast.LENGTH_SHORT).show();
        };
        mAdapter = new ReceitasAdapter(getContext(), Receitas, listener);
        //mAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        addButtonClickListener();
        potCrafted();

        */
        return view;

    }

    private void potCrafted (){
        if(currentProgress == 100){

            PlayerItem pi = new PlayerItem();

            currentProgress = 0;
            //thread.sleep(2000);
            pb.setProgress(currentProgress);
            ing1 = true;
            ing2 = true;
            ing3 = true;
            ing4 = true;
            ing5 = true;
        }
    }

    private void addButtonClickListener() {
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentProgress < 100 && ing1) {
                    currentProgress += 20;
                    pb.setProgress(currentProgress);
                    ing1 = false;
                }
                potCrafted();

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentProgress < 100 && ing2) {
                    currentProgress += 20;
                    pb.setProgress(currentProgress);
                    ing2 = false;
                }
                potCrafted();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentProgress < 100 && ing3) {
                    currentProgress += 20;
                    pb.setProgress(currentProgress);
                    ing3 = false;
                }
                potCrafted();
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentProgress < 100 && ing4) {
                    currentProgress += 20;
                    pb.setProgress(currentProgress);
                    ing4 = false;
                }
                potCrafted();
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentProgress < 100 && ing5) {
                    currentProgress += 20;
                    pb.setProgress(currentProgress);
                    ing5 = false;
                }
                potCrafted();
            }
        });
    }


}
