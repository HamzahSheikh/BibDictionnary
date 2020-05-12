import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;

/** 
* 
* @author Hamzah Sheikh, 40103993
*
*/
public class BibCreator 
{
	private static int count = 0;
	
	/** 
	 * parameterized constructor 
	 * @param x denotes the open Scanner already created for each Latex file
	 * @param j denotes the count of the file
	 * @param a denoted the IEEE file created and to which we will add the data
	 * @param b denoted the ACM file created and to which we will add the data
	 * @param c denoted the NJ file created and to which we will add the data
	 */
	public static void processFilesForValidation(Scanner x,int j, File a,File b,File c) 
	{	
		int countForFile = 1;
		String[] WHATINFO = new String[11];
		String[] INFO = new String[11];
		/** 
		 * try block used to catch any FileInvalidExceptions during execution of the code
		 */
		try
		{
			/** 
			 * while loop which will keep looping until there is no more lines in a given file.
			 */
			while(x.hasNextLine()) 
			{
				/** 
				 * Setting a delimiter to make it easier to access certain data
				 */
				x.useDelimiter("},");
				
				/** 
				 * StringTokenizer will seperate further the data 
				 */
				StringTokenizer DataExtractor = new StringTokenizer(x.next(), "={");
				countForFile++;
				
				/** 
				 * Will check if there is more elements to avoid any exceptions.
				 * Used to skip the "@Author" and number lines of data 
				 */
				if(DataExtractor.hasMoreElements())
					DataExtractor.nextElement();
				else
					break;
				
				/** 
				 * Will check if there is more elements to avoid any exceptions, will also examine and save what the first type of
				 * data is entered in the first ={} 
				 */
				if(DataExtractor.hasMoreElements())
				{
					String t = DataExtractor.nextToken().trim().replaceAll("(?m)^[ \t]*\r?\n", "");;
					
					StringTokenizer author = new StringTokenizer(t,"\n");
					author.nextToken();
					WHATINFO[0] = author.nextToken();
				}
				else
					break;
				
				/** 
				 * Will check if there is infact data added for this line of information by counting how many tokens are in it.
				 * It will throw an exception if there is indeed no data entered
				 */
				if(DataExtractor.countTokens() == 1)
					INFO[0] = DataExtractor.nextToken();
				else
					throw new FileInvalidException("ERROR DETECTED:\nCould not open file: Latex"+j+".bib due to file being invalid!\n"
							+ "The "+WHATINFO[0].trim().toUpperCase()+" field of article "+(countForFile-1)+" is empty!! Processing was stopped at this point..."
							+ "\nOther fields in this file might still be empty.");
				
				
				/** 
				 * Will loop for the next lines of data and repeat the same steps as taken to reccord the first line of data
				 * The only difference is that there is no "@Author" and number lines neede to be skipped
				 */
				for(int w = 1; w<11; w++) 
				{
					DataExtractor = new StringTokenizer(x.next(), "={");
					
					WHATINFO[w] = (String) DataExtractor.nextElement();
					
					if(DataExtractor.countTokens() == 1)
						INFO[w] = DataExtractor.nextToken();
					else
						throw new FileInvalidException("ERROR DETECTED:\nCould not open file: Latex"+j+".bib due to file being invalid!\n"
								+ "The "+WHATINFO[w].trim().toUpperCase()+" field of article "+(countForFile-1)+" is empty!! Processing was stopped at this point..."
								+ "\nOther fields in this file might still be empty.");	
				}
			
				PrintWriter PW = null; 
				/**
				*IEEE Format-------------------------------------------------------------------------
				*/
				try {
					PW = new PrintWriter(new FileOutputStream(a,true));
				} catch (FileNotFoundException e) {
					System.out.print("File was not Found...");
					System.exit(0);
				}
				

				/**
				 * Array test contains the individual names of the authors
				 */
				String[] test = null; 
				for(int o = 0; o <= 10; o++)
				{
					if(WHATINFO[o].trim().equalsIgnoreCase("author"))
					{
						test = INFO[o].split(" and");
					}
				}
				
				/**
				 * Print gathered data in a specific fashion for IEEE by looping until the specific
				 * piece of data is found.
				 */
				for (int i1 = 0; i1<test.length;i1++)
				{
					PW.print(test[i1]);
					
					if(i1<test.length-1) 
					{
						PW.print(",");
					}
					else
					{
						PW.print(". ");
					}
			
				}

				for(int o = 0; o <= 10; o++)
				{
					if(WHATINFO[o].trim().equalsIgnoreCase("title"))
					{
						PW.print("\""+INFO[o]+"\", ");
					}
				}
				
				for(int o = 0; o <= 10; o++)
				{
					if(WHATINFO[o].trim().equalsIgnoreCase("journal"))
					{
						PW.print(INFO[o]+", vol. ");
					}
				}
				
				for(int o = 0; o <= 10; o++)
				{
					if(WHATINFO[o].trim().equalsIgnoreCase("volume"))
					{
						PW.print(INFO[o]+", no. ");
					}
				}
				
				for(int o = 0; o <= 10; o++)
				{
					if(WHATINFO[o].trim().equalsIgnoreCase("number"))
					{
						PW.print(INFO[o]+", p. ");
					}
				}
				for(int o = 0; o <= 10; o++)
				{
					if(WHATINFO[o].trim().equalsIgnoreCase("pages"))
					{
						PW.print(INFO[o]+", ");
					}
				}
				for(int o = 0; o <= 10; o++)
				{
					if(WHATINFO[o].trim().equalsIgnoreCase("month"))
					{
						PW.print(INFO[o]+" ");
					}
				}
				for(int o = 0; o <= 10; o++)
				{
					if(WHATINFO[o].trim().equalsIgnoreCase("year"))
					{
						PW.println(INFO[o]+".\n");
						PW.close();
					}
				}
				
				/**
				*ACM Format-------------------------------------------------------------------------
				*/
				try {
					PW = new PrintWriter(new FileOutputStream(b, true));
				} catch (FileNotFoundException e) {
					System.out.print("File was not Found...");
					System.exit(0);
				}	
				
				/**
				 * Prints data to a file in ACM format
				 */
				for(int o = 0; o <= 10; o++)
				{
					if(WHATINFO[o].trim().equalsIgnoreCase("author")) {
						PW.print("["+(countForFile-1)+"]\t"+test[o]+" et al. ");
					}
				}
				
				for(int o = 0; o <= 10; o++)
				{
					if(WHATINFO[o].trim().equalsIgnoreCase("year"))
					{
						PW.print(INFO[o]+". ");
					}
				}
				
				for(int o = 0; o <= 10; o++)
				{
					if(WHATINFO[o].trim().equalsIgnoreCase("title"))
					{
						PW.print(INFO[o]+". ");
					}
				}
				
				for(int o = 0; o <= 10; o++)
				{
					if(WHATINFO[o].trim().equalsIgnoreCase("journal"))
					{
						PW.print(INFO[o]+". ");
					}
				}
				
				for(int o = 0; o <= 10; o++)
				{
					if(WHATINFO[o].trim().equalsIgnoreCase("volume"))
					{
						PW.print(INFO[o]+", ");
					}
				}
				
				for(int o = 0; o <= 10; o++)
				{
					if(WHATINFO[o].trim().equalsIgnoreCase("number"))
					{
						PW.print(INFO[o]+" (");
					}
				}
				
				for(int o = 0; o <= 10; o++)
				{
					if(WHATINFO[o].trim().equalsIgnoreCase("year"))
					{
						PW.print(INFO[o]+"), ");
					}
				}
				
				for(int o = 0; o <= 10; o++)
				{
					if(WHATINFO[o].trim().equalsIgnoreCase("pages"))
					{
						PW.print(INFO[o]+".");
					}
				}
				
				for(int o = 0; o <= 10; o++)
				{
					if(WHATINFO[o].trim().equalsIgnoreCase("DOI"))
					{
						PW.println(" DOI:https://doi.org/"+INFO[o]+".\n");
						PW.close();
					}
				}
			
				try {
					PW = new PrintWriter(new FileOutputStream(c, true));
				} catch (FileNotFoundException e) {
					System.out.print("File was not Found...");
					System.exit(0);
				}
				
				/**
				 * Prints data to a file in NJ format
				 */
				for (int i1 = 0; i1<test.length;i1++)
				{
					PW.print(test[i1]);
					if(i1<test.length-1) 
					{
						PW.print(" &");
					}
					else 
					{
						PW.print(". ");
					}
				}
				
				for(int o = 0; o <= 10; o++)
				{
					if(WHATINFO[o].trim().equalsIgnoreCase("title"))
						PW.print(INFO[o]+". ");
				}
				for(int o = 0; o <= 10; o++)
				{
					if(WHATINFO[o].trim().equalsIgnoreCase("journal"))
						PW.print(INFO[o]+". ");
				}
				for(int o = 0; o <= 10; o++)
				{
					if(WHATINFO[o].trim().equalsIgnoreCase("volume"))
						PW.print(INFO[o]+", ");
				}
				
				for(int o = 0; o <= 10; o++)
				{
					if(WHATINFO[o].trim().equalsIgnoreCase("pages"))
						PW.print(INFO[o]);
				}
			
				for(int o = 0; o <= 10; o++)
				{
					if(WHATINFO[o].trim().equalsIgnoreCase("year")) 
					{
						PW.println("("+INFO[o]+").\n");
						PW.close();
					}
						
				}
			}
		}
		/**
		 * Deletes created files aswell when the exception is found
		 */
		catch (FileInvalidException e) 
		{
			System.out.println();
			System.out.println(e.getMessage()+"\n----------------------------------------------------------------------------------");
			a.delete();
			b.delete();
			c.delete();
			count++;
			System.out.println();
		}
	}

