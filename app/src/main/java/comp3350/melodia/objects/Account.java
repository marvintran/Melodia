package comp3350.melodia.objects;

import java.security.SecureRandom;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;

public class Account{

    public static int ID_MAX = 1000;

    private int userID;
    private String accountSalt;
    private String passwordHash;
    private String fullName;
    private String userName;
    private String email;
    private ArrayList<Integer> favoritePlaylistID;

    // getting user from database
    public Account(int userID,  String accountSalt, String passwordHash,  String fullName, String userName, String email, ArrayList<Integer> favoritePlaylistID){
        this.userID = userID;
        this.accountSalt = accountSalt;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.favoritePlaylistID = favoritePlaylistID;
    }

    // creating new user
    public Account(String fullName, String userName, String password, String email){
        this.userID = createRandomID();
        this.accountSalt = createRandomString(8);
        this.passwordHash = hashPassword(password, this.accountSalt);
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.favoritePlaylistID = new ArrayList<>();
    }

    public int getUserID(){ return userID;}

    public String getFullName(){
        return fullName;
    }

    public String getUserName(){
        return userName;
    }

    public String getEmail(){
        return email;
    }

    public String getAccountSalt() { return accountSalt; }

    public String getPasswordHash(){ return passwordHash; }

    public ArrayList<Integer> getFavoritePlaylistID(){return favoritePlaylistID;}

    public void setFavouritePlaylist(int playlistID){
        this.favoritePlaylistID.add(playlistID);
    }

    public boolean comparePassword(String submittedPassword){
        String hashedSubmitedPassword = hashPassword(submittedPassword, this.accountSalt);
        return hashedSubmitedPassword.equalsIgnoreCase(this.passwordHash);
    }

    public static String convertBytesToString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static int createRandomID()  {
        try {
            SecureRandom srng = SecureRandom.getInstance("SHA1PRNG", "SUN");
            return srng.nextInt(ID_MAX);
        } catch (final NoSuchProviderException e)  {
            throw new RuntimeException(e);
        } catch (final NoSuchAlgorithmException e)  {
            throw new RuntimeException(e);
        }
    }

    public static String createRandomString(int size) {
        byte[] bytes = new byte[size];
        try {
            SecureRandom srng = SecureRandom.getInstance("SHA1PRNG", "SUN");
            srng.nextBytes(bytes);
            return convertBytesToString(bytes);
        } catch (final NoSuchProviderException e)  {
            throw new RuntimeException(e);
        } catch (final NoSuchAlgorithmException e)  {
            throw new RuntimeException(e);
        }
    }

    // parts of code taken from this website for hashing
    // https://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
    public static String hashPassword(String passwordToHash, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(salt.getBytes());

            byte[] bytes = md.digest(passwordToHash.getBytes());
            //This bytes[] has bytes in decimal format;
            //Get complete hashed password in hex format
            generatedPassword = convertBytesToString(bytes);
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

}
