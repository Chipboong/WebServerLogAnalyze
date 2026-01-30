import java.util.Date;

public class LogEntry {

 private String ipAddress;
 private Date accesstime;
 private String request;
 private int statusCode;
 private int bytesReturned;

 public LogEntry(String ip, Date time, String req, int status, int bytes) {
     this.ipAddress =  ip;
     this.accesstime = time;
     this.request = req;
     this.statusCode = status;
     this.bytesReturned = bytes;
 }

 public String getIpAddress() {
     return ipAddress;
 }

 public Date getAccesstime() {
     return accesstime;
 }

 public String getRequest() {
     return request;
 }

 public int getStatusCode() {
     return statusCode;
 }

 public int getBytesReturned() {
     return bytesReturned;
 }

 public String toString() {
     return ipAddress + "\t" + accesstime + "\t" + request + "\t" + statusCode + "\t" + bytesReturned;
 }

}
