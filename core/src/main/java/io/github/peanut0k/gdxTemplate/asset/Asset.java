package io.github.peanut0k.testrpg.asset;

import com.badlogic.gdx.assets.AssetDescriptor;

public interface Asset<T> {
    AssetDescriptor<T> getDescriptor();
}
