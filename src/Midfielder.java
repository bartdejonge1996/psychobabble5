import java.math.BigDecimal;

/**
 * 
 * @author Bart de Jonge
 *
 */
public class Midfielder extends FieldPlayer {

	/**
	 * @param price
	 * @param team
	 * @param name
	 * @param age
	 * @param dribblingValue
	 * @param finishingValue
	 * @param defenseValue
	 * @param staminaValue
	 * @param number
	 */
	public Midfielder(BigDecimal price, String team, String name, int age,
			int dribblingValue, int finishingValue, int defenseValue,
			int staminaValue, int number) {
		super(price, team, name, age, dribblingValue, finishingValue, defenseValue,
				staminaValue, number);
		this.setPlayerType("Midfielder");
	}
	
	
}
