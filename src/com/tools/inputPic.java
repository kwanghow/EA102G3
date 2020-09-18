package com.tools;

import java.io.*;
import java.sql.*;

import com.exptype.model.*;
import com.mem.model.*;
import com.pro.controller.ImageUtil;
import com.pro.model.ProDAO;
import com.pro.model.ProVO;
import com.coach.model.*;

public class inputPic {
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G3";
	String passwd = "123456";
	

	private static final String UPDATE_PRO =
			"UPDATE PRODUCT set PRODUCT_LPIC=? , PRODUCT_LPIC1=? , PRODUCT_SPIC=? where PRODUCT_ID=?";
	
	
	public static void updatePRO(ProVO ProVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String userid = "EA102G3";
		String passwd = "123456";
		
		
		try {
					
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_PRO);
			
			pstmt.setBytes(1, ProVO.getProduct_Lpic());
			pstmt.setBytes(2, ProVO.getProduct_Lpic1());
			pstmt.setBytes(3, ProVO.getProduct_Spic());
			pstmt.setString(4, ProVO.getProduct_Id());
			
			pstmt.executeUpdate();
			//pstmt.clearParameters();
					
					
			}catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. "
						+ e.getMessage());
				
			}catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
				
			}finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					}catch (SQLException se) {
						se.printStackTrace(System.err);
					}
				}
				if (con != null) {
					try {
						con.close();
					}catch (Exception e) {
						e.printStackTrace(System.err);
					}
				}
			}
		}
	
	public static void main (String args[]) throws IOException {
		// 修改專長項目圖片
		ExptypeJDBCDAO dao = new ExptypeJDBCDAO();		
		ExptypeVO exptypeVO3 = new ExptypeVO();
		
		exptypeVO3.setExp_Id("S01");
		byte[] pic = getPictureByteArray("WebContent/front-end/assets/images/e01.jpeg");
		exptypeVO3.setExp_Img(pic);
		dao.update(exptypeVO3);
		
		exptypeVO3.setExp_Id("S02");
		byte[] pic1 = getPictureByteArray("WebContent/front-end/assets/images/e02.jpeg");
		exptypeVO3.setExp_Img(pic1);
		dao.update(exptypeVO3);
		
		exptypeVO3.setExp_Id("S04");
		byte[] pic2 = getPictureByteArray("WebContent/front-end/assets/images/e04.jpeg");
		exptypeVO3.setExp_Img(pic2);
		dao.update(exptypeVO3);
		
		exptypeVO3.setExp_Id("S03");
		byte[] pic3 = getPictureByteArray("WebContent/front-end/assets/images/e03.jpg");
		exptypeVO3.setExp_Img(pic3);
		dao.update(exptypeVO3);
		
		exptypeVO3.setExp_Id("S05");
		byte[] pic4 = getPictureByteArray("WebContent/front-end/assets/images/e05.jpg");
		exptypeVO3.setExp_Img(pic4);
		dao.update(exptypeVO3);
		
		exptypeVO3.setExp_Id("S06");
		byte[] pic5 = getPictureByteArray("WebContent/front-end/assets/images/e06.jpg");
		exptypeVO3.setExp_Img(pic5);
		dao.update(exptypeVO3);
		
		exptypeVO3.setExp_Id("S07");
		byte[] pic6 = getPictureByteArray("WebContent/front-end/assets/images/e07.jpg");
		exptypeVO3.setExp_Img(pic6);
		dao.update(exptypeVO3);
		
		exptypeVO3.setExp_Id("S08");
		byte[] pic7 = getPictureByteArray("WebContent/front-end/assets/images/e08.jpg");
		exptypeVO3.setExp_Img(pic7);
		dao.update(exptypeVO3);
		
		exptypeVO3.setExp_Id("S09");
		byte[] pic8 = getPictureByteArray("WebContent/front-end/assets/images/e09.jpg");
		exptypeVO3.setExp_Img(pic8);
		dao.update(exptypeVO3);
		
		exptypeVO3.setExp_Id("S10");
		byte[] pic9 = getPictureByteArray("WebContent/front-end/assets/images/e10.jpg");
		exptypeVO3.setExp_Img(pic9);
		dao.update(exptypeVO3);
		
		// 修改教練圖片
		CoachJDBCDAO coachdao = new CoachJDBCDAO();
		CoachVO coachVO = new CoachVO();
		
		coachVO.setCoach_Id("C01");
		byte[] pic10 = getPictureByteArray("WebContent/front-end/assets/images/s4.jpg");
		coachVO.setCoach_Img(pic10);
		coachdao.updateImg(coachVO);
		
		coachVO.setCoach_Id("C02");
		byte[] pic11 = getPictureByteArray("WebContent/front-end/assets/images/s1.jpg");
		coachVO.setCoach_Img(pic11);
		coachdao.updateImg(coachVO);
		
		coachVO.setCoach_Id("C03");
		byte[] pic12 = getPictureByteArray("WebContent/front-end/assets/images/s2.jpg");
		coachVO.setCoach_Img(pic12);
		coachdao.updateImg(coachVO);
		
		coachVO.setCoach_Id("C04");
		byte[] pic13 = getPictureByteArray("WebContent/front-end/assets/images/s7.jpg");
		coachVO.setCoach_Img(pic13);
		coachdao.updateImg(coachVO);
		
		coachVO.setCoach_Id("C05");
		byte[] pic30 = getPictureByteArray("WebContent/front-end/assets/images/m005.jpg");
		coachVO.setCoach_Img(pic30);
		coachdao.updateImg(coachVO);
		
		// 修改會員圖片
		MemJDBCDAO memdao = new MemJDBCDAO();
		MemVO memVO = new MemVO();
		
		memVO.setMember_Id("M005");
		byte[] pic14 = getPictureByteArray("WebContent/front-end/assets/images/m005.jpg");
		memVO.setMem_Img(pic14);
		memdao.updateImg(memVO);
		
		memVO.setMember_Id("M006");
		byte[] pic15 = getPictureByteArray("WebContent/front-end/assets/images/m006.jpg");
		memVO.setMem_Img(pic15);
		memdao.updateImg(memVO);
		
		memVO.setMember_Id("M007");
		byte[] pic16 = getPictureByteArray("WebContent/front-end/assets/images/m007.jpg");
		memVO.setMem_Img(pic16);
		memdao.updateImg(memVO);
		
		memVO.setMember_Id("M008");
		byte[] pic17 = getPictureByteArray("WebContent/front-end/assets/images/m008.jpg");
		memVO.setMem_Img(pic17);
		memdao.updateImg(memVO);
		
		memVO.setMember_Id("M009");
		byte[] pic18 = getPictureByteArray("WebContent/front-end/assets/images/m009.jpg");
		memVO.setMem_Img(pic18);
		memdao.updateImg(memVO);
		
		memVO.setMember_Id("M010");
		byte[] pic19 = getPictureByteArray("WebContent/front-end/assets/images/m010.jpg");
		memVO.setMem_Img(pic19);
		memdao.updateImg(memVO);
		
		memVO.setMember_Id("M011");
		byte[] pic20 = getPictureByteArray("WebContent/front-end/assets/images/m011.jpg");
		memVO.setMem_Img(pic20);
		memdao.updateImg(memVO);
		
		memVO.setMember_Id("M012");
		byte[] pic21 = getPictureByteArray("WebContent/front-end/assets/images/m012.jpg");
		memVO.setMem_Img(pic21);
		memdao.updateImg(memVO);
		
		memVO.setMember_Id("M013");
		byte[] pic22 = getPictureByteArray("WebContent/front-end/assets/images/m013.jpg");
		memVO.setMem_Img(pic22);
		memdao.updateImg(memVO);
		
		memVO.setMember_Id("M014");
		byte[] pic23 = getPictureByteArray("WebContent/front-end/assets/images/m014.jpg");
		memVO.setMem_Img(pic23);
		memdao.updateImg(memVO);
		
		memVO.setMember_Id("M015");
		byte[] pic24 = getPictureByteArray("WebContent/front-end/assets/images/m015.jpg");
		memVO.setMem_Img(pic24);
		memdao.updateImg(memVO);
		
		memVO.setMember_Id("M016");
		byte[] pic25 = getPictureByteArray("WebContent/front-end/assets/images/m016.jpg");
		memVO.setMem_Img(pic25);
		memdao.updateImg(memVO);
		
		memVO.setMember_Id("M001");
		byte[] pic26 = getPictureByteArray("WebContent/front-end/assets/images/s4.jpg");
		memVO.setMem_Img(pic26);
		memdao.updateImg(memVO);
		
		memVO.setMember_Id("M002");
		byte[] pic27 = getPictureByteArray("WebContent/front-end/assets/images/s1.jpg");
		memVO.setMem_Img(pic27);
		memdao.updateImg(memVO);
		
		memVO.setMember_Id("M003");
		byte[] pic28 = getPictureByteArray("WebContent/front-end/assets/images/s2.jpg");
		memVO.setMem_Img(pic28);
		memdao.updateImg(memVO);
		
		memVO.setMember_Id("M004");
		byte[] pic29 = getPictureByteArray("WebContent/front-end/assets/images/s7.jpg");
		memVO.setMem_Img(pic29);
		memdao.updateImg(memVO);
		
		
//		新增商品圖片
		ProVO ProVO = new ProVO();
//		開始
		//on
		ProVO.setProduct_Id("P001");
		byte[] Lpic1 = getPictureByteArray1("WebContent/front-end/assets/images/pro1.jpg");
		byte[] Lppic1 = getPictureByteArray1("WebContent/front-end/assets/images/pro2.jpg");
		byte[] Spic1 = getPictureByteArray1("WebContent/front-end/assets/images/on.jpg");
		ProVO.setProduct_Lpic(Lpic1);
		ProVO.setProduct_Lpic1(Lppic1);
		ProVO.setProduct_Spic(Spic1);		
		updatePRO(ProVO);
		ProVO.setProduct_Id("P002");
		byte[] Lpic2 = getPictureByteArray1("WebContent/front-end/assets/images/pro3.jpg");
		byte[] Lpic22 = getPictureByteArray1("WebContent/front-end/assets/images/pro4.jpg");
		byte[] Spic2 = getPictureByteArray1("WebContent/front-end/assets/images/on.jpg");
		ProVO.setProduct_Lpic(Lpic2);
		ProVO.setProduct_Lpic1(Lpic22);
		ProVO.setProduct_Spic(Spic2);		
		updatePRO(ProVO);
		ProVO.setProduct_Id("P003");
		byte[] Lpic3 = getPictureByteArray1("WebContent/front-end/assets/images/pro5.jpg");
		byte[] Lpic33 = getPictureByteArray1("WebContent/front-end/assets/images/pro6.jpg");
		byte[] Spic3 = getPictureByteArray1("WebContent/front-end/assets/images/on.jpg");
		ProVO.setProduct_Lpic(Lpic3);
		ProVO.setProduct_Lpic1(Lpic33);
		ProVO.setProduct_Spic(Spic3);		
		updatePRO(ProVO);
	//myprotein
		ProVO.setProduct_Id("P004");
		byte[] Lpic4 = getPictureByteArray1("WebContent/front-end/assets/images/pro7.jpg");
		byte[] Lpic44 = getPictureByteArray1("WebContent/front-end/assets/images/pro8.jpg");
		byte[] Spic4 = getPictureByteArray1("WebContent/front-end/assets/images/myprotein-.jpg");
		ProVO.setProduct_Lpic(Lpic4);
		ProVO.setProduct_Lpic1(Lpic44);
		ProVO.setProduct_Spic(Spic4);		
		updatePRO(ProVO);
		ProVO.setProduct_Id("P005");
		byte[] Lpic5 = getPictureByteArray1("WebContent/front-end/assets/images/pro9.jpg");
		byte[] Lpic55 = getPictureByteArray1("WebContent/front-end/assets/images/pro10.jpg");
		byte[] Spic5 = getPictureByteArray1("WebContent/front-end/assets/images/myprotein-.jpg");
		ProVO.setProduct_Lpic(Lpic5);
		ProVO.setProduct_Lpic1(Lpic55);
		ProVO.setProduct_Spic(Spic5);		
		updatePRO(ProVO);
		ProVO.setProduct_Id("P006");
		byte[] Lpic6 = getPictureByteArray1("WebContent/front-end/assets/images/pro11.jpg");
		byte[] Lpic66 = getPictureByteArray1("WebContent/front-end/assets/images/pro11-1.jpg");
		byte[] Spic6 = getPictureByteArray1("WebContent/front-end/assets/images/pro12.jpg");
		ProVO.setProduct_Lpic(Lpic6);
		ProVO.setProduct_Lpic1(Lpic66);
		ProVO.setProduct_Spic(Spic6);		
		updatePRO(ProVO);
		//運動用品
		ProVO.setProduct_Id("P007");
		byte[] Lpic7 = getPictureByteArray1("WebContent/front-end/assets/images/pro13.jpg");
		byte[] Lpic77 = getPictureByteArray1("WebContent/front-end/assets/images/pro14.jpg");
		byte[] Spic7 = getPictureByteArray1("WebContent/front-end/assets/images/pro15.jpg");
		ProVO.setProduct_Lpic(Lpic7);
		ProVO.setProduct_Lpic1(Lpic77);
		ProVO.setProduct_Spic(Spic7);		
		updatePRO(ProVO);
		ProVO.setProduct_Id("P008");
		byte[] Lpic8 = getPictureByteArray1("WebContent/front-end/assets/images/pro16.jpg");
		byte[] Lpic88 = getPictureByteArray1("WebContent/front-end/assets/images/pro17.jpg");
		byte[] Spic8 = getPictureByteArray1("WebContent/front-end/assets/images/pro18.jpg");
		ProVO.setProduct_Lpic(Lpic8);
		ProVO.setProduct_Lpic1(Lpic88);
		ProVO.setProduct_Spic(Spic8);		
		updatePRO(ProVO);
		ProVO.setProduct_Id("P009");
		byte[] Lpic9 = getPictureByteArray1("WebContent/front-end/assets/images/pro19.jpg");
		byte[] Lpic99 = getPictureByteArray1("WebContent/front-end/assets/images/pro20.jpg");
		byte[] Spic9 = getPictureByteArray1("WebContent/front-end/assets/images/pro21.jpg");
		ProVO.setProduct_Lpic(Lpic9);
		ProVO.setProduct_Lpic1(Lpic99);
		ProVO.setProduct_Spic(Spic9);		
		updatePRO(ProVO);
		ProVO.setProduct_Id("P010");
		byte[] Lpic10 = getPictureByteArray1("WebContent/front-end/assets/images/pro22.jpg");
		byte[] Lppic10 = getPictureByteArray1("WebContent/front-end/assets/images/pro23.jpg");
		byte[] Spic10 = getPictureByteArray1("WebContent/front-end/assets/images/pro24.jpg");
		ProVO.setProduct_Lpic(Lpic10);
		ProVO.setProduct_Lpic1(Lppic10);
		ProVO.setProduct_Spic(Spic10);		
		updatePRO(ProVO);
		ProVO.setProduct_Id("P011");
		byte[] Lpic11 = getPictureByteArray1("WebContent/front-end/assets/images/pro25.jpg");
		byte[] Lppic11 = getPictureByteArray1("WebContent/front-end/assets/images/pro26.jpg");
		byte[] Spic11 = getPictureByteArray1("WebContent/front-end/assets/images/pro27.jpg");
		ProVO.setProduct_Lpic(Lpic11);
		ProVO.setProduct_Lpic1(Lppic11);
		ProVO.setProduct_Spic(Spic11);		
		updatePRO(ProVO);		
		ProVO.setProduct_Id("P012");
		byte[] Lpic12 = getPictureByteArray1("WebContent/front-end/assets/images/pro28.jpeg");
		byte[] Lppic12 = getPictureByteArray1("WebContent/front-end/assets/images/pro29.jpg");
		byte[] Spic12 = getPictureByteArray1("WebContent/front-end/assets/images/pro30.jpg");
		ProVO.setProduct_Lpic(Lpic12);
		ProVO.setProduct_Lpic1(Lppic12);
		ProVO.setProduct_Spic(Spic12);		
		updatePRO(ProVO);
		//健身器材
		ProVO.setProduct_Id("P013");
		byte[] Lpic13 = getPictureByteArray1("WebContent/front-end/assets/images/pro31.jpg");
		byte[] Lppic13 = getPictureByteArray1("WebContent/front-end/assets/images/pro32.jpg");
		byte[] Spic13 = getPictureByteArray1("WebContent/front-end/assets/images/pro33.jpg");
		ProVO.setProduct_Lpic(Lpic13);
		ProVO.setProduct_Lpic1(Lppic13);
		ProVO.setProduct_Spic(Spic13);		
		updatePRO(ProVO);
		ProVO.setProduct_Id("P014");
		byte[] Lpic14 = getPictureByteArray1("WebContent/front-end/assets/images/pro34.jpg");
		byte[] Lppic14 = getPictureByteArray1("WebContent/front-end/assets/images/pro35.jpg");
		byte[] Spic14 = getPictureByteArray1("WebContent/front-end/assets/images/pro36.jpg");
		ProVO.setProduct_Lpic(Lpic14);
		ProVO.setProduct_Lpic1(Lppic14);
		ProVO.setProduct_Spic(Spic14);		
		updatePRO(ProVO);
		ProVO.setProduct_Id("P015");
		byte[] Lpic15 = getPictureByteArray1("WebContent/front-end/assets/images/pro37.jpg");
		byte[] Lppic15 = getPictureByteArray1("WebContent/front-end/assets/images/pro38.jpg");
		byte[] Spic15 = getPictureByteArray1("WebContent/front-end/assets/images/pro39.jpg");
		ProVO.setProduct_Lpic(Lpic15);
		ProVO.setProduct_Lpic1(Lppic15);
		ProVO.setProduct_Spic(Spic15);		
		updatePRO(ProVO);
		ProVO.setProduct_Id("P016");
		byte[] Lpic16 = getPictureByteArray1("WebContent/front-end/assets/images/pro40.jpg");
		byte[] Lppic16 = getPictureByteArray1("WebContent/front-end/assets/images/pro41.jpg");
		byte[] Spic16 = getPictureByteArray1("WebContent/front-end/assets/images/pro42.jpg");
		ProVO.setProduct_Lpic(Lpic16);
		ProVO.setProduct_Lpic1(Lppic16);
		ProVO.setProduct_Spic(Spic16);		
		updatePRO(ProVO);
		//健身食品
				ProVO.setProduct_Id("P017");
				byte[] Lpic17 = getPictureByteArray1("WebContent/front-end/assets/images/pro43.jpg");
				byte[] Lppic17 = getPictureByteArray1("WebContent/front-end/assets/images/pro44.jpg");
				byte[] Spic17 = getPictureByteArray1("WebContent/front-end/assets/images/pro45.jpg");
				ProVO.setProduct_Lpic(Lpic17);
				ProVO.setProduct_Lpic1(Lppic17);
				ProVO.setProduct_Spic(Spic17);		
				updatePRO(ProVO);
				ProVO.setProduct_Id("P018");
				byte[] Lpic18 = getPictureByteArray1("WebContent/front-end/assets/images/pro46.jpg");
				byte[] Lppic18 = getPictureByteArray1("WebContent/front-end/assets/images/pro47.jpg");
				byte[] Spic18 = getPictureByteArray1("WebContent/front-end/assets/images/pro48.jpg");
				ProVO.setProduct_Lpic(Lpic18);
				ProVO.setProduct_Lpic1(Lppic18);
				ProVO.setProduct_Spic(Spic18);		
				updatePRO(ProVO);
				ProVO.setProduct_Id("P019");
				byte[] Lpic19 = getPictureByteArray1("WebContent/front-end/assets/images/pro49.jpg");
				byte[] Lppic19 = getPictureByteArray1("WebContent/front-end/assets/images/pro50.jpg");
				byte[] Spic19 = getPictureByteArray1("WebContent/front-end/assets/images/pro51.jpg");
				ProVO.setProduct_Lpic(Lpic19);
				ProVO.setProduct_Lpic1(Lppic19);
				ProVO.setProduct_Spic(Spic19);		
				updatePRO(ProVO);
				ProVO.setProduct_Id("P020");
				byte[] Lpic20 = getPictureByteArray1("WebContent/front-end/assets/images/pro52.jpg");
				byte[] Lppic20 = getPictureByteArray1("WebContent/front-end/assets/images/pro53.jpg");
				byte[] Spic20 = getPictureByteArray1("WebContent/front-end/assets/images/pro54.jpg");
				ProVO.setProduct_Lpic(Lpic20);
				ProVO.setProduct_Lpic1(Lppic20);
				ProVO.setProduct_Spic(Spic20);		
				updatePRO(ProVO);
		
		
		
		
		System.out.println("所有圖片載入成功!");
		
	}
	
	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		// ByteArray 也是一個低階管, 管子裡面已經有一個預設的Byte的空陣列
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();
	}
	public static byte[] getPictureByteArray1(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		// ByteArray 也是一個低階管, 管子裡面已經有一個預設的Byte的空陣列
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();
		byte[] small = ImageUtil.shrink(baos.toByteArray(), 600);

		return small;
	}

}
