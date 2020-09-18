
package com.artilove.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.article.model.ArtVO;
import com.artilove.model.*;


import ExceptionHandle.CloseHandle;

public class ArtILoveDAO implements ArtILoveDAO_interface {
	
	
	static String driver = "oracle.jdbc.driver.OracleDriver";
	static String url = "jdbc:oracle:thin:@localhost:1521:XE";
	static String userid = "EA102G3";
	static String passwd = "123456";
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO TB_ILOVE(ARTICLE_NO, MEM_NO) VALUES(?,?)";
	private static final String SELECT_STMT = "SELECT * FROM TB_ILOVE WHERE ARTICLE_NO=? AND MEM_NO = ?";
	private static final String DELETELOVE = "DELETE FROM TB_ILOVE where ARTICLE_NO=? AND MEM_NO = ? ";
	private static final String GET_ALL_STMT = "SELECT * FROM TB_ILOVE";
	private static final String GET_ALL_STMT_BY_MEMNO = "SELECT * FROM TB_ILOVE lov"
			+ " join TB_ARTICLE art ON art.ARTICLE_NO=lov.ARTICLE_NO"
			+ " where MEM_NO=? ";

	CloseHandle close = new CloseHandle();
	
	
	
	@Override
	public void Insert(ArtILoveVO ArtILoveVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			System.out.println("ilove_DAO_INS_start");
			pstmt = con.prepareStatement(INSERT_STMT);
			
				pstmt.setString(1, ArtILoveVO.getArticleNo());
				pstmt.setString(2, ArtILoveVO.getMemNo());
				
				pstmt.executeUpdate();
				System.out.println("�憓���");
//				
			
			
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
	

	@Override
	public void Delete(ArtILoveVO ArtILoveVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETELOVE);

			pstmt.setString(1,ArtILoveVO.getArticleNo());
			pstmt.setString(2,ArtILoveVO.getMemNo());
			System.out.println("DAO love_DELETE");
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			close.close(pstmt);
			close.close(con);
		}
	}

	@Override
	public ArtILoveVO Select(String article_no,String mem_no ) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArtILoveVO ArtILoveVO = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SELECT_STMT);

			pstmt.setString(1,article_no);
			pstmt.setString(2,mem_no);

			rs = pstmt.executeQuery();
			System.out.println("INTO_DAO loveSELECT");
			
			
			if  (rs.next()) {
				ArtILoveVO = new ArtILoveVO();
				ArtILoveVO.setArticleNo(rs.getString("Article_No"));
				ArtILoveVO.setMemNo(rs.getString("Mem_No"));
				
			}
//			

		} catch (SQLException se) {
			se.printStackTrace();
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		System.out.println("daoProSelect蝯��");
		return ArtILoveVO;
		
	}
	

	
public List<ArtILoveVO> getAll() {
	List<ArtILoveVO> list = new ArrayList<ArtILoveVO>();
	ArtILoveVO ArtILoveVO = null;

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	try {

		con = ds.getConnection();
		pstmt = con.prepareStatement(GET_ALL_STMT);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			// empVO 銋迂� Domain objects
			ArtILoveVO = new ArtILoveVO();
			ArtILoveVO.setArticleNo(rs.getString("Article_No"));
			ArtILoveVO.setMemNo(rs.getString("Mem_No"));
			
//			if(!(rs.getInt("Product_Status")==1)) {
				list.add(ArtILoveVO);
//			}
														// 憒������撌脖�銝＊蝷箸��
				 // Store the row in the list
			
		}
		System.out.println("頛����");
		// Handle any driver errors
	} catch (SQLException se) {
		throw new RuntimeException("A database error occured. " + se.getMessage());
		// Clean up JDBC resources
	} finally {
		close.close(rs);
		close.close(pstmt);
		close.close(con);
	}
	return list;
}


public List<ArtILoveVO> getAllByMemNo(String memberId) {
	List<ArtILoveVO> list = new ArrayList<ArtILoveVO>();
	ArtILoveVO ArtILoveVO = null;

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	try {

		con = ds.getConnection();
		pstmt = con.prepareStatement(GET_ALL_STMT_BY_MEMNO);
		pstmt.setString(1, memberId);;
		rs = pstmt.executeQuery();
		while (rs.next()) {
			// empVO 銋迂� Domain objects
			ArtILoveVO = new ArtILoveVO();
			ArtILoveVO.setArticleNo(rs.getString("Article_No"));
			ArtILoveVO.setMemNo(rs.getString("Mem_No"));
			
			ArtVO artVO = new ArtVO();
			artVO.setArticleNo(rs.getString("article_no"));
			artVO.setMemId(rs.getString("mem_id"));
			artVO.setArticleCatNo(rs.getString("article_cat_no"));
			artVO.setArticleTitle(rs.getString("article_Title"));
			artVO.setArticleTitleSub(rs.getString("article_Title_Sub"));
			artVO.setArticleAuthor(rs.getString("article_Author"));
			artVO.setArticleContent(rs.getString("article_Content"));
			artVO.setPostTime(rs.getDate("post_Time"));
			artVO.setArticleReport(rs.getInt("article_Report"));
			
			ArtILoveVO.setArtVO(artVO);
			
//			if(!(rs.getInt("Product_Status")==1)) {
			list.add(ArtILoveVO);
//			}
			// 憒������撌脖�銝＊蝷箸��
		    // Store the row in the list
			
		}
		System.out.println("頛����");
		// Handle any driver errors
	} catch (SQLException se) {
		se.printStackTrace();
		throw new RuntimeException("A database error occured. " + se.getMessage());
		// Clean up JDBC resources
	} finally {
		close.close(rs);
		close.close(pstmt);
		close.close(con);
	}
	return list;
}


