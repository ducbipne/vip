/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import helper.jdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Entity.nguoiHoc;

/**
 *
 * @author Sieu Nhan Bay
 */
public class nguoiHocDAO {
    private nguoiHoc readFromResultSet(ResultSet rs) throws SQLException{
	nguoiHoc entity=new nguoiHoc();
         entity.setMaNH(rs.getString("MaNH"));
         entity.setHoTen(rs.getString("HoTen"));
         entity.setNgaySinh(rs.getDate("NgaySinh"));
         entity.setGioiTinh(rs.getBoolean("GioiTinh"));
         entity.setDienThoai(rs.getString("DienThoai"));
         entity.setEmail(rs.getString("Email"));
         entity.setGhiChu(rs.getString("GhiChu"));
         entity.setMaNV(rs.getString("MaNV"));
         entity.setNgayDK(rs.getDate("NgayDK"));
         return entity;
    }

    private List<nguoiHoc> select(String sql, Object...args){
        List<nguoiHoc> list=new ArrayList<>();
        try {
            ResultSet rs=null;
            try{
                rs=jdbcHelper.executeQuery(sql, args);
                while(rs.next()){
                    list.add(readFromResultSet(rs));
                }
            }finally{
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException();
        }
        return list;
    }
 
  
    public void insert(nguoiHoc entity) {
        String sql="INSERT INTO NGUOIHOC (MANH, HOTEN, NGAYSINH, GIOITINH, DIENTHOAI, EMAIL, GHICHU, MANV) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
         jdbcHelper.executeUpdate(sql,
             entity.getMaNH(),
             entity.getHoTen(),
             entity.getNgaySinh(),
             entity.isGioiTinh(),
             entity.getDienThoai(),
             entity.getEmail(),
             entity.getGhiChu(),
             entity.getMaNV());
    }

    public void update(nguoiHoc model) {
        String sql="UPDATE NGUOIHOC SET HOTEN=?, NGAYSINH=?, GIOITINH=?, DIENTHOAI=?, EMAIL=?, GHICHU=?,MANV=? WHERE MANH=?";
        jdbcHelper.executeUpdate(sql,            
                 model.getHoTen(),
                 model.getNgaySinh(),
                 model.isGioiTinh(),
                 model.getDienThoai(),
                 model.getEmail(),
                 model.getGhiChu(),
                 model.getMaNV(),
                 model.getMaNH());
    }

 
    public void delete(String id) {
        String sql="DELETE FROM NGUOIHOC WHERE MANH=?";
        jdbcHelper.executeUpdate(sql, id);
    }

    public List<nguoiHoc> select() {
        String sql="SELECT * FROM NGUOIHOC";
        return select(sql);
    }

    public List<nguoiHoc> selectByKeyword(String keyword) {
        String sql="SELECT * FROM NGUOIHOC WHERE HOTEN LIKE ?";
        return select(sql, "%"+keyword+"%");
    }
    

     public List<nguoiHoc> selectByCourse(Integer makh){   
     String sql="SELECT * FROM NGUOIHOC WHERE MANH NOT IN (SELECT MANH FROM HOCVIEN WHERE MAKH=?)";
     return select(sql, makh);
     }
     

     public nguoiHoc findById(String manh){
     String sql="SELECT * FROM NGUOIHOC WHERE MANH=?";
     List<nguoiHoc> list = select(sql, manh);
     return list.size() > 0 ? list.get(0) : null;
     }
}
