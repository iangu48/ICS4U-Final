private class Fight {
    Player player = player; //WHATEVER THE PLAYER IS CALLED
    int MEATHEAL =  10 //WHAT DOES IT HEAL
    Enemy enemy;
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