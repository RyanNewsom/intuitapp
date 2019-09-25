package com.ryannewsom.intuitinterviewapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.ryannewsom.intuitinterviewapp.R
import com.ryannewsom.intuitinterviewapp.data.http.HttpRequestFailedException
import kotlinx.android.synthetic.main.main_activity.*
import java.lang.Exception

class MainActivity : AppCompatActivity(), LoadingHandler, ErrorHandler {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var errorDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val navController = findNavController(R.id.dashboard_nav_host)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onStop() {
        super.onStop()

        errorDialog?.dismiss()
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.dashboard_nav_host).navigateUp()
    }

    override fun showLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress_bar.visibility = View.GONE
    }

    override fun showError(exception: Exception, retryHandler: (() -> Unit)?) {
        val errorMessage = if (exception is HttpRequestFailedException) {
            getString(R.string.known_error, exception.statusCode)
        } else {
            getString(R.string.unknown_error)
        }
        errorDialog = AlertDialog.Builder(this)
            .setTitle(R.string.error)
            .setMessage(errorMessage)
            .setPositiveButton(R.string.retry) { view, _ ->
                view.dismiss()
                retryHandler?.invoke()
            }
            .setNegativeButton(R.string.dismiss) { view, _ ->
                view.dismiss()
            }.show()
    }
}
