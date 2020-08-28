package com.example.lazapp

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lazapp.adapter.ForYouAdapter
import com.example.lazapp.model.ForYouProduct
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_list_favorite.*

class ListFavoriteActivity : AppCompatActivity() {
    private var adapter: ForYouAdapter? = null
    private var sharedPre: SharedPreferences? = null
    private var prefsEditor: SharedPreferences.Editor? = null
    private var listLike: MutableList<String>? = null
    private var listIdString: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_favorite)
        createActionBar()

        recyclerViewListFavorite.layoutManager = GridLayoutManager(this, 2)
        recyclerViewListFavorite.setHasFixedSize(true)

        sharedPre = getSharedPreferences("APP_LAZADA", Activity.MODE_PRIVATE)
        prefsEditor = sharedPre?.edit()
        listIdString = sharedPre?.getString("LIST_ID", "") ?: ""
        listLike = Gson().fromJson<MutableList<String>>(
            listIdString,
            object : TypeToken<MutableList<String>>() {}.type
        ) ?: mutableListOf()
        val listItem: MutableList<ForYouProduct> =
            intent?.getParcelableArrayListExtra<ForYouProduct>("DATA") ?: mutableListOf()
        val listResult: MutableList<ForYouProduct> = mutableListOf()
        for (i in listItem) {
            for (id in (listLike ?: mutableListOf())) {
                if (id == i.id) {
                    listResult.add(i)
                }
            }
        }
        adapter = ForYouAdapter(baseContext,listResult)
        recyclerViewListFavorite.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite, menu)
        return true
    }

    private fun createActionBar() {
        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "Danh sách yêu thích"
        title = actionBar?.title
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btnCartFavorite -> {
                val intent: Intent = Intent(this@ListFavoriteActivity, CartForYouActivity::class.java)
                startActivity(intent)
            }
            android.R.id.home -> {
                onBackPressed()
                return true
            }

        }
        return true
    }
}