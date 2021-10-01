package vloboda.myProject.breathe;

public class HealthInfo {

    String mHealthInfo;
    long timeHasToPass;

    public HealthInfo() {
    }

    public HealthInfo(String mHealthInfo, long timeHasToPass) {
        this.mHealthInfo = mHealthInfo;
        this.timeHasToPass = timeHasToPass;
    }

    public String getmHealthInfo() {
        return mHealthInfo;
    }

    public void setmHealthInfo(String mHealthInfo) {
        this.mHealthInfo = mHealthInfo;
    }

    public long getTimeHasToPass() {
        return timeHasToPass;
    }

    public void setTimeHasToPass(long timeHasToPass) {
        this.timeHasToPass = timeHasToPass;
    }
}
