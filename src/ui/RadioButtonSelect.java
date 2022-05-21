package ui;

import ui.property.Property;
import ui.property.ReadonlyProperty;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RadioButtonSelect<T> extends VBox {

    private Property<Option<T>> _selected;

    public ReadonlyProperty<Option<T>> selectedProperty() {
        return _selected;
    }

    public void addOptions(String labelText, Option<T>... options) {
        addOptions(labelText, Arrays.stream(options).toList());
    }

    public void addOptions(String labelText, List<Option<T>> options) {
        var label = new JLabel(labelText);

        var buttons = options.stream().map(option -> {
            var button = new JRadioButton(option.name());
            button.addActionListener(e -> this.onOptionSelected(option));
            return button;
        }).toList();
        buttons.get(0).setSelected(true);
        var group = new ButtonGroup();
        buttons.forEach(group::add);

        var list = new ArrayList<Component>();
        list.add(label);
        list.addAll(buttons);

        this.setChildren(list);

    }

    private void onOptionSelected(Option<T> option) {
        _selected.setValue(option);
    }

    public record Option<T>(String name, T value) {
    }

}
