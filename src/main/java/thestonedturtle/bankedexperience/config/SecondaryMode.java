package thestonedturtle.bankedexperience.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SecondaryMode {
    NONE("None"),
    ALL("All"),
    MISSING("Needed");

    private final String name;

    @Override
    public String toString()
    {
        return name;
    }
}
