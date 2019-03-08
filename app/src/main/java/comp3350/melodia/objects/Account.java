package comp3350.melodia.objects;

import java.util.List;

import comp3350.melodia.logic.Login;


public class Account{

    private String fullName;
    private String userName;
    private String email;
    private Payment payInfo;
    private String profile;
    private List<Song> favoriteList;
    private Login loginInf;

    public Account(String fullName, String userName, String email, String profile, List<Song> favoriteList, Login newlog){
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.profile = profile;
        this.favoriteList = favoriteList;
        this.loginInf = newlog;
        //userAccess = null;
    }

    public Account(String fullName, String userName, String email, String profile, List<Song> favoriteList){
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.profile = profile;
        this.favoriteList = favoriteList;
    }


    public String getFullName(){
        return fullName;
    }
    
    public Login getLoginInf(){
        return loginInf;
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

    public void setFavorList(List<Song> newList){
        this.favoriteList = newList;
    }

    public List<Song> getFavorList(){
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
        String result = String.format("Name :%s \nUserName: %s \neMail : %s \nProfile: %s" ,
                fullName, userName, email, profile);
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
