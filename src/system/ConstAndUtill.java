package system;

public interface ConstAndUtill {
	
	//Server setttings
	final String 	SERVER_HOST 		= "127.0.0.1";
	final int		SERVER_PORT 		= 1102;
	final int 		BACKLOG				= 10;

	//Message headers
	final int CLIENT_LOGIN			= 1;
	final int CLIENT_LOGOUT			= 2;
	final int PUBLIC_MESSAGE		= 3;
	final int PRIVATE_MESSAGE		= 4;
	final int USERS					= 5;	
}
