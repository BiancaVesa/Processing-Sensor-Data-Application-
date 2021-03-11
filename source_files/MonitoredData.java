public class MonitoredData {
    private String startTime;
    private String endTime;
    private String activity;

    public MonitoredData(String startTime, String endTime, String activity) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.activity = activity;
    }

    public int getStartYear() {
        return Integer.parseInt(startTime.substring(0, 4));
    }

    public int getStartMonth() {
        return Integer.parseInt(startTime.substring(5, 7));
    }

    public int getStartDay() {
        return Integer.parseInt(startTime.substring(8, 10));
    }

    public int getEndYear() {
        return Integer.parseInt(endTime.substring(0, 4));
    }

    public int getEndMonth() {
        return Integer.parseInt(endTime.substring(5, 7));
    }

    public int getEndDay() {
        return Integer.parseInt(endTime.substring(8, 10));
    }

    public int getEndHour() {
        return Integer.parseInt(endTime.substring(11, 13));
    }

    public int getEndMinute() {
        return Integer.parseInt(endTime.substring(14, 16));
    }

    public int getEndSecond() {
        return Integer.parseInt(endTime.substring(17, 19));
    }

    public int getStartHour() {
        return Integer.parseInt(startTime.substring(11, 13));
    }

    public int getStartMinute() {
        return Integer.parseInt(startTime.substring(14, 16));
    }

    public int getStartSecond() {
        return Integer.parseInt(startTime.substring(17, 19));
    }

    public String getStartDate() {
        return startTime.substring(0, 10);
    }


    public boolean hasDate(String date) {
        if (getStartDate().equals(date) || getEndDate().equals(date)) {
            return true;
        }
        return false;
    }


    public String getEndDate() {
        return endTime.substring(0, 10);
    }

    public String getActivity() {
        return activity;
    }

    public String toString() {
        return "Start time: " + startTime + " End time: " + endTime + " Activity: " + activity;
    }
}
