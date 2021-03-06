
public class Main3
{

	static int[][] tab = new int[81][11];
	static boolean initialisationTerminee = false;
	static int nombreChiffresRemplis = 0;
	
	public static void main(String[] args) 
	{
		int nombre = 0;
		int difficult� = 1;
		int nombreChiffresATrouver = 81; // Ecrire le nombre de chiffres suivants � trouver 
										 // (�crire par exemple 100 pour finir le sudoku)
		
		InitialisationGrille(tab);
		
		// Ecrire ici les valeurs de d�part sous la forme : EntrerValeur(ligne, colonne, valeur);
		EntrerValeur(1,4,5);
		EntrerValeur(1,8,6);
		EntrerValeur(2,1,8);
		EntrerValeur(2,3,9);	
		EntrerValeur(2,8,1);
		EntrerValeur(3,1,1);
		EntrerValeur(3,2,6);
		EntrerValeur(3,5,8);
		EntrerValeur(3,6,7);
		EntrerValeur(4,1,3);
		EntrerValeur(4,5,2);
		EntrerValeur(4,6,6);
		EntrerValeur(5,3,7);
		EntrerValeur(5,5,1);
		EntrerValeur(5,7,6);
		EntrerValeur(6,4,8);
		EntrerValeur(6,5,5);
		EntrerValeur(6,9,3);
		EntrerValeur(7,4,4);
		EntrerValeur(7,5,7);
		EntrerValeur(7,8,2);
		EntrerValeur(7,9,1);
		EntrerValeur(8,2,4);
		EntrerValeur(8,7,9);
		EntrerValeur(8,9,8);
		EntrerValeur(9,2,8);
		EntrerValeur(9,6,3);
		
		initialisationTerminee = true;
		
		System.out.println("Grille originale : ");
		
		Afficher(tab);
		
		while (nombre < nombreChiffresATrouver) 
		{
			if (TrouverChiffreSuivant()==false)
			{
				if (difficult�==1)
					difficult�=2;
				
				if (Algo2()==false)
				{
					difficult�=3;
					
					if (GroupesIsolesMelanges()==false)
					{
						System.out.println("Chiffre suivant non trouv�.");
						nombre = nombreChiffresATrouver;
					}
				}
				
				if (TrouverChiffreSuivant()==true)
				{
					Afficher(tab);
					nombre++;
				}
			}
			else
			{
				Afficher(tab);
				nombre++;
			}
			
			if (nombreChiffresRemplis==81)
			{
				nombre = nombreChiffresATrouver;
				System.out.print("Sudoku r�solu ! Difficult� : " + difficult� + "/3 ");
				if (difficult�==1)
					System.out.println("(facile ou moyen).");
				if (difficult�==2)
					System.out.println("(difficile).");
				if (difficult�==3)
					System.out.println("(tr�s difficile).");
			}
		}
	}
	
	public static void InitialisationGrille(int[][] tab)
	{
		for (int i=0; i<81; i++) 
		{
			tab[i][0]=0;
			tab[i][10]=0;
			
			for (int j=1; j<10; j++)
			{
				tab[i][j]=1;
			}
		}
	}
	
	public static void EntrerValeur(int i, int j, int valeur)
	{		
		if (initialisationTerminee == false)
			tab[(i-1)*9 + j-1][0]=2;
		else
			tab[(i-1)*9 + j-1][0]=1;
		
		tab[(i-1)*9 + j-1][10]=valeur;
		
		nombreChiffresRemplis++;
		
		// On va maintenant d�duire des informations de cette nouvelle valeur
		
		for (int k=1; k<10; k++) // Une case est remplie par une unique valeur
		{
			if (k!=valeur)
			{
				tab[(i-1)*9 + j-1][k]=0;
			}
		}
		
		for (int ligne=0; ligne<9; ligne++) // Une valeur appara�t une fois par ligne
		{
			if (ligne!=i-1)
			{
				tab[ligne*9 + j-1][valeur]=0;
			}
		}
		
		for (int colonne=0; colonne<9; colonne++) // Une valeur appara�t une fois par colonne
		{
			if (colonne!=j-1)
			{
				tab[(i-1)*9 + colonne][valeur]=0;
			}
		}
		
		while (i%3 != 1) // On cherche la 1e case de la r�gion o� on se trouve 
			i--;
		while (j%3 != 1)
			j--;
		for (int h = (i-1)*9 + j-1; h < (i-1)*9 + j+20; h++) // Une fois par r�gion
		{
			if (tab[h][10]!=valeur)
				tab[h][valeur]=0;
			if (h%3==2)
				h+=6;
		}
		
	}
	
