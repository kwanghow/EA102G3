package idv.david.stream;

public enum Bonus {
	STAFF(0.02), MANAGER(0.04), EXECUTIVE(0.06);

	private final double percent;

	Bonus(double percent) {
		this.percent = percent;
	}

	public double percent() {
		return percent;
	}

	public static double byRole(Role role) {

		double bonusPercent = 0.0d;

		switch (role) {
		case EXECUTIVE:
			bonusPercent = EXECUTIVE.percent;
			break;
		case MANAGER:
			bonusPercent = MANAGER.percent;
			break;
		case STAFF:
			bonusPercent = STAFF.percent;
			break;
		}
		return bonusPercent;
	}

}
