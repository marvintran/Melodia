package comp3350.melodia.objects;

import java.util.Collection;
import java.util.List;


public class Account{

    private String fullName;
    private String userName;
    private String email;
    private Payment payInfo;
    private String profile;
    private List<Song> favoriteList;
    private Login loginInf;
    //private AccessAccount userAccess;

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
        //userAccess = null;
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
class Login{
	private String loginID;
	private String password;
	private String[] securityQues;
	private String[] securityAws;
	private int numOfQuestion;
	private boolean secondPwSetted;
	private String secondPassword;
	
	public Login(String loginID, String password){
		this.password = password;
		this.loginID = loginID;
		this.secondPassword = password;
		numOfQuestion = 0;
		secondPwSetted = false;
		securityQues = new String[3];
		securityAws = new String[3];
		
	}
	
	
	
	public void setSecurityQuestion(String question, String answer){
		if(numOfQuestion >2 ){
			System.out.println("Three Secrity Question Setted, Cannot add more");
		}
		else{
			securityQues[numOfQuestion] = question;
			securityAws[numOfQuestion] = answer;
			numOfQuestion ++;
		}
	}
	
	public boolean checkSecurityQuestion(String question, String answer){
		boolean result = false;
		for(int i = 0; i < 3; i++){
			if(securityQues[i] == question){
				if(securityAws[i].compareTo(answer) == 0){
					result = true;
				}
			}
		}
		return result;
	}
	
	public void setSecondPassword(String inputPassword){
		this.secondPassword = inputPassword;
		secondPwSetted = true;
	}
	
	public boolean checkPassword(String inputPassword){
		boolean result = false;
		if(inputPassword.compareTo(password) == 0){
			result = true;
		}
		
		return result;
	}
	
	
	public boolean changePassword(int opition, String newPassword, String ip1, String ip2){
		boolean result = false;
		if(opition == 1){//user use secrity question to change password
			if(checkSecurityQuestion(ip1,ip2)){
				this.password = newPassword;
				result = true;
			}
		}
		else if(opition == 2){//user use second password to change password
			if(ip1.compareTo(secondPassword) == 0){
				this.password = newPassword;
				result = true;
			}
		}
		return result;
	
	}
	
	
	
}