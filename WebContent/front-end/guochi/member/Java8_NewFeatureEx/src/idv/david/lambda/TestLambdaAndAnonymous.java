package idv.david.lambda;

public class TestLambdaAndAnonymous {

	public static void main(String[] args) {
		new TestLambdaAndAnonymous().go();
	}

	private void go() {
		// 依然是Test物件實體
		Runnable r1 = () -> {
			System.out.println("r1: " + this.getClass());
		};

		// 匿名類別會再額外產生一個物件實體
		Runnable r2 = new Runnable() {
			public void run() {
				System.out.println("r2: " + this.getClass());
			}
		};

		new Thread(r1).start();
		new Thread(r2).start();
	}

}
