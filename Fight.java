public class Fight {
    int MEATHEAL=8; //Heals 8
    Player player;
    Enemy enemy;

    //Constructor for Fight
    public Fight(Enemy enemy, Player player) {
        this.enem = enemy;
        this.play = player;
    }
    private boolean startFight(Enemy enemy) {
        //gui.startBattle() START BATTLE FROM GUI
        //if player dies, return false
    }
    private void consumeMeat() {
        player.heal(MEATHEAL);
    }
    private void playerFight() {
        player.attack(enemy);
    }
    private void enemyFight() {
        enemy.attack(player);
    }
}
