package app;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

import models.Animal;
import models.Plant;
import models.Player;
import models.PlayerItem;
import models.User;
import services.UserFileHandler;
import services.UserValidator;
import models.items.AnimalProduct;
import models.items.FarmProduct;
import models.items.PlantSeed;
import models.items.Tool;
import views.GameMapView;
import views.GameUIView;

public class Main {
	
	private Player player;
	private ArrayList<char[][]> maps;
	
    private ArrayList<Tool> availableTools;
    private ArrayList<Animal> availableAnimals;
    private ArrayList<PlantSeed> availableSeeds;
	
	private int currMapIndex = 1;
	private char currTile = ' ';
	
	private Scanner sc;
	private Random rand;
	
	private boolean devMode = false;
	private User currentUser;
	
	private static final String RESET = "\u001B[0m";
	private static final String GREEN = "\u001B[32m";
	private static final String YELLOW = "\u001B[33m";
	private static final String WHITE_BRIGHT = "\u001B[97m";
	private static final String BROWN = "\u001B[38;5;94m";

	public static void main(String[] args) {
		new Main().init();
	}
	
	private void init () {
		sc = new Scanner(System.in);
		rand = new Random();
		
		loginOrRegister();
		
		maps = new ArrayList<>();
		maps.add(GameMapView.PLANT_FARM_MAP);
		maps.add(GameMapView.HOME_MAP);
		maps.add(GameMapView.ANIMAL_FARM_MAP);
		
		player = new Player(currentUser.getUsername(), "");
		
		availableTools = new ArrayList<>();
		loadAvailableTools();
		
		availableAnimals = new ArrayList<>();
		loadAvailableAnimals();
		
		availableSeeds = new ArrayList<>();
		loadAvailableSeeds();
		
		loadPlayerData();
		
		run();
	}
	
	private void loadAvailableTools () {
		try {
	    	FileReader file = new FileReader("tools.txt");
			BufferedReader br = new BufferedReader(file);
			
			String line;
			
            while ((line = br.readLine()) != null) {
            	String[] parts = line.split("#");
            	
            	String name = parts[0].trim();
            	
            	int price = Integer.parseInt(parts[1].trim());
            	
            	availableTools.add(new Tool(name, price));
            }
            
            br.close();
	    } catch (FileNotFoundException e) {
	        System.out.println("file not found");
	    } catch (IOException e) {
	        System.out.println("io");
	    }
	}
	
	private void loadAvailableSeeds () {
		try {
	    	FileReader file = new FileReader("plants.txt");
			BufferedReader br = new BufferedReader(file);
			
			String line;
			
            while ((line = br.readLine()) != null) {
            	String[] parts = line.split("#");
            	
            	String name = parts[0].trim();
            	
            	int growthTime = Integer.parseInt(parts[1].trim());
            	
            	double price = Double.parseDouble(parts[2].trim());
            	
                availableSeeds.add(new PlantSeed(name, price, Character.toLowerCase(name.charAt(0)), growthTime));
            }
            
            br.close();
	    } catch (FileNotFoundException e) {
	        System.out.println("file not found");
	    } catch (IOException e) {
	        System.out.println("io");
	    }
	}
	
	private void loadAvailableAnimals () {
		try {
	    	FileReader file = new FileReader("animals.txt");
			BufferedReader br = new BufferedReader(file);
			
			String line;
			
            while ((line = br.readLine()) != null) {
            	String[] parts = line.split("#");
            	
            	String type = parts[0].trim();
            	
            	int harvestRate = Integer.parseInt(parts[1].trim());
            	
            	double price = Double.parseDouble(parts[2].trim());
            	
                availableAnimals.add(new Animal(' ', "", type, "", harvestRate, 0, 0, price, false));
            }
            
            br.close();
	    } catch (FileNotFoundException e) {
	        System.out.println("file not found");
	    } catch (IOException e) {
	        System.out.println("io");
	    }
	}
	
	private void run () {
		while (true) {
			spaceConsole();
			displayMap();
			move();
		}
	}
	
	private void spaceConsole() {
        for (int i = 0; i < 40; i++) {
            System.out.println();
        }
    }
	
	private void pause () {
		System.out.print("Press ENTER to continue...");
		sc.nextLine();
	}
	
	private void displayMap() {
	    char[][] currMap = maps.get(currMapIndex);

	    currMap[player.getPosition().getX()]
	           [player.getPosition().getY()] = 'P';

	    char[][] infoUI = new char[GameUIView.PLAYER_INFO_UI.length][];
	    for (int i = 0; i < GameUIView.PLAYER_INFO_UI.length; i++) {
	        infoUI[i] = GameUIView.PLAYER_INFO_UI[i].clone();
	    }

	    char[][] keybindUI = GameUIView.PLAYER_KEYBINDS_UI;

	    for (int i = 1; i <= 2; i++) {
	        String line = new String(infoUI[i]);
	        int colon = line.indexOf(":");
	        String value = i == 1 ? String.valueOf(player.getDay()) : String.valueOf(player.getMoney());
	        int width = 16;
	        line = line.substring(0, colon + 2) + String.format("%-" + width + "s", value) + "#";
	        infoUI[i] = line.toCharArray();
	    }

	    int mapHeight = currMap.length;
	    int mapWidth = currMap[0].length;
	    int uiHeight = infoUI.length + 1 + keybindUI.length;
	    int totalRows = Math.max(mapHeight, uiHeight);

	    for (int row = 0; row < totalRows; row++) {
	        StringBuilder line = new StringBuilder();

	        if (row < mapHeight) {
	            for (int col = 0; col < currMap[row].length; col++) {
	                line.append(colorize(currMap[row][col]));
	            }
	        } else {
	            line.append(" ".repeat(mapWidth));
	        }

	        line.append("  ");

	        if (row < infoUI.length) {
	            line.append(new String(infoUI[row]));
	        } else if (row == infoUI.length) {
	            line.append(" ".repeat(infoUI[0].length));
	        } else if (row < infoUI.length + 1 + keybindUI.length) {
	            line.append(new String(keybindUI[row - infoUI.length - 1]));
	        } else {
	            line.append(" ".repeat(keybindUI[0].length));
	        }

	        System.out.println(line);
	    }
	}

