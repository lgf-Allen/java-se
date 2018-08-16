/**
 * 
 */
package com.allen.sevlet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * @author Allen
 * @version v1.0
 */

public class ConsoleTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		//console1();
		console2();
		//console3();
	}
	
	/**
	 * 使用标准输入串System.in
	 * @throws Exception
	 */ 
	protected static void console1() throws Exception {
		
		//System.in.read();一次只读入一个字节
		System.out.println("输入数据：");
		char read = (char) System.in.read();
		System.out.println("输入数据："+read);
	}
	
	/**
	 * 使用Scanner取得一个字符串或一组数字
	 * @throws Exception
	 */
	protected static void console2() throws Exception {
		
		System.out.println("输入数据：");
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String data = sc.nextLine();
		System.out.println("输入数据："+data);
	}
	
	/**
	 * 使用BufferedReader取得含空格的输入
	 * @throws Exception
	 */
	protected static void console3() throws Exception {
		//取得页面的输入流
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		String read = null;
		System.out.print("输入数据：");
		read = bf.readLine();
		System.out.println("输入数据："+read); 
	}
}
