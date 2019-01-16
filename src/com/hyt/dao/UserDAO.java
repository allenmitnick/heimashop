package com.hyt.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.hyt.domain.User;
import com.hyt.utils.DataSourceUtils;

public class UserDAO {

	
	public User findByUsername(String username) {
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM user WHERE username =?";
		Object[] params = {username};
		try {
			return qr.query(sql, new BeanHandler<User>(User.class), params);
		} catch (Exception e) {
			
			throw new RuntimeException();
		}
		
	}

	public void addUser(User user) {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "INSERT INTO user VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {user.getUid(),user.getUsername(),user.getPassword(),
				user.getName(),user.getEmail(),user.getTelephone(),user.getBirthday(),
				user.getSex(),user.getState(),user.getCode(),user.getAddress()};
		try {
			qr.update(sql, params);
//			System.out.println("insert ok");
		} catch (Exception e) {
			
			throw new RuntimeException();
		}
	}

	public User findByCode(String code) {

		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM user WHERE code =?";
		Object[] params = {code};
		try {
			return qr.query(sql, new BeanHandler<User>(User.class), params);
		} catch (Exception e) {
			
			throw new RuntimeException();
		}
	}

	public void Update(User existUser) {
		
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "UPDATE user SET state=?, code=? WHERE uid = ?";
		Object[] params = {existUser.getState(), existUser.getCode(), existUser.getUid()};
		try {
			
			qr.update(sql, params);
//			System.out.println("update ok");
		} catch (Exception e) {
			
			throw new RuntimeException();
		}
		
	}

	public User findByNamePwd(String username, String password) {
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM user WHERE username =? and password =?";
		Object[] params = {username,password};
		try {
			return qr.query(sql, new BeanHandler<User>(User.class), params);
		} catch (Exception e) {
			
			throw new RuntimeException();
		}
	}

	

}
