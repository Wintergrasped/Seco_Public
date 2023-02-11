package cc.koffeecreations.main.database;

import cc.koffeecreations.main.database.Mysql;

public class Sync {

	Mysql sql;
	
	public Sync (Mysql m) {
		
		sql = m;
		
	}
	
	public synchronized void syncDB() {
		sql.syncDB();
	}
	
}
