package com.kingoftheill.app1.presentation.ui.util;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.kingoftheill.app1.R;
import com.kingoftheill.app1.domain2.Item;
import com.kingoftheill.app1.domain2.PlayerFC;
import com.kingoftheill.app1.domain2.PlayerItem;

import java.util.ArrayList;
import java.util.List;

public class BagFragment extends Fragment {
    private List<PlayerItem> PlayerItems;

    private static final String TAG = "BagFragment";

    //RECYCLER VIEW
    private RecyclerView mRecyclerView;
    private FirestoreRecyclerAdapter adapter;
    private GridLayoutManager manager;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseFirestore mFirebaseFirestore;

    private String itemStatFlag, itemTypeFlag;
    private int itemValueFlag, itemPositionFlag, playerItemQuantityFlag;


    private static CollectionReference PLAYER_ITEMS;

    private String mUsername;
    private static DocumentReference PLAYER;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bag,container,false);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseFirestore = FirebaseFirestore.getInstance();

        mUsername = mFirebaseUser.getUid();
        PLAYER = mFirebaseFirestore.document("Users/" + mUsername);

        PLAYER_ITEMS = mFirebaseFirestore.collection("Users/" + mUsername + "/Items");
        PlayerItems = new ArrayList<>();
        for (int i =0; i<=29; i++) {
            PlayerItems.add(new PlayerItem(null, 10, ""));
        }

        manager = new GridLayoutManager(getContext(), 5);
        mRecyclerView = view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setLayoutManager(manager);

        //Item Info
        TextView itemName = view.findViewById(R.id.textView3);
        TextView itemStat = view.findViewById(R.id.textView4);
        TextView itemValue =  view.findViewById(R.id.textView5);
        TextView itemDescription = view.findViewById(R.id.textView2);
        ImageView itemImage = view.findViewById(R.id.imageView2);
        Button use = view.findViewById(R.id.button);



        //UPDATE PLAYER ITEMS
        FirestoreRecyclerOptions<PlayerItem> options = new FirestoreRecyclerOptions.Builder<PlayerItem>()
                .setQuery(PLAYER_ITEMS, PlayerItem.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<PlayerItem, ViewHolder>(options) {
            @Override
            public void onBindViewHolder(ViewHolder holder, int position, PlayerItem model) {
                // Bind the Chat object to the ChatHolder
                // ...

                playerItemQuantityFlag = model.getQuantity();


                if (model.getQuantity() > 0) {
                    holder.myTextView.setText(model.getQuantity()+"");
                    if (model.getImage().equals(""))
                        holder.image.setBackgroundResource(getContext()
                                .getResources().getIdentifier("bubonic_plague_doc_icon_3",
                                        "drawable", getContext().getPackageName()));
                    else
                        holder.image.setBackgroundResource(getContext()
                                    .getResources().getIdentifier(model.getImage(),
                                "drawable", getContext().getPackageName()));


                    holder.itemView.setOnClickListener(v -> {
                        if (model.getQuantity() > 0) {
                            model.getItemId().get()
                                    .addOnSuccessListener(documentSnapshot -> {
                                          Item temp = documentSnapshot.toObject(Item.class);
                                          itemName.setText(temp.getName());
                                          itemDescription.setText(temp.getDescription());
                                          itemImage.setBackgroundResource(getContext()
                                                  .getResources().getIdentifier(model.getImage(),
                                                          "drawable", getContext().getPackageName()));
                                          itemStat.setText(temp.getStat());
                                          itemValue.setText(temp.getValue()+"");

                                          itemStatFlag = temp.getStat();
                                          itemTypeFlag = temp.getType();
                                          itemValueFlag = temp.getValue();
                                          itemPositionFlag = position;
                                    })
                                    .addOnFailureListener(e -> Log.e("error", e.getMessage()));
                        }
                    });

                    use.setOnClickListener(v -> {
                        if(itemTypeFlag.equals("consume")){
                            PLAYER.get().addOnCompleteListener(task -> {
                                int val = 0;
                                playerItemQuantityFlag = model.getQuantity();
                                if (task.isSuccessful()){
                                    //Toast.makeText(getContext(), "Item Consumed!", Toast.LENGTH_SHORT).show();
                                    PlayerFC p = task.getResult().toObject(PlayerFC.class);
                                    switch(itemStatFlag){
                                        case ("life"):
                                            int maxHealth = 100 + ((p.getLevel() - 1)* 5);
                                            if (p.getLife() + itemValueFlag > maxHealth)
                                                val = maxHealth;
                                            else
                                                val = itemValueFlag + p.getLife();

                                            PLAYER.update("life", val);
                                            break;
                                        case ("resistance"):
                                            val = p.getResistance() + itemValueFlag;
                                            PLAYER.update("resistance", val);
                                            break;
                                        case ("cure0"):
                                            if(p.getType() != 0) {
                                                //PLAYER.update(/*CAMPO DA DOENÇA INFECTADO*/, null);
                                            }
                                            break;
                                        case ("cure1"):
                                            if(p.getType() != 1){
                                                //PLAYER.update(/*CAMPO DA DOENÇA INFECTADO*/, null);
                                            }
                                            break;
                                        case ("cure2"):
                                            if(p.getType() != 2){
                                                //PLAYER.update(/*CAMPO DA DOENÇA INFECTADO*/, null);
                                            }
                                            break;
                                        default: PLAYER.update(itemStatFlag, itemValueFlag);
                                    }
                                    if (itemStatFlag.equals("life")) {
                                        int temp = task.getResult().toObject(PlayerFC.class).getLife();
                                        if (temp + itemValueFlag > 100)
                                            val = 100;
                                        else
                                            val = itemValueFlag + temp;

                                        PLAYER.update("life", val);
                                    }
                                    else
                                        PLAYER.update(itemStatFlag, itemValueFlag);//.addOnSuccessListener().addOnFailureListener();
                                }
                                else
                                    Log.w(TAG, "Error on getting player for update");
                            });
                            String itemDoc = String.valueOf(itemPositionFlag);
                            if(itemPositionFlag< 10){
                                itemDoc = "0"+itemPositionFlag;
                            }
                            Log.w(TAG, "Position: " + playerItemQuantityFlag + " , " + itemDoc);
                            PLAYER_ITEMS.document(itemDoc).update(PlayerItem.QUANTITY,(playerItemQuantityFlag-1));
                        }else if (itemTypeFlag.equals("craft")){
                            //TODO SWITCH TO CRAFT
                        }
                    });

                }
            }

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup group, int i) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.grid_cell_layout, group, false);

                return new ViewHolder(view);
            }

            @Override
            public void onError(FirebaseFirestoreException e) {
                Log.e("error", e.getMessage());
            }
        };

        mRecyclerView.setAdapter(adapter);

       return view;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;
        ImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.quantity_item);
            image = itemView.findViewById(R.id.image_item);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }



}
