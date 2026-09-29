package io.github.peanut0k.testrpg.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Graphic implements Component {
    private TextureRegion region;
    private final Color color;
    public static final ComponentMapper<Graphic> MAPPER = ComponentMapper.getFor(Graphic.class);

    public Graphic(TextureRegion region, Color color) {
        this.region = region;
        this.color = color;
    }

    public void setRegion(TextureRegion region) {
        this.region = region;
    }

    public TextureRegion getRegion() {
        return region;
    }

    public Color getColor() {
        return color;
    }
}
