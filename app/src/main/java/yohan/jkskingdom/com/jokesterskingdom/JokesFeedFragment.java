package yohan.jkskingdom.com.jokesterskingdom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Query;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Yohan on 29/06/2018.
 */

public class JokesFeedFragment extends Fragment  {


    View v;
    RecyclerView mJokeList;
    List<JokePost> joke_list;
    FirebaseFirestore firebaseFirestore;
    JokeRecyclerAdapter jokeRecyclerAdapter;


    public JokesFeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_jokesfeed, container, false);


        joke_list = new ArrayList<>();
        mJokeList=v.findViewById(R.id.joke_recycler_view);

        jokeRecyclerAdapter = new JokeRecyclerAdapter(joke_list);
        mJokeList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mJokeList.setAdapter(jokeRecyclerAdapter);

        firebaseFirestore = FirebaseFirestore.getInstance();
        //ORDER JOKES POST BY MOST RECENT DATE
        Query firstQuery = firebaseFirestore.collection("Jokes").orderBy("timestamp", Query.Direction.DESCENDING);

        firstQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                //AVOID THE APP TO CRASH IF THE USER LOGOUT AFTER DATA IS RETRIEVE
                if(documentSnapshots != null) {

                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                        if (doc.getType() == DocumentChange.Type.ADDED) {

                            JokePost jokePost = doc.getDocument().toObject(JokePost.class);
                            joke_list.add(jokePost);

                            jokeRecyclerAdapter.notifyDataSetChanged();

                        }

                    }
                }

            }
        });




        return v;
    }

}