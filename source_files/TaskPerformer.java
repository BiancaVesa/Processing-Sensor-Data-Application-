import java.io.*;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class TaskPerformer {
    private ArrayList<MonitoredData> monitoredData = new ArrayList<>();
    private ArrayList<String> days = new ArrayList<>();
    private ArrayList<String> activities = new ArrayList<>();
    private Map<String, Integer> distinctActivities = new HashMap<>();
    private Map<String, LocalDateTime> activityDuration = new HashMap<>();
    private List<String> distinctDays = new ArrayList<>();
    private Map<Integer, Map<String, Integer>> distinctActivitiesPerDay = new HashMap<>();
    private File fileIn = new File("Activities.txt");
    private String fileTask1 = "task_1.txt";
    private String fileTask2 = "task_2.txt";
    private String fileTask3 = "task_3.txt";
    private String fileTask4 = "task_4.txt";
    private String fileTask5 = "task_5.txt";
    private String fileTask6 = "task_6.txt";

    public void task1() {
        try {
            Scanner reader = new Scanner(fileIn);
            while (reader.hasNextLine()) {
                ArrayList<String> params = new ArrayList<>();
                String data = reader.nextLine();
                Stream<String> stream = Stream.of(data.split("[\\t ][\\t ]"));
                stream.forEach(s -> params.add(s));
                MonitoredData m = new MonitoredData(params.get(0), params.get(1), params.get(2));
                monitoredData.add(m);
                days.add(m.getStartDate());
                days.add(m.getEndDate());
                activities.add(m.getActivity());
            }
            reader.close();
        } catch (FileNotFoundException e) {
        }

        try {
            FileWriter writer = new FileWriter(fileTask1);
            PrintWriter printWriter = new PrintWriter(writer);
            monitoredData.forEach(m -> printWriter.println(m));
            printWriter.close();
        } catch (IOException e) {
        }
    }

    public void task2() {
        distinctDays = days.stream().distinct().collect(Collectors.toList());
        try {
            FileWriter writer = new FileWriter(fileTask2);
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.println("Number of distinct days: " + distinctDays.size());
            printWriter.close();
        } catch (IOException e) {
        }
        days.clear();
    }

    public void task3() {
        monitoredData.forEach(m -> {
            if (!distinctActivities.containsKey(m.getActivity())) {
                Integer totalMatched = (int) activities.stream()
                        .filter((s) -> s.equals(m.getActivity()))
                        .count();
                distinctActivities.put(m.getActivity(), totalMatched);
            }
        });

        try {
            FileWriter writer = new FileWriter(fileTask3);
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.println("Activities");
            distinctActivities.forEach((a, i) -> printWriter.println("Activity: " + a + "   Occurrences: " + i));
            printWriter.close();
        } catch (IOException e) {
        }
    }

    public void task4() {
        List<String> list = activities.stream().distinct().collect(Collectors.toList());

        distinctDays.forEach(day -> {
            Map<String, Integer> activitiesPerDay = new HashMap<>();
            list.forEach(activity -> {
                Integer totalMatched = 0;
                if (!activitiesPerDay.containsKey(activity)) {
                    totalMatched = (int) monitoredData.stream()
                            .filter((m) -> m.hasDate(day))
                            .filter((m) -> activity.equals(m.getActivity()))
                            .count();
                    activitiesPerDay.put(activity, totalMatched);
                }
            });
            distinctActivitiesPerDay.put(Integer.parseInt(day.substring(8, 10)), activitiesPerDay);
        });

        try {
            FileWriter writer = new FileWriter(fileTask4);
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.println("Activities");
            distinctActivitiesPerDay.forEach((d, m) -> m.forEach((a, i) -> printWriter.println("Day: " + d + "        Activity: " + a + "      Occurrences: " + i)));
            printWriter.close();
        } catch (IOException e) {
        }
    }

    public void task5() {
        List<String> list = activities.stream().distinct().collect(Collectors.toList());
        list.forEach(activity -> {
                    List<MonitoredData> dataList = monitoredData.stream().filter((m) -> activity.equals(m.getActivity())).collect(Collectors.toList());
                    LocalDateTime total = LocalDateTime.of(0, 1, 1, 0, 0, 0);
                    for (MonitoredData m : dataList) {
                        LocalDateTime start = LocalDateTime.of(m.getStartYear(), m.getStartMonth(), m.getStartDay(), m.getStartHour(), m.getStartMinute(), m.getStartSecond());
                        LocalDateTime end = LocalDateTime.of(m.getEndYear(), m.getEndMonth(), m.getEndDay(), m.getEndHour(), m.getEndMinute(), m.getEndSecond());
                        Duration duration = Duration.between(start, end);
                        total = (LocalDateTime) duration.addTo(total);
                    }
                    activityDuration.put(activity, total);
                }
        );

        try {
            FileWriter writer = new FileWriter(fileTask5);
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.println("Activities");
            activityDuration.forEach((a, d) -> printWriter.println("Activity: " + a + "     Duration: " + (d.getMonthValue() - 1) + " months, " + (d.getDayOfMonth() - 1) + " days, " + d.getHour() + " hours, " + d.getMinute() + " minutes, " + d.getSecond() + " seconds"));
            printWriter.close();
        } catch (IOException e) {
        }
    }

    public void task6() {
        List<String> list = activities.stream().distinct().collect(Collectors.toList());
        List<String> filterActivities = new ArrayList<>();

        list.forEach(activity -> {
            List<MonitoredData> dataList = monitoredData.stream().filter((m) -> activity.equals(m.getActivity())).collect(Collectors.toList());

            double secPerActivity = 0;
            int nrDays = 0;
            for (MonitoredData m : dataList) {
                LocalDateTime init = LocalDateTime.of(0, 1, 1, 0, 0, 0);
                LocalDateTime start = LocalDateTime.of(m.getStartYear(), m.getStartMonth(), m.getStartDay(), m.getStartHour(), m.getStartMinute(), m.getStartSecond());
                LocalDateTime end = LocalDateTime.of(m.getEndYear(), m.getEndMonth(), m.getEndDay(), m.getEndHour(), m.getEndMinute(), m.getEndSecond());
                Duration duration = Duration.between(start, end);
                init = (LocalDateTime) duration.addTo(init);

                secPerActivity = init.getSecond() + 60 * init.getMinute() + 3600 * init.getHour() + 86400 * (init.getDayOfMonth() - 1);
                if (secPerActivity < 300) {
                    nrDays++;
                }
            }
            double occurrences = distinctActivities.get(activity);
            if ((double) nrDays / occurrences > 0.9 && !filterActivities.contains(activity)) {
                filterActivities.add(activity);
            }
        });

        try {
            FileWriter writer = new FileWriter(fileTask6);
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.println("Activities");
            filterActivities.forEach(a -> printWriter.println(a));
            printWriter.close();
        } catch (IOException e) {
        }
    }
}