	public static void Afficher(int[][] tab)
	{
		for (int i=0; i<81; i++)
		{			
			/*if (i%9!=8)
			{
				if (tab[i][0]==2)
					System.out.print(tab[i][10]);
				else
					System.out.print(".");
				
				if (i%3==2 && i%9!=0)
					System.out.print(" | ");
				else
					System.out.print("  ");
			}
			else
			{
				
				if (tab[i][0]==2)
					System.out.println(tab[i][10]);
				else
					System.out.println(".");
				
				if (i%27==26 && i!=80)
					System.out.println("-  -  - + -  -  - + -  -  -");
			}	*/
			
			if (i%9!=8)
			{
				if (tab[i][0]>0)
					System.out.print(tab[i][10]);
				else
					System.out.print(".");
				
				if (i%3==2 && i%9!=0)
					System.out.print(" | ");
				else
					System.out.print("  ");
			}
			else
			{
				
				if (tab[i][0]>0)
					System.out.println(tab[i][10]);
				else
					System.out.println(".");
				
				if (i%27==26 && i!=80)
					System.out.println("-  -  - + -  -  - + -  -  -");
			}	
		}
	}
	
	public static boolean TrouverChiffreSuivant()
	{
		int compteur;
		
		for (int valeur=1; valeur<10; valeur++) // On va faire tous les tests valeur par valeur
		{
			for (int ligne=0; ligne<9; ligne++) // On compl�te une ligne si possible
			{
				compteur = 0;
				for (int colonne=0; colonne<9; colonne++)
				{
					if (tab[(ligne*9) + colonne][valeur]==1)
						compteur++;

					if (tab[(ligne*9) + colonne][10]==valeur)
						colonne=9;
					
					if (compteur==1 && colonne==8)
					{
						CompleterLigne(ligne*9, valeur);
						return true;
					}
				}
			}
			
			for (int colonne=0; colonne<9; colonne++) // On compl�te une colonne si possible
			{
				compteur = 0;
				for (int ligne=0; ligne<9; ligne++)
				{
					if (tab[(ligne*9) + colonne][valeur]==1)
						compteur++;
					
					if (tab[(ligne*9) + colonne][10]==valeur)
						ligne=9;
					
					if (compteur==1 && ligne==8)
					{
						CompleterColonne(colonne, valeur);
						return true;
					}
				}
			}
			
			for (int ligne=0; ligne<9; ligne+=3) // On compl�te une r�gion si possible
			{
				for (int colonne=0; colonne<9; colonne+=3)
				{
					compteur = 0;
					for (int ligneRegion=0; ligneRegion<3; ligneRegion++)
					{
						for (int colonneRegion=0; colonneRegion<3; colonneRegion++)
						{
							if (tab[(ligne+ligneRegion)*9 + (colonne+colonneRegion)][valeur]==1)
								compteur++;
							
							if (tab[(ligne+ligneRegion)*9 + (colonne+colonneRegion)][10]==valeur)
							{
								colonneRegion=3;
								ligneRegion=3;
							}
						
							if (compteur==1 && ligneRegion==2 && colonneRegion==2)
							{
								CompleterRegion(ligne*9 + colonne, valeur);
								return true;
							}
						}
					}
				}
			}
			
			for (int ligne=0; ligne<9; ligne++) // On remplit une case si possible
			{
				for (int colonne=0; colonne<9; colonne++)
				{
					compteur = 0;
					if (tab[(ligne*9) + colonne][0]==0)
					{
						
						if (tab[(ligne*9) + colonne][valeur]==1)
						{
							compteur++;
							for (int v=1; v<10; v++)
							{
								if (tab[(ligne*9) + colonne][v]==1 && v!=valeur)
								{
									compteur++;
									v=10;
								}
							}
						}
						
						if (compteur==1)
						{
							System.out.print("\nLa valeur " + valeur);
							System.out.print(" est la seule possible ligne " + (ligne+1));
							System.out.println(", colonne " + (colonne+1) + " :");
							EntrerValeur(ligne+1, colonne+1, valeur);
							return true;
						}
					}
				}
			}
		}
		return false;		
	}
	
