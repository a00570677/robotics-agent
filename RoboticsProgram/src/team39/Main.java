package team39;

import team39.challenges.*;
import team39.technical.Explorer;

public class Main {
	static Challenge1 challenge1;
	static Challenge2 challenge2;
	static Challenge3 challenge3 ;
	static Explorer r;
	
	public static void main(String[] args) {
		challenge1 = new Challenge1();
		challenge1.run();
		//challenge2 = new Challenge2();
		//challenge2.run();
		//challenge3 = new Challenge3();
		//challenge3.run();
	}
}
