package ui.property;

public record ValueChangedEvent<T>(T oldValue, T newValue, Property<T> property) {
}

