package studio.honidot.linetv.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import studio.honidot.linetv.data.Drama

@Dao
interface LineTVDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDrama(drama: Drama)

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by drama_id in ascending order.
     */
    @Query("SELECT * FROM dramas_in_cart_table ORDER BY drama_id ASC")
    fun getAllDramas():
            LiveData<List<Drama>>

}

