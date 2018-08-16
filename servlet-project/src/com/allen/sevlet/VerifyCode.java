/**
 * 
 */
package com.allen.sevlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Allen
 * 使用servlet生成验证码
 * 页面：verifyCode.html
 */
@WebServlet(value="/code.jpg")
public class VerifyCode extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1133324132746989191L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		int width= 100;
		int height=40;
		Random random = new Random(System.currentTimeMillis());
		//画布
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		//画笔
		Graphics g = image.getGraphics();
		
		//背景生成干扰线
		for(int i = 0;i<50;i++) {
			g.setColor(new Color(25*random.nextInt(10), 25*random.nextInt(10), 25*random.nextInt(10)));//图片背景
			g.fillRect(0, i, width, i);//图片大小
		}
		
		//生成随机验证码
		for(int i = 0;i<4;i++) {
			int num = random.nextInt(9);
			String code = num+"";
			g.setColor(new Color(25*random.nextInt(10), 25*random.nextInt(10), 25*random.nextInt(10)));
			g.setFont(new Font("微软雅黑",Font.PLAIN,random.nextInt(10)+25));
			g.drawString(code, 20+i*15, 30);
		}
		//将生成的验证码输出到页面
		ImageIO.write(image, "jpg", response.getOutputStream());
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