    private void move() {
        String input = sc.nextLine().toLowerCase().trim();
        if (input.isEmpty()) return;
        
        if (input.equals("devmode")) {
        	devMode = true;
        	player.setMoney(1000000);
        	return;
        }

        char key = input.charAt(0);

        char[][] currMap = maps.get(currMapIndex);

        int newX = player.getPosition().getX();
        int newY = player.getPosition().getY();
        
        switch (key) {
            case 'w': 
            	newX--; 
            	break;
            case 'a': 
            	newY--; 
            	break;
            case 's': 
            	newX++; 
            	break;
            case 'd': 
            	newY++; 
            	break;
            case 'e':
            	openInventory();
                return;
            case 'q':
            	exitGame();
            	return;
            case 'r':
            	if (devMode)
            		initSleep();
            	return;
            case 'g':
            	if (devMode) {
            		for (int i = 0; i < 20; i++) {
            			sleep();
            		}
            	}
            	return;
            case 'u':
            	if (devMode) {
            		insertAnimal("Chicken", "Chicken1");
            		insertAnimal("Chicken", "Chicken2");
            		insertAnimal("Chicken", "Chicken3");
            		insertAnimal("Sheep", "Sheep1");
            		insertAnimal("Sheep", "Sheep2");
            		insertAnimal("Sheep", "Sheep3");
            		insertAnimal("Cow", "Cow1");
            		insertAnimal("Cow", "Cow2");
            		insertAnimal("Cow", "Cow3");
            	}
            	return;
            case 't':
            	if (devMode) {
            		for (Tool tool : availableTools) {
                        player.getInventory().add(
                            new PlayerItem(new Tool(tool.getName(), (int) tool.getPrice()), 1)
                        );
                    }
                    availableTools.clear();
            	}
            	return;
            case 'p':
            	if (devMode) {
            		
            		int grade = getGrade();
            		
            		AnimalProduct egg = new AnimalProduct(
							"Egg", 
							(int) (100 * getGradeMultiplier(grade)), 
							grade
					);
            		
            		AnimalProduct milk = new AnimalProduct(
            				"Milk", 
            				(int) (300 * getGradeMultiplier(grade)), 
            				grade
    				);
            		
            		AnimalProduct wool = new AnimalProduct(
            				"Wool", 
            				(int) (900 * getGradeMultiplier(grade)), 
            				grade
    				);

            		player.addItem(egg, 1);
            		player.addItem(milk, 1);
            		player.addItem(wool, 1);
            	}
            	return;
            case 'k':
            	if (devMode) {
            		PlantSeed wheat = new PlantSeed("Wheat", 50, 'w', 3);
            		player.addItem(wheat, 10);
            	}
            	return;
            case '1':
            	if (devMode) {
            		currMapIndex = 0;
                    player.getPosition().moveTo(10, 21);
                    devMode_ClearAllPlayers();
            	}
            	return;
            case '2':
            	if (devMode) {
            		currMapIndex = 1;
                    player.getPosition().moveTo(10, 21);
                    devMode_ClearAllPlayers();
            	}
            	return;
            case '3':
            	if (devMode) {
            		currMapIndex = 2;
                    player.getPosition().moveTo(10, 21);
                    devMode_ClearAllPlayers();
            	}
            	return;
            default: 
            	return;
        }

        int width = currMap.length;
        int height = currMap[0].length;

        if (newX < 0 || newX >= width ||
            newY < 0 || newY >= height) {
            return;
        }

        if (checkWall(currMap, newX, newY)) 
        	return;

        currMap[player.getPosition().getX()]
               [player.getPosition().getY()] = currTile;

        currTile = currMap[newX][newY];

        player.getPosition().moveTo(newX, newY);

        currMap[newX][newY] = 'P';
        
//        System.out.println("X: " + newX + " Y: " + newY);

        triggerEvent(newX, newY);
    }
    
