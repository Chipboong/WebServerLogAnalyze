import edu.duke.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class LogAnalyzer {
    private ArrayList<LogEntry> records;

    public LogAnalyzer() {
        records = new ArrayList<LogEntry>();
    }
    public void readFile() {
        records.clear();
        FileResource fr = new FileResource();
        for (String line : fr.lines()) {
            records.add(WebLogParser.parseEntry(line));
        }
    }

    public void printAll() {
        readFile();
        for (LogEntry entry : records) {
            System.out.println(entry.getAccesstime());
        }
    }
    public int countUniqueIPs() {
        ArrayList<String> uniqueIPs = new ArrayList<String>();
        for (LogEntry le : records) {
            String ipAddress = le.getIpAddress();
            if (!uniqueIPs.contains(ipAddress)) {
                uniqueIPs.add(ipAddress);
            }
        }
        return uniqueIPs.size();
    }
    public void printAllHigherThanNum(int num) {
        for (LogEntry entry : records) {
            int statusCode = entry.getStatusCode();
            if (statusCode > num) {
                System.out.println(entry);
            }
        }
    }

    public ArrayList<LogEntry> uniqueIPVisitsOnDay(String someday) {
        ArrayList<LogEntry> le = new ArrayList<LogEntry>();
        ArrayList<String> uniqueIPs = new ArrayList<String>();
        for (LogEntry entry : records) {
            String ipAddress = entry.getIpAddress();
            if (!uniqueIPs.contains(ipAddress)) {
                String d = entry.getAccesstime().toString();
                int firstIdx = d.indexOf(someday);
                int lastIdx = d.lastIndexOf(someday);
                if (firstIdx != -1 && lastIdx != -1) {
                    String date = d.substring(firstIdx, lastIdx);
                    if (!date.equals(someday) && !le.contains(ipAddress)) {
                        le.add(entry);
                        uniqueIPs.add(ipAddress);
                    }
                }
            }
        }
        return le;
    }
    public int countUniqueIPsInRange(int low, int high) {
        ArrayList<LogEntry> le = new ArrayList<LogEntry>();
        ArrayList<String> uniqueIPs = new ArrayList<String>();
        for (LogEntry entry : records) {
            if (!uniqueIPs.contains(entry.getIpAddress())) {
                if (low <= entry.getStatusCode() && high >= entry.getStatusCode()) {
                    le.add(entry);
                    uniqueIPs.add(entry.getIpAddress());
                }
            }
        }
        return le.size();
    }

    public HashMap<String,Integer> countVisitspPerIP() {
        HashMap<String, Integer> counts = new HashMap<String, Integer>();
        for (LogEntry entry : records) {
            String ipAddress = entry.getIpAddress();
            if (!counts.containsKey(ipAddress)) {
                counts.put(ipAddress, 1);
            } else {
                counts.put(ipAddress, counts.get(ipAddress) + 1);
            }
        }
        return counts;
    }

    public int mostNumberVisitsByIP (HashMap<String, Integer> ipAddress) {
        int max = 0;
        for (String ip : ipAddress.keySet()) {
            if (ipAddress.get(ip) > max) {
                max = ipAddress.get(ip);
            }
        }
        return max;
    }

    public ArrayList<String> iPsMostVisits(HashMap<String, Integer> ipAddress) {
        int max = mostNumberVisitsByIP(ipAddress);
        ArrayList<String> iPAddress = new ArrayList<String>();
        for (String s : ipAddress.keySet()) {
            if (ipAddress.get(s) == max) {
                iPAddress.add(s);
            }
        }
        return iPAddress;
    }

    public HashMap<String,ArrayList<String>> iPForDays() {
        HashMap <String, ArrayList<String>> iPForDays = new HashMap<String, ArrayList<String>>();
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd", Locale.ENGLISH);
        for (LogEntry entry : records) {
            Date time = entry.getAccesstime();
            String date = formatter.format(time);
            String ipAddress = entry.getIpAddress();
            if (!iPForDays.containsKey(date)) {
                ArrayList<String> iPs = new ArrayList<String>();
                iPs.add(ipAddress);
                iPForDays.put(date, iPs);
            } else {
                ArrayList<String> iPs = iPForDays.get(date);
                    iPs.add(ipAddress);
                    iPForDays.put(date, iPs);
            }
        }

        return iPForDays;
    }

    public String dayWithMostIPVisits(HashMap<String, ArrayList<String>> map) {
        int max = 0;
        for (String date : map.keySet()) {
            int size = map.get(date).size();
            if (size > max) {
                max = size;
            }
        }
        for (String date : map.keySet()) {
            int size = map.get(date).size();
            if (size == max) {
                return date;
            }
        }
        return "";
    }

    public ArrayList<String> iPsWithMostVisitsOnDay(HashMap<String, ArrayList<String>> map, String date) {
        ArrayList<String> MostVistIPs = new ArrayList<String>();
        ArrayList<String> iPs = map.get(date);
        HashMap<String, Integer> newMap = new HashMap<String, Integer>();
        for (String ip : iPs) {
            if (!newMap.containsKey(ip)) {
                newMap.put(ip, 1);
            }else {
                newMap.put(ip, newMap.get(ip) + 1);
            }
        }

        int max = mostNumberVisitsByIP(newMap);
        for (String s : newMap.keySet()) {
            if (newMap.get(s) == max) {
                MostVistIPs.add(s);
            }
        }

        return MostVistIPs;
    }

}
