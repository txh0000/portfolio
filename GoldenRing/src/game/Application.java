package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.FancyGroundFactory;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.World;
import game.actors.players.Astrologer;
import game.actors.players.Bandit;
import game.actors.players.Player;
import game.actors.players.Samurai;
import game.actors.players.Wretch;
import game.actors.traders.FingerReaderEnia;
import game.environments.*;
import game.environments.spawningground.*;

/**
 * The main class to start the game.
 * Created by:
 * @author Adrian Kristanto
 * Modified by:
 * @author Jun Lim
 * @author jingweiong
 * @author Xi Heng
 */
public class Application {

	public static void main(String[] args) {
		World world = new World(new Display());

		FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Wall(), new Floor(),
				new Graveyard(), new Wind(), new Water(), new TheFirstStep(), new SummonSign(),
				new Barrack(), new Cage(), new LakeOfRot(), new Cliff());

		List<String> limgrave = Arrays.asList(
			"......................#.............#..........................+++.........",
				"......................#.............#.......................+++++..........",
				"......................#..___....____#.........................+++++........",
				"......................#...........__#............................++........",
				"......................#_____........#.............................+++......",
				"......................#............_#..............................+++.....",
				"......................######...######......................................",
				"...........................................................................",
				"...........................=...............................................",
				"........++++......................###___###................................",
				"........+++++++...................________#................................",
				"..........+++.....................#________................................",
				"............+++...................#_______#................................",
				".............+....................###___###................................",
				"............++......................#___#..................................",
				"..............+...................=........................................",
				"..............++.................................................=.........",
				"..............................................++...........................",
				"..................++++......................+++...............######..##...",
				"#####___######....++...........................+++............#....____....",
				"_____________#.....++++..........................+..............__.....#...",
				"_____________#.....+....++........................++.........._.....__.#...",
				"_____________#.........+..+.....................+++...........###..__###...",
				"_____________#.............++..............................................");
		List<String> stormveilCastle = Arrays.asList(
			"...........................................................................",
				"..................<...............<........................................",
				"...........................................................................",
				"##############################################...##########################",
				"............................#................#.......B..............B......",
				".....B...............B......#................#.............................",
				"...............................<.........<.................................",
				".....B...............B......#................#.......B..............B......",
				"............................#................#.............................",
				"#####################..#############...############.####..#########...#####",
				"...............#++++++++++++#................#++++++++++++#................",
				"...............#++++++++++++...<.........<...#++++++++++++#................",
				"...............#++++++++++++..................++++++++++++#................",
				"...............#++++++++++++#................#++++++++++++#................",
				"#####...##########.....#############...#############..#############...#####",
				".._______........................B......B........................B.....B...",
				"_____..._..____....&&........<..............<..............................",
				".........____......&&......................................................",
				"...._______..................<..............<....................<.....<...",
				"#####....##...###..#####...##########___###############......##.....####...",
				"+++++++++++++++++++++++++++#...................#+++++++++++++++++++++++++++",
				"+++++++++++++++++++++++++++....................#+++++++++++++++++++++++++++",
				"+++++++++++++++++++++++++++#....................+++++++++++++++++++++++++++",
				"+++++++++++++++++++++++++++#...................#+++++++++++++++++++++++++++");
		List<String> roundtableHold = Arrays.asList(
				"##################",
				"#________________#",
				"#________________#",
				"#________________#",
				"#________________#",
				"#________________#",
				"#________________#",
				"#________________#",
				"#________________#",
				"#________________#",
				"########___#######");
		List<String> stormveilBossRoom = Arrays.asList(
				"+++++++++++++++++++++++++",
				".........................",
				"..=......................",
				".........................",
				".........................",
				".........................",
				".........................",
				".........................",
				"+++++++++++++++++++++++++");

		GameMap limgraveGameMap = new GameMap(groundFactory, limgrave);
		GameMap stormveilCastleGameMap = new GameMap(groundFactory, stormveilCastle);
		GameMap roundtableHoldGameMap = new GameMap(groundFactory, roundtableHold);
		GameMap stormveilCastleBossRoomGameMap = new GameMap(groundFactory, stormveilBossRoom);

