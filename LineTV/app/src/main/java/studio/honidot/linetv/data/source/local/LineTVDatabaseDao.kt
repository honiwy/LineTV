package studio.honidot.linetv.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import studio.honidot.linetv.data.Drama

@Dao
interface LineTVDatabaseDao {

    @Insert
    fun insertCart(drama: Drama)

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM dramas_in_cart_table")
    fun clearCart()


    /**
     * Selects and returns all rows in the table,
     *
     * sorted by drama_id in ascending order.
     */
    @Query("SELECT * FROM dramas_in_cart_table ORDER BY drama_id ASC")
    fun getAllDramasCart():
            LiveData<List<Drama>>


    /**
     * Selects and return the [Drama] with given id and name
     * @param id: [Drama.id]
     * @param name: [Drama.name]
     */
    @Query("SELECT * from dramas_in_cart_table WHERE drama_id = :id AND name = :name ")
    fun getCart(id: Long, name: String): Drama?

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param drama: [Drama]
     */
    @Update
    fun update(drama: Drama)

    @Query("DELETE from dramas_in_cart_table WHERE drama_id = :id AND name = :name")
    fun deleteCart(id: Long, name: String)

}

