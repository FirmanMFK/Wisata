package id.firman.app.wisata;

/**
 * Created by Firman on 5/25/2017.
 */

public class Config {

    // Server user login url
    public static String URL_LOGIN = "http://wisata.uphero.com/server_wisata/login.php";

    // Server user register url
    public static String URL_REGISTER = "http://wisata.uphero.com/server_wisata/register.php";

    public static final String SHARED_PREF_NAME = "myloginapp";

    //This would be used to store the email of current logged in user
    public static final String EMAIL_SHARED_PREF = "email";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";


}
