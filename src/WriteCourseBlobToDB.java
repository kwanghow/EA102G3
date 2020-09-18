// 匯入假資料課程圖片用
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WriteCourseBlobToDB {	
	
	public static final String PATH = "C:\\Users\\lupopo86\\Pictures\\CoursePicture\\資料庫圖片\\";
	
	public static void main(String[] args) {
		Connection con = null;		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "EA102G3", "123456");
		
			File file = new File(PATH);
			File img;
			String course_id, picIndex, sql;
			
			String[] filelist = file.list();
			for(int i=0; i<filelist.length; i++) {
				if(filelist[i].startsWith("c")) {
					course_id = "C200" + filelist[i].substring(1,3);
					picIndex = "1";
				}else {
					course_id = "C2000" + filelist[i].substring(0,1);
					picIndex = filelist[i].substring(2,3);					
				}
				sql = "UPDATE COURSE SET PIC" + picIndex + " = ? WHERE COURSE_ID = '" + course_id + "'";
				img = new File(PATH, filelist[i]);
				
				InputStream is = new FileInputStream(img);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				int len;
				while((len = is.read()) != -1) {
					baos.write(len);
				}
				byte[] pic = baos.toByteArray();			
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setBytes(1, pic);
				pstmt.executeUpdate();
				pstmt.clearParameters();
				
				baos.close();
				is.close();	
			}
			System.out.println("Jessica圖片匯入資料庫成功");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}		
	}

}
