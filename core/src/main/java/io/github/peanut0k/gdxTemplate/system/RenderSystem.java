package io.github.peanut0k.testrpg.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.peanut0k.testrpg.GdxGame;
import io.github.peanut0k.testrpg.asset.AssetService;
import io.github.peanut0k.testrpg.components.Graphic;
import io.github.peanut0k.testrpg.components.Transform;

import java.util.Comparator;

public class RenderSystem extends SortedIteratingSystem implements Disposable {
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final Viewport viewport;
    private final AssetService assetService;
    private final Batch batch;
    private final OrthographicCamera camera;

    public RenderSystem(Batch batch, Viewport viewport, AssetService assetService) {
        super(
            Family.all(Transform.class, Graphic.class).get(),
            Comparator.comparing(Transform.MAPPER::get)
        );

        this.batch = batch;
        this.viewport = viewport;
        this.assetService = assetService;
        this.camera = (OrthographicCamera) viewport.getCamera();
        this.mapRenderer = new OrthogonalTiledMapRenderer(null, GdxGame.UNIT_SCALE, this.batch);
    }


    @Override
    public void update(float deltaTime) {
        this.viewport.apply();
        this.batch.setColor(Color.WHITE);
        this.mapRenderer.setView(this.camera);
        this.mapRenderer.render();

        forceSort();
        super.update(deltaTime);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Transform transform = Transform.MAPPER.get(entity);
        Graphic graphic = Graphic.MAPPER.get(entity);

        if (graphic.getRegion() == null) {
            return;
        }
        Vector2 position = transform.getPosition();
        Vector2 scaling = transform.getScaling();
        Vector2 size = transform.getSize();
        this.batch.setColor(graphic.getColor());
        this.batch.draw(
            graphic.getRegion(),
            position.x - (1f - scaling.x) * size.x * 0.5f,
            position.y - (1f - scaling.y) * size.y * 0.5f,
            size.x * 0.5f, size.y * 0.5f,
            size.x, size.y,
            scaling.x, scaling.y,
            transform.getRotationDeg()
        );
    }

    public void setMap(TiledMap map) {
        this.mapRenderer.setMap(map);
    }

    @Override
    public void dispose() {
        this.mapRenderer.dispose();
    }
}