public static void main(String[] args) {
	ArtILoveDAO dao = new ArtILoveDAO();

	// �憓�
//	ArtILoveVO ArtILoveVO = new ArtILoveVO();
//	ArtILoveVO.setArticleNo("2");
//	ArtILoveVO.setMemNo("M001");
//	
//	dao.Insert(ArtILoveVO);
//
//////	憿舐內�憓���楊��
//	List<ArtILoveVO> list = dao./getAll();// 頝閰Ｗ���
//	String newestProductNum = null;
//	Iterator<ArtILoveVO> iterator = list.iterator();
//	while(iterator.hasNext()) {
//		newestProductNum = iterator.next().getProduct_Id();
//	}
//	System.out.println("�憓���!!���楊�� : " + newestProductNum);
//	憿舐內�憓���楊���

	// 靽格��
//	Timestamp time = new Timestamp(System.currentTimeMillis());
//	ArtILoveVO ArtILoveVO2 = new ArtILoveVO();
//	ArtILoveVO2.setProduct_Id("P004");
//	ArtILoveVO2.setCategory_Id("C001");
//	ArtILoveVO2.setProduct_Name("��曈�2000");
//	ArtILoveVO2.setProduct_Price(13888);
//	ArtILoveVO2.setProduct_Detail("��8");	
//	ArtILoveVO2.setProduct_Status(0);
//	ArtILoveVO2.setProduct_Stock(0);
//	ArtILoveVO2.setProduct_Brand("甈詨��12");
//	ArtILoveVO2.setProduct_Hot(1);
//	dao.ProUpdate(ArtILoveVO2);
//	System.out.println("靽格����");
//
	// ��(靽格�����)
//	ArtILoveVO productVO3 = new ArtILoveVO();
//	productVO3.setArticleNo("1");
//	productVO3.setMemNo("M001");
//	System.out.println(productVO3.getArticleNo() + "撌脖�");
//	dao.Delete(productVO3);
////	System.out.println("銝����");
////	// �閰�
//	ArtILoveVO productVO5 = new ArtILoveVO();
//	productVO5.setArticleNo("1");
//	productVO5.setMemNo("M001");
//	ArtILoveVO ProductVO4 = dao.Select(productVO5);
//	System.out.println("--------------------------------");
//	System.out.println("���楊��� : " + "\t" + ProductVO4.getArticleNo());
//	System.out.println("����迂 : " + "\t" + ProductVO4.getMemNo());
//	System.out.println("憿蝺刻�� : " + "\t" + ProductVO4.getCategory_Id());
//	System.out.println("�� : " + "\t" + ProductVO4.getProduct_Price());
//	System.out.println("摨怠�� : " +"\t" + ProductVO4.getProduct_Stock());
//	System.out.println("���底��� : " + "\t" + ProductVO4.getProduct_Detail());
//	System.out.println("���� : " + "\t" + ProductVO4.getProduct_Lpic());
//	System.out.println("���� : " + "\t" + ProductVO4.getProduct_Lpic1());
//	System.out.println("蝮桀�� : " + "\t" + ProductVO4.getProduct_Spic());
////	System.out.println("蝮質��� : " +"\t" + ProductVO4.getP_rating());
////	System.out.println("閰鈭箸 : " + "\t" + ProductVO4.getNumber_of_rating());
//	System.out.println("����� : " + "\t" + ProductVO4.getProduct_Status());
//
//
////	// �閰Ｗ�
//
////		System.out.println("List撱箇����");
//
//	for (ArtILoveVO aProduct : list) {
//		System.out.println("--------------------------------");
//		System.out.println("���楊��� : " + "\t" + aProduct.getArticleNo());
//		System.out.println("����迂 : " + "\t" + aProduct.getMemNo());
////		System.out.println("憿蝺刻�� : " + "\t" + aProduct.getCategory_Id());
//		System.out.println("�� : " + "\t" + aProduct.getProduct_Price());
//		System.out.println("摨怠�� : " +"\t" + aProduct.getProduct_Stock());
//		System.out.println("���底��� : " + "\t" + aProduct.getProduct_Detail());
//		System.out.println("���� : " + "\t" + aProduct.getProduct_Lpic());
//		System.out.println("���� : " + "\t" + aProduct.getProduct_Lpic1());
//		System.out.println("蝮桀�� : " + "\t" + aProduct.getProduct_Spic());
////		System.out.println("蝮質��� : " +"\t" + aProduct.getP_rating());
////		System.out.println("閰鈭箸 : " + "\t" + aProduct.getNumber_of_rating());
//		System.out.println("����� : " + "\t" + aProduct.getProduct_Status());
//
//	}
//	System.out.println("--------------------------------");
//	System.out.println("憿舐內����");
//}
//}
}


@Override
public ArtILoveVO findByPrimaryKey(String articleNo) {
	// TODO Auto-generated method stub
	return null;
}
}




