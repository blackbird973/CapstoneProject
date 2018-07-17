package yohan.jkskingdom.com.jokesterskingdom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by Yohan on 29/06/2018.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener {

    Button btn_logout;
    View v;
    TextView tv_username;


    FirebaseUser user;
    String uid;
    FirebaseFirestore firebaseFirestore;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_profile, container, false);


        btn_logout = v.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(this);
        tv_username = v.findViewById(R.id.tv_name);
        //RETRIEVE THE username OF THE USER AND DISPLAY IT IN THE TEXTVIEW
        //database reference pointing to root of database
        firebaseFirestore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();


        firebaseFirestore.collection("Users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                //RETRIEVE THE USERNAME OF THE USER FROM HIS UID AND DISPLAY IT IN THE TEXTVIEW
                String username = task.getResult().getString("username");
                tv_username.setText(username);


            }
        });


        //inflate the fragment for the layout
        return v;
    }

    //LOGOUT THE USER FROM FIREBASE AND BRING BACK THE USER TO THE LOGIN ACTIVITY
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_logout:

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
        }
    }

}



