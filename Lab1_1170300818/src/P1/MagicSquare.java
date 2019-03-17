package P1;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;

public class MagicSquare {	
public static void main(String[] args) throws IOException {
	 MagicSquare t =new  MagicSquare();
	 boolean answer;
	 for(int i=1;i<=5;i++) {
		 String fileName="src/P1/txt/"+String.valueOf(i)+".txt";
		 answer=t.isLegalMagicSquare(fileName);
		 System.out.println(answer);
	 }
	int n=5;
	boolean a =t.generateMagicSquare(n);
	System.out.println("The result of the sixth is "+a);
}

public boolean isLegalMagicSquare(String fileName) throws IOException {

	 File file = new File(fileName);
	 int sumlength=0;
     BufferedReader Du = null;
     int length=0,number=0;
     ArrayList<String[]> list= new ArrayList();
     try {
         Du = new BufferedReader(new FileReader(file));
         String tempString = null;
         while ((tempString = Du.readLine()) != null) {
             String[] a= tempString.split("\t");
             sumlength=a.length;
             length+=a.length;
             number++;
             if(length!=sumlength*number) {
            	 return false;
             }
             list.add(a);
         }
         Du.close();
         if(number!=sumlength) {
        	 return false;
         }
         String[][] test = (String [][])list.toArray(new String[0][0]);
         int[][] retest = new int [sumlength][sumlength];
         for(int i=0;i<sumlength;i++) {
        	 for(int j=0;j<sumlength;j++) {
        			if(test[i][j].indexOf(".")>-1) {
        				System.out.println("Ð¡Êý");
        				return false;
        			}
        			retest[i][j]=Integer.parseInt(test[i][j]);
        			if(retest[i][j]<=0) {
        				return false;
        			}
        	 }
         }
         int initsum=0,sum=0;
         for(int i=0;i<sumlength;i++) {
        	 initsum+=retest[i][i];
         }
         for(int i=0;i<sumlength;i++) {
        	 	 sum+=retest[i][sumlength-1-i];
         }
         if(sum!=initsum) {
			 return false;
		 }
         sum=0;
         for(int i=0;i<sumlength;i++) {
        	 for(int j=0;j<sumlength;j++) {
        		 sum+=retest[i][j];
        	 }
        	 if(sum!=initsum*(i+1)) {
    			 return false;
    		 }
         }
         sum=0;
         for(int i=0;i<sumlength;i++) {
        	 for(int j=0;j<sumlength;j++) {
        		 sum+=retest[j][i];
        	 }
        	 if(sum!=initsum*(i+1)) {
    			 return false;
    		 }
         }
     } catch (IOException e) {
         e.printStackTrace();
     }
     return true;
}


public static boolean generateMagicSquare(int n) throws EOFException{
	try{
		int magic[][] = new int[n][n];
		 int row = 0,col =n/2,i,j,square = n*n;
		 for(i=1;i<=square;i++) {
			 magic[row][col]=i;
			 if(i%n==0)
				 row++;
			 else {
				 if(row==0)
					 row = n-1;
				 else 
					 row--;
				 if(col==(n-1))
					 col=0;
				 else
					 col++;
			 }
		 }   
		 for(i=0;i<n;i++) {
			 for(j=0;j<n;j++)
				 System.out.print(magic[i][j]+"\t");
			 System.out.println();
		 }
		 FileWriter fw = null;
		 String fileName="src/P1/txt/"+String.valueOf(6)+".txt";
	  		try {
	  		File f=new File(fileName);
	  		fw = new FileWriter(f, true);
	  		} catch (IOException e) {
	  		e.printStackTrace();
	  		}
	  		PrintWriter pw = new PrintWriter(fw);
	  		for(i=0;i<n;i++) {
				 for(j=0;j<n;j++) {
					 pw.print(String.format("%d\t",magic[i][j]));
				 }
				 pw.println(String.format("")); 
			 }
	  		pw.flush();
	  		try {
	  		fw.flush();
	  		pw.close();
	  		fw.close();
	  		} catch (IOException e) {
	  		e.printStackTrace();
	  		}
	  		MagicSquare t =new  MagicSquare();
	  		boolean answer=t.isLegalMagicSquare(fileName);
	}catch(Exception e) {
		System.out.println("´íÎó");
		return false;
	}
	return true;
}
}