	public static void CompleterLigne(int i, int valeur)
	{
		int j = i;
		
		while (tab[j][valeur]==0)
			j++;
		
		tab[j][10]=valeur;
		
		System.out.print("\nLa valeur " + valeur + " de la " + (i/9 + 1) );
		System.out.println("e ligne est seulement possible ici :");
		EntrerValeur(j/9 + 1, j%9 + 1, valeur);
	}

	public static void CompleterColonne(int i, int valeur)
	{
		int j = i;
		
		while (tab[j][valeur]==0)
			j+=9;
		
		tab[j][10]=valeur;

		System.out.print("\nLa valeur " + valeur + " de la " + (i + 1) );
		System.out.println("e colonne est seulement possible ici :");
		EntrerValeur(j/9 + 1, j%9 + 1, valeur);
	}

	public static void CompleterRegion(int i, int valeur)
	{
		int j = i;
		
		while (tab[j][valeur]==0)
		{
			if (j%3==2)
				j+=6;
			j++;
		}
		
		tab[j][10]=valeur;

		System.out.print("\nLa valeur " + valeur + " de la " + (i/9 + (i%9)/3 + 1));
		System.out.println("e r�gion est seulement possible ici :");
		EntrerValeur(j/9 + 1, j%9 + 1, valeur);
	}
	
	public static boolean Algo2()
	{
		System.out.println("Recherche d'informations avec Algo2.");
		
		int compteur;
		int compteurAffichage;
		int i;
		boolean RechercheFructueuse = false;
		
		for (int valeur=1; valeur<10; valeur++) 
		{ 
			for (int ligne=0; ligne<9; ligne++) // Algorithme de niveau 2 horizontal
			{
				compteur=0;
				i=ligne;
				
				for (int colonne=0; colonne<9; colonne+=3)
				{
					compteurAffichage=0;
					
					if (tab[ligne*9 + colonne][valeur]==0 && tab[ligne*9 + colonne+1][valeur]==0 
														  && tab[ligne*9 + colonne+2][valeur]==0)
						compteur += colonne+1;
					
					if (tab[ligne*9 + colonne][10]==valeur || tab[ligne*9 + colonne+1][10]==valeur 
							  							   || tab[ligne*9 + colonne+2][10]==valeur)
						colonne = 7;
					
					if (compteur%3==2 && colonne==6)
					{
						while (i%3!=0)
							i--;
							
						for (int j=i; j<i+3; j++)
						{
							if (j!=ligne) // 11 - compteur car (c+1)*2 (marche en pratique)
							{
								if (tab[j*9 + 11 - compteur][valeur]!=0
								 || tab[j*9 + 12 - compteur][valeur]!=0
								 || tab[j*9 + 13 - compteur][valeur]!=0)
								{
									RechercheFructueuse = true;
									tab[j*9 + 11 - compteur][valeur]=0;
									tab[j*9 + 12 - compteur][valeur]=0;
									tab[j*9 + 13 - compteur][valeur]=0;	
									
									if (compteurAffichage == 0)
									{	
										System.out.print("Le chiffre " + valeur);
										System.out.print(" de la ligne " + (ligne+1));
										System.out.print(" est dans la r�gion ");
										if (i==0)
											System.out.print("en haut ");
										if (i==3 && compteur!=8)
											System.out.print("au milieu ");
										if (i==6)
											System.out.print("en bas ");
										if (compteur==11)
											System.out.print("� gauche, ");
										if (compteur==8)
											System.out.print("au milieu, ");
										if (compteur==5)
											System.out.print("� droite, ");
										System.out.print("donc pas de " + valeur);
										System.out.println(" dans le reste de la r�gion.");
										compteurAffichage++;
									}
								}								
							}									
						}
					}
				}
			}
			
			for (int colonne=0; colonne<9; colonne++) // Algorithme de niveau 2 vertical
			{
				compteur=0;
				i=colonne;
				
				for (int ligne=0; ligne<9; ligne+=3)
				{
					compteurAffichage=0;
					
					if (tab[ligne*9 + colonne][valeur]==0 && tab[(ligne+1)*9 + colonne][valeur]==0 
														  && tab[(ligne+2)*9 + colonne][valeur]==0)
						compteur += ligne+1;
					
					if (tab[ligne*9 + colonne][10]==valeur || tab[(ligne+1)*9 + colonne][10]==valeur 
							  							   || tab[(ligne+2)*9 + colonne][10]==valeur)
						ligne = 7;
					
					if (compteur%3==2 && ligne==6)
					{
						while (i%3!=0)
							i--;
							
						for (int j=i; j<i+3; j++)
						{							
							if (j!=colonne)
							{
								if (tab[(11-compteur)*9 + j][valeur]!=0
								 || tab[(12-compteur)*9 + j][valeur]!=0
								 || tab[(13-compteur)*9 + j][valeur]!=0)
								{
									RechercheFructueuse = true;
									tab[(11-compteur)*9 + j][valeur]=0;
									tab[(12-compteur)*9 + j][valeur]=0;
									tab[(13-compteur)*9 + j][valeur]=0;
									
									if (compteurAffichage == 0)
									{
										System.out.print("Le chiffre " + valeur);
										System.out.print(" de la colonne " + (colonne+1));
										System.out.print(" est dans la r�gion ");
										if (compteur==11)
											System.out.print("en haut ");
										if (compteur==8 && i!=3)
											System.out.print("au milieu ");
										if (compteur==5)
											System.out.print("en bas ");
										if (i==0)
											System.out.print("� gauche, ");
										if (i==3)
											System.out.print("au milieu, ");
										if (i==6)
											System.out.print("� droite, ");
										System.out.print("donc pas de " + valeur);
										System.out.println(" dans le reste de la r�gion.");
										compteurAffichage++;
									}
								}
							}									 
						}
					}
				}
			}
		}
		return RechercheFructueuse;
	}
	
