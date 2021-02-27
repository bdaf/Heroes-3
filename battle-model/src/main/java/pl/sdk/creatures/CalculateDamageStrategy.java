package pl.sdk.creatures;

public interface CalculateDamageStrategy {
    int count(Creature attacker, Creature defender);
}
