package com.hyt.service;

import java.util.List;

import com.google.gson.Gson;
import com.hyt.dao.CategoryDao;
import com.hyt.dao.ProductDao;
import com.hyt.domain.Category;
import com.hyt.utils.JedisUtils;

import redis.clients.jedis.Jedis;

public class CategoryService {

	CategoryDao categoryDao = new CategoryDao();

	public List<Category> findAll() {
		// TODO Auto-generated method stub
		return categoryDao.findAll();
	}

	public Category findById(String cid) {
		// TODO Auto-generated method stub
		return categoryDao.findById(cid);
	}

	public void updateCategory(Category category) {

		categoryDao.updateCategory(category);
	}

	public void deleteCategoryById(String cid) {

		categoryDao.deleteCategoryById(cid);
	}

	public void addCategory(Category category) {

		categoryDao.addCategory(category);
	}

//	返回所有分类的ajax请求
	public String findAllByAjax() {
//		以上是每一次都通过访问数据库获得分类 以下是通过redis缓存处理		

		Jedis jedis = null;
		String value = null;
		Gson gson = new Gson();
		try {
//		从redis获取分类信息
//		1.获取连接
			jedis = JedisUtils.getJedis();
//		获取数据 判断数据是否为空
			value = jedis.get("category_list");

//		若不为空，直接返回数据
			if (value != null) {
//				System.out.println("缓存中有数据！");
				return value;
			}
//		若为空，从mysql数据库中获取，并放入redis中
			List<Category> allCategory = findAll();
//		将allCategory转成json返回并放入redis中
			value = gson.toJson(allCategory).toString();
			jedis.set("category_list", value);
			return value;

		} finally {
			JedisUtils.close(jedis);
		}
	}

}
