package com.example.edward.rxkotlinapp


import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import com.example.edward.rxkotlinapp.adapter.UserAdapter
import com.example.edward.rxkotlinapp.model.User
import com.example.edward.rxkotlinapp.retrofit.RetrofitAPI
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()
    private val adapter = UserAdapter()
    lateinit var layoutManager: GridLayoutManager
//    val subject = PublishSubject.create<List<User>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        val d = RxRecyclerView.scrollEvents(recyclerView)
            .observeOn(Schedulers.computation())
            .debounce(1000, TimeUnit.MILLISECONDS)
            .subscribe { event ->
                if (event.dy() > 0) {
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        // recyclerView is at the bottom
                        Log.d(TAG, "recyclerView is at the bottom")
                        val d1 = getAllUsers().observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                { list ->
                                    Log.d(TAG, "list: $list")
                                    val prevCount = sizeTextView.text.toString().toInt()
                                    sizeTextView.text = (list.size + prevCount).toString()
                                    adapter.setList(list)
                                    adapter.notifyDataSetChanged()
                                },
                                { error -> Log.d(TAG, "Error: ${error.message}") })

                        compositeDisposable.add(d1)
                    }
                }
            }

        val d2 = RxView.clicks(sizeTextView)
            .subscribe {
                AlertDialog.Builder(this)
                    .setTitle(R.string.download_to_sdcard_error_title)
                    .setMessage(R.string.download_to_sdcard_error_message)
                    .setPositiveButton(R.string.yes) { dialogInterface: DialogInterface, i: Int ->
//                        NewPipeSettings.resetDownloadFolders(this)

                        finish()
                    }
                    .setNegativeButton(R.string.cancel) { dialogInterface: DialogInterface, i: Int ->
                        dialogInterface.dismiss()
                        finish()
                    }
                    .create()
                    .show()

            }

        val d3 = RxView.clicks(dialogButton)
            .subscribe {
                AlertDialog.Builder(this)
                    .setTitle("Test AlertDialog")
                    .setMessage(R.string.download_to_sdcard_error_message)
                    .setPositiveButton("OK"){dialog, which ->
                        val crash = 12 / 0

                    }
                    .setNegativeButton("No"){dialog, which ->
                        dialog.dismiss()  // redundant, no dismiss, once you click, the dialog will dismiss.
                    }
                    .create()
                    .show()
            }
        compositeDisposable.addAll(d, d2, d3)
    }

    override fun onResume() {
        super.onResume()
        val d = RxView.clicks(button)
            .observeOn(Schedulers.io())
            .debounce(300, TimeUnit.MILLISECONDS)
            .flatMapSingle { obj -> getAllUsers() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list ->
                    adapter.resetList()
                    Log.d(TAG, "list: $list")
                    sizeTextView.text = list.size.toString()
                    adapter.setList(list)
                    adapter.notifyDataSetChanged()
                },
                { error -> Log.d(TAG, "Error: ${error.message}") })

        compositeDisposable.addAll(d)
    }

    private fun getAllUsers(): Single<List<User>> = RetrofitAPI.create()
        .getUsers()
        .subscribeOn(Schedulers.io())
        .onErrorResumeNext(Single.just(emptyList()))
        .doOnSuccess { Log.d(TAG, "users: $it") }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    companion object {
        const val TAG = "MainActivity"
    }


}
