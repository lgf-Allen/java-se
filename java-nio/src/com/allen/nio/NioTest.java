/**
 * 
 */
package com.allen.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @author first
 *
 */
public class NioTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
//		channelToBuffer();
//		bufferToChannel();
		socketChannel();
	}

	private static void channelToBuffer() throws IOException {
		RandomAccessFile file = new RandomAccessFile("nio.txt", "rw");
		FileChannel channel = file.getChannel();// create channel

		ByteBuffer buffer = ByteBuffer.allocate(24);// create buffer

		int byteRead = channel.read(buffer);// channel ->buffer
		while (byteRead != -1) {
			buffer.flip();// make buffer ready for read
			while (buffer.hasRemaining()) {
				System.out.print((char) buffer.get());
			}
			buffer.clear();// make buffer ready for writing
			byteRead = channel.read(buffer);
		}
		file.close();
	}

	private static void mutilpleBufferToChannle() throws IOException {
		RandomAccessFile file = new RandomAccessFile("nio.txt", "rw");
		FileChannel channel = file.getChannel();// create channel
		ByteBuffer header = ByteBuffer.allocate(128);
		ByteBuffer body = ByteBuffer.allocate(1024);
		ByteBuffer[] bufferArray = { header, body };

		channel.read(bufferArray);
		
		Selector selector = Selector.open();
		SocketChannel socket = SocketChannel.open();
		socket.configureBlocking(false);//通道非堵塞
		SelectionKey key = socket.register(selector, SelectionKey.OP_ACCEPT);
		key.attach("");
		Object obj = key.attachment();
		
		InputStream in = new FileInputStream("test.txt");
		
	}
	
	private static void bufferToChannel() throws IOException {
		RandomAccessFile file = new RandomAccessFile("nio.txt", "rw");
		FileChannel channel = file.getChannel();// create channel
		String str = "This is a test file.";
		ByteBuffer buffer = ByteBuffer.allocate(24);
		buffer.clear();
		buffer.put(str.getBytes());//write to buffer
		buffer.flip();
		while(buffer.hasRemaining()) {
			long size = channel.size();
			System.out.println(size);
			channel.write(buffer);
			Long position = channel.position();
			System.out.println(position);
		}
		channel.close();
	}
	
	private static void socketChannel() throws Exception {
		SocketChannel socket = SocketChannel.open();
		socket.connect(new InetSocketAddress("localhost", 8888));
		
		while(!socket.finishConnect()) {
			System.out.println("connect success.");
		}
		socket.close();
	
	}
}
