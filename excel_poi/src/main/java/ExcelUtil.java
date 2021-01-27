import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
/**
 * @Author Ashe
 * @Date 2021/1/21 19:18
 * @Version 1.0
 */
public class ExcelUtil {

    private static Workbook wb;
    /**
     * <table tableName="pls_case_assign" domainObjectName="caseAssign"
     *      enableCountByExample="false" enableUpdateByExample="false" enableDeleteByExample="false" enableSelectByExample="false" selectByExampleQueryId="false"></table>
     * @param path
     * @throws Exception
     */
    public static StringBuilder createLabel(String path) throws Exception{

        checkAndCreateSheet(path);
        StringBuilder sb1 = new StringBuilder();
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            Sheet sheet = wb.getSheetAt(i);     //读取sheet
            StringBuilder sb = new StringBuilder();
            String tableName = sheet.getRow(1).getCell(2).toString().toLowerCase();
            sb.append("<table tableName=\"").append(tableName).append("\" domainObjectName=\"");
            String[] s = tableName.split("_");
            for(int j =1;j<s.length;j++){
                String substring = s[j].substring(0,1).toUpperCase();
                String substring1 = s[j].substring(1);
                if(s[0].equals("pls")){
                    sb.append(substring+substring1);
                }else {
                    System.out.println("<!--特殊"+tableName+"-->");
                }
            }
            sb.append("\"").append("\n\t").append("enableCountByExample=\"false\" enableUpdateByExample=\"false\" \n\t enableDeleteByExample=\"false\" enableSelectByExample=\"false\" selectByExampleQueryId=\"false\"></table>");
            System.out.println(sb);
            sb1 = sb1.append(sb);
        }
        return sb1;
    }

    public static StringBuilder createTable(String path) throws Exception {

        checkAndCreateSheet(path);
        StringBuilder sb1 = new StringBuilder();
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            //开始解析
            Sheet sheet = wb.getSheetAt(i);     //读取sheet
            int firstRowNum = sheet.getFirstRowNum();// 表名 ：岗位管理表
            int secondRowIndex = sheet.getFirstRowNum() + 1;   //表名 ：hr_recruit_post
            int lastRowIndex = sheet.getLastRowNum();

            StringBuilder sb = new StringBuilder();
            String tableName = sheet.getRow(1).getCell(2).toString();
            sb.append("DROP TABLE IF EXISTS ").append(tableName).append(";");
            sb.append("\n");
            sb.append("CREATE TABLE ").append(tableName).append(" ( ");
            sb.append("\n");
            String primaryKey = "";
            String primaryKey1=null;
            for (int rIndex = 6; rIndex <= lastRowIndex; rIndex++) {   //遍历行 从第7行开始
                Row row = sheet.getRow(rIndex);
                if (row != null) {
                    int firstCellIndex = row.getFirstCellNum();//字段注释

                    //Column name(logical name)  Column name(physical name)"	Type	Length	Decimal	PK	NOT NULL	Enum	Relate Table
                    for (int cIndex = firstCellIndex + 1; cIndex <=6; cIndex++) {   //遍历列 ，到第6列 是否非空

                        Cell cell = row.getCell(cIndex);
                        if (cell == null) {
                            continue;
                        }
                        switch (cIndex) {
                            case 1://field
                                sb.append(cell.toString() + " ");
                                break;
                            case 2://type
                                String s = cell.toString().toLowerCase();
                                if (s.contains("date") || s.contains("time")) {
                                    sb.append(s);
                                } else if (s.contains("(")) {
                                    int temp = s.indexOf("(");
                                    String substring = s.substring(0, temp);
                                    sb.append(substring + "(");

                                    int numericCellValue = (int) row.getCell(3).getNumericCellValue();
                                    sb.append(numericCellValue);
                                    //小数点后位数
                                    if(substring.toLowerCase().contains("decimal")){
                                        int dotCellValue = (int) row.getCell(4).getNumericCellValue();
                                        sb.append(",").append(dotCellValue);

                                    }
                                    sb.append(") ");
                                } else if ("".equals(row.getCell(3).toString().trim())) {
                                    sb.append(s);
                                } else {
                                    sb.append(s + "(" + (int) row.getCell(3).getNumericCellValue() + ")").append(" ");
                                }
                                break;
                            case 5://PK
                                if (("*").equals(cell.toString())) {
                                    primaryKey = row.getCell(1).toString();
                                    primaryKey1 = row.getCell(1).toString();
                                }
                                break;
                            case 6://NOT NULL
                                if (("*").equals(cell.toString())) {
                                    sb.append(" NOT NULL ");
                                } else {
                                    sb.append(" DEFAULT NULL ");
                                }
                                break;
                            default:
                                break;
                        }

                    }

                    sb.append(" COMMENT ").append("'").append(row.getCell(0).toString()).append("' ").append(primaryKey1==null?"":"auto_increment").append(",");
                    primaryKey1=null;
                    sb.append("\n");
                }
            }
            sb.append("PRIMARY KEY (").append(primaryKey).append(")").append(" USING BTREE ");
            sb.append("\n");

            sb.append(") COMMENT='").append(sheet.getRow(0).getCell(2).toString()).append("';");
            sb.append("\n");
            System.out.println(sb);
            sb1 = sb1.append(sb);
        }
            return sb1;
    }

    private static void checkAndCreateSheet(String path) throws Exception{
        if (path == null) {
            return;
        }
//    String excelPath = "E:\\readExcelMaven\\test.xlsx";
        String excelPath = path;
        //String encoding = "GBK";
        File excel = new File(excelPath);
        String[] split = excel.getName().split("\\.");  //.是特殊字符，需要转义！！！！！
        //根据文件后缀（xls/xlsx）进行判断
        if ("xls".equals(split[1])) {
            FileInputStream fis = new FileInputStream(excel);   //文件流对象
            wb = new HSSFWorkbook(fis);
        } else if ("xlsx".equals(split[1])) {
            wb = new XSSFWorkbook(excel);
        } else {
            throw new Exception("文件类型错误!");
        }
    }
}

