
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ScheduleMain {
	public static void main(String[] args) throws IOException{
		File inputFile= new File(args[0]);
		File inputFile2= new File(args[1]);
		Scanner input=new Scanner(System.in);
		PrintWriter out = new PrintWriter(new FileWriter(args[2]));
		Scheduling S=new Scheduling();
		S.run(inputFile,inputFile2,out);
		out.close();
	}
}
//
