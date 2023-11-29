package generator;

public class GeneratePrinterFaultData {
    public static void main(String[] args) {
        String[] years = new String[]{"2020", "2021", "2022", "2023"};
        for (String year : years) {
            System.out.println(new StringBuffer("\"3\"").append(",\"").append(year).append("-01-01 12:00:00\""));
            System.out.println(new StringBuffer("\"3\"").append(",\"").append(year).append("-01-02 12:00:00\""));
            System.out.println(new StringBuffer("\"2\"").append(",\"").append(year).append("-01-03 12:00:00\""));
            System.out.println(new StringBuffer("\"2\"").append(",\"").append(year).append("-01-04 12:00:00\""));
            System.out.println(new StringBuffer("\"5\"").append(",\"").append(year).append("-01-05 12:00:00\""));
            System.out.println(new StringBuffer("\"2\"").append(",\"").append(year).append("-01-06 12:00:00\""));
            System.out.println(new StringBuffer("\"2\"").append(",\"").append(year).append("-01-07 12:00:00\""));
            System.out.println(new StringBuffer("\"7\"").append(",\"").append(year).append("-01-08 12:00:00\""));
            System.out.println(new StringBuffer("\"2\"").append(",\"").append(year).append("-01-09 12:00:00\""));
            System.out.println(new StringBuffer("\"2\"").append(",\"").append(year).append("-01-10 12:00:00\""));
            System.out.println(new StringBuffer("\"8\"").append(",\"").append(year).append("-01-11 12:00:00\""));
            System.out.println(new StringBuffer("\"9\"").append(",\"").append(year).append("-01-12 12:00:00\""));
            System.out.println(new StringBuffer("\"10\"").append(",\"").append(year).append("-01-13 12:00:00\""));

            System.out.println(new StringBuffer("\"3\"").append(",\"").append(year).append("-02-11 12:00:00\""));
            System.out.println(new StringBuffer("\"4\"").append(",\"").append(year).append("-03-12 12:00:00\""));
            System.out.println(new StringBuffer("\"5\"").append(",\"").append(year).append("-04-13 12:00:00\""));

            System.out.println(new StringBuffer("\"6\"").append(",\"").append(year).append("-05-11 12:00:00\""));
            System.out.println(new StringBuffer("\"8\"").append(",\"").append(year).append("-06-11 12:00:00\""));
            System.out.println(new StringBuffer("\"3\"").append(",\"").append(year).append("-07-11 12:00:00\""));
            System.out.println(new StringBuffer("\"6\"").append(",\"").append(year).append("-08-11 12:00:00\""));
            System.out.println(new StringBuffer("\"7\"").append(",\"").append(year).append("-09-11 12:00:00\""));
            System.out.println(new StringBuffer("\"5\"").append(",\"").append(year).append("-10-11 12:00:00\""));
            System.out.println(new StringBuffer("\"3\"").append(",\"").append(year).append("-11-11 12:00:00\""));
            System.out.println(new StringBuffer("\"7\"").append(",\"").append(year).append("-12-25 12:00:00\""));
            System.out.println(new StringBuffer("\"7\"").append(",\"").append(year).append("-12-26 12:00:00\""));
            System.out.println(new StringBuffer("\"7\"").append(",\"").append(year).append("-12-27 12:00:00\""));
            System.out.println(new StringBuffer("\"7\"").append(",\"").append(year).append("-12-28 12:00:00\""));
            System.out.println(new StringBuffer("\"7\"").append(",\"").append(year).append("-12-29 12:00:00\""));
            System.out.println(new StringBuffer("\"7\"").append(",\"").append(year).append("-12-30 12:00:00\""));
            System.out.println(new StringBuffer("\"7\"").append(",\"").append(year).append("-12-31 12:00:00\""));
        }
    }
}
