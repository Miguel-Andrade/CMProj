package com.kingoftheill.app1.presentation.ui.util;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.google.firebase.firestore.WriteBatch;
import com.kingoftheill.app1.R;
import com.kingoftheill.app1.domain2.PlayerItem;
import com.kingoftheill.app1.domain2.Receita;

import java.util.List;


public class CraftFragment extends Fragment {

    //RECYCLER VIEW
    private RecyclerView mRecyclerView;
    private FirestoreRecyclerAdapter adapter;
    private LinearLayoutManager manager;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseFirestore mFirebaseFirestore;

    private static DocumentReference PLAYER;
    private static CollectionReference PLAYER_ITEMS;
    private static CollectionReference RECEITAS;
    private String mUsername;

    private Boolean ing1, ing2, ing3, ing4, ing5;
    private ImageButton b1, b2, b3, b4, b5;
    private Button craft_button;
    private TextView rName;
    private ProgressBar pb;
    private int currentProgress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_craft,container,false);


        b1 = view.findViewById(R.id.imageButton);
        b2 = view.findViewById(R.id.imageButton2);
        b3 = view.findViewById(R.id.imageButton3);
        b4 = view.findViewById(R.id.imageButton4);
        b5 = view.findViewById(R.id.imageButton5);
        rName = view.findViewById(R.id.receitaName);
        craft_button = view.findViewById(R.id.craft_button);
        pb = view.findViewById(R.id.vprogressbar);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseFirestore = FirebaseFirestore.getInstance();

        mUsername = mFirebaseUser.getUid();
        PLAYER = mFirebaseFirestore.document("Users/" + mUsername);
        PLAYER_ITEMS = mFirebaseFirestore.collection("Users/" + mUsername + "/Items");
        RECEITAS = mFirebaseFirestore.collection("Receitas");

        manager = new LinearLayoutManager(getContext());
        mRecyclerView = view.findViewById(R.id.receitas_recycler_view);
        mRecyclerView.setLayoutManager(manager);

        //UPDATE RECEITAS
        FirestoreRecyclerOptions<Receita> options = new FirestoreRecyclerOptions.Builder<Receita>()
                .setQuery(RECEITAS, Receita.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Receita, ViewHolder>(options) {
            @Override
            public void onBindViewHolder(CraftFragment.ViewHolder holder, int position, Receita model) {
                // Bind the Chat object to the ChatHolder
                // ...
                    holder.myTextView.setText(model.getName());
                    holder.image.setBackgroundResource(getContext()
                            .getResources().getIdentifier(model.getImage(),
                                    "drawable", getContext().getPackageName()));

                    holder.itemView.setOnClickListener(v -> {
                           String receitaName = model.getName();
                           rName.setText(receitaName);
                    });

                    craft_button.setOnClickListener(v -> {


                        PLAYER_ITEMS.whereEqualTo("itemId", model.getResult()).get().addOnSuccessListener(documentSnapshot -> {
                            List<PlayerItem> pItem = documentSnapshot.toObjects(PlayerItem.class);
                            //if(isPotCrafted()) {
                                if (!pItem.isEmpty()) {
                                    WriteBatch batch = mFirebaseFirestore.batch();
                                    //ADD CRAFTED ITEM TO INVENTORY
                                    //documentSnapshot.getDocumentChanges().get(0).getDocument().getReference().update("quantity", pItem.get(0).getQuantity() + 1);

                                    //REMOVE ITEMS USED IN CRAFT FROM INVENTORY
                                    PLAYER_ITEMS.whereEqualTo("itemId", model.getIds().get(0)).get().addOnSuccessListener(documentSnapshot2 -> {
                                        List<PlayerItem> pItems = documentSnapshot2.toObjects(PlayerItem.class);
                                        if (!pItems.isEmpty()) {
                                            PLAYER_ITEMS.whereEqualTo("itemId", model.getIds().get(1)).get().addOnSuccessListener(documentSnapshot3 -> {
                                                List<PlayerItem> pItems1 = documentSnapshot3.toObjects(PlayerItem.class);
                                                if (!pItems1.isEmpty()) {
                                                    PLAYER_ITEMS.whereEqualTo("itemId", model.getIds().get(2)).get().addOnSuccessListener(documentSnapshot4 -> {
                                                        List<PlayerItem> pItems2 = documentSnapshot4.toObjects(PlayerItem.class);
                                                        if (!pItems2.isEmpty()) {
                                                            PLAYER_ITEMS.whereEqualTo("itemId", model.getIds().get(3)).get().addOnSuccessListener(documentSnapshot5 -> {
                                                                List<PlayerItem> pItems3 = documentSnapshot5.toObjects(PlayerItem.class);
                                                                if (!pItems3.isEmpty()) {
                                                                    PLAYER_ITEMS.whereEqualTo("itemId", model.getIds().get(4)).get().addOnSuccessListener(documentSnapshot6 -> {
                                                                        List<PlayerItem> pItems4 = documentSnapshot6.toObjects(PlayerItem.class);
                                                                        if (!pItems4.isEmpty()) {
                                                                            batch.update(pItems.get(0).getItemId() , "quantity", pItems.get(0).getQuantity()-1);
                                                                            batch.update(pItems1.get(0).getItemId() , "quantity", pItems1.get(0).getQuantity()-1);
                                                                            batch.update(pItems2.get(0).getItemId() , "quantity", pItems2.get(0).getQuantity()-1);
                                                                            batch.update(pItems3.get(0).getItemId() , "quantity", pItems3.get(0).getQuantity()-1);
                                                                            batch.update(pItems4.get(0).getItemId() , "quantity", pItems4.get(0).getQuantity()-1);
                                                                            batch.update(pItem.get(0).getItemId() , "quantity", pItem.get(0).getQuantity()+1);
                                                                            batch.commit()
                                                                                    .addOnSuccessListener(aVoid -> Toast.makeText(getContext(), "Craft Complete!", Toast.LENGTH_SHORT).show());
                                                                        }
                                                                    });                                                                }
                                                            });                                                        }
                                                    });                                                }
                                            });
                                        } else {
                                            Toast.makeText(getContext(), "You have missing Items!", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                } else {
                                    PLAYER_ITEMS.whereEqualTo("quantity", 0).limit(1).get().addOnSuccessListener(documentSnapshot2 -> {
                                        List<PlayerItem> pItems = documentSnapshot2.toObjects(PlayerItem.class);
                                        if (!pItems.isEmpty()) {
                                            WriteBatch batch = mFirebaseFirestore.batch();

                                            //REMOVE ITEMS USED IN CRAFT FROM INVENTORY
                                            PLAYER_ITEMS.whereEqualTo("itemId", model.getIds().get(0)).get().addOnSuccessListener(documentSnapshot7 -> {
                                                List<PlayerItem> pItems6 = documentSnapshot7.toObjects(PlayerItem.class);
                                                if (!pItems6.isEmpty()) {
                                                    PLAYER_ITEMS.whereEqualTo("itemId", model.getIds().get(1)).get().addOnSuccessListener(documentSnapshot3 -> {
                                                        List<PlayerItem> pItems1 = documentSnapshot3.toObjects(PlayerItem.class);
                                                        if (!pItems1.isEmpty()) {
                                                            PLAYER_ITEMS.whereEqualTo("itemId", model.getIds().get(2)).get().addOnSuccessListener(documentSnapshot4 -> {
                                                                List<PlayerItem> pItems2 = documentSnapshot4.toObjects(PlayerItem.class);
                                                                if (!pItems2.isEmpty()) {
                                                                    PLAYER_ITEMS.whereEqualTo("itemId", model.getIds().get(3)).get().addOnSuccessListener(documentSnapshot5 -> {
                                                                        List<PlayerItem> pItems3 = documentSnapshot5.toObjects(PlayerItem.class);
                                                                        if (!pItems3.isEmpty()) {
                                                                            PLAYER_ITEMS.whereEqualTo("itemId", model.getIds().get(4)).get().addOnSuccessListener(documentSnapshot6 -> {
                                                                                List<PlayerItem> pItems4 = documentSnapshot6.toObjects(PlayerItem.class);
                                                                                if (!pItems4.isEmpty()) {
                                                                                    batch.update(pItems6.get(0).getItemId() , "quantity", pItems.get(0).getQuantity()-1);
                                                                                    batch.update(pItems1.get(0).getItemId() , "quantity", pItems1.get(0).getQuantity()-1);
                                                                                    batch.update(pItems2.get(0).getItemId() , "quantity", pItems2.get(0).getQuantity()-1);
                                                                                    batch.update(pItems3.get(0).getItemId() , "quantity", pItems3.get(0).getQuantity()-1);
                                                                                    batch.update(pItems4.get(0).getItemId() , "quantity", pItems4.get(0).getQuantity()-1);
                                                                                    PlayerItem pi = new PlayerItem(model.getResult(), 1, model.getImage());
                                                                                    batch.set(documentSnapshot2.getDocumentChanges().get(0).getDocument().getReference(), pi);
                                                                                    batch.commit()
                                                                                            .addOnSuccessListener(aVoid -> Toast.makeText(getContext(), "Craft Complete!", Toast.LENGTH_SHORT).show());
                                                                                }
                                                                            });                                                                }
                                                                    });                                                        }
                                                            });                                                }
                                                    });
                                                } else {
                                                    Toast.makeText(getContext(), "You have missing Items!", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        } else {
                                            Toast.makeText(getContext(), "Your Inventory is Full!", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(e -> Log.e("error", e.getMessage()));

                                }
//                            }else{
//                                Toast.makeText(getContext(),"Add Your Items First!", Toast.LENGTH_SHORT).show();
//                            }
                        })
                                .addOnFailureListener(e -> {
                                    Log.e("error", e.getMessage());
                                });


//                            currentProgress = 0;
//                            pb.setProgress(currentProgress);
//                            ing1 = true;
//                            ing2 = true;
//                            ing3 = true;
//                            ing4 = true;
//                            ing5 = true;
                    });

            }



            @Override
            public ViewHolder onCreateViewHolder(ViewGroup group, int i) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.receita_grid, group, false);

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

    private boolean isPotCrafted (){
        return currentProgress == 100;
    }

    private void addButtonClickListener() {
        b1.setOnClickListener(view -> {
            if(currentProgress < 100 && ing1 && !isPotCrafted()) {
                currentProgress += 20;
                pb.setProgress(currentProgress);
                ing1 = false;
            }
        });
        b2.setOnClickListener(view -> {
            if(currentProgress < 100 && ing2 && !isPotCrafted()) {
                currentProgress += 20;
                pb.setProgress(currentProgress);
                ing2 = false;
            }
        });
        b3.setOnClickListener(view -> {
            if(currentProgress < 100 && ing3 && !isPotCrafted()) {
                currentProgress += 20;
                pb.setProgress(currentProgress);
                ing3 = false;
            }
        });
        b4.setOnClickListener(view -> {
            if(currentProgress < 100 && ing4 && !isPotCrafted()) {
                currentProgress += 20;
                pb.setProgress(currentProgress);
                ing4 = false;
            }
        });
        b5.setOnClickListener(view -> {
            if(currentProgress < 100 && ing5 && !isPotCrafted()) {
                currentProgress += 20;
                pb.setProgress(currentProgress);
                ing5 = false;
            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;
        ImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.title_item);
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
