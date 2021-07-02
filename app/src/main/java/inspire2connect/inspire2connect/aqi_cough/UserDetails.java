package inspire2connect.inspire2connect.aqi_cough;

public class UserDetails {

    private String userName;
    private String userRollNumber;
    private String userBranch;
    private int userImage;

    public UserDetails(String userName, String userRollNumber, String userBranch, int userImage) {
        this.userName = userName;
        this.userRollNumber = userRollNumber;
        this.userBranch = userBranch;
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRollNumber() {
        return userRollNumber;
    }

    public void setUserRollNumber(String userRollNumber) {
        this.userRollNumber = userRollNumber;
    }

    public String getUserBranch() {
        return userBranch;
    }

    public void setUserBranch(String userBranch) {
        this.userBranch = userBranch;
    }

    public int getUserImage() {
        return userImage;
    }

    public void setUserImage(int userImage) {
        this.userImage = userImage;
    }

}


