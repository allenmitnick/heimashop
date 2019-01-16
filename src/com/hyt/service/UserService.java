package com.hyt.service;

import com.hyt.dao.UserDAO;
import com.hyt.domain.User;
import com.hyt.utils.MailUtils;

public class UserService {

	UserDAO userDAO = new UserDAO();
//	判断用户是否存在
	public boolean isExist(String username) {
		if(userDAO.findByUsername(username) instanceof User) {
			return true;
		}

		return false;
	}

//注册 添加用户
	public void addUser(User user) {
//		1.保存用户
		userDAO.addUser(user);
//		2.发送邮件
		MailUtils.sendMail(user.getEmail(), user.getCode());
	}

//	激活用户
	public void activeUser(String code) {
//		通过激活码查询用户
		User existUser = userDAO.findByCode(code);
		if(existUser == null) {
			//自定义异常
			throw new RuntimeException("用户激活无效，请重新发送激活邮件");
		}
//		更新用户信息
		existUser.setState(1);
		existUser.setCode(null);
		userDAO.Update(existUser);
		
	}

	public User login(User user) {
		
		return userDAO.findByNamePwd(user.getUsername(),user.getPassword());
	}

}
