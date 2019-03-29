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


    public String getProfile(){
        return profile;
    }


    public String getUserName(){
        return userName;
    }


    public String getEmail(){
        return email;
    }


    public List<Song> getFavorList(){
        return favoriteList;
    }

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
        this.secondPayment = secondPayment;
    }
}
