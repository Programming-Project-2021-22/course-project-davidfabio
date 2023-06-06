import com.badlogic.gdx.graphics.Color;
import org.davidfabio.game.Bullet;
import org.davidfabio.game.Player;
import org.davidfabio.utils.Settings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {
    @Test
    @DisplayName("Create new Player")
    public void testCreatePlayer() {
        Player player = new Player();
        player.init(0, 0, 1, 100, Color.GOLD);

        assertEquals(player.getHealth(),3);
        assertEquals(player.getInitialHealth(),3);
        assertEquals(player.getAttackPower(),20);
        assertEquals(player.getHitDuration(),2.5f);
        assertEquals(player.getPickupsCollected(),0);
        assertEquals(player.getMultiplier(),1);
        assertEquals(player.getIsInHitState(),false);
        assertEquals(player.getIsDashing(),false);

        Bullet[] bullets = player.getBullets();
        assertEquals(bullets.length,Settings.MAX_PLAYER_BULLETS);
    }

    @Test
    @DisplayName("Player gets hit by Bullet")
    public void testPlayerGetsHit() {
        Player player = new Player();
        player.init(0, 0, 1, 100, Color.GOLD);

        assertEquals(player.getHealth(),3);
        assertEquals(player.getInitialHealth(),3);
        assertEquals(player.getAttackPower(),20);
        assertEquals(player.getHitDuration(),2.5f);
        assertEquals(player.getIsInHitState(),false);

        Bullet bullet = new Bullet();
        bullet.attack(player,null);
        assertEquals(player.getHealth(),3 - bullet.getAttackPower());
        assertEquals(player.getInitialHealth(),3);
    }

    @Test
    @DisplayName("Player can collect Pickups and Multiplier is calculated correctly")
    public void testPlayerCollectPickups() {
        // Since the Pickups and Multipliers are so closely connected, we test both at the
        // same time. The reasoning is that the Multiplier is actually just calculated using
        // the collected Pickups since the last reset.
        Player player = new Player();
        player.init(0, 0, 1, 100, Color.GOLD);

        int collectedPickups = 0;
        assertEquals(player.getPickupsCollected(),collectedPickups);
        assertEquals(player.getMultiplier(),1);

        player.incrementPickups();
        collectedPickups += 1;
        assertEquals(player.getPickupsCollected(),collectedPickups);

        player.incrementPickups();
        player.incrementPickups();
        player.incrementPickups();
        player.incrementPickups();
        player.incrementPickups();
        player.incrementPickups();
        player.incrementPickups();
        player.incrementPickups();
        player.incrementPickups();
        player.incrementPickups();
        collectedPickups += 10;
        assertEquals(player.getPickupsCollected(),collectedPickups);
        assertEquals(player.getMultiplier(),2);

        player.resetCurrentPickupCollection();
        assertEquals(player.getPickupsCollected(),collectedPickups);
        assertEquals(player.getMultiplier(),1);

        for (int i = 0; i < (Settings.MAX_MULTIPLIER + 2) * 10; i++) {
            player.incrementPickups();
            collectedPickups += 1;
        }
        assertEquals(player.getPickupsCollected(),collectedPickups);
        assertEquals(player.getMultiplier(),Settings.MAX_MULTIPLIER);
    }

    @Test
    @DisplayName("Player can shoot")
    public void testPlayerShooting() {
        Player player = new Player();
        player.init(0, 0, 1, 100, Color.GOLD);

        // Verify no Bullets are active
        Bullet[] bullets = player.getBullets();
        List<Bullet> bulletList = Arrays.stream(bullets).toList();
        assertEquals(getNoOfActiveBullets(bulletList),0);

        // Shoot a single Bullet
        player.shoot();
        bullets = player.getBullets();
        bulletList = Arrays.stream(bullets).toList();
        assertEquals(getNoOfActiveBullets(bulletList),1);

        // Shoot more than the maximum of available Player Bullets.
        for(int i = 0; i < (Settings.MAX_PLAYER_BULLETS + 10); i++) {
            player.shoot();
        }
        bullets = player.getBullets();
        bulletList = Arrays.stream(bullets).toList();
        assertEquals(getNoOfActiveBullets(bulletList),Settings.MAX_PLAYER_BULLETS);
    }

    private long getNoOfActiveBullets(List<Bullet> bullets) {
        return bullets.stream().filter(bullet -> bullet.getIsActive()).count();
    }
}