		limgraveGameMap.at(0,8).setGround(new GoldenFogDoor(stormveilCastleGameMap.at(38,23))); // from limgrave to stormveil castle
		limgraveGameMap.at(74,10).setGround(new GoldenFogDoor(roundtableHoldGameMap.at(9,10))); // from limgrave to roundtable hold

		stormveilCastleGameMap.at(38,23).setGround(new GoldenFogDoor(limgraveGameMap.at(0,8))); // from stormveil castle to limgrave
		stormveilCastleGameMap.at(38,0).setGround(new GoldenFogDoor(stormveilCastleBossRoomGameMap.at(12,7))); // from stormveil castle to stormveil boss room

		roundtableHoldGameMap.at(9,10).setGround(new GoldenFogDoor(limgraveGameMap.at(0,8))); // from roundtable hold to limgrave
		
		world.addGameMap(limgraveGameMap);
		world.addGameMap(stormveilCastleGameMap);
		world.addGameMap(roundtableHoldGameMap);
		world.addGameMap(stormveilCastleBossRoomGameMap);

		// (Jun Lim) removed for faster debugging
		// // BEHOLD, ELDEN RING
		for (String line : FancyMessage.ELDEN_RING.split("\n")) {
			new Display().println(line);
			try {
				Thread.sleep(200);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
		// Let player choose their class
	
		ArrayList<Player> classList = new ArrayList<>();
		classList.add(new Samurai());
		classList.add(new Bandit());
		classList.add(new Wretch());
		classList.add(new Astrologer());

		HashMap<Integer, Player> playerClassMap = new HashMap<Integer, Player>();

		System.out.println("Pick a class:");

		int counter = 1;
		for (Player playerClass : classList) {
			playerClassMap.put(counter, playerClass);
			System.out.println(counter + ": " + playerClass);
			counter ++;
		}

		Scanner keyboard = new Scanner(System.in);
		int i = 0;
		
		do {
			i = keyboard.nextInt();
		} while (!playerClassMap.containsKey(i));

		Player player = playerClassMap.get(i);

		classList.remove(player); // cleaning up list of resettables
		ResetManager resetManager = ResetManager.getInstance();
		for (Player playerClass : classList) {
			resetManager.removeResettable(playerClass);
		}

		world.addPlayer(player, limgraveGameMap.at(1, 7));

		// gameMap.at(32, 12).addActor(new GiantCrayfish());
		// gameMap.at(31, 12).addActor1(new HeavySkeletalSwordsman());
		// gameMap.at(32, 12).addActor(new HeavySkeletalSwordsman());
		// gameMap.at(33, 12).addActor(new HeavySkeletalSwordsman());
		// gameMap.at(30, 11).addActor(new HeavySkeletalSwordsman());
		// limgraveGameMap.at(32,10).addItem(new GoldenRunes());
		// gameMap.at(30,11).addActor(new Invader());
		// gameMap.at(29,11).addActor(new Ally());
		// gameMap.at(30,12).addActor(new Invader());
		// gameMap.at(30,10).addActor(new Ally());
		// gameMap.at(31, 10).addActor(new HeavySkeletalSwordsman());
		// gameMap.at(32, 10).addActor(new HeavySkeletalSwordsman());
		// gameMap.at(33, 10).addActor(new HeavySkeletalSwordsman());
		// gameMap.at(33, 11).addActor(new HeavySkeletalSwordsman());






		// gameMap.at(31, 11).addActor(new GiantCrab());
		// gameMap.at(31, 11).addActor(new GiantCrayfish());
		// gameMap.at(31, 10).addActor(new GiantCrayfish());



		// RuneManager runeManager = new RuneManager(1000);

		// HINT: what does it mean to prefer composition to inheritance?

		limgraveGameMap.at(33,11).addActor(new FingerReaderEnia());
//		gameMap.at(32,11).addItem(new RemembranceOfTheGrafted());
		world.run();
	}
}
