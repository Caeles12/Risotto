package entity;

public class Object {
	public static void getEffect(int id, Player pl) {
		/**
		 * 1- riz
		 * 2- seau vide
		 * 3- seau rempli d'eau
		 * 4- champignon
		 * 5- risotto aux champignons
		 */
		switch(id) {
		case 1:
			System.out.println("il faut cuir le riz");
			break;
		case 2:
			System.out.println("a utiliser proche de l'eau");
			break;
		case 3:
			System.out.println("Je n'ai pas soif");
			break;
		case 4:
			System.out.println("Sans le cuir je ne grandirais pas :/");
			break;
		case 5:
			System.out.println("miam miam crounch FIREBALL press F");
			break;
			
		default:
			// les poches vides T_T
			break;
		}
	}
	
	public static String getNom(int id) {
		switch(id) {
		case 1:
			return "Riz";
		case 2:
			return "Seau vide";
		case 3:
			return "Seau plein";
		case 4:
			return "Champignon";
		case 5:
			return "Risotto aux Champignons";
		case 6:
			return "Os";
		case 7:
			return "Balais";
		default:
			return "Air";
		}
	}
	
	public static String getImage(int id) {
		switch(id) {
		case 1:
			return "star";
		case 2:
			return "cup_01a";
		case 3:
			return "cup_01b";
		case 4:
			return "star";
		case 5:
			return "star";
		case 6:
			return "bone01a";
		case 7:
			return "balais";
		default:
			return "star";
		}
	}
}
