package com.datacity.erp.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.datacity.erp.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class Constants {

	// Json filename
	public static final String JSON_FILENAME = "erp.json";

	// MainActivity
	public static final String SERIALIZABLE_OBJ_NAME = "buildingMap";
	public static final String SERIALIZABLE_BUILDING = "buildingClass";

	// List of Fragment identifiers
	public static final int MAP = 0;
	public static final int CATEGORIES = 1;
	public static final int FAVORITES = 2;
	public static final int ABOUT = 3;
	public static final int BUILDING = 4;
	public static final int CATEGORY_SECOND = 5;
	public static final int SEARCH = 6;
	public static final int FRAGMENT_COUNT = 7;

	// Map Fragment
	public static final LatLngBounds BOUNDS = new LatLngBounds(new LatLng(
			43.127048, 3.223114), new LatLng(44.006645, 4.393158));
	public static final LatLng DEFAULT_POS = new LatLng(43.610769, 3.876716);
	public static final int MAX_ZOOM = 19;
	public static final int MIN_ZOOM = 8;

	// Categories Fragment
	public static final String[] CATEGORIES_LIST = new String[] {
			"Administration", "Association", "Autre", "Commerce",
			"Culture & Loisir", "Emploi-Formation", "Enseignement",
			"Parcs & jardins", "Petite enfance", "Point de propreté",
			"Recherche & dévelopement", "Santé", "Social", "Sport" };

	// Icons of categories
	public static final int[] ICONS_ID = new int[] { R.drawable.administration,
			R.drawable.association, R.drawable.autre, R.drawable.commerce,
			R.drawable.culture_loisir, R.drawable.emploi_formation,
			R.drawable.enseignement, R.drawable.parcs_jardins,
			R.drawable.petite_enfance, R.drawable.point_de_proprete,
			R.drawable.recherche_developement, R.drawable.sante,
			R.drawable.social, R.drawable.sport };

	public static final Map<String, String> mCategoriesMap;
	static {
		Map<String, String> aMap = new HashMap<String, String>();
		// Administration
		aMap.put("Agglomération", CATEGORIES_LIST[0]);
		aMap.put("Autres multi-accueils", CATEGORIES_LIST[0]);
		aMap.put("Consulats", CATEGORIES_LIST[0]);
		aMap.put("Département", CATEGORIES_LIST[0]);
		aMap.put("Etat", CATEGORIES_LIST[0]);
		aMap.put("Mairies annexes", CATEGORIES_LIST[0]);
		aMap.put("Multi-accueils municipaux", CATEGORIES_LIST[0]);
		aMap.put("Postes de Police municipale", CATEGORIES_LIST[0]);
		aMap.put("Région", CATEGORIES_LIST[0]);
		aMap.put("Services municipaux", CATEGORIES_LIST[0]);

		// Association
		aMap.put("Associations", CATEGORIES_LIST[1]);
		aMap.put("Compagnies", CATEGORIES_LIST[1]);
		aMap.put("Conseils de quartier", CATEGORIES_LIST[1]);
		aMap.put("Service d'accueil familial associatif", CATEGORIES_LIST[1]);

		// Autre
		aMap.put("Adresses utiles", CATEGORIES_LIST[2]);
		aMap.put("Autres contacts utiles", CATEGORIES_LIST[2]);
		aMap.put("Autres lieux", CATEGORIES_LIST[2]);
		aMap.put("Quartiers", CATEGORIES_LIST[2]);
		aMap.put("Parkings du centre ville", CATEGORIES_LIST[2]);
		aMap.put("Places", CATEGORIES_LIST[2]);
		aMap.put("Maîtrise de l'énergie", CATEGORIES_LIST[2]);
		aMap.put("salles", CATEGORIES_LIST[2]);
		aMap.put("Salles de réunions", CATEGORIES_LIST[2]);

		// Commerce
		aMap.put("Commerce artisanat", CATEGORIES_LIST[3]);
		aMap.put("Grands marchés", CATEGORIES_LIST[3]);
		aMap.put("Halle", CATEGORIES_LIST[3]);
		aMap.put("Marchés thématiques", CATEGORIES_LIST[3]);
		aMap.put("Marchés de quartiers", CATEGORIES_LIST[3]);

		// Culture & Loisir
		aMap.put("Bibliothèques et médiathèques", CATEGORIES_LIST[4]);
		aMap.put("Culture", CATEGORIES_LIST[4]);
		aMap.put("Chorales", CATEGORIES_LIST[4]);
		aMap.put("Galeries d'art", CATEGORIES_LIST[4]);
		aMap.put("Musées", CATEGORIES_LIST[4]);
		aMap.put("Musées, galeries d'art", CATEGORIES_LIST[4]);
		aMap.put("Monuments", CATEGORIES_LIST[4]);
		aMap.put("Maisons pour tous", CATEGORIES_LIST[4]);
		aMap.put("Salles d'expo", CATEGORIES_LIST[4]);
		aMap.put("Palais des congrès", CATEGORIES_LIST[4]);
		aMap.put("Auto modélisme", CATEGORIES_LIST[4]);
		aMap.put("Bases nautiques", CATEGORIES_LIST[4]);
		aMap.put("Cinémas", CATEGORIES_LIST[4]);
		aMap.put("Clubs de loisirs", CATEGORIES_LIST[4]);
		aMap.put("Centres de loisirs municipaux", CATEGORIES_LIST[4]);
		aMap.put("Centres de loisirs associatifs", CATEGORIES_LIST[4]);
		aMap.put("Centres de loisirs des Maisons pour tous", CATEGORIES_LIST[4]);
		aMap.put("Espaces jeux", CATEGORIES_LIST[4]);
		aMap.put("Lieux d'accès multimédia", CATEGORIES_LIST[4]);
		aMap.put("Loisirs divers (incomplet)", CATEGORIES_LIST[4]);
		aMap.put("Salles de spectacle et concert", CATEGORIES_LIST[4]);

		// Emploi-Formation
		aMap.put("Missions locales des jeunes", CATEGORIES_LIST[5]);
		aMap.put("Pôles emploi", CATEGORIES_LIST[5]);

		// Enseignement
		aMap.put("Calandretas", CATEGORIES_LIST[6]);
		aMap.put("Collèges", CATEGORIES_LIST[6]);
		aMap.put("Enseignement primaire", CATEGORIES_LIST[6]);
		aMap.put("Ecoles d'enseignement supérieur", CATEGORIES_LIST[6]);
		aMap.put("Ecoles élémentaires", CATEGORIES_LIST[6]);
		aMap.put("Ecoles maternelles", CATEGORIES_LIST[6]);
		aMap.put("Ecoles privées sous contrat", CATEGORIES_LIST[6]);
		aMap.put("Lycées", CATEGORIES_LIST[6]);
		aMap.put("Métiers Petite Enfance", CATEGORIES_LIST[6]);
		aMap.put("Universités", CATEGORIES_LIST[6]);

		// Parcs & jardins
		aMap.put("Fontaines", CATEGORIES_LIST[7]);
		aMap.put("Jardins d'enfants", CATEGORIES_LIST[7]);
		aMap.put("Jardin-École", CATEGORIES_LIST[7]);
		aMap.put("Jardins partagés", CATEGORIES_LIST[7]);
		aMap.put("Parcs et jardins", CATEGORIES_LIST[7]);

		// Petite enfance
		aMap.put("Baby sitting", CATEGORIES_LIST[8]);
		aMap.put("Crèches à horaires atypiques", CATEGORIES_LIST[8]);
		aMap.put("Crèches, accueil petite enfance", CATEGORIES_LIST[8]);
		aMap.put("Crèches parentales et associatives", CATEGORIES_LIST[8]);
		aMap.put("Relais assistantes maternelles indépendantes",
				CATEGORIES_LIST[8]);

		// Point de propreté
		aMap.put("Propreté", CATEGORIES_LIST[9]);

		// Recherche & dévelopement
		aMap.put("Centres de recherche", CATEGORIES_LIST[10]);

		// Santé
		aMap.put("Cliniques", CATEGORIES_LIST[11]);
		aMap.put("Don d'organes", CATEGORIES_LIST[11]);
		aMap.put("Hôpitaux", CATEGORIES_LIST[11]);
		aMap.put("Santé, solidarité", CATEGORIES_LIST[11]);

		// Social
		aMap.put("Centres d'accueil", CATEGORIES_LIST[12]);
		aMap.put("Lieux de rencontre parents / enfants", CATEGORIES_LIST[12]);
		aMap.put("Restos du Coeur", CATEGORIES_LIST[12]);
		aMap.put("Foyers d'hébergement", CATEGORIES_LIST[12]);
		aMap.put("Logement", CATEGORIES_LIST[12]);
		aMap.put("Résidences-foyers", CATEGORIES_LIST[12]);
		aMap.put("Sociétés d'HLM", CATEGORIES_LIST[12]);

		// Sport
		aMap.put("Autres Équipement", CATEGORIES_LIST[13]);
		aMap.put("Beach volley", CATEGORIES_LIST[13]);
		aMap.put("Boulodromes de Pétanque", CATEGORIES_LIST[13]);
		aMap.put("Boule lyonnaise", CATEGORIES_LIST[13]);
		aMap.put("Clubs sportifs", CATEGORIES_LIST[13]);
		aMap.put("Escalade", CATEGORIES_LIST[13]);
		aMap.put("Football", CATEGORIES_LIST[13]);
		aMap.put("Gymnases", CATEGORIES_LIST[13]);
		aMap.put("Multisports", CATEGORIES_LIST[13]);
		aMap.put("Palais des sports", CATEGORIES_LIST[13]);
		aMap.put("Parcours sportifs", CATEGORIES_LIST[13]);
		aMap.put("Plateaux sportifs", CATEGORIES_LIST[13]);
		aMap.put("Piscines", CATEGORIES_LIST[13]);
		aMap.put("Skate board", CATEGORIES_LIST[13]);
		aMap.put("Stades", CATEGORIES_LIST[13]);
		aMap.put("Salles de sports", CATEGORIES_LIST[13]);
		aMap.put("Tennis", CATEGORIES_LIST[13]);
		aMap.put("Terrains de rugby", CATEGORIES_LIST[13]);
		aMap.put("Terrains de boules", CATEGORIES_LIST[13]);
		aMap.put("Terrains de foot", CATEGORIES_LIST[13]);

		mCategoriesMap = Collections.unmodifiableMap(aMap);
	}

}
