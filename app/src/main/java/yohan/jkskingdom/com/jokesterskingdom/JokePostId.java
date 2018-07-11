package yohan.jkskingdom.com.jokesterskingdom;

import com.google.firebase.database.Exclude;

import javax.annotation.Nonnull;

/**
 * Created by Yohan on 11/07/2018.
 */

public class JokePostId{

    @Exclude
    public String JokePostId;

    public <T extends JokePostId> T withId(@Nonnull final String id) {
        this.JokePostId = id;
        return (T) this;
    }
}