    private void devMode_ClearAllPlayers() {
        for (char[][] map : maps) {
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {
                    if (map[i][j] == 'P') {
                        map[i][j] = ' ';
                    }
                }
            }
        }
    }
    
    private boolean checkWall(char[][] map, int x, int y) {
        String walls = "#+-_|/\\\"'`':,";

        char tile = map[x][y];

        for (int i = 0; i < walls.length(); i++) {
            if (tile == walls.charAt(i)) {
                return true;
            }
        }

        return false;
    }
    
    private void triggerEvent(int x, int y) {
    	
    	if (currMapIndex == 1) {
            if (x == 10 && y == 0) {
                currMapIndex = 0;
                player.getPosition().moveTo(11, 43);
            } else if (x == 16 && y == 43) {
                currMapIndex = 2;
                player.getPosition().moveTo(5, 0);
            } else if (x == 7 && (y == 21 || y == 22)) {
                initSleep();
            } else if (x == 15 && (y == 16 || y == 17)) {
                initAnimalStore();
            } else if (x == 17 && (y == 31 || y == 32)) {
	    		initToolStore();
	    	}
        } else if (currMapIndex == 0) {
        	if (x == 11 && y == 43) {
        		currMapIndex = 1;
                player.getPosition().moveTo(10, 0);
        	} else if (x == 4 && (y == 23 || y == 24)) {
                initFarmStore();
            } else if (currTile == '.') {
                triggerFarmTile(x, y, true);
            } else if (Character.isUpperCase(currTile) && currTile != 'P') {
                collectPlant(x, y);
            }
        } else if (currMapIndex == 2) {
        	if (x == 5 && y == 0) {
        		currMapIndex = 1;
                player.getPosition().moveTo(16, 43);
        	} else if (currTile == 'C' || currTile == 'c' || currTile == 'S') {
                collectAnimal(x, y);
            }
        }
    }
    
    private void triggerFarmTile (int x, int y, boolean planting) {
    	if (planting) {
    		if (!player.hasItem("Hoe")) {
    			System.out.println("You need a Hoe to plant! Buy one from the tool store.");
    			pause();
    			return;
    		}
    		
    		int counter = 1;
        	ArrayList<PlantSeed> playerSeeds = new ArrayList<>();
        	
        	for (PlayerItem item : player.getInventory()) {
        		if (item.getItem() instanceof PlantSeed) {
        			playerSeeds.add((PlantSeed) item.getItem());
        			System.out.printf("%d. %s Seed - %d\n", counter++, item.getItem().getName(), item.getQuantity());
        		}
        	}
        	
        	if (playerSeeds.isEmpty()) {
        		System.out.println("No seeds in your inventory!");
        		pause();
        		return;
        	}
        	
        	int choice = -1;
        	
        	do {
        		System.out.printf("Which seed do you want to plant [1-%d] [0 to exit]: \n", playerSeeds.size());
        		try {
					choice = sc.nextInt();
					sc.nextLine();
				} catch (Exception e) {
					sc.nextLine();
				}
        	} while (choice < 0 || choice > playerSeeds.size());
        	
        	if (choice == 0) return;
        	
        	PlantSeed selectedSeed = playerSeeds.get(choice - 1);
        	
        	insertPlant(selectedSeed, x, y);
        	
        	currTile = selectedSeed.getSymbol();
        	
        	Iterator<PlayerItem> it = player.getInventory().iterator();
        	while (it.hasNext()) {
        		PlayerItem item = it.next();
        		if (item.getItem() instanceof PlantSeed) {
        			if (item.getItem().getName().equals(selectedSeed.getName())) {
        				item.removeQuantity(1);
        				
        				if (item.isEmpty()) {
        					it.remove();
        				}
        				break;
        			}
        		}
        	}
    	}
    }
    
    private void initSleep () {
        char choice = 0;
        
        do {
            spaceConsole();
            System.out.print("Do you want to sleep? [y/n]: ");
            
            String input = sc.nextLine().trim().toLowerCase();
            
            if (input.length() == 0) {
                System.out.println("Invalid input!");
                pause();
                continue;
            }
            
            choice = input.charAt(0);
            
            if (choice != 'y' && choice != 'n') {
                System.out.println("Invalid input!");
                pause();
            }
            
        } while (choice != 'y' && choice != 'n');
        
        if (choice == 'y') {
            sleep();
        }
    }
    
    private void sleep () {
    	player.advanceDay();
		updateHarvest();
		updateGrowthTime();
		updateFreshness();
    }
    
    private void updateHarvest () {
		for (Animal animal : player.getAnimals()) {
			animal.tickHarvest();
		}
	}
    
    private void updateGrowthTime () {
    	for (Plant plant : player.getPlants()) {
    		if (plant.tickGrowth()) {
    			GameMapView.PLANT_FARM_MAP[plant.getPosition().getX()][plant.getPosition().getY()] = Character.toUpperCase(plant.getSymbol());
    		}
    	}
    }
    
    private void updateFreshness () {
        Iterator<PlayerItem> it = player.getInventory().iterator();

        while (it.hasNext()) {
            PlayerItem item = it.next();

            if (item.getItem() instanceof FarmProduct) {

                FarmProduct farmProduct = (FarmProduct) item.getItem();

                if (farmProduct.tickFreshness()) {
                    it.remove();
                }
            }
        }
    }
     
    private void initAnimalStore () {
    	while (true) {
    		spaceConsole();
    		System.out.println("Animal Shop");
    		System.out.printf("Money: %.2f$\n", player.getMoney());
            System.out.println("1. Buy Farm Animals");
            System.out.println("2. Sell Farm Animals");
            System.out.println("3. Sell Animal Products");
            System.out.println("4. Exit");
            System.out.print(">> ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                    	initBuyAnimal();
                    	break;
                    case 2:
                    	initSellAnimal();
                        break;
                    case 3:
                    	initSellAnimalProduct();
                    	break;
                    case 4:
                    	return;
                }
            } catch (Exception e) {
                sc.nextLine();
            }
        }
    }
    
    private void initBuyAnimal () {
    	while (true) {
			int counter = 1;
    		
			spaceConsole();
			
    		System.out.println("Buy Farm Animals");
    		System.out.println();
    		System.out.println("Money: $" + player.getMoney());
    		System.out.println();
    		
    		System.out.println("================================================");
    		System.out.printf("| %-3s | %-10s | %-12s | %-10s |\n", "No.", "Name", "Harvest Rate", "Price");
    		System.out.println("================================================");
            
            for (Animal animal : availableAnimals) {
            	System.out.printf("| %-3d | %-10s | %-12d | %-10.1f |\n", counter++, animal.getType(), animal.getHarvestRate(), animal.getPrice());
            }
            
            System.out.println("================================================");
            System.out.print("Choose Farm Animals [1-" + (counter - 1) + "] [0 to exit]: ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();
                
                if (choice == 0) return;

                if (choice >= 1 && choice < counter) {
                    Animal selectedAnimal = availableAnimals.get(choice - 1);

                    if (player.getMoney() >= selectedAnimal.getPrice()) {
                        player.spendMoney(selectedAnimal.getPrice());
                        
                        String name = null;
                        boolean nameTaken;
                        
                        do {
                        	System.out.print("Input new farm animal's name [<= 15 characters]: ");
                        	name = sc.nextLine();
                        	
                        	nameTaken = false;
                        	for (Animal a : player.getAnimals()) {
                        		if (a.getName().equalsIgnoreCase(name.trim())) {
                        			nameTaken = true;
                        			System.out.println("Name already taken!");
                        			break;
                        		}
                        	}
                        } while (name.length() > 15 || name.trim().isEmpty() || nameTaken);
                        
                        insertAnimal(selectedAnimal.getType(), name);

                        System.out.println("Successfully bought a farm animal");
                    } else {
                        System.out.println("Not enough money!");
                    }
                } else {
                    System.out.println("Invalid choice!");
                }
                
                pause();
            } catch (Exception e) {
                sc.nextLine();
            }
        }
	}
    
    private void initSellAnimal () {
    	if (player.getAnimals().isEmpty()) {
    		System.out.println("No Animals obtained yet!");
    		pause();
    		return;
    	}
    	
		while (true) {
			int counter = 1;
    		
			spaceConsole();
			
    		System.out.println("Sell Farm Animals");
    		System.out.println();
    		System.out.println("Money: $" + player.getMoney());
    		System.out.println();
    		
    		System.out.println("===================================================");
    		System.out.printf("| %-3s | %-10s | %-15s | %-10s |\n", "No.", "Type", "Name", "Price");
    		System.out.println("===================================================");
    		
            for (Animal animal : player.getAnimals()) {
            	System.out.printf(
            			"| %-3d | %-10s | %-15s | %-10.1f |\n", 
            			counter++,
            			animal.getType(),
            			animal.getName(), 
            			animal.getPrice()
            	);
            }
            
            System.out.println("===================================================");
            System.out.print("Choose Farm Animal to sell [1-" + (counter - 1) + "] [0 to exit]: ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();
                
                if (choice == 0) return;

                if (choice >= 1 && choice < counter) {
                	Animal selectedAnimal = player.getAnimals().get(choice - 1);
                    player.addMoney(selectedAnimal.getPrice());
                    GameMapView.ANIMAL_FARM_MAP[selectedAnimal.getPosition().getX()][selectedAnimal.getPosition().getY()] = ' ';
                    player.getAnimals().remove(selectedAnimal);

                    System.out.println("Sucessfully sold a farm animal!");
                } else {
                    System.out.println("Invalid choice!");
                }

                pause();
            } catch (Exception e) {
                sc.nextLine();
            }
        }
	}
    
    private void initSellAnimalProduct () {
		while (true) {
	    	boolean hasProducts = false;
	    	for (PlayerItem item : player.getInventory()) {
	    		if (item.getItem() instanceof AnimalProduct) {
	    			hasProducts = true;
	    			break;
	    		}
	    	}
	    	
	    	if (!hasProducts) {
	    		System.out.println("No Animal Products in inventory!");
	    		pause();
	    		return;
	    	}
	    	
			int counter = 1;
    		
			spaceConsole();
			
			System.out.println("Sell Animal Products");
    		System.out.println();
    		System.out.println("Money: $" + player.getMoney());
    		System.out.println();
			
			System.out.println("===========================================================");
    		System.out.printf("| %-3s | %-10s | %-10s | %-10s | %-10s |\n", "No.", "Name", "Grade", "Quantity", "Price");
    		System.out.println("===========================================================");
    		
    		for (PlayerItem item : player.getInventory()) {
        		if (item.getItem() instanceof AnimalProduct) {
                    AnimalProduct animalProduct = ((AnimalProduct) item.getItem());
                    System.out.printf(
                    		"| %-3d | %-10s | %-10d | %-10d | %-10.1f |\n", 
                    		counter++, 
                    		animalProduct.getName(), 
                    		animalProduct.getGrade(), 
                    		item.getQuantity(),
                    		(double) (animalProduct.getPrice() * getGradeMultiplier(animalProduct.getGrade()))
                    );
                }
            }
            
            System.out.println("===========================================================");
            System.out.print("Choose Farm Animal to sell [1-" + (counter - 1) + "] [0 to exit]: ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();
                
                if (choice == 0) return;

                if (choice >= 1 && choice < counter) {
                	int selectedIndex = findAnimalProduct(choice);
                	
                	PlayerItem selectedPlayerItem = player.getInventory().get(selectedIndex);
                	AnimalProduct selectedAnimalProduct = (AnimalProduct) selectedPlayerItem.getItem();
                	
                	int quantityToSell = -1;
                	
                	do {
                		System.out.printf("How many items do you want to sell [%d-%d]: ", 1, selectedPlayerItem.getQuantity());
                		try {
							quantityToSell = sc.nextInt();
							sc.nextLine();
						} catch (Exception e) {
							sc.nextLine();
						}
                	} while(quantityToSell < 1 || quantityToSell > selectedPlayerItem.getQuantity());

                	player.addMoney(
                			selectedAnimalProduct.getPrice() * getGradeMultiplier(selectedAnimalProduct.getGrade()) * quantityToSell
                	);
                	
                	player.removeItemQuantity(selectedIndex, quantityToSell);
                	
                    System.out.println("Sucessfully sold an animal product!");
                } else {
                    System.out.println("Invalid choice!");
                }

                pause();
            } catch (Exception e) {
                sc.nextLine();
            }
        }
    }
    
    private int findAnimalProduct(int choice) {
		int counter = 1;
		for (PlayerItem item : player.getInventory()) {
            if (item.getItem() instanceof AnimalProduct) {
                if (counter == choice) {
                	return player.getInventory().indexOf(item);
                }
                
                counter++;
            }
        }
		
		return -1;
	}
    
    private void initFarmStore () {
    	while (true) {
    		spaceConsole();
    		System.out.println("Farm Shop");
    		System.out.printf("Money: %.2f$\n", player.getMoney());
            System.out.println("1. Buy Seeds");
            System.out.println("2. Sell Farm Products");
            System.out.println("3. Exit");
            System.out.print(">> ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                    	initBuySeed();
                    	break;
                    case 2:
                    	initSellFarmProduct();
                    	break;
                    case 3:
                    	return;
                }
            } catch (Exception e) {
                sc.nextLine();
            }
        }
	}
    
    private void initBuySeed () {
    	while (true) {
			int counter = 1;
    		
			spaceConsole();
			
    		System.out.println("Buy Seeds");
    		System.out.println();
    		System.out.println("Money: $" + player.getMoney());
    		System.out.println();
    		
    		System.out.println("================================================");
    		System.out.printf("| %-3s | %-10s | %-12s | %-10s |\n", "No.", "Name", "Growth Time", "Price");
    		System.out.println("================================================");
            
            for (PlantSeed seed : availableSeeds) {
            	System.out.printf("| %-3d | %-10s | %-12d | %-10.1f |\n", counter++, seed.getName(), seed.getGrowthTime(), seed.getPrice());
            }
            
            System.out.println("================================================");
            System.out.print("Choose Seed [1-" + (counter - 1) + "] [0 to exit]: ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();
                
                if (choice == 0) return;

                if (choice >= 1 && choice < counter) {
                    PlantSeed selectedSeed = availableSeeds.get(choice - 1);
                    
                    int quantity = -1;

                    do {
                    	try {
                    		System.out.printf("How many %s seeds would you like to purchase: ", selectedSeed.getName());
                    		quantity = sc.nextInt();
                    		sc.nextLine();
						} catch (Exception e) {
							sc.nextLine();
						}
                    } while(quantity <= 0);
                    
                    double totalPrice = selectedSeed.getPrice() * quantity;

                    if (player.spendMoney(totalPrice)) {
                        player.addItem(selectedSeed, quantity);

                        System.out.printf("Successfully bought %d %s Seeds\n", quantity, selectedSeed.getName());
                    } else {
                        System.out.println("Not enough money!");
                    }
                } else {
                    System.out.println("Invalid choice!");
                }
                
                pause();
            } catch (Exception e) {
                sc.nextLine();
            }
        }
    }
    
    private void initSellFarmProduct () {
		while (true) {
	    	boolean hasProducts = false;
	    	for (PlayerItem item : player.getInventory()) {
	    		if (item.getItem() instanceof FarmProduct) {
	    			hasProducts = true;
	    			break;
	    		}
	    	}
	    	
	    	if (!hasProducts) {
	    		System.out.println("No Farm Products in inventory!");
	    		pause();
	    		return;
	    	}
	    	
			int counter = 1;
    		
			spaceConsole();
			
			System.out.println("Sell Farm Products");
    		System.out.println();
    		System.out.println("Money: $" + player.getMoney());
    		System.out.println();
			
			System.out.println("===========================================================");
    		System.out.printf("| %-3s | %-10s | %-10s | %-10s | %-10s |\n", "No.", "Name", "Grade", "Quantity", "Price");
    		System.out.println("===========================================================");
    		
    		for (PlayerItem item : player.getInventory()) {
        		if (item.getItem() instanceof FarmProduct) {
        			FarmProduct farmProduct = ((FarmProduct) item.getItem());
                    System.out.printf(
                    		"| %-3d | %-10s | %-10d | %-10d | %-10.1f |\n", 
                    		counter++, 
                    		farmProduct.getName(), 
                    		farmProduct.getFreshness(), 
                    		item.getQuantity(),
                    		(double) (farmProduct.getPrice() * getFreshnessMultiplier(farmProduct.getFreshness()) * 2)
                    );
                }
            }
            
            System.out.println("===========================================================");
            System.out.print("Choose Farm Product to sell [1-" + (counter - 1) + "] [0 to exit]: ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();
                
                if (choice == 0) return;

                if (choice >= 1 && choice < counter) {
                	int selectedIndex = findFarmProduct(choice);
                	
                	PlayerItem selectedPlayerItem = player.getInventory().get(selectedIndex);
                	FarmProduct selectedFarmProduct = (FarmProduct) selectedPlayerItem.getItem();
                	
                	int quantityToSell = -1;
                	
                	do {
                		System.out.printf("How many items do you want to sell [%d-%d]: ", 1, selectedPlayerItem.getQuantity());
                		try {
							quantityToSell = sc.nextInt();
							sc.nextLine();
						} catch (Exception e) {
							sc.nextLine();
						}
                	} while(quantityToSell < 1 || quantityToSell > selectedPlayerItem.getQuantity());

                	player.addMoney(
                			selectedFarmProduct.getPrice() * getFreshnessMultiplier(selectedFarmProduct.getFreshness()) * 2 * quantityToSell
                	);
                	
                	player.removeItemQuantity(selectedIndex, quantityToSell);
                	
                    System.out.println("Sucessfully sold a farm product!");
                } else {
                    System.out.println("Invalid choice!");
                }

                pause();
            } catch (Exception e) {
                sc.nextLine();
            }
        }
    }
    
    private int findFarmProduct(int choice) {
		int counter = 1;
		for (PlayerItem item : player.getInventory()) {
            if (item.getItem() instanceof FarmProduct) {
                if (counter == choice) {
                	return player.getInventory().indexOf(item);
                }
                
                counter++;
            }
        }
		
		return -1;
	}
    
    private void initToolStore () {
    	while (true) {
			int counter = 1;
    		
			spaceConsole();
    		System.out.println("Buy Tools");
    		System.out.println();
    		System.out.println("Money: $" + player.getMoney());
    		System.out.println();
    		
    		if (availableTools.isEmpty()) {
    			System.out.println("You already buy all tools!");
    			pause();
    			return;
    		}
            System.out.println("=================================");
            System.out.printf("| %-3s | %-10s | %-10s |\n", "No.", "Name", "Price");
            System.out.println("=================================");
            
            for (Tool tool : availableTools) {
            	System.out.printf("| %-3s | %-10s | %-10s |\n", counter++, tool.getName(), tool.getPrice());
            }
            
            System.out.println("=================================");
            System.out.print("Choose Tool [1-" + (counter - 1) + "] [0 to exit]: ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();
                
                if (choice == 0) return;

                if (choice >= 1 && choice < counter) {
                    Tool selectedTool = availableTools.get(choice - 1);
                    int price = (int) selectedTool.getPrice();

                    if (player.spendMoney(price)) {
                        player.getInventory().add(new PlayerItem(new Tool(selectedTool.getName(), price), 1));
                        availableTools.remove(selectedTool);

                        System.out.println("Successfully buy a tool");
                    } else {
                        System.out.println("Not enough money!");
                    }
                } else {
                    System.out.println("Invalid choice!");
                }

                pause();
            } catch (Exception e) {
                sc.nextLine();
            }
        }
    }
    
    private void insertAnimal(String type, String name) {
	    int height = GameMapView.ANIMAL_FARM_MAP.length;
	    int width = GameMapView.ANIMAL_FARM_MAP[0].length;
	    
	    int animalX, animalY;
	    
	    char symbol = 0;
	    String animalProduct = null;
	    int harvestRate = 0;
	    double price = 0;
	    
	    if (type.equals("Chicken")) {
	    	symbol = 'c';
	        animalProduct = "Egg";
	        harvestRate = 1;
	        price = 200.0;
	    } else if (type.equals("Cow")) {
	    	symbol = 'C';
	        animalProduct = "Milk";
	        harvestRate = 2;
	        price = 300.0;
	    } else if (type.equals("Sheep")) {
	    	symbol = 'S';
	        animalProduct = "Wool";
	        harvestRate = 5;
	        price = 500.0;
	    }

	    while (true) {
	        animalX = rand.nextInt(height);
	        animalY = rand.nextInt(width);

	        if (GameMapView.ANIMAL_FARM_MAP[animalX][animalY] != ' ') continue;

	        boolean occupied = false;
	        for (Animal animal : player.getAnimals()) {
	            if (animal.getPosition().getX() == animalX && animal.getPosition().getY() == animalY) {
	                occupied = true;
	                break;
	            }
	        }

	        if (!occupied) {
	            Animal animal = new Animal(symbol, name, type, animalProduct, harvestRate, animalX, animalY, price, true);
	            player.getAnimals().add(animal);
	            GameMapView.ANIMAL_FARM_MAP[animalX][animalY] = symbol;
	            break;
	        }
	    }
	}
    
    private void insertPlant(PlantSeed selectedSeed, int x, int y) {
	    Plant plant = new Plant(selectedSeed.getSymbol(), selectedSeed.getName(), x, y, selectedSeed.getGrowthTime(), selectedSeed.getPrice(), false);
	    player.getPlants().add(plant);
	}
    
    private void openInventory () {
    	while (true) {
    		spaceConsole();
    		System.out.println("Inventory Menu");
            System.out.println("1. View Animal Products");
            System.out.println("2. View Farm Products");
            System.out.println("3. View Animals");
            System.out.println("4. View Tools");
            System.out.println("5. View Plant Seeds");
            System.out.println("6. Exit");
            System.out.print(">> ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                    	initViewAnimalProducts();
                        break;
                    case 2:
                    	initViewFarmProducts();
                    	break;
                    case 3:
                    	initViewAnimals();
                        break;
                    case 4:
                    	initViewTools();
                    	break;
                    case 5:
                    	initViewPlantSeeds();
                    	break;
                    case 6:
                    	return;
                }
            } catch (Exception e) {
                sc.nextLine();
            }
        }
    }
    
    private void initViewAnimalProducts () {
    	spaceConsole();
    	
    	int counter = 1;
    	
    	for (PlayerItem playerItem : player.getInventory()) {
    		if (playerItem.getItem() instanceof AnimalProduct) {
    			System.out.printf(
    					"%d. %s(%d) - %d\n",
    					counter++,
    					playerItem.getItem().getName(),
    					((AnimalProduct) playerItem.getItem()).getGrade(),
    					playerItem.getQuantity()
    			);
    		}
    	}
    	
    	if (counter == 1) {
    		System.out.println("No animal products in inventory!");
    	}
    	
    	pause();
    }
    
    private void initViewFarmProducts () {
    	spaceConsole();
    	
    	int counter = 1;
    	
    	for (PlayerItem playerItem : player.getInventory()) {
    		if (playerItem.getItem() instanceof FarmProduct) {
    			System.out.printf(
    					"%d. %s(%d) - %d\n",
    					counter++,
    					playerItem.getItem().getName(),
    					((FarmProduct) playerItem.getItem()).getFreshness(),
    					playerItem.getQuantity()
    			);
    		}
    	}
    	
    	if (counter == 1) {
    		System.out.println("No farm products in inventory!");
    	}
    	
    	pause();
    }
    
    private void initViewAnimals () {
    	spaceConsole();
    	
    	int counter = 1;
    	
    	for (Animal animal : player.getAnimals()) {
			System.out.printf(
					"%d. %s(%s)\n",
					counter++,
					animal.getName(),
					animal.getType()
			);
    	}
    	
    	if (counter == 1) {
    		System.out.println("No animals in inventory!");
    	}
    	
    	pause();
    }
    
    private void initViewTools () {
    	spaceConsole();
    	
    	int counter = 1;
    	
    	for (PlayerItem playerItem : player.getInventory()) {
    		if (playerItem.getItem() instanceof Tool) {
    			System.out.printf(
    					"%d. %s\n",
    					counter++,
    					playerItem.getItem().getName()
    			);
    		}
    	}
    	
    	if (counter == 1) {
    		System.out.println("No tools in inventory!");
    	}
    	
    	pause();
    }
    
    private void initViewPlantSeeds () {
    	spaceConsole();
    	
    	int counter = 1;
    	
    	for (PlayerItem playerItem : player.getInventory()) {
    		if (playerItem.getItem() instanceof PlantSeed) {
    			System.out.printf(
    					"%d. %s - %d\n",
    					counter++,
    					playerItem.getItem().getName(),
    					playerItem.getQuantity()
    			);
    		}
    	}
    	
    	if (counter == 1) {
    		System.out.println("No plant seeds in inventory!");
    	}
    	
    	pause();
    }
    
    private void collectAnimal (int animalX, int animalY) {
		for (Animal animal : player.getAnimals()) {
	        if (animal.getPosition().getX() == animalX && animal.getPosition().getY() == animalY) {
	        	if (!animal.isHarvestable()) break;
	        	
	        	int choice = 0;
	        	int grade = getGrade();
	        	
	        	while (true) {
	        		System.out.printf("Want to take %s %s?\n", possessive(animal.getName()), animal.getAnimalProduct());
	        		System.out.println("1. Take");
	        		System.out.println("2. Don't take");
	        		System.out.print(">> ");
	        		try {
	                    choice = sc.nextInt();
	                    sc.nextLine();

	                    if (choice == 1) {
		        			if (animal.getSymbol() == 'C' && !player.hasItem("Bucket")) {
		        				System.out.println("You don't have a bucket to get " + possessive(animal.getName()) + " " + animal.getAnimalProduct());
		        				pause();
		        				return;
		        			} else if (animal.getSymbol() == 'S' && !player.hasItem("Shears")) {
		        				System.out.println("You don't have shears to get " + possessive(animal.getName()) + " " + animal.getAnimalProduct());
		        				pause();
		        				return;
		        			} else {
		        				
		        				AnimalProduct newProduct = new AnimalProduct(
    		        						animal.getAnimalProduct(), 
    		        						(int) (animal.getPrice() * getGradeMultiplier(grade)), 
    		        						grade
    		        				);
		        				
		        				player.addItem(newProduct, 1);
		        						
		        				animal.collectProduct();
		        				break;
		        			}
		        		}
	                    
	                    if (choice == 2) break;
	                    
	                    if (choice < 1 || choice > 2) {
	                    	System.out.println("Invalid range number input!");
	                    	pause();
	                    }
	                } catch (Exception e) {
	                    sc.nextLine();
	                }
	        	}
	        }
	    }
	}
    
    private void collectPlant(int plantX, int plantY) {

        Iterator<Plant> it = player.getPlants().iterator();

        while (it.hasNext()) {
            Plant plant = it.next();

            if (plant.getPosition().getX() == plantX && plant.getPosition().getY() == plantY) {

                int freshness = 5;

                FarmProduct newProduct = new FarmProduct(
                        plant.getName(),
                        (int) (plant.getPrice() * getFreshnessMultiplier(freshness)),
                        freshness
                );

                player.addItem(newProduct, 1);

                it.remove();

                GameMapView.PLANT_FARM_MAP[plantX][plantY] = '.';

                currTile = '.';

                break;
            }
        }
    }
    
    private int getGrade() {
        int gradeRand = rand.nextInt(100) + 1;
        int day = player.getDay();

        int grade2Chance = day;
        int grade3Chance = day / 2;

        if (gradeRand <= grade2Chance) {
            return 2;
        } else if (gradeRand <= grade2Chance + grade3Chance) {
            return 3;
        } else {
            return 1;
        }
    }
	
	private int getGradeMultiplier (int grade) {
		if (grade == 1) 
			return 1;
		else if (grade == 2) 
			return 2;
		else 
			return 5;
	}
	
	private double getFreshnessMultiplier (int freshness) {
		if (freshness == 1) 
			return 0.25;
		else if (freshness == 3) 
			return 0.5;
		else 
			return 1;
	}
	
	private String colorize (char c) {
		switch (c) {
			case 'P': return GREEN + c + RESET;
			case 'c': return YELLOW + c + RESET;
			case 'C': return BROWN + c + RESET;
			case 'S': return WHITE_BRIGHT + c + RESET;
			default: return String.valueOf(c);
		}
	}
	
	private String possessive (String name) {
		if (name.toLowerCase().endsWith("s")) {
			return name + "'";
		}
		return name + "'s";
	}
	
	private void loginOrRegister () {
		while (true) {
			spaceConsole();
			System.out.println("                                                                                   ");
			System.out.println("    ▄▄▄                                          ▄▄▄              ▄▄ ▄▄             ");
			System.out.println("   ██▀▀█▄ █▄               █▄                   █▀██  ██  ██▀▀     ██ ██            ");
			System.out.println("   ██ ▄█▀▄██▄      ▄       ██                     ██  ██  ██       ██ ██            ");
			System.out.println("   ██▀▀█▄ ██ ▄▀▀█▄ ████▄▄████ ▄█▀█▄▀█▄ █▄ ██▀     ██  ██  ██ ▄▀▀█▄ ██ ██ ▄█▀█▄ ██ ██");
			System.out.println(" ▄ ██  ▄█ ██ ▄█▀██ ██   ██ ██ ██▄█▀ ██▄██▄██      ██▄ ██▄ ██ ▄█▀██ ██ ██ ██▄█▀ ██▄██");
			System.out.println(" ▀██████▀▄██▄▀█▄██▄█▀  ▄█▀███▄▀█▄▄▄  ▀██▀██▀      ▀████▀███▀▄▀█▄██▄██▄██▄▀█▄▄▄▄▄▀██▀");
			System.out.println("                                                                                 ██ ");
			System.out.println("                                                                               ▀▀▀  ");
			System.out.println();

			System.out.println("1. Login");
			System.out.println("2. Register");
			System.out.println("3. Tutorial");
			System.out.println("4. Exit");
			System.out.print(">> ");
			
			try {
				int choice = sc.nextInt();
				sc.nextLine();
				
				switch (choice) {
					case 1:
						if (initLogin()) return;
						break;
					case 2:
						if (initRegister()) return;
						break;
					case 3:
						showTutorial();
						break;
					case 4:
						System.out.println("Goodbye!");
						System.exit(0);
				}
			} catch (Exception e) {
				sc.nextLine();
			}
		}
	}
	
	private boolean initLogin () {
		spaceConsole();
		System.out.println(" _                 _       ");
		System.out.println("| |               (_)      ");
		System.out.println("| |     ___   __ _ _ _ __  ");
		System.out.println("| |    / _ \\ / _` | | '_ \\ ");
		System.out.println("| |___| (_) | (_| | | | | |");
		System.out.println("\\_____/\\___/ \\__, |_|_| |_|");
		System.out.println("              __/ |        ");
		System.out.println("             |___/         ");
		System.out.println();

		System.out.print("Username (0 to go back): ");
		String username = sc.nextLine().trim();
		if (username.equals("0")) return false;
		
		System.out.print("Password (0 to go back): ");
		String password = sc.nextLine().trim();
		if (password.equals("0")) return false;
		
		User user = UserFileHandler.authenticate(username, password);
		if (user != null) {
			currentUser = user;
			System.out.println("Login successful!");
			pause();
			return true;
		} else {
			System.out.println("Invalid username or password!");
			pause();
			return false;
		}
	}
	
	private boolean initRegister () {
		spaceConsole();
		System.out.println("______           _     _            ");
		System.out.println("| ___ \\         (_)   | |           ");
		System.out.println("| |_/ /___  __ _ _ ___| |_ ___ _ __ ");
		System.out.println("|    // _ \\/ _` | / __| __/ _ \\ '__|");
		System.out.println("| |\\ \\  __/ (_| | \\__ \\ ||  __/ |   ");
		System.out.println("\\_| \\_\\___|\\__, |_|___/\\__\\___|_|   ");
		System.out.println("            __/ |                   ");
		System.out.println("           |___/                    ");
		System.out.println();
		
		String username;
		while (true) {
			System.out.print("Username (>= 8 characters) [0 to go back]: ");
			username = sc.nextLine().trim();
			
			if (username.equals("0")) return false;
			
			if (!UserValidator.isValidUsername(username)) {
				System.out.println("Username must be at least 8 characters!");
				continue;
			}
			
			if (UserFileHandler.isUsernameTaken(username)) {
				System.out.println("Username already taken!");
				continue;
			}
			
			break;
		}
		
		String password;
		while (true) {
			System.out.print("Password (>= 8 characters, at least 1 letter and 1 number) [0 to go back]: ");
			password = sc.nextLine().trim();
			
			if (password.equals("0")) return false;
			
			if (!UserValidator.isValidPassword(password)) {
				System.out.println("Password must be at least 8 characters and contain at least 1 letter and 1 number!");
				continue;
			}
			
			break;
		}
		
		UserFileHandler.register(username, password);
		currentUser = new User(username, password);
		System.out.println("Registration successful!");
		pause();
		return true;
	}
	
	private void exitGame () {
		savePlayerData();
		System.out.println("Game saved successfully! Goodbye!");
		System.exit(0);
	}
	
	private void savePlayerData () {
		try {
			File dir = new File("user_data");
			if (!dir.exists()) {
				dir.mkdir();
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter("user_data/" + currentUser.getUsername() + "_data.txt"));
			
			bw.write(String.format("PLAYER#%.2f#%d#%d#%d#%d#%c",
					player.getMoney(), player.getDay(), currMapIndex,
					player.getPosition().getX(), player.getPosition().getY(), currTile));
			bw.newLine();

			for (PlayerItem item : player.getInventory()) {
				if (item.getItem() instanceof Tool) {
					bw.write(String.format("TOOL#%s#%d", item.getItem().getName(), item.getQuantity()));
				} else if (item.getItem() instanceof PlantSeed) {
					PlantSeed seed = (PlantSeed) item.getItem();
					bw.write(String.format("SEED#%s#%.2f#%c#%d#%d",
							seed.getName(), seed.getPrice(), seed.getSymbol(), seed.getGrowthTime(), item.getQuantity()));
				} else if (item.getItem() instanceof AnimalProduct) {
					AnimalProduct ap = (AnimalProduct) item.getItem();
					bw.write(String.format("ANIMAL_PRODUCT#%s#%.2f#%d#%d",
							ap.getName(), ap.getPrice(), ap.getGrade(), item.getQuantity()));
				} else if (item.getItem() instanceof FarmProduct) {
					FarmProduct fp = (FarmProduct) item.getItem();
					bw.write(String.format("FARM_PRODUCT#%s#%.2f#%d#%d",
							fp.getName(), fp.getPrice(), fp.getFreshness(), item.getQuantity()));
				}
				bw.newLine();
			}
			
			for (Animal animal : player.getAnimals()) {
				bw.write(String.format("ANIMAL#%c#%s#%s#%s#%d#%d#%d#%.2f#%b",
						animal.getSymbol(), animal.getName(), animal.getType(), animal.getAnimalProduct(),
						animal.getHarvestRate(), animal.getPosition().getX(), animal.getPosition().getY(),
						animal.getPrice(), animal.isHarvestable()));
				bw.newLine();
			}
			
			for (Plant plant : player.getPlants()) {
				bw.write(String.format("PLANT#%c#%s#%d#%d#%d#%.2f#%b",
						plant.getSymbol(), plant.getName(), plant.getPosition().getX(), plant.getPosition().getY(),
						plant.getGrowthTime(), plant.getPrice(), plant.isHarvestable()));
				bw.newLine();
			}
			
			bw.close();
		} catch (IOException e) {
			System.out.println("Error saving player data!");
		}
	}
	
	private void loadPlayerData () {
		File saveFile = new File("user_data/" + currentUser.getUsername() + "_data.txt");
		if (!saveFile.exists()) return;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(saveFile));
			String line;
			
			while ((line = br.readLine()) != null) {
				String[] parts = line.split("#");
				String type = parts[0];
				
				switch (type) {
					case "PLAYER":
						player.setMoney(Double.parseDouble(parts[1]));
						player.setDay(Integer.parseInt(parts[2]));
						currMapIndex = Integer.parseInt(parts[3]);
						player.getPosition().moveTo(
							Integer.parseInt(parts[4]),
							Integer.parseInt(parts[5])
						);
						currTile = parts[6].charAt(0);
						break;
					
					case "TOOL":
						String toolName = parts[1];
						int toolQty = Integer.parseInt(parts[2]);
						Tool loadedTool = new Tool(toolName, 0);
						player.getInventory().add(new PlayerItem(
								new Tool(toolName, (int) loadedTool.getPrice()), toolQty));
						break;
					
					case "SEED":
						String seedName = parts[1];
						double seedPrice = Double.parseDouble(parts[2]);
						char seedSymbol = parts[3].charAt(0);
						int seedGrowth = Integer.parseInt(parts[4]);
						int seedQty = Integer.parseInt(parts[5]);
						player.getInventory().add(new PlayerItem(
								new PlantSeed(seedName, seedPrice, seedSymbol, seedGrowth), seedQty));
						break;
					
					case "ANIMAL_PRODUCT":
						String apName = parts[1];
						double apPrice = Double.parseDouble(parts[2]);
						int apGrade = Integer.parseInt(parts[3]);
						int apQty = Integer.parseInt(parts[4]);
						player.getInventory().add(new PlayerItem(
								new AnimalProduct(apName, (int) apPrice, apGrade), apQty));
						break;
					
					case "FARM_PRODUCT":
						String fpName = parts[1];
						double fpPrice = Double.parseDouble(parts[2]);
						int fpFreshness = Integer.parseInt(parts[3]);
						int fpQty = Integer.parseInt(parts[4]);
						player.getInventory().add(new PlayerItem(
								new FarmProduct(fpName, fpPrice, fpFreshness), fpQty));
						break;
					
					case "ANIMAL":
						char aSymbol = parts[1].charAt(0);
						String aName = parts[2];
						String aType = parts[3];
						String aProduct = parts[4];
						int aHarvestRate = Integer.parseInt(parts[5]);
						int aX = Integer.parseInt(parts[6]);
						int aY = Integer.parseInt(parts[7]);
						double aPrice = Double.parseDouble(parts[8]);
						boolean aHarvestable = Boolean.parseBoolean(parts[9]);
						Animal animal = new Animal(aSymbol, aName, aType, aProduct, aHarvestRate, aX, aY, aPrice, aHarvestable);
						player.getAnimals().add(animal);
						GameMapView.ANIMAL_FARM_MAP[aX][aY] = aSymbol;
						break;
					
					case "PLANT":
						char pSymbol = parts[1].charAt(0);
						String pName = parts[2];
						int pX = Integer.parseInt(parts[3]);
						int pY = Integer.parseInt(parts[4]);
						int pGrowthTime = Integer.parseInt(parts[5]);
						double pPrice = Double.parseDouble(parts[6]);
						boolean pHarvestable = Boolean.parseBoolean(parts[7]);
						Plant plant = new Plant(pSymbol, pName, pX, pY, pGrowthTime, pPrice, pHarvestable);
						player.getPlants().add(plant);
						if (pHarvestable) {
							GameMapView.PLANT_FARM_MAP[pX][pY] = Character.toUpperCase(pSymbol);
						} else {
							GameMapView.PLANT_FARM_MAP[pX][pY] = pSymbol;
						}
						break;
				}
			}
			
			br.close();
			
			Iterator<Tool> it = availableTools.iterator();
			while (it.hasNext()) {
				Tool tool = it.next();
				for (PlayerItem item : player.getInventory()) {
					if (item.getItem() instanceof Tool && item.getItem().getName().equals(tool.getName())) {
						it.remove();
						break;
					}
				}
			}
			
		} catch (FileNotFoundException e) {

		} catch (IOException e) {
			System.out.println("Error loading player data!");
		}
	}
	
	private void showTutorial () {
		String[][] pages = getTutorialPages();
		int currentPage = 0;
		
		while (true) {
			spaceConsole();
			
			System.out.println("============================================================");
			System.out.println("               BTARDEW WALLEY - TUTORIAL");
			System.out.println("============================================================");
			System.out.println();
			
			String[] page = pages[currentPage];
			
			System.out.println("  >> " + page[0] + " <<");
			System.out.println("------------------------------------------------------------");
			
			for (int i = 1; i < page.length; i++) {
				System.out.println("  " + page[i]);
			}
			
			System.out.println();
			System.out.println("------------------------------------------------------------");
			System.out.printf("  Page %d / %d\n", currentPage + 1, pages.length);
			System.out.println("------------------------------------------------------------");
			
			StringBuilder nav = new StringBuilder("  ");
			if (currentPage > 0) nav.append("[P] Previous  ");
			if (currentPage < pages.length - 1) nav.append("[N] Next  ");
			nav.append("[Q] Back to Menu");
			System.out.println(nav.toString());
			System.out.print("  >> ");
			
			String tutInput = sc.nextLine().trim().toLowerCase();
			
			if (tutInput.equals("n") && currentPage < pages.length - 1) {
				currentPage++;
			} else if (tutInput.equals("p") && currentPage > 0) {
				currentPage--;
			} else if (tutInput.equals("q")) {
				return;
			}
		}
	}
	
	private String[][] getTutorialPages () {
		return new String[][] {
			{
				"GETTING STARTED",
				"",
				"Welcome to Btardew Walley!",
				"A terminal-based farming and animal husbandry simulator.",
				"",
				"Before entering the game world, you must create an",
				"account or log in with an existing one.",
				"",
				"  Account Requirements:",
				"  - Username: at least 8 characters",
				"  - Password: at least 8 characters,",
				"              must contain 1 letter and 1 number",
				"",
				"Once logged in, you start with $1,000 and begin",
				"on the Home Map. Your progress is saved when you",
				"exit with 'q', so you can pick up where you left off!"
			},
			{
				"THE WORLD",
				"",
				"The game world is split into three interconnected maps:",
				"",
				"  1. HOME MAP",
				"     Your starting area. Here you can:",
				"     - Sleep in your bed to advance time",
				"     - Visit the Animal Shop to buy/sell animals",
				"     - Visit the Tool Shop for essential equipment",
				"",
				"  2. PLANT FARM MAP (North of Home)",
				"     The farming area. Here you can:",
				"     - Buy seeds from the Farm Shop",
				"     - Plant crops on dirt tiles (.)",
				"     - Harvest mature crops",
				"     - Sell farm products",
				"",
				"  3. ANIMAL FARM MAP (East of Home)",
				"     The grazing pasture for your livestock.",
				"     Animals will appear here after purchase."
			},
			{
				"CONTROLS",
				"",
				"Move your character (P) onto points of interest:",
				"",
				"  Movement:",
				"    W  -  Move Up",
				"    S  -  Move Down",
				"    A  -  Move Left",
				"    D  -  Move Right",
				"",
				"  Actions:",
				"    E  -  Open Inventory Menu",
				"    Q  -  Save & Exit Game",
				"",
				"  Other:",
				"    ENTER  -  Continue dialogues",
				"",
				"  The Inventory Menu lets you view:",
				"    1. Animal Products  2. Farm Products",
				"    3. Animals          4. Tools",
				"    5. Plant Seeds"
			},
			{
				"PLANT FARMING",
				"",
				"Head North to the Plant Farm map to grow crops!",
				"",
				"  BUYING SEEDS:",
				"  Step on the Farm Shop tile to buy seeds.",
				"    - Wheat Seed    : $50  (grows in 3 days)",
				"    - Beetroot Seed : $100 (grows in 5 days)",
				"",
				"  PLANTING:",
				"  Step onto an empty dirt tile (.) to plant.",
				"  * You MUST have a Hoe in your inventory!",
				"  * Enter 0 to exit the planting menu.",
				"",
				"  HARVESTING:",
				"  After enough days of sleeping, crops mature.",
				"  A lowercase symbol (w, b) becomes UPPERCASE (W, B).",
				"  Step on a mature crop to auto-harvest it!",
				"",
				"  Sell harvested products at the Farm Shop."
			},
			{
				"ANIMAL HUSBANDRY",
				"",
				"Buy animals from the Animal Shop on the Home Map.",
				"Each animal needs a unique name (max 15 characters).",
				"",
				"  ANIMALS & PRODUCTS:",
				"  +----------+--------+--------+---------+----------+",
				"  | Animal   | Symbol | Price  | Product | Harvest  |",
				"  +----------+--------+--------+---------+----------+",
				"  | Chicken  |   c    |  $200  |  Egg    | 1 day    |",
				"  | Cow      |   C    |  $300  |  Milk   | 2 days   |",
				"  | Sheep    |   S    |  $500  |  Wool   | 5 days   |",
				"  +----------+--------+--------+---------+----------+",
				"",
				"  TOOL REQUIREMENTS:",
				"  - Chickens: No tool needed",
				"  - Cows:     Requires a Bucket",
				"  - Sheep:    Requires Shears",
				"",
				"  MAP COLORS:  P=Green  c=Yellow  C=Brown  S=White"
			},
			{
				"BUYING TOOLS",
				"",
				"Purchase essential equipment from the Tool Shop",
				"on the Home Map.",
				"",
				"  AVAILABLE TOOLS:",
				"  +----------+----------+---------------------------+",
				"  | Tool     | Price    | Purpose                   |",
				"  +----------+----------+---------------------------+",
				"  | Bucket   | $1,000   | Collect Milk from Cows    |",
				"  | Shears   | $1,500   | Collect Wool from Sheep   |",
				"  | Hoe      | $3,000   | Required to plant seeds   |",
				"  +----------+----------+---------------------------+",
				"",
				"  TIP: Tools are one-time purchases. Once bought,",
				"  they remain in your inventory permanently.",
				"",
				"  RECOMMENDED: Buy a Hoe first so you can start",
				"  farming, then save up for a Bucket and Shears."
			},
			{
				"SLEEPING & TIME PROGRESSION",
				"",
				"Time in Btardew Walley progresses when you sleep.",
				"",
				"  HOW TO SLEEP:",
				"  Walk to your bed on the Home Map and confirm.",
				"",
				"  WHAT HAPPENS WHEN YOU SLEEP:",
				"  - Day counter increases by 1",
				"  - Animal harvest timers count down",
				"  - Crop growth timers count down",
				"  - Farm product freshness decreases by 1",
				"",
				"  WARNING: Sleeping too much without selling your",
				"  farm products will cause them to rot! (Freshness",
				"  reaching 0 removes the item from your inventory.)"
			},
			{
				"ADVANCED ECONOMICS",
				"",
				"  FRESHNESS (Farm Products):",
				"  Starts at 5 when harvested, drops by 1 per day.",
				"  +-------------------+-------------------+",
				"  | Freshness         | Sell Multiplier   |",
				"  +-------------------+-------------------+",
				"  | 4-5 (High)        | 1.0x (Full)       |",
				"  | 3   (Medium)      | 0.5x (Half)       |",
				"  | 1-2 (Low)         | 0.25x (Quarter)   |",
				"  | 0   (Rotten)      | ITEM DESTROYED    |",
				"  +-------------------+-------------------+",
				"",
				"  GRADE QUALITY (Animal Products):",
				"  Random grade assigned when harvesting animals.",
				"  Higher day count = better chance of rare grades!",
				"  +----------+-------------------+",
				"  | Grade    | Sell Multiplier   |",
				"  +----------+-------------------+",
				"  | Grade 1  | 1x base price     |",
				"  | Grade 2  | 2x base price     |",
				"  | Grade 3  | 5x base price     |",
				"  +----------+-------------------+"
			}
		};
	}
}
