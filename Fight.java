private class Fight {
    int MEATHEAL =  10; //WHAT DOES IT HEAL
    
    //Constructor for Fight
    public Fight(Enemy enemy, Player player) {
        this.enemy = enemy;
        this.player = player;
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
