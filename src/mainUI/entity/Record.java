package mainUI.entity;

public class Record
{
	private String website;
	private String username;
	private String password;

	public Record(String website, String username, String password)
	{
		this.website = website;
		this.username = username;
		this.password = password;
	}
	
	public Record(String websites){
		
		this(websites, "default", "default");
	}
	

	public String getWebsite()
	{
		return website;
	}


	public void setWebsite(String website)
	{
		this.website = website;
	}


	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

}
