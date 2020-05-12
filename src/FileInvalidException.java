
/** 
 * 
 * @author Hamzah Sheikh, 40103993
 *
 */
public class FileInvalidException extends Exception 
{
	/** 
	 * default constructor
	 */
	public FileInvalidException()
	{
		super("Error: Input file cannot be parsed due to missing information (i.e. month={}, title={}, etc.)");
	}
	
	/** 
	 * parameterized constructor 
	 * @param x denotes the message as modified by the user
	 */
	public FileInvalidException(String x)
	{
		super(x);
	}
	
	
	
	
	
	
}
