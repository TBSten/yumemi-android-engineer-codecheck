package jp.co.yumemi.android.code_check.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.yumemi.android.code_check.model.KeepApi
import jp.co.yumemi.android.code_check.model.RepositoryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class KeepViewModel : ViewModel() {
    private var _keeps = MutableLiveData(mutableSetOf<RepositoryItem>())

    /**
     * キープされているレポジトリの一覧
     */
    var keeps: LiveData<MutableSet<RepositoryItem>> = _keeps

    init {
        load()
    }

    fun addKeep(repo: RepositoryItem, autoSave: Boolean = true) {
        _keeps.value = mutableSetOf<RepositoryItem>().apply {
            this.addAll(_keeps.value ?: mutableSetOf())
            this.add(repo)
        }
        Log.d("", "add keep")
        if (autoSave) {
            save()
        }
    }

    fun removeKeep(repo: RepositoryItem, autoSave: Boolean = true) {
        _keeps.value = mutableSetOf<RepositoryItem>().apply {
            this.addAll(_keeps.value ?: mutableSetOf())
            this.remove(repo)
        }
        if (autoSave) {
            save()
        }
    }

    fun toggleKeep(repo: RepositoryItem, autoSave: Boolean = true) {
        if (keeps.value?.contains(repo) == true) {
            removeKeep(repo, autoSave)
        } else {
            addKeep(repo, autoSave)
        }
    }

    /**
     * キープ一覧を読み込む
     */
    fun load() {
        viewModelScope.async(Dispatchers.IO) {
            _keeps.value = KeepApi().load().toMutableSet()
            Log.d("", "load keeps ${_keeps.value}")
        }
    }

    /**
     * キープ一覧を永続化する
     */
    fun save() {
        viewModelScope.async(Dispatchers.IO) {
            Log.d("", "save keeps ${_keeps.value?.size}")
            Thread.sleep(1000)
            val keeps = _keeps.value
            if (keeps == null) {
                return@async
            }
            KeepApi().save(keeps.toList())
        }
    }

}

