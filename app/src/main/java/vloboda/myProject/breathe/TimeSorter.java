package vloboda.myProject.breathe;

import java.util.Comparator;

public class TimeSorter implements Comparator<HealthInfo> {

    @Override
    public int compare(HealthInfo o1, HealthInfo o2) {
        return (int)o1.getTimeHasToPass()-(int)(o2.getTimeHasToPass());
    }
}
