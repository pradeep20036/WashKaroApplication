package inspire2connect.inspire2connect.aqi_cough;

public class UserHelperClass {
    String aqiActualString;
    String userLatitude;
    String userLongitude;
    String heightString;
    String weightString;
    String ageString;
    boolean bronchitisVal;
    boolean asthmaVal;
    boolean pneumoniaVal;
    boolean cancerVal;
    boolean tbVal;
    boolean otherRespVal;
    boolean femaleVal;
    boolean maleVal;
    boolean otherGenderVal;
    String currentTime;

    public UserHelperClass() {

    }

    public UserHelperClass(String aqiActualString, String userLatitude, String userLongitude, String heightString, String weightString, String ageString, boolean bronchitisVal, boolean asthmaVal, boolean pneumoniaVal, boolean cancerVal, boolean tbVal, boolean otherRespVal, boolean femaleVal, boolean maleVal, boolean otherGenderVal, String currentTime) {
        this.aqiActualString = aqiActualString;
        this.userLatitude = userLatitude;
        this.userLongitude = userLongitude;
        this.heightString = heightString;
        this.weightString = weightString;
        this.ageString = ageString;
        this.bronchitisVal = bronchitisVal;
        this.asthmaVal = asthmaVal;
        this.pneumoniaVal = pneumoniaVal;
        this.cancerVal = cancerVal;
        this.tbVal = tbVal;
        this.otherRespVal = otherRespVal;
        this.femaleVal = femaleVal;
        this.maleVal = maleVal;
        this.otherGenderVal = otherGenderVal;
        this.currentTime = currentTime;
    }

    public String getAqiActualString() {
        return aqiActualString;
    }

    public void setAqiActualString(String aqiActualString) {
        this.aqiActualString = aqiActualString;
    }

    public String getUserLatitude() {
        return userLatitude;
    }

    public void setUserLatitude(String userLatitude) {
        this.userLatitude = userLatitude;
    }

    public String getUserLongitude() {
        return userLongitude;
    }

    public void setUserLongitude(String userLongitude) {
        this.userLongitude = userLongitude;
    }

    public String getHeightString() {
        return heightString;
    }

    public void setHeightString(String heightString) {
        this.heightString = heightString;
    }

    public String getWeightString() {
        return weightString;
    }

    public void setWeightString(String weightString) {
        this.weightString = weightString;
    }

    public String getAgeString() {
        return ageString;
    }

    public void setAgeString(String ageString) {
        this.ageString = ageString;
    }

    public boolean isBronchitisVal() {
        return bronchitisVal;
    }

    public void setBronchitisVal(boolean bronchitisVal) {
        this.bronchitisVal = bronchitisVal;
    }

    public boolean isAsthmaVal() {
        return asthmaVal;
    }

    public void setAsthmaVal(boolean asthmaVal) {
        this.asthmaVal = asthmaVal;
    }

    public boolean isPneumoniaVal() {
        return pneumoniaVal;
    }

    public void setPneumoniaVal(boolean pneumoniaVal) {
        this.pneumoniaVal = pneumoniaVal;
    }

    public boolean isCancerVal() {
        return cancerVal;
    }

    public void setCancerVal(boolean cancerVal) {
        this.cancerVal = cancerVal;
    }

    public boolean isTbVal() {
        return tbVal;
    }

    public void setTbVal(boolean tbVal) {
        this.tbVal = tbVal;
    }

    public boolean isOtherRespVal() {
        return otherRespVal;
    }

    public void setOtherRespVal(boolean otherRespVal) {
        this.otherRespVal = otherRespVal;
    }

    public boolean isFemaleVal() {
        return femaleVal;
    }

    public void setFemaleVal(boolean femaleVal) {
        this.femaleVal = femaleVal;
    }

    public boolean isMaleVal() {
        return maleVal;
    }

    public void setMaleVal(boolean maleVal) {
        this.maleVal = maleVal;
    }

    public boolean isOtherGenderVal() {
        return otherGenderVal;
    }

    public void setOtherGenderVal(boolean otherGenderVal) {
        this.otherGenderVal = otherGenderVal;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
}
