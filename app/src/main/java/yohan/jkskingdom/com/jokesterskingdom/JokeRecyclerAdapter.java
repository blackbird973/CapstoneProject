package yohan.jkskingdom.com.jokesterskingdom;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.text.format.DateFormat;
import android.content.Context;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Date;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * Created by Yohan on 11/07/2018.
 */

public class JokeRecyclerAdapter extends RecyclerView.Adapter<JokeRecyclerAdapter.ViewHolder> {

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;



    public List<JokePost> joke_list;

    public Context context;


    public JokeRecyclerAdapter(List<JokePost> joke_list){

        this.joke_list = joke_list;



    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.joke_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {





        //RETRIEVE THE ID OF EACH POST FOR THE LIKE FEATURE
        final String jokePostId = joke_list.get(position).JokePostId;
        final String currentUserId = firebaseAuth.getCurrentUser().getUid();

        //RETRIEVE THE JOKE TEXT
         final String joke_data = joke_list.get(position).getJoke();
         holder.setJokeText(joke_data);
         //RETRIEVE THE USERNAME TEXT
         String username_data = joke_list.get(position).getUsername();
         holder.setUsernameText(username_data);
         //RETRIEVE TIME OF THE POSTED JOKE
        final long millisecond = joke_list.get(position).getTimestamp().getTime();
        String dateString = DateFormat.format("dd/MM/yyyy", new Date(millisecond)).toString();
        holder.setTime(dateString);
        //MAKE THE DELETE OPTION ONLY FOR LOGGED USERS POST
        String joke_user_id = joke_list.get(position).getUser_id();
        if(joke_user_id.equals(currentUserId)){

            holder.deleteJokeBtn.setVisibility(View.VISIBLE);

        }

        //DELETE THE JOKE ON CLICK OF THE TRASH ICON
        holder.deleteJokeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseFirestore.collection("Jokes").document(jokePostId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        joke_list.remove(position);


                    }
                });

            }
        });


        //COUNT LIKES
        firebaseFirestore.collection("Jokes").document(jokePostId).collection("Likes").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {

                if(documentSnapshots != null) {

                if(!documentSnapshots.isEmpty()){

                    int count = documentSnapshots.size();
                    holder.updateLikesCount(count);

                } else {

                    holder.updateLikesCount(0);

                }}

            }
        });


        //GET LIKES
        firebaseFirestore.collection("Jokes").document(jokePostId).collection("Likes").document(currentUserId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {

                if(documentSnapshot != null) {

                if(documentSnapshot.exists()){

                    holder.jokeLikeBtn.setImageDrawable(context.getDrawable(R.mipmap.action_like_purple));

                } else {

                    holder.jokeLikeBtn.setImageDrawable(context.getDrawable(R.mipmap.action_like_grey));

                }}

            }
        });

        //ADD LIKES FEATURE
        holder.jokeLikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseFirestore.collection("Jokes").document(jokePostId).collection("Likes").document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(!task.getResult().exists()){
                            Map<String, Object> likesMap = new HashMap<>();
                            likesMap.put("timestamp", FieldValue.serverTimestamp());

                            firebaseFirestore.collection("Jokes").document(jokePostId).collection("Likes").document(currentUserId).set(likesMap);
                        } else {

                            firebaseFirestore.collection("Jokes").document(jokePostId).collection("Likes").document(currentUserId).delete();

                        }


                    }
                });

            }
        });



    }

    @Override
    public int getItemCount() {
        return joke_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private View mview;

        private TextView jokeView;
        private TextView usernameView;
        private TextView jokeDate;

        private ImageView jokeLikeBtn;
        private TextView jokeLikeCount;

        private ImageView deleteJokeBtn;


        public ViewHolder(View itemView) {
            super(itemView);
            mview = itemView;

            jokeLikeBtn = mview.findViewById(R.id.joke_like_btn);
            deleteJokeBtn = mview.findViewById(R.id.delete_joke_btn);
        }

        public void setJokeText(String jokeText){

            jokeView = mview.findViewById(R.id.tv_joke);
            jokeView.setText(jokeText);

        }

        public void setUsernameText(String usernameText){

            usernameView = mview.findViewById(R.id.tv_username);
            usernameView.setText(usernameText);
        }

        public void setTime(String date){

            jokeDate = mview.findViewById(R.id.tv_date);
            jokeDate.setText(date);

        }

        public void updateLikesCount(int count){

            jokeLikeCount = mview.findViewById(R.id.joke_like_count);
            jokeLikeCount.setText(String.valueOf(count));

        }
    }
}
