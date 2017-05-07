import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GetDate {

	Date date;
	
	public GetDate(){
		date = Calendar.getInstance().getTime();
	}
	
	public String getDateFormat(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	
	public String getTimeFormat(){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(date);
	}
	
}
	

