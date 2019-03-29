package comp3350.melodia.logic;

public class Login{
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

}