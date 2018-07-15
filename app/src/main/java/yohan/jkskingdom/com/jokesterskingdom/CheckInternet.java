package yohan.jkskingdom.com.jokesterskingdom;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.lang.reflect.Parameter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by Yohan on 15/07/2018.
 */
public class CheckInternet extends AsyncTask<String,Void,Integer>{
    Context context;

    public CheckInternet(Context context) {
        this.context=context;
    }



    public  boolean isConnected()
    {
        ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Service.CONNECTIVITY_SERVICE);

        if (connectivityManager!=null)
        {
            NetworkInfo info=connectivityManager.getActiveNetworkInfo();
            if (info!=null)
            {
                if (info.getState()==NetworkInfo.State.CONNECTED)
                {
                    return true;
                }
            }
        }
        return false;
    }



    @Override
    protected Integer doInBackground(String... params) {

        Integer result=0;
        try {
            Socket socket=new Socket();
            SocketAddress socketAddress=new InetSocketAddress("8.8.8.8",53);
            socket.connect(socketAddress,1500);
            socket.close();
            result=1;
        } catch (IOException e) {
            e.printStackTrace();
            result=0;
        }

        return result;
    }

    @Override
    protected void onPostExecute(Integer result) {
        if (isConnected())
        {
            if (result==1)
            {
                //DO NOTHING IF INTERNET IS ON
            }

            if(result==0)
            {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage(R.string.alert_dialog_asynctask_message);
                alertDialogBuilder.setPositiveButton(R.string.alert_dialog_asynctask_opensettings,
                        new DialogInterface.OnClickListener() {
                            @Override
                            //IF THE USER CLICK YES, THEN THE JOKE IS DELETED
                            public void onClick(DialogInterface arg0, int arg1) {


                                context.startActivity(new Intent(Settings.ACTION_SETTINGS));



                            }
                        });

                alertDialogBuilder.setNegativeButton(R.string.alert_dialog_asynctask_leaveapp,new DialogInterface.OnClickListener() {
                    //IF THE USER CLICK "NO" THE JOKE IS NOT DELETED
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        System.exit(0);

                    }
                });
                //DISPLAY THE ALERT DIALOG
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        }
        else
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setMessage(R.string.alert_dialog_asynctask_message);
            alertDialogBuilder.setPositiveButton(R.string.alert_dialog_asynctask_opensettings,
                    new DialogInterface.OnClickListener() {
                        @Override
                        //IF THE USER CLICK YES, THEN THE JOKE IS DELETED
                        public void onClick(DialogInterface arg0, int arg1) {


                            context.startActivity(new Intent(Settings.ACTION_SETTINGS));



                        }
                    });

            alertDialogBuilder.setNegativeButton(R.string.alert_dialog_asynctask_leaveapp,new DialogInterface.OnClickListener() {
                //IF THE USER CLICK "NO" THE JOKE IS NOT DELETED
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    System.exit(0);

                }
            });
            //DISPLAY THE ALERT DIALOG
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        super.onPostExecute(result);
    }
}
