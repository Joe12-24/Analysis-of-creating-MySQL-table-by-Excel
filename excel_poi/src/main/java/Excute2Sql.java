import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @Author Ashe
 * @Date 2021/1/21 19:18
 * @Version 1.0
 */
public class Excute2Sql {
    public static void main(String[] args) throws Exception {
        //从bat脚本获取输入参数 , 参数为excel文件名
        String path1  = System.getProperty("param");
        //本地测试   excel解析创建sql语句.xlsx
        String path2  ="excel解析创建sql语句.xlsx";

        String  path = "D:\\360MoveData\\Users\\linkage\\Desktop\\MyBat\\excel\\"+path1;
        StringBuilder label = ExcelUtil.createLabel(path);
        StringBuilder table = ExcelUtil.createTable(path);
        String filePath = "D:\\360MoveData\\Users\\linkage\\Desktop\\MyBat\\excel_2_creatSql.txt";
        // 把文本设置到剪贴板（复制）
        ClipBoard.setClipboardString(table.toString());
        // sql写入文本text中
        write2Text(filePath,table);
    }
    /**
     * 把生成的sql写入文本中
     * */
    public static void write2Text(String filePath, StringBuilder table) throws IOException {
        File dir = new File(filePath);
        FileWriter writer = null;

        try {
            // 二、检查目标文件是否存在，不存在则创建
            if (!dir.exists()) {
                dir.createNewFile();// 创建目标文件
            }
            // 三、向目标文件中写入内容
            // FileWriter(File file, boolean append)，append为true时为追加模式，false或缺省则为覆盖模式


            writer = new FileWriter(dir, true);
            writer/*.append(label).append("\n\n")*/.append(table);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                writer.close();
            }
        }
    }
}
