package agh.ics.oop.data;

// Interfejs do rejestrowania zmian statystycznych (np. dodania nowego zwierzaka / genomu) w zależności od implementacji
public interface IStatLog {
    void registerStatChange(Object change);
    String getStatValue();
    void clearParameters();
}
