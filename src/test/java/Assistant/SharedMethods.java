package Assistant;

import DataPortal_Pages.DataPortal_Login_Page;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class SharedMethods {

//    DataPortal_Login_Page dataPortalLoginPage = new DataPortal_Login_Page();

    /*public void enterIntheSystem_DataPortal(){
        dataPortalLoginPage.enterUser_DataPortal(ConstantsAssistant.DELIVERYCENTER_USER_ADM_DEUTSCHE);
        dataPortalLoginPage.enterPassword_DataPortal(ConstantsAssistant.DELIVERYCENTER_USER_PSWD_DEUTSCHE);
    }*/

    public String returnDateHours(){
        Date _date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyyHHmmss");
        String date = formatter.format(_date);
        return date;
    }

    public static LocalDateTime convertStringToTimeStamp(String date){
        String [] arrayDate = date.split("[- :]");
        int year = Integer.parseInt(arrayDate[0]);
        int mounth = Integer.parseInt(arrayDate[1]);
        int day = Integer.parseInt(arrayDate[2]);
        int hour = Integer.parseInt(arrayDate[3]);
        int minutes = Integer.parseInt(arrayDate[4]);
        int seconds = Integer.parseInt(arrayDate[5]);
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.of(LocalDate.of(year, mounth, day),
                LocalTime.of(hour, minutes, seconds)));
        return timestamp.toLocalDateTime();
    }

}
