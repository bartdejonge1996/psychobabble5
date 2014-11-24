import java.math.BigDecimal;

/**
 * 
 * @author Bart de Jonge
 *
 */
public abstract class Player {
	
	private BigDecimal price;
	private String team, playerType, name;
	private int age, number;

	/**
	 * Constructor
	 * 
	 * @param price			- The price of the player
	 * @param team			- The team of the player
	 * @param name			- The name of the player
	 * @param age			- The age of the player
	 */
	public Player(BigDecimal price, String team, String name, int age, int number) {
		super();
		this.price = price;
		this.team = team;
		this.name = name;
		this.age = age;
		this.number = number;
	}
	
	

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}



	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}



	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}



	/**
	 * @param price - the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}



	/**
	 * @return the team
	 */
	public String getTeam() {
		return team;
	}

	/**
	 * @param team the - team to set
	 */
	public void setTeam(String team) {
		this.team = team;
	}

	/**
	 * @return the playerType
	 */
	public String getPlayerType() {
		return playerType;
	}

	/**
	 * @param playerType - the playerType to set
	 */
	public void setPlayerType(String playerType) {
		this.playerType = playerType;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name - the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age - the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}	
}
