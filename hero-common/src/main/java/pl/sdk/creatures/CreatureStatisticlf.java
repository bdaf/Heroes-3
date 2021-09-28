package pl.sdk.creatures;

import com.google.common.collect.Range;
import pl.sdk.hero.Fraction;

public interface CreatureStatisticlf {

    String getTranslatedCreatureName();

    int getAttack();

    int getArmor();

    int getMaxHp();

    int getMoveRange();

    Range<Integer> getDamage();

    int getTier();

    String getDescription();

    boolean isUpgraded();

    int getShots();

    Fraction getFraction();

}
