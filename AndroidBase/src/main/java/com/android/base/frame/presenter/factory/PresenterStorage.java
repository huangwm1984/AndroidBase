package com.android.base.frame.presenter.factory;

import com.android.base.frame.presenter.XPresenter;

import java.util.HashMap;

/**
 * This is the singleton where all presenters are stored.
 */
public enum PresenterStorage {

    INSTANCE;

    private HashMap<String, XPresenter> idToPresenter = new HashMap<>();
    private HashMap<XPresenter, String> presenterToId = new HashMap<>();

    /**
     * Adds a presenter to the storage
     *
     * @param presenter a presenter to add
     */
    public void add(final XPresenter presenter) {
        String id = presenter.getClass().getSimpleName() + "/" + System.nanoTime() + "/" + (int)(Math.random() * Integer.MAX_VALUE);
        idToPresenter.put(id, presenter);
        presenterToId.put(presenter, id);
        presenter.addOnDestroyListener(new XPresenter.OnDestroyListener() {
            @Override
            public void onDestroy() {
                idToPresenter.remove(presenterToId.remove(presenter));
            }
        });
    }

    /**
     * Returns a presenter by id or null if such presenter does not exist.
     *
     * @param id  id of a presenter that has been received by calling {@link #getId(XPresenter)}
     * @param <P> a type of presenter
     * @return a presenter or null
     */
    public <P> P getPresenter(String id) {
        //noinspection unchecked
        return (P)idToPresenter.get(id);
    }

    /**
     * Returns id of a given presenter.
     *
     * @param presenter a presenter to get id for.
     * @return if of the presenter.
     */
    public String getId(XPresenter presenter) {
        return presenterToId.get(presenter);
    }

    /**
     * Removes all presenters from the storage.
     * Use this method for testing purposes only.
     */
    public void clear() {
        idToPresenter.clear();
        presenterToId.clear();
    }
}
