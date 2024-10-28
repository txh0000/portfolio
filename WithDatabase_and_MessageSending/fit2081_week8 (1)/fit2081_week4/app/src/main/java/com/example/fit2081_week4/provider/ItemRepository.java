package com.example.fit2081_week4.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemRepository {
    private ItemDao mItemDao;
    private LiveData<List<Item>> mAllItems;

    ItemRepository(Application application) {
        ItemDatabase db = ItemDatabase.getDatabase(application);
        mItemDao = db.itemDao();
        mAllItems = mItemDao.getAllItem();
    }
    LiveData<List<Item>> getAllItems() {
        return mAllItems;
    }
    void insert(Item item) {
        ItemDatabase.databaseWriteExecutor.execute(() -> mItemDao.addItem(item));
    }

    void deleteAll(){
        ItemDatabase.databaseWriteExecutor.execute(()->{
            mItemDao.deleteAllItem();
        });
    }
    void deleteSpecified(){
        ItemDatabase.databaseWriteExecutor.execute(()->{
            mItemDao.deleteItemSpecified();
        });
    }
}
