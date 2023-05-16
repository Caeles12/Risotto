package entity;

public class Object {
	public static void getEffect(int id, Player pl) {
		/**
		 * 1- riz
		 * 2- seau vide
		 * 3- seau remplit d'eau
		 * 4- champignon
		 * 5- risoto au champignon
		 */
		switch(id) {
		case 1:
			System.out.println("il faut cuir le riz");
			break;
		case 2:
			System.out.println("a utiliser proche de l'eau");
			break;
		case 3:
			System.out.println("je n'ai pas soif");
			break;
		case 4:
			System.out.println("sans le cuir je ne grandirais pas :/");
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
			return "seau vide";
		case 3:
			return "seau plein";
		case 4:
			return "Champignon";
		case 5:
			return "Rizotto au Champignons";
		default:
			return "Air";
		}
	}
}
