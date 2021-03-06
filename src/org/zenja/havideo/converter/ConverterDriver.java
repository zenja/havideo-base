package org.zenja.havideo.converter;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.zenja.havideo.hdfs.HDFS;

public class ConverterDriver {
	
	private final static String FFMPEG = "/usr/bin/ffmpeg";
	private static String local_src;
	private static String local_dst;
	private static String videoDstPath;
	private static String thumbDstPath;
	
	/**
	 * Start conversion thread
	 */
	public void startConversion(String srcPath, String videoDstPath, String thumbDstPath) {
		ConverterDriver.videoDstPath = videoDstPath;
		ConverterDriver.thumbDstPath = thumbDstPath;
		
		// remove destination file if it exists
		try {
			HDFS.deleteFile(videoDstPath, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// the name of the video
		String video_name = srcPath.substring(srcPath.lastIndexOf("/") + 1,
				srcPath.lastIndexOf("."));

		// local video position after download from HDFS
		local_src = ConverterConfiguration.getConverterLocalDirectory()
				+ srcPath.substring(srcPath.lastIndexOf("/") + 1);
		// local video position after exec
		local_dst = ConverterConfiguration.getConverterLocalDirectory() + video_name;

		/*
		 * test
		 */
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("PROCEDURE:");
		System.out.println(srcPath + " ------> " + local_src);
		System.out.println(local_src + " ------> " + local_dst);
		System.out.println(local_src.substring(0, local_src.lastIndexOf("."))
				+ "_flv_final.flv" + " ------> " + videoDstPath);
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
		copyToLocal(srcPath, local_src);

		SubThread thread = new SubThread(local_src, local_dst);
		thread.start();
//		try {
//			thread.join();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	
	public static class SubThread extends Thread {
		String jarName = ConverterConfiguration.getConverterJarPath();
		String arg1;
		String arg2;

		public SubThread(String src, String dst) {
			arg1 = "file:" + src;
			arg2 = "file:" + dst;
		}

		public void run() {
			Runtime run = Runtime.getRuntime();
			Process p;
			try {
				String command = ConverterConfiguration.getHadoopExecutablePath()
						+ " jar " + jarName + " "
						+ "-D mapred.child.java.opts=-Xmx1024M " + arg1 + " "
						+ arg2;
				System.out.println(command);
				p = run.exec(command);
				doWaitFor(p);
				p.destroy();
				
				System.out.println("after compression ---------------------------------");
				copyFromLocal(local_src.substring(0, local_src.lastIndexOf("."))
						+ "_flv_final.flv", videoDstPath);
				
				// generate thumbnail
				getImage(local_src.substring(0, local_src.lastIndexOf("."))
						+ "_flv_final.flv", thumbDstPath);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private static void getImage(String video_src, String img_dst) {
		String img_local = video_src.substring(0, video_src.lastIndexOf("."))
				+ ".jpg";
		Runtime run = Runtime.getRuntime();
		Process p;
		try {
			String command = FFMPEG + " -i " + video_src
					+ " -y -f image2 -ss 00:00:01 -t 0.001 " + img_local;
			System.out.println(command);
			p = run.exec(command);
			BufferedInputStream in = new BufferedInputStream(p.getInputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			br.readLine();

			p.destroy();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		copyFromLocal(img_local, img_dst);
	}
	
	/* a helper method */
	private void copyToLocal(String src, String dst) {
		Runtime run = Runtime.getRuntime();
		Process p;
		try {
			String command = ConverterConfiguration.getHadoopExecutablePath()
					+ " fs -copyToLocal " + src + " " + dst;
			System.out.println(command);
			p = run.exec(command);
			BufferedInputStream in = new BufferedInputStream(p.getInputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			br.readLine();

			p.destroy();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* a helper method */
	private static void copyFromLocal(String src, String dst) {
		Runtime run = Runtime.getRuntime();
		Process p;
		try {
			String command = ConverterConfiguration.getHadoopExecutablePath()
					+ " fs -copyFromLocal " + src + " " + dst;
			System.out.println(command);
			p = run.exec(command);
			doWaitFor(p);

			p.destroy();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* a helper method */
	public static int doWaitFor(Process p) {
		InputStream in = null;
		InputStream err = null;
		int exitValue = -1; // returned to caller when p is finished
		try {
			System.out.println("comeing");
			in = p.getInputStream();
			err = p.getErrorStream();
			boolean finished = false; // Set to true when p is finished
			while (!finished) {
				try {
					while (in.available() > 0) {
						Character c = new Character((char) in.read());
						// in.read();
						System.out.print(c);
					}
					while (err.available() > 0) {
						Character c = new Character((char) err.read());
						// err.read();
						System.out.print(c);
					}
					exitValue = p.exitValue();
					finished = true;
				} catch (IllegalThreadStateException e) {
					Thread.currentThread();
					Thread.sleep(500);
				}
			}
		} catch (Exception e) {
			System.err.println("doWaitFor();: unexpected exception - "
					+ e.getMessage());
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			if (err != null) {
				try {
					err.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
		return exitValue;
	}
}
