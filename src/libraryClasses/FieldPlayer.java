package libraryClasses;
import java.math.BigDecimal;

/**
 * 
 * @author Bart de Jonge
 *
 */
public abstract class FieldPlayer extends Player {

	private int dribblingValue, finishingValue, defenseValue, staminaValue;
	

	/**
	 * @param price
	 * @param team
	 * @param name
	 * @param age
	 * @param number
	 * @param goals
	 * @param assists
	 * @param yellowcards
	 * @param redcards
	 * @param dribblingValue
	 * @param finishingValue
	 * @param defenseValue
	 * @param staminaValue
	 */
	public FieldPlayer(BigDecimal price, String team, String name, int age,
			int number, int dribblingValue, int finishingValue, int defenseValue,
			int staminaValue, int goals, int assists, int yellowcards, int redcards) {
		super(price, team, name, age, number, goals, assists, yellowcards,
				redcards);
		this.dribblingValue = dribblingValue;
		this.finishingValue = finishingValue;
		this.defenseValue = defenseValue;
		this.staminaValue = staminaValue;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getPlayerType() + " [name=" + this.getName() + ", age=" + this.getAge() + ", team=" + this.getTeam()
				+ ", number=" + this.getNumber() + ", price=" + this.getPrice() + ", dribblingValue=" + dribblingValue
				+ ", finishingValue=" + finishingValue + ", defenseValue=" + defenseValue + ", staminaValue=" + staminaValue
				+ ", goals=" + this.getGoals() + ", assists=" + this.getAssists() + ", yellowCards=" + this.getYellowcards()
				+ ", redCards=" + this.getRedcards();
	}
	

	/**
	 * @return the dribblingValue
	 */
	public int getDribblingValue() {
		return dribblingValue;
	}


	/**
	 * @param dribblingValue the dribblingValue to set
	 */
	public void setDribblingValue(int dribblingValue) {
		this.dribblingValue = dribblingValue;
	}


	/**
	 * @return the finishingValue
	 */
	public int getFinishingValue() {
		return finishingValue;
	}


	/**
	 * @param finishingValue the finishingValue to set
	 */
	public void setFinishingValue(int finishingValue) {
		this.finishingValue = finishingValue;
	}


	/**
	 * @return the defenseValue
	 */
	public int getDefenseValue() {
		return defenseValue;
	}

	/**
	 * @param defenseValue - the defenseValue to set
	 */
	public void setDefenseValue(int defenseValue) {
		this.defenseValue = defenseValue;
	}

	/**
	 * @return the staminaValue
	 */
	public int getStaminaValue() {
		return staminaValue;
	}

	/**
	 * @param staminaValue - the staminaValue to set
	 */
	public void setStaminaValue(int staminaValue) {
		this.staminaValue = staminaValue;
	}	
	
}