	public static void main(String[] args) 
	{
		System.out.println("WELCOME TO BibCreator!\n");
	
			Scanner [] x = new Scanner[10];
			int i = 0;
			/**
			 * Creates an array of Scanner for each specific Latex
			 */
			try 
			{
				for(i = 1; i <= 10; i++)
				{	
					x[i-1] = new Scanner(new FileInputStream("Latex"+i+".bib")); 
				}
			}
			catch(FileNotFoundException e)
			{
				System.out.println("Could not open input file "+"Latex"+i+".bib"+" for reading."
									+ "\n\nPlease check if file exists! "
									+ "Program will terminate after closing any opened files.");
				for(int q = 1; q < i;q++)
				{
					x[q-1].close();	
				}
				System.exit(0);
			} 		
			
			/**
			 * Creating 30 files for each latex, 3 different styles
			 */
			File [] IEEEFile = new File[10];
			File [] ACMFile = new File[10];
			File [] NJFile = new File[10];
			
			/**
			 * Naming the files
			 */
			for(int t=0; t < 10; t++) 
			{
				IEEEFile[t] = new File("IEEE"+(t+1)+".json");
				ACMFile[t] = new File("ACM"+(t+1)+".json");
				NJFile[t] = new File("NJ"+(t+1)+".json");	
			}
		
			PrintWriter [] pw = new PrintWriter[10];
			
			/**
			 * Tries to see if each file can be opened or not.
			 * Closing Scanner and deleting the files if it cant be opened
			 */
			for(int t=0; t<10; t++) 
			{		
				try 
				{
					pw[t] = new PrintWriter(new FileOutputStream(IEEEFile[t]));
					pw[t].close();				
				}
				catch(FileNotFoundException e)
				{
					System.out.println(IEEEFile[t]+"cannot be opened/created");
					System.out.println("All other created output files will be deleted");	
					for(int j=0; j<=t; j++) 
					{
						IEEEFile[j].delete();
						ACMFile[j].delete(); 
						NJFile[j].delete(); 	
					}
					
					for(int q = 1; q <= 10;q++)
					{
						x[q-1].close();	
					}
					
					System.out.println("Program will terminate.");
					System.exit(0);	
				}
			}
			
			/**
			 * Tries to see if each file can be opened or not.
			 * Closing Scanner and deleting the files if it cant be opened
			 */
			for(int t=0; t<10; t++) 
			{
			
				try 
				{
					pw[t] = new PrintWriter(new FileOutputStream(ACMFile[t]));
					pw[t].close();
				}
				catch(FileNotFoundException e)
				{
					System.out.println(ACMFile[t]+"cannot be opened/created");
					System.out.println("All other created output files will be deleted");	
					for(int j=0; j<=t; j++) 
					{
						IEEEFile[j].delete();
						ACMFile[j].delete(); 
						NJFile[j].delete(); 	
					}
					for(int q = 1; q <= 10;q++)
					{
						x[q-1].close();	
					}
					System.out.println("Program will terminate.");
					System.exit(0);	
				}
			}
		
			/**
			 * Tries to see if each file can be opened or not.
			 * Closing Scanner and deleting the files if it cant be opened
			 */
			for(int t=0; t<10; t++) 
			{
			
				try 
				{
					pw[t]= new PrintWriter(new FileOutputStream(NJFile[t]));
					pw[t].close();
				}
				catch(FileNotFoundException e)
				{
					System.out.println(NJFile[t]+"cannot be opened/created");
					System.out.println("All other created output files will be deleted");	
					for(int j=0; j<=t; j++) 
					{
						IEEEFile[j].delete();
						ACMFile[j].delete(); 
						NJFile[j].delete(); 	
					}
					for(int q = 1; q <= 10;q++)
					{
						x[q-1].close();	
					}
					System.out.println("Program will terminate.");
					System.exit(0);	
				}
			}
			
			/**
			 * Loop which processes each latex file using static method
			 */
				for(int q = 0; q<=9; q++)
					processFilesForValidation(x[q],(q+1),IEEEFile[q],ACMFile[q],NJFile[q]);
				
				
				/**
				 * Returns the count of files created and not created
				 * Plus asks for users input
				 */
				System.out.print("A total of "+count+" files were found to be INVALID!\nThe other "+(10-count)+
						" files were found to be VALID and were created!"
						+ "\n\nPlease enter one of the names of the valid files to review it: ");
				
				Scanner in = new Scanner(System.in);
				
				String input;
				input = in.next();
				BufferedReader userRetrival = null;
				
				/**
				 * Tries to open users input
				 * if not, it gives user one other try
				 * if an error is repeated it terminates the program
				 */
				try 
				{
					userRetrival = new BufferedReader(new FileReader(input));
				}
				catch(FileNotFoundException e)
				{
					System.out.println("\nCould not open input file "+input + "....\nPlease try again: ");
					input = in.next();
					try 
					{
						userRetrival = new BufferedReader(new FileReader(input));
					}
					catch(FileNotFoundException e2)
					{
						System.out.println("\nCould not open input file "+input + "....\nProgram will now close!\n\nBYE BYE! ");
						System.exit(0);
					}
				}
				
				in.close();
				
				System.out.println("\nContent of input file "+input+": \n----------------------------------------------------------------------------------\n");
				
				String end = null;
				
				/**
				 * Used to determine end of the file
				 */
				try 
				{
					end = userRetrival.readLine();	
				}
				catch(IOException e)
				{
					e.getMessage();
				}
				
				/**
				 * Prints out the contents of the file
				 */
				while(end != null)
				{
					try 
					{
						System.out.println(end);
						end = userRetrival.readLine();					
					}
					catch(IOException e)
					{
						e.getMessage();
					}
				
				}
			
			System.out.print("----------------------------------------------------------------------------------\nThank You for using BibCreator! \nProgram will now End.");
			
			
			
			
		
		
	}

}
