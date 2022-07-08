package jp.co.yumemi.android.code_check.model

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Database(entities = [KeepEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun keepDao(): KeepDao
}

@Entity(tableName = "keep")
data class KeepEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    val id: Int,
    val repositoryItems: String,
)

fun repositoryItemsToKeepEntity(repositoryItems: List<RepositoryItem>): KeepEntity {
    return KeepEntity(
        id = 0,
        repositoryItems = Gson().toJson(repositoryItems),
    )
}

fun keepEntityToRepositoryItems(keepEntity: KeepEntity): List<RepositoryItem> {
    val listOfRepositoryItem = object : TypeToken<List<RepositoryItem>>() {}.type
    return Gson().fromJson<List<RepositoryItem>>(keepEntity.repositoryItems, listOfRepositoryItem)
}

@Dao
interface KeepDao {
    @Query("SELECT * FROM keep")
    fun loadKeepEntity(): List<KeepEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveKeepEntity(keep: KeepEntity)
}

