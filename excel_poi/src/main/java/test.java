import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class test {
    public static void main(String[] args) throws Exception {
        String path1  = System.getProperty("param");
        String path  ="";
        path = "D:\\360MoveData\\Users\\linkage\\Desktop\\MyBat\\excel\\"+"baio.xlsx";
        StringBuilder label = ExcelUtil.createLabel(path);
        StringBuilder table = ExcelUtil.createTable(path);
        String filePath = "D:\\360MoveData\\Users\\linkage\\Desktop\\MyBat\\excel_2_creatSql.txt";
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
