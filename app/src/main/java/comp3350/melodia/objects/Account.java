package comp3350.melodia.objects;

import java.util.Collection;


public class Account{

    private String fullName;
    private String userName;
    private String email;
    private Payment payInfo;
    private String profile;
    private Collection<Song> favoriteList;
    //private AccessAccount userAccess;

    public Account(String fullName, String userName, String email, String profile, Collection<Song> favoriteList){
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.profile = profile;
        this.favoriteList = favoriteList;
        //userAccess = null;
    }

    public String getFullName(){
        return fullName;
    }

    public void setFullName(String inputName){
        this.fullName = inputName;
    }

    public String getProfile(){
        return profile;
    }

    public void setProfile(String profile){
        this.profile = profile;
    }

    public void setUserName(String newName){
        this.userName = newName;
    }

    public String getUserName(){
        return userName;
    }

    public void setEmail(String inputEmail){
        this.email = inputEmail;
    }

    public String getEmail(){
        return email;
    }

    public void setFavorList(Collection<Song> newList){
        this.favoriteList = newList;
    }

    public Collection<Song> getFavorList(){
        return favoriteList;
    }

    public void printFavorList(){
        System.out.println("Favourite songs list of :");
        String result = "";

//        for(int i = 0; i < favoriteList.size(); i++){
//            result += favoriteList.get(i) + "\n";
//        }
        for( Song song : this.favoriteList){
            result += song + "\n";
        }

        System.out.println(result);
    }
    //public void setAccess(String userID, String password){
    //this.userAccess = new AccessAccount(userID, password);
    //}use for test

    //public AccessAccount getUserAccess(){
    //return this.userAccess;
    //}//use for test

    public String toString(){
        String result = "";
        result += "Name :" + fullName+" \n";
        result += "UserName: " + userName + " \n";
        result += "eMail : " + email + " \n";
        result += "Profile: " + profile;
        return result;
    }

}
class Payment{
    private int bankAccount;
    private String accountHolder;
    private int vaildDate;
    private Payment secondPayment;

    Payment(int bankAccount, String accountHolder, int vaildDate, Payment secondPayment){
        this.bankAccount = bankAccount;
        this.accountHolder = accountHolder;
        this.vaildDate = vaildDate;
        secondPayment = null;
    }


}