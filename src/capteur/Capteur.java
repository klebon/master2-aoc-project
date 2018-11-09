package capteur;

import diffusion.Diffusion;

public class Capteur extends Thread implements CapteurMoniteur {
	
	private Diffusion diffusion;
	
	private Integer compteur;
	
	public Capteur(Diffusion diffusion) {
		this.compteur = 0;
		this.diffusion = diffusion;
	}
	
	public void run() {
		
		while(true) {
			compteur++;
			
		}
	}

	@Override
	public synchronized Integer getCompteur() {
		return compteur;
	}
}
