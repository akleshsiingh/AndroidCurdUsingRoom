package com.androidfizz.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.androidfizz.model.ModelPerson;

import java.util.List;

/**
 * Created by Aklesh on 3/9/2018.
 */

@Dao
public interface PersonDao {

    @Query("SELECT * FROM ModelPerson")
    List<ModelPerson> getAllPerson();

    @Insert
    long addPerson(ModelPerson person);

    @Insert
    long[] addPersonList(List<ModelPerson> person);

    @Update
    int  updatePersonRecord(ModelPerson person);

    @Delete
    void delete(ModelPerson person);

}
