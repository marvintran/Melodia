package comp3350.melodia.logic;

public class AccessAccount{

	private String loginID;
	private String password;
	private String[] securityQues;
	private String[] securityAws;
	private int numOfQuestion;
	private boolean secondPwSetted;
	private String secondPassword;
	
	public AccessAccount(String loginID, String password){
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