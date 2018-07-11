package yohan.jkskingdom.com.jokesterskingdom;

import java.util.Date;
/**
 * Created by Yohan on 30/06/2018.
 */

public class JokePost extends JokePostId{

    public  String user_id, joke, username;
    public Date timestamp;

    public JokePost(){}

    public JokePost(String user_id, String joke, String username, Date timestamp) {
        this.user_id = user_id;
        this.joke = joke;
        this.username = username;
        this.timestamp = timestamp;

    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }




}
