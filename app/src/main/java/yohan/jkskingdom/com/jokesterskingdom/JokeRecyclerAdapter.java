package yohan.jkskingdom.com.jokesterskingdom;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.text.format.DateFormat;
import java.util.List;
import java.util.Date;

/**
 * Created by Yohan on 11/07/2018.
 */

public class JokeRecyclerAdapter extends RecyclerView.Adapter<JokeRecyclerAdapter.ViewHolder> {

    public List<JokePost> joke_list;

    public JokeRecyclerAdapter(List<JokePost> joke_list){

        this.joke_list = joke_list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.joke_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //RETRIEVE THE JOKE TEXT
         String joke_data = joke_list.get(position).getJoke();
         holder.setJokeText(joke_data);
         //RETRIEVE THE USERNAME TEXT
         String username_data = joke_list.get(position).getUsername();
         holder.setUsernameText(username_data);
         //RETRIEVE TIME OF THE POSTED JOKE
        long millisecond = joke_list.get(position).getTimestamp().getTime();
        String dateString = DateFormat.format("dd/MM/yyyy", new Date(millisecond)).toString();
        holder.setTime(dateString);



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

        public ViewHolder(View itemView) {
            super(itemView);
            mview = itemView;
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
    }
}
