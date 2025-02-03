package personnage;

import config.GameConfiguration;
import map.Case;
import map.Grille;

public class PersonnageManager {
		private Grille grille;
		
		private Gardien gardien;
		
		public PersonnageManager(Grille grille) {
			this.grille = grille;
		}
		
		public void moveLeftGardien() {
			Case position = gardien.getCase();
			
			if(position.getPosition().getColonne() > 0) {
				Case newposition = grille.getCase(position.getPosition().getLigne(), position.getPosition().getColonne() - 1);
				gardien.setCase(newposition);
			}
		}
		
		public void moveRightGardien() {
			Case position = gardien.getCase();
			
			if(position.getPosition().getColonne() < GameConfiguration.NB_COLONNE - 1) {
				Case newposition = grille.getCase(position.getPosition().getLigne(), position.getPosition().getColonne() + 1);
				gardien.setCase(newposition);
			}
		}
		
		public void moveUpGardien() {
			Case position = gardien.getCase();
			
			if(position.getPosition().getLigne() > 0) {
				Case newposition = grille.getCase(position.getPosition().getLigne() - 1, position.getPosition().getColonne());
				gardien.setCase(newposition);
			}
		}
		
		public void moveDownGardien() {
			Case position = gardien.getCase();
			
			if(position.getPosition().getColonne() < GameConfiguration.NB_LIGNE - 1) {
				Case newposition = grille.getCase(position.getPosition().getLigne() + 1, position.getPosition().getColonne());
				gardien.setCase(newposition);
			}
		}
}
