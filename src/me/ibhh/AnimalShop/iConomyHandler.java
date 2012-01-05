/**
 * 
 */
package me.ibhh.AnimalShop;

import org.bukkit.entity.Player;

import com.iCo6.system.Accounts;
import com.iConomy.system.Account;
import com.iConomy.system.Holdings;
import com.nijikokun.register.payment.Methods;

/**
 * @author Simon
 * 
 */
public class iConomyHandler {

	private static int iConomyversion = 0;
	private Holdings balance5;
	private Double balance;
	private AnimalShop AnimalShopV;
	
	public iConomyHandler(AnimalShop AnimalShop)
	{
		AnimalShopV = AnimalShop;
		AnimalShopV.aktuelleVersion();
	}

	private static boolean packageExists(String[] packages) {
		try {
			for (String pkg : packages) {
				Class.forName(pkg);
			}
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * Checks the config.yml wich moneyplugin should be used.
	 * 
	 * @param
	 * @return 1: Register 5: iConomy5 6: iConomy6
	 */
	public static int iConomyversion() {
		try {
			if (packageExists(new String[] { "com.nijikokun.register.payment.Methods" })) {
				iConomyversion = 1;
				System.out.println("[AuctionTrade] AuctionTrade hooked into Register");
			} else if (packageExists(new String[] { "com.iCo6.system.Accounts" })) {
				iConomyversion = 6;
				System.out.println("[AuctionTrade] AuctionTrade hooked into iConomy6");
			} else if (packageExists(new String[] { "com.iConomy.iConomy",
					"com.iConomy.system.Account", "com.iConomy.system.Holdings" })) {
				iConomyversion = 5;
				System.out.println("[AuctionTrade] AuctionTrade hooked into iConomy5");
			} else {
				System.out
						.println("[AuctionTrade] AuctionTrade cant hook into iConomy5, iConomy6 or Register. Downloading Register!");
				System.out
						.println("[AuctionTrade] ************ Please configure Register!!!!! **********");
				try {
					String path = "plugins/";
					Update.autoDownload(
							"http://mirror.nexua.org/Register/latest/stable/Register.jar",
							path, "Register.jar");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		} catch (Exception E) {
			E.printStackTrace();
			iConomyversion = 0;
		}
		return iConomyversion;
	}

	/**
	 * Gets Balance of a player.
	 * 
	 * @param player
	 *            , iConomyversion() must be performed before.
	 * @return Double balance.
	 */
	public Double getBalance156(Player player) {
		String name = player.getName();
		if (iConomyversion == 5) {
			try {
				if (hasAccount5(name) == true) {
					balance5 = getAccount5(name).getHoldings();
				}
			} catch (Exception E) {
				System.out.println("[AuctionTrade] "
						+ "No Account! Please report it to an admin!");
				player.sendMessage("[AuctionTrade] "
						+ "No Account! Please report it to an admin!");
				E.printStackTrace();
				balance5 = null;
				return balance;
			}
			try {
				balance = (double) balance5.balance();
			} catch (Exception E) {
				System.out.println("[AuctionTrade] "
						+ "No Account! Please report it to an admin!");
				player.sendMessage("[AuctionTrade] "
						+ "No Account! Please report it to an admin!");
				E.printStackTrace();
				balance5 = null;
				return balance;
			}
			return balance;
		} else if (iConomyversion == 6) {
			try {
				balance = new Accounts().get(player.getName()).getHoldings()
						.getBalance();
			} catch (Exception e) {
				System.out.println("[AuctionTrade] "
						+ "No Account! Please report it to an admin!");
				player.sendMessage("[AuctionTrade] "
						+ "No Account! Please report it to an admin!");
				e.printStackTrace();
				balance5 = null;
				return balance;
			}

		} else if (iConomyversion == 1) {
			try {
				balance = Methods.getMethod().getAccount(player.getName())
						.balance();
			} catch (Exception e) {
				System.out.println("[AuctionTrade] "
						+ "No Account! Please report it to an admin!");
				player.sendMessage("[AuctionTrade] "
						+ "No Account! Please report it to an admin!");
				e.printStackTrace();
				balance5 = null;
				return balance;
			}

		}
		return balance;
	}

	private Account getAccount5(String name) {
		return com.iConomy.iConomy.getAccount(name);
	}

	private boolean hasAccount5(String name) {
		return com.iConomy.iConomy.hasAccount(name);
	}

	/**
	 * Substracts money.
	 * 
	 * @param player
	 *            , amount
	 * @return
	 */
	public void substractmoney156(double amountsubstract, Player player) {
		String name = player.getName();
		if (iConomyversion == 5) {
			try {
				getAccount5(name).getHoldings().subtract(amountsubstract);
			} catch (Exception e) {
				System.out.println("[AuctionTrade] "
						+ "Cant substract money! Does account exist?");
				player.sendMessage("[AuctionTrade] "
						+ "Cant substract money! Does account exist?");
				e.printStackTrace();
				return;
			}
		} else if (iConomyversion == 6) {
			try {
				com.iCo6.system.Account account = new Accounts().get(player
						.getName());
				account.getHoldings().subtract(amountsubstract);
			} catch (Exception e) {
				System.out.println("[AuctionTrade] "
						+ "Cant substract money! Does account exist?");
				player.sendMessage("[AuctionTrade] "
						+ "Cant substract money! Does account exist?");
				e.printStackTrace();
				return;
			}
		} else if (iConomyversion == 1) {
			try {
				Methods.getMethod().getAccount(player.getName())
						.subtract(amountsubstract);
			} catch (Exception e) {
				System.out.println("[AuctionTrade] "
						+ "Cant substract money! Does account exist?");
				player.sendMessage("[AuctionTrade] "
						+ "Cant substract money! Does account exist?");
				e.printStackTrace();
				return;
			}
		}
		return;
	}

	/**
	 * Adds money
	 * 
	 * @param player
	 *            , amount
	 * @return
	 */
	public void addmoney156(double amountadd, Player player) {
		String name = player.getName();
		if (iConomyversion == 5) {
			try {
				getAccount5(name).getHoldings().add(amountadd);
			} catch (Exception e) {
				System.out.println("[AuctionTrade] "
						+ "Cant add money! Does account exist?");
				player.sendMessage("[AuctionTrade] "
						+ "Cant add money! Does account exist?");
				e.printStackTrace();
				return;
			}
		} else if (iConomyversion == 6) {
			try {
				com.iCo6.system.Account account = new Accounts().get(player
						.getName());
				account.getHoldings().add(amountadd);
			} catch (Exception e) {
				System.out.println("[AuctionTrade] "
						+ "Cant add money! Does account exist?");
				player.sendMessage("[AuctionTrade] "
						+ "Cant add money! Does account exist?");
				e.printStackTrace();
				return;
			}

		} else if (iConomyversion == 1) {
			try {
				Methods.getMethod().getAccount(player.getName()).add(amountadd);
			} catch (Exception e) {
				System.out.println("[AuctionTrade] "
						+ "Cant add money! Does account exist?");
				player.sendMessage("[AuctionTrade] "
						+ "Cant add money! Does account exist?");
				e.printStackTrace();
				return;
			}
		}
		return;
	}

}
