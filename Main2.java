import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// ========================================================================
//? MAIN APPLICATION CLASS
// ========================================================================

/*
 * Main class that contains all components for the Computer Graphics project
 * This creates an animated scene with ground, grass, rocks, trees, and a slime character
 */
public class Main2 {
    public static void main(String[] args) {
        // Create the main window for our graphics application
        JFrame frame = new JFrame("Tree Cycle Poop - Computer Graphics Project");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null); // Center the window on screen

        // Create and add our animation controller to the window
        AnimationController controller = new AnimationController();
        frame.add(controller);

        // Make the window visible and start the animation
        frame.setVisible(true);
        controller.startAnimation();
    }
}

// ========================================================================
// ? COLOR CONFIGURATION CLASS
// ========================================================================

/*
 * Color definitions for all ground-related elements
 * Contains predefined colors for consistent theming throughout the application
 */
class GroundColor {
    // Ground/soil colors - browns for realistic earth appearance
    public static final Color BROWN_PRIMARY = Color.decode("#6C4E31");
    public static final Color BROWN_SECONDARY = Color.decode("#603F26");

    // Rock/gravel colors - grays for stone-like appearance
    public static final Color GRAVEL_PRIMARY = Color.decode("#2b2929");
    public static final Color GRAVEL_SECONDARY = Color.decode("#5a5b5c");
    public static final Color GRAVEL_TERTIARY = Color.decode("#62636c");

    // Grass colors - greens for natural vegetation
    public static final Color GRASS_PRIMARY = Color.decode("#0A400C");
    public static final Color GRASS_SECONDARY = Color.decode("#2D4F2B");

    // Sky colors - blues for atmospheric background
    public static final Color SKY_PRIMARY = Color.decode("#009ceb");
    public static final Color SKY_SECONDARY = Color.decode("#9ee0f9");
    public static final Color SKY_TERTIARY = Color.decode("#f9e5bc");

    // Private constructor to prevent instantiation (utility class)
    private GroundColor() {
    }
}

// ========================================================================
// ? DATA MODEL CLASSES
// ========================================================================

/*
 * Simple data class representing a grass blade
 * Stores position and size information for individual grass elements
 */
class Grass {
    private int x, y, size;

    // Default constructor creates grass at origin with no size
    public Grass() {
        this.x = 0;
        this.y = 0;
        this.size = 0;
    }