	public static boolean GroupesIsolesMelanges()
	{
		System.out.println("Recherche d'informations avec GroupesIsolesMelanges.");
		
		boolean RechercheFructueuse = false;
		int compteur;
		int i;
		int n;
		int d;
		int temp[] = new int[10];
		int compteurColonnesAffichees = 0; // On peut initialiser ici car on return d�s qu'on l'utilise
		int nombreCasesATester;
		int casesPossibles;
		
		for (int ligne=0; ligne<9; ligne++) // Algorithme sur les lignes
		{
			for (int colonne=0; colonne<9; colonne++) // Trouve le nombre de chiffres impossibles par case
			{
				i=ligne*9 + colonne;
				compteur = 0 ; 
				temp[colonne] = 0;
				if (tab[i][0]==0) 
				{
					for (int v=1; v<10; v++)
					{
						if (tab[i][v]==0)
							compteur++; // Compte le nombre de chiffres impossibles par case
					}
					
					if (compteur > 1 && compteur < 8)  // Test si la case a entre 2 et 7 impossibilit�s
						temp[colonne] = 9-compteur; // temp stocke le nombre de valeurs possibles
													// et donc le nombre de cases � tester
				}
			}
			
			for (int colonne=0; colonne<9; colonne++) // On regarde si les m�mes n valeurs impossibles 
													  // sont dans 9-n cases
			{
				i=ligne*9 + colonne;
				nombreCasesATester = temp[colonne]; // Nombre de valeurs possibles
				casesPossibles = 1;
				
				if (nombreCasesATester > 1 && nombreCasesATester < 8) // Si la case est sujette � tests
				{
					n=1;
					for (int v=1; v<10; v++) // On �crit dans temp[colonne] les valeurs impossibles
					{
						if  (tab[i][v] == 0)
						{
							temp[colonne]+=n*v; 
							n*=10;
						}
					}
					
					temp[colonne]-=nombreCasesATester; // temp est un nombre contenant 
													   // (nombreCasesATester) chiffres
				
					for (int j=0; j<9; j++) // On regarde si les n valeurs impossibles sont dans 
											// 8-n autres cases
					{
						i = ligne*9 + j;
						
						if (j!=colonne && tab[i][0]==0) // On teste case par case les non remplies
						{
							for (int m=0; m < 9-nombreCasesATester; m++) 
								// m s�lectionne le chiffre de temp[colonne]
							{
								if (tab[i][(temp[colonne]/((int)Math.pow(10,m))) % 10] != 0)
									m=10; // Si une valeur impossible d'une case est possible dans l'autre
								if (m==8-nombreCasesATester)
								{
									casesPossibles++;
									temp[j]=colonne+10; // On retient les cases v�rifiant l'algorithme
										// +10 diff�rencie les temp[j] v�rifiant l'algorithme
										// des temp[j] stockant le nombre de valeurs possibles.
										// C'est 2 tableaux en 1, moins de place m�moire
										// mais beaucoup plus de bordel !															
								}
							}
						}
					}
					
					if (casesPossibles > nombreCasesATester)
						System.err.println("Sudoku impossible, n valeurs doivent �tre dans n-1 cases !");
					
					else if (casesPossibles == nombreCasesATester)
					{
						for (int j=0; j < 9; j++) // On enl�ve les possibilit�s
						{
							i = ligne*9 + j;
							
							if (temp[j] != colonne+10 && j!=colonne)
							{
								d=1;
								
								for (int m=1; m < 10; m++) // On enl�ve les possibilit�s case par case
								{
									if ((temp[colonne]/((int)Math.pow(10,m-d))) % 10!=m)
									{
										d++;
										if (tab[i][m]!=0)
										{
											RechercheFructueuse = true;	
											tab[i][m]=0;
										}
									}
								}	
							}
						}
						
						if (RechercheFructueuse) // On affiche les infos
						{
							d=1;
							System.out.print("Dans la ligne " + (ligne+1) + ", les valeurs ");
							
							for (int m=1; m < 10; m++) 
							{
								if ((temp[colonne]/((int)Math.pow(10,m-d))) % 10!=m)
								{
									d++;
									if (d!=nombreCasesATester+1)
										System.out.print(m + ", ");
									else
										System.out.print(m);
								}
							}
							System.out.print(" ne sont possibles que dans les colonnes ");
							
							for (int j=0; j < 9; j++)
							{
								if (temp[j] == colonne+10 || j==colonne)
								{
									if (compteurColonnesAffichees < nombreCasesATester-1)
									{
										System.out.print((j+1) + ", ");
										compteurColonnesAffichees++;
									}
									else
										System.out.println((j+1) + ".");

								}		
							}
							return RechercheFructueuse;
						}
					}
				}
			}
		}
		
		for (int j=0; j<10; j++)
			temp[j]=0;

		for (int colonne=0; colonne<9; colonne++) // Algorithme sur les colonnes
		{
			for (int ligne=0; ligne<9; ligne++) // Trouve le nombre de chiffres impossibles par case
			{
				i=ligne*9 + colonne;
				compteur = 0 ; 
				temp[ligne] = 0;
				if (tab[i][0]==0) 
				{
					for (int v=1; v<10; v++)
					{
						if (tab[i][v]==0)
							compteur++; // Compte le nombre de chiffres impossibles par case
					}
					
					if (compteur > 1 && compteur < 8)  // Test si la case a entre 2 et 7 impossibilit�s
						temp[ligne] = 9-compteur; // temp stocke le nombre de valeurs possibles
													// et donc le nombre de cases � tester
				}
			}
			
			for (int ligne=0; ligne<9; ligne++) // On regarde si les m�mes n valeurs impossibles 
													  // sont dans 9-n cases
			{
				i=ligne*9 + colonne;
				nombreCasesATester = temp[ligne]; // Nombre de valeurs possibles
				casesPossibles = 1;
				
				if (nombreCasesATester > 1 && nombreCasesATester < 8) // Si la case est sujette � tests
				{
					n=1;
					for (int v=1; v<10; v++) // On �crit dans temp[colonne] les valeurs impossibles
					{
						if  (tab[i][v] == 0)
						{
							temp[ligne]+=n*v; 
							n*=10;
						}
					}
					
					temp[ligne]-=nombreCasesATester; // temp est un nombre contenant 
													   // (nombreCasesATester) chiffres
				
					for (int j=0; j<9; j++) // On regarde si les n valeurs impossibles sont dans 
											// 8-n autres cases
					{
						i = j*9 + colonne;
						
						if (j!=ligne && tab[i][0]==0) // On teste case par case les non remplies
						{
							for (int m=0; m < 9-nombreCasesATester; m++) 
								// m s�lectionne le chiffre de temp[colonne]
							{
								if (tab[i][(temp[ligne]/((int)Math.pow(10,m))) % 10] != 0)
									m=10; // Si une valeur impossible d'une case est possible dans l'autre
								if (m==8-nombreCasesATester)
								{
									casesPossibles++;
									temp[j]=ligne+10; // On retient les cases v�rifiant l'algorithme
										// +10 diff�rencie les temp[j] v�rifiant l'algorithme
										// des temp[j] stockant le nombre de valeurs possibles.
										// C'est 2 tableaux en 1, moins de place m�moire
										// mais beaucoup plus de bordel !															
								}
							}
						}
					}
					
					if (casesPossibles > nombreCasesATester)
						System.err.println("Sudoku impossible, n valeurs doivent �tre dans n-1 cases !");
					
					else if (casesPossibles == nombreCasesATester)
					{
						for (int j=0; j < 9; j++) // On enl�ve les possibilit�s
						{
							i = j*9 + colonne;
							
							if (temp[j] != ligne+10 && j!=ligne)
							{
								d=1;
								
								for (int m=1; m < 10; m++) // On enl�ve les possibilit�s case par case
								{
									if ((temp[ligne]/((int)Math.pow(10,m-d))) % 10!=m)
									{
										d++;
										if (tab[i][m]!=0)
										{
											RechercheFructueuse = true;	
											tab[i][m]=0;
										}
									}
								}	
							}
						}
						
						if (RechercheFructueuse) // On affiche les infos
						{
							d=1;
							System.out.print("Dans la colonne " + (colonne+1) + ", les valeurs ");
							
							for (int m=1; m < 10; m++) 
							{
								if ((temp[ligne]/((int)Math.pow(10,m-d))) % 10!=m)
								{
									d++;
									if (d!=nombreCasesATester+1)
										System.out.print(m + ", ");
									else
										System.out.print(m);
								}
							}
							System.out.print(" ne sont possibles que dans les lignes ");
							
							for (int j=0; j < 9; j++)
							{
								if (temp[j] == ligne+10 || j==ligne)
								{
									if (compteurColonnesAffichees < nombreCasesATester-1)
									{
										System.out.print((j+1) + ", ");
										compteurColonnesAffichees++;
									}
									else
										System.out.println((j+1) + ".");

								}		
							}
							return RechercheFructueuse;
						}
					}
				}
			}
		}
		
		for (int j=0; j<10; j++)
			temp[j]=0;
		
		for (int ligne=0; ligne<9; ligne+=3) // Algorithme sur les r�gions
		{
			for (int colonne=0; colonne<9; colonne+=3) // Trouve le nombre de chiffres impossibles par case
			{
				for (int ligneRegion=0; ligneRegion<3; ligneRegion++) // Algorithme sur les r�gions
				{
					for (int colonneRegion=0; colonneRegion<3; colonneRegion++) // Trouve le nombre de chiffres impossibles par case
					{
						i=(ligne+ligneRegion)*9 + (colonne+colonneRegion);
						compteur = 0 ; 
						temp[ligneRegion*3 + colonneRegion] = 0;
						if (tab[i][0]==0) 
						{
							for (int v=1; v<10; v++)
							{
								if (tab[i][v]==0)
									compteur++; // Compte le nombre de chiffres impossibles par case
							}
							
							if (compteur > 1 && compteur < 8)  // Test si la case a entre 2 et 7 impossibilit�s
								temp[ligneRegion*3 + colonneRegion] = 9-compteur; // temp stocke le nombre de valeurs possibles
															// et donc le nombre de cases � tester
						}
					}
					
					for (int colonneRegion=0; colonneRegion<3; colonneRegion++) // On regarde si les m�mes n valeurs impossibles 
															  // sont dans 9-n cases
					{
						i=(ligne+ligneRegion)*9 + (colonne+colonneRegion);
						nombreCasesATester = temp[ligneRegion*3 + colonneRegion]; // Nombre de valeurs possibles
						casesPossibles = 1;
						
						if (nombreCasesATester > 1 && nombreCasesATester < 8) // Si la case est sujette � tests
						{
							n=1;
							for (int v=1; v<10; v++) // On �crit dans temp[colonne] les valeurs impossibles
							{
								if  (tab[i][v] == 0)
								{
									temp[ligneRegion*3 + colonneRegion]+=n*v; 
									n*=10;
								}
							}
							
							temp[ligneRegion*3 + colonneRegion]-=nombreCasesATester; // temp est un nombre contenant 
															   // (nombreCasesATester) chiffres
						
							for (int j=0; j<9; j++) // On regarde si les n valeurs impossibles sont dans 
													// 8-n autres cases
							{
								i = ligne*9 + colonne + (j/3)*9 + j%3; //
								
								if (j!=ligneRegion*3 + colonneRegion && tab[i][0]==0) // On teste case par case les non remplies
								{
									for (int m=0; m < 9-nombreCasesATester; m++) 
										// m s�lectionne le chiffre de temp[colonne]
									{
										if (tab[i][(temp[ligneRegion*3 + colonneRegion]/((int)Math.pow(10,m))) % 10] != 0)
											m=10; // Si une valeur impossible d'une case est possible dans l'autre
										if (m==8-nombreCasesATester)
										{
											casesPossibles++;
											temp[j]=ligneRegion*3 + colonneRegion+10; // On retient les cases v�rifiant l'algorithme
												// +10 diff�rencie les temp[j] v�rifiant l'algorithme
												// des temp[j] stockant le nombre de valeurs possibles.
												// C'est 2 tableaux en 1, moins de place m�moire
												// mais beaucoup plus de bordel !															
										}
									}
								}
							}
							
							if (casesPossibles > nombreCasesATester)
								System.err.println("Sudoku impossible, n valeurs doivent �tre dans n-1 cases !");
							
							else if (casesPossibles == nombreCasesATester)
							{
								for (int j=0; j < 9; j++) // On enl�ve les possibilit�s
								{
									i = ligne*9 + colonne + (j/3)*9 + j%3;
									
									if (temp[j] != ligneRegion*3 + colonneRegion+10 && j!=ligneRegion*3 + colonneRegion)
									{
										d=1;
										
										for (int m=1; m < 10; m++) // Case par case
										{
											if ((temp[ligneRegion*3 + colonneRegion]/((int)Math.pow(10,m-d))) % 10!=m)
											{
												d++;
												if (tab[i][m]!=0)
												{
													RechercheFructueuse = true;	
													tab[i][m]=0;
												}
											}
										}	
									}
								}
								
								if (RechercheFructueuse) // On affiche les infos
								{
									d=1;
									System.out.print("Dans la r�gion ");
									if (ligne==0)
										System.out.print("en haut ");
									if (ligne==3 && colonne!=3)
										System.out.print("au milieu ");
									if (ligne==6)
										System.out.print("en bas ");
									if (colonne==0)
										System.out.print("� gauche, ");
									if (colonne==3)
										System.out.print("au milieu, ");
									if (colonne==6)
										System.out.print("� droite, ");
									System.out.print("les valeurs ");
									for (int m=1; m < 10; m++) 
									{
										if ((temp[ligneRegion*3 + colonneRegion]/((int)Math.pow(10,m-d))) % 10!=m)
										{
											d++;
											if (d!=nombreCasesATester+1)
												System.out.print(m + ", ");
											else
												System.out.print(m);
										}
									}
									System.out.print(" ne sont possibles que dans les cases ");
									
									for (int j=0; j < 9; j++)
									{
										if (temp[j] == ligneRegion*3 + colonneRegion+10 || j==ligneRegion*3 + colonneRegion)
										{
											if (compteurColonnesAffichees < nombreCasesATester-1)
											{
												System.out.print((j+1) + ", ");
												compteurColonnesAffichees++;
											}
											else
												System.out.println((j+1) + ".");

										}		
									}
									return RechercheFructueuse;
								}
							}
						}
					}
				}
			}
		}
		return RechercheFructueuse;

	}
}
