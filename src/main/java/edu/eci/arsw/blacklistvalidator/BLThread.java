package edu.eci.arsw.blacklistvalidator;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

public class BLThread extends Thread{
	
	private HostBlacklistsDataSourceFacade skds = HostBlacklistsDataSourceFacade.getInstance();
	private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());
	private int ocurrences = 0;
	private String host;
	private int serverRA, serverRB;
	private LinkedList<Integer> blackListOcurrences=new LinkedList<>();
	private int BLALARMCOUNT;
	
	public BLThread(String host, int a, int b, int BLAC) {
		this.host = host;
		this.serverRA = a;
		this.serverRB = b;
		this.BLALARMCOUNT = BLAC;
		
	}

	public int getOcurrences() {
		return ocurrences;
	}
	
	@SuppressWarnings("deprecation")
	public void run() {
		int checkedListsCount=0;
        
        for (int i = serverRA; i<serverRB && this.ocurrences<BLALARMCOUNT; i++){
            checkedListsCount++;
            
            if (skds.isInBlackListServer(i, host)){
                blackListOcurrences.add(i);
                
                this.ocurrences++;
            }
        }
        
        if (this.ocurrences>=BLALARMCOUNT){
            skds.reportAsNotTrustworthy(host);
        }
        else{
            skds.reportAsTrustworthy(host);
        }
        
        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{checkedListsCount, skds.getRegisteredServersCount()});
        
        this.stop();
	}
	
	public LinkedList<Integer> getBlacklistOcurrences(){
		return blackListOcurrences;
	}
	
}