    // Constructor with specific position and size
    public Grass(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    // Getter methods for accessing grass properties
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    // Setter methods for modifying grass properties
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

/*
 * Simple data class representing a rock
 * Stores position and size information for individual rock elements
 */
class Rock {
    private int x, y, size;

    // Default constructor creates rock at origin with no size
    public Rock() {
        this.x = 0;
        this.y = 0;
        this.size = 0;
    }

    // Constructor with specific position and size
    public Rock(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    // Getter methods for accessing rock properties
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    // Setter methods for modifying rock properties
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

// ========================================================================
// ? TREE COMPONENT CLASS
// ========================================================================

/*
 * Tree component that handles drawing and managing multiple trees
 * Creates a collection of trees with trunks and foliage
 */
class Tree extends JPanel {
    // Tree configuration constants
    private static final int TOP_GROUND_HEIGHT = 200;
    private static final int TREE_COUNT = 5;
    private static final int TREE_SPACING = 100;
    private static final int TREE_START_X = 100;

    // Instance variables
    private int x;
    private int baseY = 600 - TOP_GROUND_HEIGHT; // Base line where trees are planted

    // Collection of all trees in the scene
    ArrayList<Tree> trees = new ArrayList<>();
    Random random = new Random();

    // Constructor for individual tree with specific x position
    public Tree(int x) {
        this.x = x;
    }

    // Default constructor that generates multiple trees
    public Tree() {
        generateTrees(600, 600, TREE_COUNT); // Create trees across the scene
    }

    /*
     * Main drawing method that renders all trees
     */
    public void draw(Graphics g, int w, int h) {
        drawTree(g, w, h);
        // Note: Circular leaves drawing is commented out for now
        // for (Tree tree : trees) {
        // drawLeaves(g, tree.x, baseY, 20);
        // }
    }

    /*
     * Draws all trees with simple rectangular shapes
     * Each tree consists of a brown trunk and green foliage
     */
    public void drawTree(Graphics g, int w, int h) {
        for (Tree tree : trees) {
            // Draw tree foliage (leaves) - two green rectangles of different sizes
            g.setColor(GroundColor.GRASS_SECONDARY);
            g.fillRect(tree.x - 20, baseY - 80, 30, 30); // Upper smaller foliage
            g.fillRect(tree.x - 20, baseY - 50, 50, 50); // Lower larger foliage

            // Draw tree trunk - brown vertical rectangle
            g.setColor(GroundColor.BROWN_PRIMARY);
            g.fillRect(tree.x, baseY, 10, 100); // Trunk extending down into ground
        }
    }

    /*
     * Generates a specified number of trees at regular intervals
     */
    public void generateTrees(int w, int h, int count) {
        trees.clear(); // Remove any existing trees

        // Create trees spaced evenly starting at TREE_START_X
        for (int i = 0; i < count; i++) {
            trees.add(new Tree(TREE_START_X + (i * TREE_SPACING)));
        }
    }

    /*
     * Alternative method for drawing circular leaves using midpoint circle
     * algorithm
     * Currently not used but kept for future enhancement
     */
    public void drawLeaves(Graphics g, int xc, int yc, int r) {
        int x = 0;
        int y = r;
        int Dx = 2 * x;
        int Dy = 2 * y;
        int D = 1 - r;

        // Midpoint circle algorithm implementation
        while (x <= y) {
            plot8(g, xc, yc, x, y); // Draw 8 symmetric points

            x = x + 1;
            Dx = Dx + 2;
            D = D + Dx + 1;
            if (D >= 0) {
                y = y - 1;
                Dy = Dy - 2;
                D = D - Dy;
            }
        }
    }

    /*
     * Helper method for circle drawing - plots 8 symmetric points
     */
    private void plot8(Graphics g, int xc, int yc, int x, int y) {
        g.fillRect(xc + x, yc + y, 1, 1);
        g.fillRect(xc - x, yc + y, 1, 1);
        g.fillRect(xc + x, yc - y, 1, 1);
        g.fillRect(xc - x, yc - y, 1, 1);
        g.fillRect(xc + y, yc + x, 1, 1);
        g.fillRect(xc - y, yc + x, 1, 1);
        g.fillRect(xc + y, yc - x, 1, 1);
        g.fillRect(xc - y, yc - x, 1, 1);
    }
}

// ========================================================================
// ? SLIME CHARACTER CLASS
// ========================================================================

/*
 * Slime character component - placeholder for future implementation
 * Will contain the main character that moves around the scene
 */
class Slime extends JPanel {
    private static final int TOP_GROUND_HEIGHT = 200;
    private int baseY = 600 - TOP_GROUND_HEIGHT; // Ground level for slime movement

    // TODO: Implement actual slime character with movement and animation
    public Slime() {
        // Currently empty - ready for slime implementation
    }
}
// ========================================================================
// ? BIRD COMPONENT
// ========================================================================

/*
 * Create bird with animation
 */

class Bird extends JPanel{

    //TODO: Implement a bird on HERE!!!
    public Bird(){}
}
// ========================================================================
// ? GROUND MANAGEMENT CLASS
// ========================================================================

/*
 * Ground component that manages the entire background scene
 * Handles drawing of sky, ground layers, grass, rocks, and trees
 * Also manages wind animation for grass movement
 */
class Ground extends JPanel {
    // Layout constants for consistent ground structure
    private static final int TOP_GROUND_HEIGHT = 100; // Height of main ground layer
    private static final int UNDER_LAYER_START_OFFSET = 90; // Where underground layer starts
    private static final int UNDER_LAYER_HEIGHT = 100; // Height of underground layer

    // Environment generation constants
    private static final int GRASS_BASE_COUNT = 500;
    private static final int GRASS_VARIANCE = 11;
    private static final int ROCK_BASE_COUNT = 20;
    private static final int ROCK_VARIANCE = 11;
    private static final double WIND_SPEED = 0.12;

    // Core components
    private Tree tree; // Tree component for managing all trees
    private Runnable repaintCallback; // Animation callback for requesting repaints
    private Timer timer; // Timer for animation updates

    // Random generator for procedural generation
    private final Random random = new Random();

    // Collections for environmental elements
    private final List<Rock> rocks1 = new ArrayList<>(); // Rocks in underground layer
    private final List<Rock> rocks2 = new ArrayList<>(); // Additional rock layer (unused)
    private final List<Grass> grass1 = new ArrayList<>(); // Primary grass layer
    private final List<Grass> grass2 = new ArrayList<>(); // Secondary grass layer

    // Animation state for wind effect
    private double windTime = 0;

    /*
     * Constructor initializes all scene elements
     */
    public Ground() {
        generateRocks(600, 600); // Create random rocks in underground
        generateGrass(650, 600); // Create random grass on surface
        tree = new Tree(); // Initialize tree system
        tick(); // Start animation timer
    }

    /*
     * Main drawing method that renders the entire scene
     * Draws from back to front: sky, ground, trees, grass, underground, rocks
     */
    public void draw(Graphics g2d, int width, int height) {
        // Draw sky background
        drawSky(g2d, width, height);

        // Draw main ground layer (brown soil)
        g2d.setColor(GroundColor.BROWN_PRIMARY);
        g2d.fillRect(0, height - TOP_GROUND_HEIGHT, width, TOP_GROUND_HEIGHT);

        // Draw trees on top of ground
        tree.draw(g2d, width, height);

        // Draw animated grass swaying in wind
        drawGrass(g2d, width, height);

        // Draw underground layer (darker brown)
        g2d.setColor(GroundColor.BROWN_SECONDARY);
        g2d.fillRect(0, height - UNDER_LAYER_START_OFFSET, width, UNDER_LAYER_HEIGHT);

        // Draw rocks in underground layer
        drawRocks(g2d);
    }

    /*
     * Updates animation state - called every frame
     */
    private void tick() {
        windTime += WIND_SPEED; // Increment wind animation time
    }

    /*
     * Draws the sky background with a solid color
     */
    private void drawSky(Graphics g, int width, int height) {
        g.setColor(GroundColor.SKY_SECONDARY);
        g.fillRect(0, 0, width, height);
    }

    // ---- ROCK MANAGEMENT METHODS ----

    /*
     * Draws all rocks in the underground layer
     * Each rock has three color layers for depth effect
     */
    private void drawRocks(Graphics g2d) {
        for (Rock rock : rocks1) {
            // Outer layer (darkest)
            g2d.setColor(GroundColor.GRAVEL_SECONDARY);
            g2d.fillRect(rock.getX(), rock.getY(), rock.getSize(), rock.getSize());

            // Middle layer (medium gray)
            g2d.setColor(GroundColor.GRAVEL_TERTIARY);
            g2d.fillRect(rock.getX() + 5, rock.getY() + 5, rock.getSize() - 5, rock.getSize() - 5);

            // Inner layer (lightest)
            g2d.setColor(GroundColor.GRAVEL_PRIMARY);
            g2d.fillRect(rock.getX() + 10, rock.getY() + 10, rock.getSize() - 10, rock.getSize() - 10);
        }
    }

    /*
     * Generates random rocks in the underground layer
     */
    private void generateRocks(int width, int height) {
        rocks1.clear();
        rocks2.clear();

        // Create 20-30 random rocks
        int numRocks = random.nextInt(ROCK_VARIANCE) + ROCK_BASE_COUNT;

        for (int i = 0; i < numRocks; i++) {
            // Random horizontal position
            int x1 = random.nextInt(width - 30);

            // Position in underground layer (height-90 to height-40)
            int y1 = height - 90 + random.nextInt(50);

            // Random rock size (10-30 pixels)
            int rockSize = random.nextInt(20) + 10;

            rocks1.add(new Rock(x1, y1, rockSize));
        }
    }

    // ---- GRASS MANAGEMENT METHODS ----

    /*
     * Draws animated grass that sways in the wind
     * Uses two layers of grass for depth and variety
     */
    private void drawGrass(Graphics g2d, int w, int h) {
        Graphics2D g = (Graphics2D) g2d;
        Stroke old = g.getStroke(); // Save original stroke for restoration

        // Draw background grass layer (darker, thicker strokes)
        g.setColor(GroundColor.GRASS_SECONDARY);
        g.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for (Grass grass : grass1) {
            int size = Math.max(grass.getSize() - 4, 6); // Ensure minimum size
            // Calculate wind sway based on position and time
            double phase = grass.getX() * 0.07 + 0.5;
            double sway = Math.sin(windTime + phase) * Math.max(2, size / 5);
            drawGrassCluster(g, grass.getX(), h - TOP_GROUND_HEIGHT, size, sway);
        }

        // Draw foreground grass layer (lighter, thinner strokes)
        g.setColor(GroundColor.GRASS_PRIMARY);
        g.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for (Grass grass : grass2) {
            int size = Math.max(grass.getSize() - 4, 6);
            // Each grass blade has slightly different wind response
            double phase = grass.getX() * 0.07 + 0.5;
            double sway = Math.sin(windTime + phase) * Math.max(2, size / 5);
            drawGrassCluster(g, grass.getX(), h - TOP_GROUND_HEIGHT, size, sway);
        }

        g.setStroke(old); // Restore original stroke
    }

    /*
     * Draws an individual grass cluster with multiple blades
     * Creates realistic grass appearance with varying blade heights
     */
    private void drawGrassCluster(Graphics2D g, int baseX, int baseY, int size, double sway) {
        int h = size;
        int tipX = baseX + (int) Math.round(sway);

        // Main central blade
        g.drawLine(baseX, baseY, tipX, baseY - h);

        // Side blades at different heights for natural look
        g.drawLine(baseX, baseY, tipX - 3, baseY - (int) (h * 0.85));
        g.drawLine(baseX, baseY, tipX + 3, baseY - (int) (h * 0.85));

        // Additional shorter blades from mid-height
        g.drawLine(baseX, baseY - (int) (h * 0.4), tipX - 2, baseY - h);
        g.drawLine(baseX, baseY - (int) (h * 0.4), tipX + 2, baseY - h);
    }

    /*
     * Generates random grass across the ground surface
     * Creates two layers of grass for depth effect
     */
    private void generateGrass(int width, int height) {
        grass1.clear();
        grass2.clear();

        // Create 500-510 grass blades for dense coverage
        int numGrass = random.nextInt(GRASS_VARIANCE) + GRASS_BASE_COUNT;

        for (int i = 0; i < numGrass; i++) {
            // Random horizontal position
            int x1 = random.nextInt(width - 30);

            // Random grass size (10-30 pixels)
            int grassSize = random.nextInt(21) + 10;

            // Add to both grass layers with size variation
            grass1.add(new Grass(x1, height - TOP_GROUND_HEIGHT, grassSize));
            grass2.add(new Grass(x1, height - TOP_GROUND_HEIGHT, Math.max(grassSize - 10, 6)));
        }
    }

    // ---- ANIMATION MANAGEMENT METHODS ----

    /*
     * Starts the animation system with a callback for repainting
     */
    public void startAnimation(Runnable repaintCallback) {
        this.repaintCallback = repaintCallback;

        // Create timer that updates every 10ms (approximately 100 FPS)
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tick(); // Update animation state
                if (Ground.this.repaintCallback != null) {
                    Ground.this.repaintCallback.run(); // Request repaint
                }
                repaint(); // Repaint this component
            }
        });
        timer.start();
    }
}

// ========================================================================
// ? ANIMATION CONTROLLER CLASS
// ========================================================================

/*
 * Animation controller that manages the main rendering panel
 * Acts as the primary canvas where all graphics are drawn
 */
class AnimationController extends JPanel {
    // Main ground component that contains all scene elements
    private Ground ground;

    /*
     * Constructor sets up the canvas size and initializes components
     */
    public AnimationController() {
        setPreferredSize(new Dimension(600, 600)); // Set canvas size
        initializeComponents();
    }

    /*
     * Initialize all scene components
     */
    private void initializeComponents() {
        ground = new Ground(); // Create the main ground scene
    }

    /*
     * Start the animation loop
     * Ground will handle timing and request repaints of this panel
     */
    public void startAnimation() {
        ground.startAnimation(this::repaint);
    }

    /*
     * Main paint method called by Swing to render the scene
     * Enables antialiasing for smooth graphics and delegates drawing to ground
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Clear the canvas

        Graphics2D g2d = (Graphics2D) g;
        // Enable antialiasing for smoother lines and shapes
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the entire scene through the ground component
        ground.draw(g2d, getWidth(), getHeight());
    }
}