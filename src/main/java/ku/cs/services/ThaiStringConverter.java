package ku.cs.services;

import ku.cs.models.users.User;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ThaiStringConverter {
    public static String getThaiTimeStampFormattedString(LocalDateTime localDateTime) {
        if (localDateTime == null) throw new NullPointerException("Can't format to String");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy\nHH:mm:ss");
        return localDateTime.format(formatter);
    }

    public static String getThaiUserRoleString(User user) {
        if (user == null) throw new NullPointerException("Can't format to String");
        if (user.getRole() == null) throw new NullPointerException("Can't format to String");
        return switch (user.getRole()) {
            case "admin" -> "ผู้ดูแลระบบ";
            case "advisor" -> "อาจารย์ที่ปรึกษา";
            case "student" -> "นิสิต";
            case "departmentOfficer" -> "เจ้าหน้าที่ภาควิชา";
            case "facultyOfficer" -> "เจ้าหน้าที่คณะ";
            default -> user.getRole();
        };
    }

    public static String getThaiMonthString(int monthValue) {
        if (monthValue < 1 || monthValue > 12) return null;
        return switch (monthValue) {
            case 1 -> "มกราคม";
            case 2 -> "กุมภาพันธ์";
            case 3 -> "มีนาคม";
            case 4 -> "เมษายน";
            case 5 -> "พฤษภาคม";
            case 6 -> "มิถุนายน";
            case 7 -> "กรกฎาคม";
            case 8 -> "สิงหาคม";
            case 9 -> "กันยายน";
            case 10 -> "ตุลาคม";
            case 11 -> "พฤศจิกายน";
            case 12 -> "ธันวาคม";
            default -> monthValue + "";
        };
    }

    public static String getThaiYearString(int yearValue) {
        return String.valueOf(yearValue + 543);
    }
}
