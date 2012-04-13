package cn.edu.nju.software.liushuai.storage.exception;

public class SeeGodException extends Exception {

	private static final long serialVersionUID = 8276967839550084184L;

	public SeeGodException() {
		super("Hey,God's here!Come to see him!");
	}
	
	public SeeGodException(String string) {
		super(string);
	}
}
