package drives.sizecheck;


public class CheckSize {

	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String google_access_token="ya29.GlzkBEtvj7zToYpqwAuGdFYumKUNrwidzC9vWYV2jzkNOl98i5eH9zFrFxswIGfqDoKMu5L8VPzzzzeX9Wj4XX1NixJq5HeT_edZ9HhpPFGDj3DKYMoPxkPPX8bibg";
		
		String Box_access_token="d9DXcOJFlnR2Mmn5aCUogT7tQ5mTJh9O";
		
		String Drop_access_token="kFb_ENWtmyUAAAAAAAAAyTBrdlVR4ZlHTlzRk2fAZA-A3uwdUe_h9x6c51POUFU5";
		
		
		GoogleChecksize google = new GoogleChecksize(google_access_token);
		google.start();
		
		
		
		BoxChecksize box = new BoxChecksize(Box_access_token);
		box.start();
		
		
		DropChecksize drop = new DropChecksize(Drop_access_token);
		drop.start();
		
	}
}
