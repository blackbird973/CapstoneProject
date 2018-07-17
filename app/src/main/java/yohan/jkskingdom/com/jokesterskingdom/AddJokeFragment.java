package yohan.jkskingdom.com.jokesterskingdom;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yohan on 29/06/2018.
 */

public class AddJokeFragment extends Fragment implements View.OnClickListener {

    Button btnSendJoke;
    View v;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser user;
    String uid;
    TextInputLayout inputJoke;

    public AddJokeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_add_joke, container, false);

        btnSendJoke = v.findViewById(R.id.buttonAddJoke);
        btnSendJoke.setOnClickListener(this);
        inputJoke = v.findViewById(R.id.textInputLayoutJoke);
        //RETRIEVE THE username OF THE USER AND DISPLAY IT IN THE TEXTVIEW
        //database reference pointing to root of database
        firebaseFirestore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        //inflate the fragment for the layout
        return v;
    }

    //LOGOUT THE USER FROM FIREBASE AND BRING BACK THE USER TO THE LOGIN ACTIVITY
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonAddJoke:


                //RETRIEVE THE USERNAME FROM THE USERS COLLECTION AND INSERT IT IN THE postMAP WHICH WILL BE INSERT IN THE "JOKES" COLLECTION
                firebaseFirestore.collection("Users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        //RETRIEVE THE USERNAME OF THE USER FROM HIS UID AND DISPLAY IT IN THE TEXTVIEW
                        String username = task.getResult().getString("username");

                        String joke = inputJoke.getEditText().getText().toString();

                        if (!TextUtils.isEmpty(joke)) {
                            //ADD ALL INFOS  INTO THE MAP AND POST THIS MAP IN THE FIRESTORE COLLECTION
                            Map<String, Object> postMap = new HashMap<>();
                            postMap.put("joke", inputJoke.getEditText().getText().toString());
                            postMap.put("user_id", uid);
                            postMap.put("timestamp", FieldValue.serverTimestamp());
                            postMap.put("username", username);

                            firebaseFirestore.collection("Jokes").add(postMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {

                                    if (task.isSuccessful()) {

                                        StyleableToast.makeText(getActivity(), getString(R.string.add_joke_success), Toast.LENGTH_SHORT, R.style.mytoast).show();
                                        //BRING BACK THE USER TO THE JOKES FEED FRAGMENT
                                        JokesFeedFragment newFragment = new JokesFeedFragment();
                                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                        fragmentTransaction.replace(R.id.fragment_container, newFragment);
                                        fragmentTransaction.addToBackStack(null);
                                        fragmentTransaction.commit();


                                    } else {
                                        StyleableToast.makeText(getActivity(), getString(R.string.add_joke_error), Toast.LENGTH_SHORT, R.style.mytoast).show();
                                    }


                                }
                            });
                        } else {
                            StyleableToast.makeText(getActivity(), getString(R.string.add_joke_empty_joke), Toast.LENGTH_SHORT, R.style.mytoast).show();
                        }


                    }
                });


        }
    }

}
