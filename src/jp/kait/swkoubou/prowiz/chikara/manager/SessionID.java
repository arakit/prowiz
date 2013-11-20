package jp.kait.swkoubou.prowiz.chikara.manager;

import java.io.Serializable;

public class SessionID implements Serializable{


	/**
	 * 		@author Chikara Funabashi
	 * 		@date 2013/07/06
	 *
	 */


	final String sid;



	SessionID(String sid) {
		this.sid = sid;
	}

	public String getSessionID(){
		return this.sid;
	}

}
