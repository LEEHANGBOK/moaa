package drives.siizecheck;


public class CheckSize {

	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String google_access_token="ya29.GlzkBEtvj7zToYpqwAuGdFYumKUNrwidzC9vWYV2jzkNOl98i5eH9zFrFxswIGfqDoKMu5L8VPzzzzeX9Wj4XX1NixJq5HeT_edZ9HhpPFGDj3DKYMoPxkPPX8bibg";
		
		String Box_access_token="d9DXcOJFlnR2Mmn5aCUogT7tQ5mTJh9O";
		
		String Drop_access_token="kFb_ENWtmyUAAAAAAAAAyTBrdlVR4ZlHTlzRk2fAZA-A3uwdUe_h9x6c51POUFU5";
		
		
		Google_checksize google = new Google_checksize(google_access_token);
		google.start();
		
		
		
		Box_checksize box = new Box_checksize(Box_access_token);
		box.start();
		
		
		Drop_checksize drop = new Drop_checksize(Drop_access_token);
		drop.start();
		
	}
}
