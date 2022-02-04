package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import common.JdbcUtil;

public class BoardDao {
	private JdbcUtil jdbc;
	
	public BoardDao() {
		jdbc = JdbcUtil.getInstance();
	}
	
	//삽입(C)
	public int insert(BoardVo vo) { //boardvo 를 전달받아 db삽업
		Connection con = null;
		PreparedStatement pstmt = null;
		String query = "insert into board(num, title, writer, content, regdate, cnt)\r\n" + "values(board_seq.NEXTVAL,?, ?, ?, sysdate, 0)";
		
		int ret = -1; //문제가 있는 상태면 -1을 주겠다
		try {
			con = jdbc.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getWriter());
			pstmt.setString(3, vo.getContent());
			ret = pstmt.executeUpdate(); // 한 행이 반환되었습니다. 들어옴
			return ret;
		} catch (SQLException e) {
			e.printStackTrace();
			
		}finally {
			if(pstmt != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}
	
	
	//조회(R)
	public List<BoardVo> selectAll(){ //제네릭 클래스 내부에서 지정하는 거 x 외부에서 사용자에 의해 지정되는 것 
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String query ="select num, title, writer, content, regdate, cnt from board order by num desc";
		ArrayList<BoardVo> ls = new ArrayList<BoardVo>();
		try {
			con = jdbc.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				BoardVo vo = new BoardVo(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						new Date(rs.getDate(5).getTime()),
						rs.getInt(6));
				ls.add(vo);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return ls;
			
	}
	
	public BoardVo selectOne(int num){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query ="select num, title, writer, content, regdate, cnt from board where num=?";
		BoardVo vo = null;
		try {
			con = jdbc.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				updateCnt(num); //조회수 증가
				vo = new BoardVo(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						new Date(rs.getDate(5).getTime()),
						rs.getInt(6) +1);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return vo;
			
	}
	
	
	//수정(U)
	public int update(BoardVo vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String query = "update board set title=?, content=? where num=?";
		int ret = -1; //문제가 있는 상태면 -1을 주겠다
		try {
			con = jdbc.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getNum());
			ret = pstmt.executeUpdate(); // 한 행이 반환되었습니다. 들어옴
			return ret;
		} catch (SQLException e) {
			e.printStackTrace();
			
		}finally {
			if(pstmt != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}
	
	public int updateCnt(int num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String query = "update board set cnt=cnt+1 where num=?";
		int ret = -1; //문제가 있는 상태면 -1을 주겠다
		try {
			con = jdbc.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, num);
			ret = pstmt.executeUpdate(); // 한 행이 반환되었습니다. 들어옴
		} catch (SQLException e) {
			e.printStackTrace();
			
		}finally {
			if(pstmt != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}
	
	//삭제(D)
	public int delete(int num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String query = "delete from board where num=?";
		int ret = -1; //문제가 있는 상태면 -1을 주겠다
		try {
			con = jdbc.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, num);
			ret = pstmt.executeUpdate(); // 한 행이 반환되었습니다. 들어옴
		} catch (SQLException e) {
			e.printStackTrace();
			
		}finally {
			if(pstmt != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}
	
	}

