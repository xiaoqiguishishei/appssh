package com.huike.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.tools.Tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huike.bean.Classes;
import com.huike.service.ClassesService;
import com.huike.util.Tools;
import com.huike.web.formbean.PageBean;

@Controller
@RequestMapping("/admin")
public class ClassesController {

	@Autowired
	private ClassesService cs;

	// 查询所有的班级信息
	@RequestMapping("/findAll")
	public String findAll(HttpServletRequest request) {
		System.out.println("findAll");
		List<Classes> list = cs.findAll();

		// 将集合存放到请求对象中
		request.setAttribute("list", list);

		return "admin/main";
	}

	// 修改班级状态
	@RequestMapping("/modifyCourseStatus")
	public void modifyCourseStatus(Classes classes,
			HttpServletResponse response, HttpServletRequest req) {
		System.out.println(req.getCharacterEncoding());

		try {
			String status = new String(classes.getCourseStatus().getBytes(
					"iso8859-1"), "utf-8");
			classes.setCourseStatus(status);

			boolean flag = cs.modify(classes);

			Map<String, Object> map = new HashMap<String, Object>();

			if (flag)
				map.put("info", "恭喜你，修改状态成功");
			else
				map.put("info", "修改失败");

			ObjectMapper mapper = new ObjectMapper();

			String info = mapper.writeValueAsString(map);

			// 将数据发送到客户端
			response.getWriter().print(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 查询某页的信息
	@RequestMapping("/page")
	public String page(PageBean<Classes> pageBean, HttpServletRequest request) {

		PageBean<Classes> page = cs.getPageBean(pageBean.getCurrentPageIndex(),
				pageBean.getPageCount());

		request.getSession().setAttribute("page", page);

		return "admin/main";
	}

	// 搜索
	@RequestMapping("/searchClasses")
	public String searchClassesByCondition(Classes classes, String startTime,HttpServletRequest req)
			throws ParseException {
		
		System.out.println(startTime);
		System.out.println(classes);
		// 处理乱码
		classes.setClassName(Tools.getUTFEncoding(classes.getClassName()));
		classes.setCourseStatus(Tools.getUTFEncoding(classes.getCourseStatus()));
		classes.getTeacher().setTeacherName(
				Tools.getUTFEncoding(classes.getTeacher().getTeacherName()));
		classes.getClassType().setType(
				Tools.getUTFEncoding(classes.getClassType().getType()));
		classes.getLecturer().setLecturerName(
				Tools.getUTFEncoding(classes.getLecturer().getLecturerName()));

		//classes.setStartTime(new SimpleDateFormat("yyyy-MM-dd")
			//	.parse(startTime));
		
		System.out.println(classes);
		
		//将班级的名字改为模糊表达式
		classes.setClassName("%" + classes.getClassName() + "%") ;
		
		PageBean<Classes> page = cs.getPageBean(classes,1,5);  //默认第一页

		req.getSession().setAttribute("page", page);

		return "admin/main";
	}

}
